package flashcards.controllers;

import flashcards.entities.Card;
import flashcards.requests.card.CreateCardRequest;
import flashcards.requests.card.UpdateCardRequest;
import flashcards.services.interfaces.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/flashcards/card")
@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("")
    public ResponseEntity<?> addCard(@RequestBody CreateCardRequest request){

        Card card = cardService.addCard(request.getFront(),
                request.getBackside(), request.getCollection_id());
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Integer id){
        cardService.deleteCard(id);
        return ResponseEntity.status(HttpStatus.OK).body("Card deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCard(@PathVariable Integer id, @RequestBody UpdateCardRequest request){

        Card card = cardService.updateCard(request.getFront(),
                request.getBackside(), id);
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCards(){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getAllCards());
    }

    @GetMapping("/collection/{collection_id}")
    public ResponseEntity<?> getAllCardsByCollection(@PathVariable Integer collection_id){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getAllCardsByCollection(collection_id));
    }
}
