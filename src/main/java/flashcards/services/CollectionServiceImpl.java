package flashcards.services;

import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.AccessDeniedException;
import flashcards.mapper.CardCollectionMapper;
import flashcards.repos.interfaces.CollectionRepository;
import flashcards.responses.CardCollectionResponse;
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
    public CardCollectionResponse createCollection(String title, String description, boolean isPublic){
        // get current logged user
        User loggedUser = userService.getLoggedUser();

        CardCollection cardCollection = CardCollection.builder()
                .title(title)
                .description(description)
                .isPublic(isPublic)
                .createdAt(LocalDateTime.now())
                .user(loggedUser)
                .build();
        collectionRepository.addCollection(cardCollection);

        return CardCollectionMapper.toResponse(cardCollection);

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
    public void deleteById(Integer id){
        Optional<CardCollection> collectionOpt = collectionRepository.findById(id);

        if (!collectionOpt.isPresent()) {
            //throws an exception
        }
        if(collectionOpt.get().getUser().getId() != userService.getLoggedUser().getId()) {
            throw new AccessDeniedException("You do not have permission to delete this collection.");
        }
            collectionRepository.deleteById(id);

    }

    @Override
    public CardCollectionResponse updateCollection(Integer id, String title, String description) {
        //find cardCollection with the id, Optional because it can be null.
        Optional<CardCollection> collectionOpt = collectionRepository.findById(id);

        User loggerUser = userService.getLoggedUser();
        if(!collectionOpt.isPresent()){
            //throw an exception if the cardCollection wasn't found
        }

        if(collectionOpt.get().getUser().getId() != loggerUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this collection.");
        }
            Optional<CardCollection> updatedCollection = collectionOpt;

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

        return CardCollectionMapper.toResponse(updatedCollection.get());

    }

    @Override
    public CardCollectionResponse getCollectionById(Integer id){

        Optional<CardCollection> collectionOpt = collectionRepository.findById(id);

        User loggedUser = userService.getLoggedUser();
        CardCollection collection = collectionOpt.get();

        if( !collection.isPublic() && collectionOpt.get().getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to access this collection.");
        }
        return CardCollectionMapper.toResponse(collection);
    }

    @Override
    public List<CardCollectionResponse> getLoggedUserAllCollections(){
        User loggedUser = userService.getLoggedUser();
        List<CardCollection> collections = collectionRepository.findAll(loggedUser.getId());
        return CardCollectionMapper.toResponseList(collections);
    }


    @Override
    public CardCollection getCollectionByIdForCards(Integer id){

        Optional<CardCollection> collectionOpt = collectionRepository.findById(id);

        if (!collectionOpt.isPresent()) {
            //throws an exception
        }
        User loggedUser = userService.getLoggedUser();
        CardCollection collection = collectionOpt.get();

        if(collectionOpt.get().getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to access this collection.");
        }

        return collection;
    }

    @Override
    public CardCollectionResponse changePublicStatus(Integer id, boolean isPublic){

        Optional<CardCollection> collectionOpt = collectionRepository.findById(id);

        if (!collectionOpt.isPresent()) {
            //throws an exception
        }
        User loggedUser = userService.getLoggedUser();

        CardCollection collection = collectionOpt.get();

        if(collection.getUser().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("You do not have permission to change this collection.");
        }
        collection.setPublic(isPublic);

        CardCollection cardCollection = collectionRepository.changePublicStatus(id, collection);

        return CardCollectionMapper.toResponse(cardCollection);

    }

    @Override
    public List<CardCollectionResponse> getRandomCollections(){
        User loggedUser = userService.getLoggedUser();
        List<CardCollection> cards = collectionRepository.getRandomCollections(loggedUser.getId());
        return CardCollectionMapper.toResponseList(cards);
    }

    @Override
    public CardCollectionResponse saveOtherCollection(Integer collection_id){
        User loggedUser = userService.getLoggedUser();
        Optional<CardCollection> collectionOpt = collectionRepository.findById(collection_id);
                collectionRepository.saveOtherCollection(collection_id, loggedUser.getId());

        return CardCollectionMapper.toResponse(collectionOpt.get());
    }

}
