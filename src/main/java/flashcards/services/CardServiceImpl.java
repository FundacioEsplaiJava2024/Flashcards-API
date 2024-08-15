package flashcards.services;

import flashcards.entities.Card;
import flashcards.entities.Collection;
import flashcards.entities.User;
import flashcards.repos.interfaces.CardRepository;
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
    public Card addCard(String front, String backside, Integer collection_id) {
        User loggedUser = userService.getLoggedUser();
        Collection collection = collectionService.getCollectionById(collection_id);

        Card card = Card.builder()
                .front(front)
                .backside(backside)
                .createdAt(LocalDateTime.now())
                .favourite(false)
                .collection(collection)
                .user(loggedUser)
                .build();

        cardRepository.addCard(card);
        return card;
    }

    @Override
    public Card getCardById(Integer id) {
        Optional<Card> cardOpt = cardRepository.findById(id);
            if (!cardOpt.isPresent()) {
                //throws an exception
            }
        return cardOpt.get();
    }

    @Override
    public void deleteCard(Integer id) {
        Optional<Card> cardOpt = cardRepository.findById(id);
        if(!cardOpt.isPresent()){
            //throw an exception
        }
        if(cardOpt.get().getUser().getId() == userService.getLoggedUser().getId()){
        cardRepository.deleteById(id);
        }
    }

    @Override
    public Card updateCard(String frontside, String backside, Integer id) {

        Optional<Card> cardOpt = cardRepository.findById(id);
        User loggerUser = userService.getLoggedUser();
        if(!cardOpt.isPresent()){
            //throw an exception if the card wasn't found
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

        return updatedCard.get();
    }

    @Override
    public List<Card> getAllCards() {
        User loggedUser = userService.getLoggedUser();
        return cardRepository.findAll(loggedUser.getId());
    }

    @Override
    public List<Card> getAllCardsByCollection(Integer collection_id) {
        return cardRepository.findAllByCollection(collection_id);
    }
}
