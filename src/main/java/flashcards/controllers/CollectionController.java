package flashcards.controllers;

import flashcards.entities.Collection;

import flashcards.requests.collectionRequests.CreateCollectionRequest;
import flashcards.requests.collectionRequests.UpdateCollectionRequest;
import flashcards.services.interfaces.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/collection")
@CrossOrigin("*")
@AllArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @PostMapping("")
    public ResponseEntity<?> addCollection(@RequestBody CreateCollectionRequest request){

        Collection collection = collectionService.createCollection(request.getTitle(),
                request.getDescription(), request.isPublic());
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable Integer id){
        collectionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Collection deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCollection(@PathVariable Integer id, @RequestBody UpdateCollectionRequest request){

        Collection collection = collectionService.updateCollection(id, request.getTitle(),
                request.getDescription(), request.isPublic());
        return ResponseEntity.status(HttpStatus.OK).body(collection);
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
