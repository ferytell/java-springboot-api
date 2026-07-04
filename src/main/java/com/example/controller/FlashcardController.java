package com.example.controller;

import com.example.model.Deck;
import com.example.model.Flashcard;
import com.example.service.DeckService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FlashcardController {

  private final DeckService deckService;

  public FlashcardController(DeckService deckService) {
    this.deckService = deckService;
  }

  @GetMapping("/decks")
  public ResponseEntity<List<Deck>> getAllDecks() {
    return ResponseEntity.ok(deckService.getAllDecks());
  }

  @GetMapping("/decks/{deckId}")
  public ResponseEntity<Deck> getDeckById(@PathVariable Long deckId) {
    return deckService.getDeckById(deckId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/decks")
  public ResponseEntity<Deck> createDeck(@Valid @RequestBody Deck deck) {
    Deck createdDeck = deckService.createDeck(deck);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdDeck);
  }

  @PutMapping("/decks/{deckId}")
  public ResponseEntity<Deck> updateDeck(@PathVariable Long deckId, @Valid @RequestBody Deck deck) {
    try {
      return ResponseEntity.ok(deckService.updateDeck(deckId, deck));
    } catch (RuntimeException ex) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/decks/{deckId}")
  public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
    deckService.deleteDeck(deckId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/decks/{deckId}/cards")
  public ResponseEntity<List<Flashcard>> getFlashcards(@PathVariable Long deckId) {
    return ResponseEntity.ok(deckService.getFlashcardsForDeck(deckId));
  }

  @PostMapping("/decks/{deckId}/cards")
  public ResponseEntity<Flashcard> createFlashcard(@PathVariable Long deckId, @Valid @RequestBody Flashcard flashcard) {
    try {
      Flashcard createdCard = deckService.addFlashcardToDeck(deckId, flashcard);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
    } catch (RuntimeException ex) {
      return ResponseEntity.notFound().build();
    }
  }
}
