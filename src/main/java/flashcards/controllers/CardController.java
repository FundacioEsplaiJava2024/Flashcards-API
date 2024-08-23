package flashcards.controllers;

import flashcards.entities.Card;
import flashcards.mapper.CardMapper;
import flashcards.requests.card.CreateCardRequest;
import flashcards.requests.card.HashtagsRequest;
import flashcards.requests.card.UpdateCardRequest;
import flashcards.responses.CardResponse;
import flashcards.responses.MessageResponse;
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

        Card card = cardService.addCard(request.getFront(),
                request.getBackside(), request.getCollection_id(), request.getHashtags());
        CardResponse response = CardMapper.toResponse(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Integer id){
        String message = cardService.deleteCard(id);
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCard(@PathVariable Integer id, @RequestBody UpdateCardRequest request){

        Card card= cardService.updateCard(request.getFront(),
                request.getBackside(), id);
        CardResponse response = CardMapper.toResponse(card);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable Integer id){
        Card card = cardService.getCardById(id);
        CardResponse response = CardMapper.toResponse(card);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCards(){
        List<Card> cards= cardService.getAllCards();

        List<CardResponse> response = CardMapper.toResponseList(cards);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/collection/{collection_id}")
    public ResponseEntity<?> getAllCardsByCollection(@PathVariable Integer collection_id){
        List<Card> cards = cardService.getAllCardsByCollection(collection_id);
        List<CardResponse> response = CardMapper.toResponseList(cards);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/favourite/{id}")
    public ResponseEntity<?> changeFavouriteCard(@PathVariable Integer id){
        Card card = cardService.changeFavourite(id);
        CardResponse response = CardMapper.toResponse(card);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomCards(){
        List<Card> cards = cardService.getRandomCards();

        List<CardResponse> response = CardMapper.toResponseList(cards);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //get all favourite
    @GetMapping("/favourite")
    public ResponseEntity<?> getFavouriteCards(){
        List<Card> cards = cardService.getAllFavourite();

        List<CardResponse> response = CardMapper.toResponseList(cards);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/hashtag")
    public ResponseEntity<?> getCardsByHashtag(
            @RequestParam(name = "hashtag", required = true) String hashtag){
        List<Card> cards = cardService.getCardsByHashtag(hashtag);
        List<CardResponse> response = CardMapper.toResponseList(cards);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/{card_id}/hashtag")
    public ResponseEntity<?> addHashtag(@RequestBody HashtagsRequest request, @PathVariable Integer card_id){
        Card card = cardService.addHashtag(request.getHashtags(), card_id);
        CardResponse response = CardMapper.toResponse(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{card_id}/hashtag")
    public ResponseEntity<?> deleteHashtag(@PathVariable Integer card_id,
                                           @RequestParam(name = "hashtag", required = true) String hashtag){
        String message = cardService.deleteHashtag(card_id, hashtag);
        MessageResponse response = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
