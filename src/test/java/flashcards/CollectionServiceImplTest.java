package flashcards;

import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.customexceptions.AccessDeniedException;
import flashcards.exceptions.customexceptions.CollectionNotFoundException;
import flashcards.repos.CollectionRepositoryJdbc;
import flashcards.services.CollectionServiceImpl;
import flashcards.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CollectionServiceImplTest {

    @Mock
    private CollectionRepositoryJdbc collectionRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private CollectionServiceImpl collectionService;

    private static User user;
    private static CardCollection cardCollection;

    @BeforeAll
    public static void init() {
        user = User.builder()
                .id(1)
                .username("testUser")
                .build();

        cardCollection = CardCollection.builder()
                .id(1)
                .title("TestCollection")
                .description("TestDescription")
                .isPublic(true)
                .user(user)
                .build();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCollectionSuccess() {

        when(userService.getLoggedUser()).thenReturn(user);
        when(collectionRepository.addCollection(any(CardCollection.class))).thenReturn(1);

        CardCollection result = collectionService.createCollection("TestCollection", "TestDescription", true);

        assertEquals("TestCollection", result.getTitle());
        assertEquals("TestDescription", result.getDescription());
        assertTrue(result.isPublic());
        assertEquals(1, result.getId());
        assertEquals(user, result.getUser());

        verify(collectionRepository).addCollection(any(CardCollection.class));
    }



    @Test
    public void testCreateDefaultCollection_Success() {
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        collectionService.createDefaultCollection("testUser");

        verify(collectionRepository).addCollection(any(CardCollection.class));
    }

    @Test
    public void testDeleteById_Success() {
        when(collectionRepository.findById(1)).thenReturn(Optional.of(cardCollection));
        when(userService.getLoggedUser()).thenReturn(user);

        String result = collectionService.deleteById(1);

        assertEquals("Collection with ID 1 was deleted successfully", result);
        verify(collectionRepository).deleteById(1);
    }

    @Test
    public void testDeleteByIdCollectionNotFound() {
        when(collectionRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CollectionNotFoundException.class, () -> collectionService.deleteById(1));
    }

    @Test
    public void testDeleteByIdAccessDenied() {
        User anotherUser = User.builder().id(2).username("anotherUser").build();
        cardCollection.setUser(anotherUser);

        when(collectionRepository.findById(1)).thenReturn(Optional.of(cardCollection));
        when(userService.getLoggedUser()).thenReturn(user);

        assertThrows(AccessDeniedException.class, () -> collectionService.deleteById(1));
    }
}
