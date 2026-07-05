package com.example.controller;

import com.example.model.Deck;
import com.example.model.Flashcard;
import com.example.service.DeckService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlashcardController {
  private static final Logger log = LoggerFactory.getLogger(FlashcardController.class);
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

@GetMapping("/decks/{deckId}/cards/{cardId}")
public ResponseEntity<Flashcard> getFlashcardById(@PathVariable Long deckId, @PathVariable Long cardId) {
    return deckService.getFlashcardById(deckId, cardId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
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

  @PutMapping("/decks/{deckId}/cards/{cardId}")
   public ResponseEntity<Flashcard> updateFlashcard(@PathVariable Long deckId, @PathVariable Long cardId, @Valid @RequestBody Flashcard flashcard) {
    try {
      Flashcard updatedCard = deckService.updateFlashcardById(deckId, cardId, flashcard);
       return ResponseEntity.ok(updatedCard);
     } catch (RuntimeException ex) {
       return ResponseEntity.notFound().build();
     }
   }


   //searc
     @GetMapping("/decks/search")
    public ResponseEntity<List<Deck>> searchDecks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String keyword) {
        
        List<Deck> decks;
        
        if (keyword != null && !keyword.isEmpty()) {
            decks = deckService.searchDecksByKeyword(keyword);
        } else if (title != null && !title.isEmpty()) {
            decks = deckService.searchDecksByTitle(title);
        } else if (description != null && !description.isEmpty()) {
            decks = deckService.searchDecksByDescription(description);
        } else {
            decks = deckService.getAllDecks();
        }
        
        return ResponseEntity.ok(decks);
    }
    
    @GetMapping("/cards/search")
    public ResponseEntity<List<Flashcard>> searchFlashcards(
            @RequestParam(required = false) String question,
            @RequestParam(required = false) String answer,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        
        List<Flashcard> flashcards;
        
        if (keyword != null && !keyword.isEmpty()) {
            flashcards = deckService.searchFlashcardsByKeyword(keyword);
        } else if (question != null && !question.isEmpty()) {
            flashcards = deckService.searchFlashcardsByQuestion(question);
        } else if (answer != null && !answer.isEmpty()) {
            flashcards = deckService.searchFlashcardsByAnswer(answer);
        } else if (category != null && !category.isEmpty()) {
            flashcards = deckService.searchFlashcardsByCategory(category);
        } else {
            flashcards = List.of();
        }
        
        return ResponseEntity.ok(flashcards);
    }
    
  
}
