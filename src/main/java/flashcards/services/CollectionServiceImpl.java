package flashcards.services;

import flashcards.entities.Collection;
import flashcards.entities.User;
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
    public Collection createCollection(String title, String description, boolean isPublic){
        // get current logged user
        User loggedUser = userService.getLoggedUser();

        Collection collection  = Collection.builder()
                .title(title)
                .description(description)
                .isPublic(isPublic)
                .createdAt(LocalDateTime.now())
                .user(loggedUser)
                .build();
         collectionRepository.addCollection(collection);
         return collection;
    }

    // creating a 'default' collection for all those cards that doesn't have any specified collection
    @Override
    public void createDefaultCollection(){
       User loggedUser = userService.getLoggedUser();
       Collection defaultCollection = Collection.builder()
               .title("Default")
               .createdAt(LocalDateTime.now())
               .isPublic(false)
               .user(loggedUser)
               .build();
        collectionRepository.addCollection(defaultCollection);

    }

    @Override
    public void deleteById(Integer id){
        Optional<Collection> collectionOpt = collectionRepository.findById(id);
        if(!collectionOpt.isPresent()){
            //throw an exception
        }
       // if(collectionOpt.get().getUser().getId() == userService.getLoggedUser().getId()){
            collectionRepository.deleteById(id);
       // }
    }

    @Override
    public Collection updateCollection(Integer id, String title, String description, boolean isPublic) {
        //find collection with the id, Optional because it can be null.
        Optional<Collection> collectionOpt = collectionRepository.findById(id);
        User loggerUser = userService.getLoggedUser();
        if(!collectionOpt.isPresent()){
            //throw an exception if the collection wasn't found
        }
        // collectionOpt.get() is the class Collection
        collectionOpt.get().setUser(loggerUser);
            Optional<Collection> updatedCollection = collectionOpt;
            //to change title and description
            // do in case description and title aren't null and description and title aren't blank
            if(description != null && title != null && !description.trim().isEmpty() && !title.trim().isEmpty()) {
                        collectionOpt.get().setTitle(title);
                        collectionOpt.get().setDescription(description);
                        updatedCollection = collectionRepository.updateCollection(collectionOpt.get(), id);
                }
            //to change description

            // do in case description isn't null and not empty
            else if(description != null && !description.trim().isEmpty()){
                 collectionOpt.get().setDescription(description);
                 updatedCollection = collectionRepository.updateCollection(collectionOpt.get(), id);
             }

            //to change title
            // do in case title isn't null and not empty
            else if(title != null && !title.trim().isEmpty()) {
                    collectionOpt.get().setTitle(title);
                    updatedCollection = collectionRepository.updateCollection(collectionOpt.get(), id);
            }
            //exception when the title is null

        return updatedCollection.get();

    }

    @Override
    public Collection getCollectionById(Integer id){

        Optional<Collection> collectionOpt = collectionRepository.findById(id);

        if(collectionOpt.get().getUser() == userService.getLoggedUser()) {
            if (!collectionOpt.isPresent()) {
                //throws an exception
            }

        }
        return collectionOpt.get();
    }

    @Override
    public List<Collection> getAllCollections(){
        User loggedUser = userService.getLoggedUser();
        return collectionRepository.findAll(loggedUser.getId());
    }

    /*
    @Override
    public List<Card> getAllCardsByCollection(Integer id){
        Optional<Collection> collectionOpt = collectionRepository.findById(id);

        List<Card> cardsByCollection = null;
        if(collectionOpt.get().getUser() == userService.getLoggedUser()){
            cardsByCollection = collectionOpt.get().getCards();
        }
        return cardsByCollection;
    }*/



}
