package flashcards.controllers;

import flashcards.entities.CardCollection;

import flashcards.mapper.CardCollectionMapper;
import flashcards.requests.collectionRequests.CreateCollectionRequest;
import flashcards.requests.collectionRequests.UpdateCollectionRequest;
import flashcards.responses.CardCollectionResponse;
import flashcards.responses.MessageResponse;
import flashcards.services.interfaces.CollectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards/collection")
@CrossOrigin("*")
@AllArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @PostMapping("")
    public ResponseEntity<?> addCollection(@Valid @RequestBody CreateCollectionRequest request){

        CardCollection collection = collectionService.createCollection(request.getTitle(),
                request.getDescription(), request.isPublic());
        CardCollectionResponse response = CardCollectionMapper.toResponse(collection);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable Integer id){
        String message = collectionService.deleteById(id);
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCollection(@Valid @PathVariable Integer id, @RequestBody UpdateCollectionRequest request){

        CardCollection collection = collectionService.updateCollection(id, request.getTitle(),
                request.getDescription());

        CardCollectionResponse response = CardCollectionMapper.toResponse(collection);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollection(@PathVariable Integer id){
        CardCollection collection = collectionService.getCollectionById(id);

        CardCollectionResponse response = CardCollectionMapper.toResponse(collection);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCollections(){
        List<CardCollection> collections = collectionService.getLoggedUserAllCollections();
        List<CardCollectionResponse> response = CardCollectionMapper.toResponseList(collections);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomCollection(){
        List<CardCollection> collections = collectionService.getRandomCollections();

        List<CardCollectionResponse> response = CardCollectionMapper.toResponseList(collections);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/saved/{id}")
    public ResponseEntity<?> saveOtherCollection(@PathVariable Integer id){
        CardCollection collection = collectionService.saveOtherCollection(id);

        CardCollectionResponse response = CardCollectionMapper.toResponse(collection);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/title")
    public ResponseEntity<?> findCollectionByTitle(
            @RequestParam(name = "title", required = true) String title){
        List<CardCollection> collections = collectionService.findCollectionByTitle(title);

        List<CardCollectionResponse> response = CardCollectionMapper.toResponseList(collections);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/saved")
    public ResponseEntity<?> getOtherCollection(){
        List<CardCollection> collections = collectionService.getOtherSavedCollections();
        List<CardCollectionResponse> response = CardCollectionMapper.toResponseList(collections);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/saved/{collection_id}")
    public ResponseEntity<?> deleteOtherCollection(@PathVariable Integer collection_id){
       collectionService.deleteOtherSavedCollection(collection_id);
        return ResponseEntity.status(HttpStatus.OK).body("Your saved collection with ID " +
                collection_id + " was removed");
    }


}
