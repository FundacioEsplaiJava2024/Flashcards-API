package flashcards.services;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.AccessDeniedException;
import flashcards.mapper.CardMapper;
import flashcards.repos.interfaces.CardRepository;
import flashcards.responses.CardResponse;
import flashcards.services.interfaces.CardService;
import flashcards.services.interfaces.CollectionService;
import flashcards.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserService userService;
    private final CollectionService collectionService;

    @Override
    public CardResponse addCard(String front, String backside, Integer collection_id) {
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

        cardRepository.addCard(card);
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
        Optional<Card> cardOpt = cardRepository.findById(id);
        User loggedUser = userService.getLoggedUser();
        if(!cardOpt.isPresent()){
            //throw an exception
        }
        if(cardOpt.get().getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to delete this card.");
        }
        cardRepository.deleteById(id);

    }

    @Override
    public CardResponse updateCard(String frontside, String backside, Integer id) {

        Optional<Card> cardOpt = cardRepository.findById(id);
        User loggedUser = userService.getLoggedUser();
        if(!cardOpt.isPresent()){
            //throw an exception if the card wasn't found
        }
        if(cardOpt.get().getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this card.");
        }

        Optional<Card> updatedCard = cardOpt;
        //to change frontside and backside
        if(backside != null && frontside != null && !backside.trim().isEmpty() && !frontside.trim().isEmpty()) {
            cardOpt.get().setFront(frontside);
            cardOpt.get().setBackside(backside);
            updatedCard = cardRepository.updateCard(cardOpt.get(), id);
        }
        //to change description

        // do in case backside isn't null and not empty
        else if(backside != null && !backside.trim().isEmpty()){
            cardOpt.get().setBackside(backside);
            updatedCard = cardRepository.updateCard(cardOpt.get(), id);
        }

        //to change frontside
        // do in case frinte isn't null and not empty
        else if(frontside != null && !frontside.trim().isEmpty()) {
           cardOpt.get().setFront(frontside);
            updatedCard = cardRepository.updateCard(cardOpt.get(), id);
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
        User loggedUser = userService.getLoggedUser();
        Optional<Card> cardOpt = cardRepository.findById(id);
        if(!cardOpt.isPresent()){
            //throw an exception if the card wasn't found
        }
        boolean favouriteCard;

        //id favourite is false, then change it to true
        if(!cardOpt.get().isFavourite()){
            favouriteCard = true;
        }else{
            //if favourite is true, change it to false
            favouriteCard = false;
        }

        Optional<Card> card = cardRepository.changeFavourite(favouriteCard, id);
        return CardMapper.toResponse(card.get());
    }

    // random cards
    @Override
    public List<CardResponse> getRandomCards(){
        List<Card> randomCards = cardRepository.getRandomCards();
        return CardMapper.toResponseList(randomCards);
    }

    @Override
    public List<CardResponse> getAllFavourite(){
        User user = userService.getLoggedUser();
        List<Card> favouriteCards = cardRepository.findAllFavourite(user.getId());
        return CardMapper.toResponseList(favouriteCards);
    }
}
