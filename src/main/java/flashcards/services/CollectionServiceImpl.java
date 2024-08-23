package flashcards.services;

import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.customexceptions.AccessDeniedException;
import flashcards.exceptions.customexceptions.CollectionNotFoundException;
import flashcards.repos.interfaces.CollectionRepository;
import flashcards.services.interfaces.CollectionService;
import flashcards.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserService userService;


    @Override
    public CardCollection createCollection(String title, String description, boolean isPublic){
        // get current logged user
        User loggedUser = userService.getLoggedUser();

        CardCollection cardCollection = CardCollection.builder()
                .title(title)
                .description(description)
                .isPublic(isPublic)
                .createdAt(LocalDateTime.now())
                .user(loggedUser)
                .build();
        Integer collection_id = collectionRepository.addCollection(cardCollection);

        cardCollection.setId(collection_id);
        return cardCollection;

    }

    //not implemented until email verification
    // creating a 'default' cardCollection for all those cards that doesn't have any specified cardCollection
    @Override
    public void createDefaultCollection(String username){
       User thisUser =  userService.getUserByUsername(username);
       CardCollection defaultCardCollection = CardCollection.builder()
               .title("Default")
               .createdAt(LocalDateTime.now())
               .isPublic(false)
               .user(thisUser)
               .build();
        collectionRepository.addCollection(defaultCardCollection);

    }

    @Override
    public String deleteById(Integer id){

        CardCollection collection = collectionRepository.findById(id).orElseThrow(
                () -> new CollectionNotFoundException("Collection with ID " + id + " not found"));

        User user = userService.getLoggedUser();
        if(collection.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("You do not have permission to delete this collection.");
        }
            collectionRepository.deleteById(id);
        return "Collection with ID " + id + " was deleted successfully";
    }

    @Override
    public CardCollection updateCollection(Integer id, String title, String description) {

        CardCollection collection = collectionRepository.findById(id).orElseThrow(
                () -> new CollectionNotFoundException("Collection with ID " + id + " not found"));

        User loggerUser = userService.getLoggedUser();


        if(collection.getUser().getId() != loggerUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this collection.");
        }
            Optional<CardCollection> updatedCollection = Optional.of(collection);

            //to change title and description
            // do in case description and title aren't null and description and title aren't blank
            if(description != null && title != null && !description.trim().isEmpty() && !title.trim().isEmpty()) {
                        collection.setTitle(title);
                        collection.setDescription(description);
                        updatedCollection = collectionRepository.updateCollection(collection, id);
                }

            //to change description
            // do in case description isn't null and not empty
            else if(description != null && !description.trim().isEmpty()){
                 collection.setDescription(description);
                 updatedCollection = collectionRepository.updateCollection(collection, id);
             }

            //to change title
            // do in case title isn't null and not empty
            else if(title != null && !title.trim().isEmpty()) {
                    collection.setTitle(title);
                    updatedCollection = collectionRepository.updateCollection(collection, id);
            }
            //exception when the title is null

        return updatedCollection.get();

    }

    @Override
    public CardCollection getCollectionById(Integer id){

        CardCollection collection = collectionRepository.findById(id).orElseThrow(
                () -> new CollectionNotFoundException("Collection with ID " + id + " not found"));

        User loggedUser = userService.getLoggedUser();

        if( !collection.isPublic() && collection.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to access this collection.");
        }
        return collection;
    }

    @Override
    public List<CardCollection> getLoggedUserAllCollections(){
        User loggedUser = userService.getLoggedUser();
        List<CardCollection> collections = collectionRepository.findAll(loggedUser.getId());
        return collections;
    }


    @Override
    public CardCollection changePublicStatus(Integer id, boolean isPublic){

        CardCollection collection = collectionRepository.findById(id).orElseThrow(
                () -> new CollectionNotFoundException("Collection with ID " + id + " not found"));
        User loggedUser = userService.getLoggedUser();


        if(collection.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this collection.");
        }
        collection.setPublic(isPublic);

        CardCollection cardCollection = collectionRepository.changePublicStatus(id, collection);

        return cardCollection;

    }

    @Override
    public List<CardCollection> getRandomCollections(){
        User loggedUser = userService.getLoggedUser();
        List<CardCollection> collections = collectionRepository.getRandomCollections(loggedUser.getId());
        return collections;
    }

    @Override
    public CardCollection saveOtherCollection(Integer collection_id){
        User loggedUser = userService.getLoggedUser();
        CardCollection collection = collectionRepository.findById(collection_id).orElseThrow(
                () -> new CollectionNotFoundException("Collection with ID " + collection_id + " not found"));
                collectionRepository.saveOtherCollection(collection_id, loggedUser.getId());

        return collection;
    }

    @Override
    public List<CardCollection> findCollectionByTitle(String title){
        List<CardCollection> collections = collectionRepository.findByTitle(title);

        if(collections.isEmpty()){
            throw new CollectionNotFoundException("There are no collections");
        }

        return collections;
    }

    @Override
    public List<CardCollection> getOtherSavedCollections(){
        User user = userService.getLoggedUser();
        List<CardCollection> collections = collectionRepository.getUserOtherCollection(user.getId());

        return collections;
    }

    @Override
    public void deleteOtherSavedCollection(Integer collection_id){
        collectionRepository.deleteOtherCollectionById(collection_id);
    }

}