package flashcards;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.customexceptions.AccessDeniedException;
import flashcards.exceptions.customexceptions.CardNotFoundException;
import flashcards.repos.CardRepositoryJdbc;
import flashcards.repos.HashtagRepositoryJdbc;
import flashcards.services.CardServiceImpl;
import flashcards.services.CollectionServiceImpl;
import flashcards.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {
    @Mock
    private CardRepositoryJdbc cardRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CollectionServiceImpl collectionService;

    @Mock
    private HashtagRepositoryJdbc hashtagRepository;

    @InjectMocks
    private CardServiceImpl cardService;
    private  static User loggedUser;
    private static CardCollection cardCollection;
    private static Card cardWithId;
    private static Card cardWithoutId;
    private static List<String> hashtags;

    @BeforeAll
    public static void init(){
        loggedUser = User.builder().id(1).build();


        cardCollection = CardCollection.builder().id(1).build();

        hashtags =  Arrays.asList("hashtag1", "hashtag2");


        cardWithId = Card.builder()
                .id(1)
                .front("Front")
                .backside("Back")
                .favourite(false)
                .hashtags(hashtags)
                .cardCollection(cardCollection)
                .user(loggedUser)
                .build();

        cardWithoutId = Card.builder()
                .front("Front")
                .backside("Back")
                .favourite(false)
                .hashtags(hashtags)
                .cardCollection(cardCollection)
                .user(loggedUser)
                .build();

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCard() {

        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(collectionService.getCollectionById(1)).thenReturn(cardCollection);
        when(cardRepository.addCard(any(Card.class))).thenReturn(1);

        Card result = cardService.addCard("Front", "Back", 1, hashtags);

        assertEquals(1, result.getId());
        assertEquals("Front", result.getFront());
        assertEquals("Back", result.getBackside());
        assertEquals(hashtags, result.getHashtags());
        assertEquals(loggedUser, result.getUser());
        assertEquals(1, result.getCardCollection().getId());

        verify(hashtagRepository).addHashtag(1, "hashtag1");
        verify(hashtagRepository).addHashtag(1, "hashtag2");
    }

    @Test
    public void testAddCardWithoutHashtags() {

        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(collectionService.getCollectionById(1)).thenReturn(cardCollection);
        when(cardRepository.addCard(any(Card.class))).thenReturn(1);
        when(cardRepository.findById(anyInt())).thenReturn(java.util.Optional.of(cardWithoutId));

        Card createdCard = cardService.addCard("Front", "Back", 1, null);

        assertNotNull(createdCard);
        assertEquals(1, createdCard.getId());
        assertEquals("Front", createdCard.getFront());
        assertEquals("Back", createdCard.getBackside());
        assertFalse(createdCard.isFavourite());
        assertNull(createdCard.getHashtags());

        verify(cardRepository).addCard(argThat(c ->
                c.getFront().equals("Front") && c.getBackside().equals("Back") &&
                        c.getCardCollection().equals(cardCollection) && c.getUser().equals(loggedUser)
        ));

    }


    @Test
    public void testGetCardById() {

        when(cardRepository.findById(1)).thenReturn(Optional.of(cardWithId));
        when(hashtagRepository.findAllHashtags(1)).thenReturn(Arrays.asList("hashtag1", "hashtag2"));

        Card foundCard = cardService.getCardById(1);

        assertNotNull(foundCard);
        assertEquals(1, foundCard.getId());
        assertEquals(Arrays.asList("hashtag1", "hashtag2"), foundCard.getHashtags());

        verify(cardRepository).findById(1);
    }

    @Test
    public void testGetCardByIdCardNotFound() {

        when(cardRepository.findById(1)).thenReturn(Optional.empty());

        CardNotFoundException thrownException = assertThrows(CardNotFoundException.class,
                () -> cardService.getCardById(1), "Expected getCardById() to throw CardNotFoundException"
        );
        assertEquals("Card with ID 1 not found", thrownException.getMessage());

        verify(cardRepository).findById(1);
    }

    @Test
    public void testDeleteCard() {

        Card card = Card.builder().id(1).user(loggedUser).build();

        when(cardRepository.findById(1)).thenReturn(Optional.of(card));
        when(userService.getLoggedUser()).thenReturn(loggedUser);

        String result = cardService.deleteCard(1);

        assertEquals("Card with ID 1 was deleted successfully", result);
        verify(cardRepository).deleteById(1);
    }

    @Test
    public void testUpdateCard() {

        when(cardRepository.findById(1)).thenReturn(Optional.of(cardWithId));
        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(cardRepository.updateCard(any(Card.class), anyInt())).thenReturn(Optional.of(cardWithId));

        Card updatedCard = cardService.updateCard("Updated Front", "Updated Back", 1);

        assertNotNull(updatedCard);
        assertEquals("Updated Front", updatedCard.getFront());
        assertEquals("Updated Back", updatedCard.getBackside());
        verify(cardRepository).updateCard(updatedCard, 1);
    }



    @Test
    public void testGetAllCards() {
        List<Card> cards = Collections.singletonList(cardWithoutId);

        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(cardRepository.findAll(1)).thenReturn(cards);

        List<Card> retrievedCards = cardService.getAllCards();

        assertNotNull(retrievedCards);
        assertEquals(1, retrievedCards.size());
        assertEquals(loggedUser.getId(), retrievedCards.get(0).getUser().getId());
    }

    @Test
    public void testGetAllCardsByCollection() {

        cardCollection = CardCollection.builder().id(1).isPublic(true).build();

        Card card = Card.builder().cardCollection(cardCollection).user(loggedUser).build();
        List<Card> cards = List.of(card);

        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(cardRepository.findAllByCollection(1)).thenReturn(cards);

        List<Card> retrievedCards = cardService.getAllCardsByCollection(1);

        assertNotNull(retrievedCards);
        assertEquals(1, retrievedCards.size());
    }

    @Test
    public void testGetCardsByHashtag() {

        List<Integer> cardIdList = Arrays.asList(1);

        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(hashtagRepository.findAll("hashtag")).thenReturn(cardIdList);
        when(cardRepository.findById(1)).thenReturn(Optional.of(cardWithId));

        List<Card> retrievedCards = cardService.getCardsByHashtag("hashtag");

        assertNotNull(retrievedCards);
        assertEquals(1, retrievedCards.size());
    }

    @Test
    public void testAddHashtag() {

        when(cardRepository.findById(1)).thenReturn(Optional.of(cardWithId));
        when(userService.getLoggedUser()).thenReturn(loggedUser);

        Card updatedCard = cardService.addHashtag(Arrays.asList("hashtag1"), 1);

        assertNotNull(updatedCard);
        verify(hashtagRepository).addHashtag(1, "hashtag1");
    }

    @Test
    public void testDeleteHashtagSuccess() {

        when(cardRepository.findById(1)).thenReturn(Optional.of(cardWithId));
        when(userService.getLoggedUser()).thenReturn(loggedUser);

        String result = cardService.deleteHashtag(1, "hashtag1");

        assertEquals("Hashtag hashtag1 for card with ID 1 was removed successfully", result);
        verify(hashtagRepository).deleteHashtag(1, "hashtag1");
    }

    @Test
    public void testDeleteHashtagCardNotFound() {
        when(cardRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CardNotFoundException.class, () ->
                cardService.deleteHashtag(1, "hashtag1")
        );

        assertEquals("Card with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testDeleteHashtagAccessDenied() {

        User cardUser = User.builder().id(2).build();
        Card card = Card.builder().id(2).user(cardUser).build();


        when(cardRepository.findById(1)).thenReturn(Optional.of(card));
        when(userService.getLoggedUser()).thenReturn(loggedUser);

        Exception exception = assertThrows(AccessDeniedException.class, () ->
                cardService.deleteHashtag(1, "hashtag1")
        );

        assertEquals("You do not have permission to change this card.", exception.getMessage());
    }

}
