package flashcards.controllers;

import flashcards.entities.CardCollection;

import flashcards.requests.collectionRequests.CreateCollectionRequest;
import flashcards.requests.collectionRequests.UpdateCollectionRequest;
import flashcards.responses.CardCollectionResponse;
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

        CardCollectionResponse response = collectionService.createCollection(request.getTitle(),
                request.getDescription(), request.isPublic());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable Integer id){
        collectionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("CardCollection deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCollection(@Valid @PathVariable Integer id, @RequestBody UpdateCollectionRequest request){

        CardCollectionResponse response = collectionService.updateCollection(id, request.getTitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollection(@PathVariable Integer id){
        CardCollectionResponse response = collectionService.getCollectionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCollections(){
        List<CardCollectionResponse> response = collectionService.getLoggedUserAllCollections();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
