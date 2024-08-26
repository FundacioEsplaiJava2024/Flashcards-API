package flashcards.services;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.customexceptions.AccessDeniedException;
import flashcards.exceptions.customexceptions.CardNotFoundException;
import flashcards.repos.interfaces.CardRepository;
import flashcards.repos.interfaces.HashtagRepository;
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
    public Card addCard(String front, String backside, Integer collection_id, List<String> hashtags) {
        User loggedUser = userService.getLoggedUser();
        CardCollection cardCollection = collectionService.getCollectionById(collection_id);

        Card card = Card.builder()
                .front(front)
                .backside(backside)
                .createdAt(LocalDateTime.now())
                .favourite(false)
                .cardCollection(cardCollection)
                .user(loggedUser)
                .build();

        Integer cardId = cardRepository.addCard(card);

        card.setId(cardId);
        if(hashtags != null) {
            card.setHashtags(hashtags);
            hashtags.forEach(hashtag -> hashtagRepository.addHashtag(cardId, hashtag));
        }
        return card;
    }



    @Override
    public Card getCardById(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));

        List<String> hashtags = hashtagRepository.findAllHashtags(id);
        if(hashtags != null) {
            card.setHashtags(hashtags);
        }
        return card;
    }

    @Override
    public String deleteCard(Integer id) {

        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to delete this card.");
        }
        cardRepository.deleteById(id);

        return "Card with ID " + id + " was deleted successfully";

    }

    @Override
    public Card updateCard(String frontside, String backside, Integer id) {

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

        return updatedCard.get();
    }

    @Override
    public List<Card> getAllCards() {
        User loggedUser = userService.getLoggedUser();
        List<Card> cards =  cardRepository.findAll(loggedUser.getId());
        return cards;
    }

    @Override
    public List<Card> getAllCardsByCollection(Integer collection_id) {
        User loggedUser = userService.getLoggedUser();
        List<Card> cards = cardRepository.findAllByCollection(collection_id);

        if(!cards.get(0).getCardCollection().isPublic() || cards.get(0).getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("This collection is private");
        }
        return cards;
    }

    //change favourite
    @Override
    public Card changeFavourite(Integer id){
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + id + " not found"));

        User loggedUser = userService.getLoggedUser();
        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You can add this cad to favourite, because it's not yours.");
        }

        boolean favouriteCard;

        //id favourite is false, then change it to true
        if(!card.isFavourite()){
            favouriteCard = true;
        }else{
            //if favourite is true, change it to false
            favouriteCard = false;
        }

        Optional<Card> cardChanged = cardRepository.changeFavourite(favouriteCard, id);
        return cardChanged.get();
    }

    // random cards
    @Override
    public List<Card> getRandomCards(){
        List<Card> randomCards = cardRepository.getRandomCards();
        return randomCards;
    }

    @Override
    public List<Card> getAllFavourite(){
        User loggedUser = userService.getLoggedUser();
        List<Card> favouriteCards = cardRepository.findAllFavourite(loggedUser.getId());
        return favouriteCards;
    }

    @Override
    public List<Card> getCardsByHashtag(String hashtag){
        User loggedUser = userService.getLoggedUser();
        List<Integer> cardIdListWithHashtag = hashtagRepository.findAll(hashtag);
        List<Card> cards = new ArrayList<>();

        for (Integer integer : cardIdListWithHashtag) {
            Optional<Card> card = cardRepository.findById(integer);
            if(card.get().getCardCollection().isPublic() || card.get().getUser().getId() == loggedUser.getId()) {
                cards.add(card.get());
            }
        }

        return  cards;
    }

    @Override
    public Card addHashtag(List<String> hashtag, Integer card_id){
        Card card = cardRepository.findById(card_id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + card_id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        hashtag.forEach(hashtagItem -> {
            hashtagRepository.addHashtag(card_id, hashtagItem);
        });

        return card;
    }


    @Override
    public String deleteHashtag(Integer card_id, String hashtag){

        Card card = cardRepository.findById(card_id).orElseThrow(
                () -> new CardNotFoundException("Card with ID " + card_id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if(card.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        hashtagRepository.deleteHashtag(card_id, hashtag);
        return "Hashtag " + hashtag + " for card with ID " + card_id + " was removed successfully";
    }
}
