package flashcards.services;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.AccessDeniedException;
import flashcards.exceptions.CardNotFoundException;
import flashcards.mapper.CardMapper;
import flashcards.repos.interfaces.CardRepository;
import flashcards.repos.interfaces.HashtagRepository;
import flashcards.responses.CardResponse;
import flashcards.services.interfaces.CardService;
import flashcards.services.interfaces.CollectionService;
import flashcards.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserService userService;
    private final CollectionService collectionService;
    private final HashtagRepository hashtagRepository;

    @Override
    public CardResponse addCard(String front, String backside, Integer collection_id, List<String> hashtags) {
        User loggedUser = userService.getLoggedUser();
        CardCollection cardCollection = collectionService.getCollectionByIdForCards(collection_id);

        Card card = Card.builder()
                .front(front)
                .backside(backside)
                .createdAt(LocalDateTime.now())
                .favourite(false)
                .cardCollection(cardCollection)
                .user(loggedUser)
                .build();

        Integer cardId = cardRepository.addCard(card);


        if(hashtags != null) {
            card.setHashtags(hashtags);
            hashtags.forEach(hashtag -> hashtagRepository.addHashtag(cardId, hashtag));
        }
        return CardMapper.toResponse(card);
    }

    @Override
    public CardResponse getCardById(Integer id) {
        Optional<Card> cardOpt = cardRepository.findById(id);
            if (!cardOpt.isPresent()) {
                //throws an exception
            }
        return CardMapper.toResponse(cardOpt.get());
    }

    @Override
    public void deleteCard(Integer id) {

        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to delete this card.");
        }
        cardRepository.deleteById(id);

    }

    @Override
    public CardResponse updateCard(String frontside, String backside, Integer id) {

        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));
        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        Optional<Card> updatedCard = Optional.of(card);
        //to change frontside and backside
        if(backside != null && frontside != null && !backside.trim().isEmpty() && !frontside.trim().isEmpty()) {
            card.setFront(frontside);
            card.setBackside(backside);
            updatedCard = cardRepository.updateCard(card, id);
        }
        //to change description

        // do in case backside isn't null and not empty
        else if(backside != null && !backside.trim().isEmpty()){
            card.setBackside(backside);
            updatedCard = cardRepository.updateCard(card, id);
        }

        //to change frontside
        // do in case frinte isn't null and not empty
        else if(frontside != null && !frontside.trim().isEmpty()) {
           card.setFront(frontside);
            updatedCard = cardRepository.updateCard(card, id);
        }
        //exception when the title is null

        return CardMapper.toResponse(updatedCard.get());
    }

    @Override
    public List<CardResponse> getAllCards() {
        User loggedUser = userService.getLoggedUser();
        List<Card> cards =  cardRepository.findAll(loggedUser.getId());
        return CardMapper.toResponseList(cards);
    }

    @Override
    public List<CardResponse> getAllCardsByCollection(Integer collection_id) {
        User loggedUser = userService.getLoggedUser();
        List<Card> cards = cardRepository.findAllByCollection(collection_id);

        if(!cards.get(0).getCardCollection().isPublic() || cards.get(0).getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("This collection is private");
        }
        return CardMapper.toResponseList(cards);
    }

    //change favourite
    @Override
    public CardResponse changeFavourite(Integer id){
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));
        User loggedUser = userService.getLoggedUser();

        boolean favouriteCard;

        //id favourite is false, then change it to true
        if(!card.isFavourite()){
            favouriteCard = true;
        }else{
            //if favourite is true, change it to false
            favouriteCard = false;
        }

        Optional<Card> cardChanged = cardRepository.changeFavourite(favouriteCard, id);
        return CardMapper.toResponse(cardChanged.get());
    }

    // random cards
    @Override
    public List<CardResponse> getRandomCards(){
        List<Card> randomCards = cardRepository.getRandomCards();
        return CardMapper.toResponseList(randomCards);
    }

    @Override
    public List<CardResponse> getAllFavourite(){
        User loggedUser = userService.getLoggedUser();
        List<Card> favouriteCards = cardRepository.findAllFavourite(loggedUser.getId());
        return CardMapper.toResponseList(favouriteCards);
    }

    @Override
    public List<CardResponse> getCardsByHashtag(String hashtag){
        User loggedUser = userService.getLoggedUser();
        List<Integer> cardIdListWithHashtag = hashtagRepository.findAll(hashtag);
        List<Card> cards = new ArrayList<>();

        for (Integer integer : cardIdListWithHashtag) {
            Optional<Card> card = cardRepository.findById(integer);
            if(card.get().getCardCollection().isPublic() || card.get().getUser().getId() == loggedUser.getId()) {
                cards.add(card.get());
            }
        }

        return  CardMapper.toResponseList(cards);
    }

    @Override
    public CardResponse addHashtag(List<String> hashtag, Integer card_id){
        Card card = cardRepository.findById(card_id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + card_id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        hashtag.forEach(hashtagItem -> {
            hashtagRepository.addHashtag(card_id, hashtagItem);
        });

        return CardMapper.toResponse(card);
    }


    @Override
    public void deleteHashtag(Integer card_id, String hashtag){

        Card card = cardRepository.findById(card_id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + card_id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        hashtagRepository.deleteHashtag(card_id, hashtag);
    }
}
