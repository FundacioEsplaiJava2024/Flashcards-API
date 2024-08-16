package flashcards.controllers;

import flashcards.entities.CardCollection;

import flashcards.requests.collectionRequests.CreateCollectionRequest;
import flashcards.requests.collectionRequests.UpdateCollectionRequest;
import flashcards.services.interfaces.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/cardCollection")
@CrossOrigin("*")
@AllArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @PostMapping("")
    public ResponseEntity<?> addCollection(@RequestBody CreateCollectionRequest request){

        CardCollection cardCollection = collectionService.createCollection(request.getTitle(),
                request.getDescription(), request.isPublic());
        return ResponseEntity.status(HttpStatus.CREATED).body(cardCollection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable Integer id){
        collectionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("CardCollection deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCollection(@PathVariable Integer id, @RequestBody UpdateCollectionRequest request){

        CardCollection cardCollection = collectionService.updateCollection(id, request.getTitle(),
                request.getDescription(), request.isPublic());
        return ResponseEntity.status(HttpStatus.OK).body(cardCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollection(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.getCollectionById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCollections(){
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.getAllCollections());
    }

    /*
    @GetMapping("cards/{id}")
    public ResponseEntity<?> getAllCardsByCollection(Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.getAllCardsByCollection(id));
    }*/
}
