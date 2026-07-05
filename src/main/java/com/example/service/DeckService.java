package com.example.service;

import com.example.model.Deck;
import com.example.model.Flashcard;
import com.example.repository.DeckRepository;
import com.example.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

  private final DeckRepository deckRepository;
  private final FlashcardRepository flashcardRepository;

  public DeckService(DeckRepository deckRepository, FlashcardRepository flashcardRepository) {
    this.deckRepository = deckRepository;
    this.flashcardRepository = flashcardRepository;
  }

  public List<Deck> getAllDecks() {
    return deckRepository.findAll();
  }

  public Optional<Deck> getDeckById(Long deckId) {
    return deckRepository.findById(deckId);
  }

  public Deck createDeck(Deck deck) {
    return deckRepository.save(deck);
  }

  public Deck updateDeck(Long deckId, Deck deckDetails) {
    return deckRepository.findById(deckId)
        .map(existingDeck -> {
          existingDeck.setTitle(deckDetails.getTitle());
          existingDeck.setDescription(deckDetails.getDescription());
          return deckRepository.save(existingDeck);
        })
        .orElseThrow(() -> new RuntimeException("Deck not found with id " + deckId));
  }

  public void deleteDeck(Long deckId) {
    deckRepository.deleteById(deckId);
  }

  public Flashcard addFlashcardToDeck(Long deckId, Flashcard flashcard) {
    Deck deck = deckRepository.findById(deckId)
        .orElseThrow(() -> new RuntimeException("Deck not found with id " + deckId));

    deck.addFlashcard(flashcard);
    deckRepository.save(deck);
    return flashcardRepository.save(flashcard);
  }

  public List<Flashcard> getFlashcardsForDeck(Long deckId) {
    return flashcardRepository.findByDeckId(deckId);
  }

  // public List<Flashcard> getFlashcardById(Long cardId) {
  //   return flashcardRepository.findById(cardId)
  //           .map(List::of)
  //           .orElse(List.of());
  // }
}
