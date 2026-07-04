package com.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decks")
public class Deck {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Title is required")
  @Column(nullable = false)
  private String title;

  private String description;

  @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Flashcard> flashcards = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Flashcard> getFlashcards() {
    return flashcards;
  }

  public void setFlashcards(List<Flashcard> flashcards) {
    this.flashcards = flashcards;
  }

  public void addFlashcard(Flashcard flashcard) {
    flashcards.add(flashcard);
    flashcard.setDeck(this);
  }

  public void removeFlashcard(Flashcard flashcard) {
    flashcards.remove(flashcard);
    flashcard.setDeck(null);
  }
}
