package flashcards.controllers;

import flashcards.entities.Card;
import flashcards.requests.card.CreateCardRequest;
import flashcards.requests.card.UpdateCardRequest;
import flashcards.responses.CardResponse;
import flashcards.services.interfaces.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/flashcards/card")
@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class CardController {

    private final CardService cardService;


    @PostMapping("")
    public ResponseEntity<?> addCard( @Valid @RequestBody CreateCardRequest request){

        CardResponse response = cardService.addCard(request.getFront(),
                request.getBackside(), request.getCollection_id(), request.getHashtags());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Integer id){
        cardService.deleteCard(id);
        return ResponseEntity.status(HttpStatus.OK).body("Card deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCard(@PathVariable Integer id, @RequestBody UpdateCardRequest request){

        CardResponse response = cardService.updateCard(request.getFront(),
                request.getBackside(), id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable Integer id){
        CardResponse response = cardService.getCardById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCards(){
        List<CardResponse> response = cardService.getAllCards();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/collection/{collection_id}")
    public ResponseEntity<?> getAllCardsByCollection(@PathVariable Integer collection_id){
        List<CardResponse> response = cardService.getAllCardsByCollection(collection_id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/favourite/{id}")
    public ResponseEntity<?> changeFavouriteCard(@PathVariable Integer id){
        CardResponse response = cardService.changeFavourite(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomCards(){
        List<CardResponse> response = cardService.getRandomCards();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //get all favourite
    @GetMapping("/favourite")
    public ResponseEntity<?> getFavouriteCards(){
        List<CardResponse> response = cardService.getAllFavourite();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/hashtag")
    public ResponseEntity<?> getCardsByHashtag(
            @RequestParam(name = "hashtag", required = true) String hashtag){
        List<CardResponse> response = cardService.getCardsByHashtag(hashtag);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
