package com.example.repository;

import com.example.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
  List<Flashcard> findByDeckId(Long deckId);
  List<Flashcard> findByQuestionContainingIgnoreCase(String question);
  List<Flashcard> findByAnswerContainingIgnoreCase(String answer);
  List<Flashcard> findByCategoryIgnoreCase(String category);
 @Query("SELECT f FROM Flashcard f WHERE LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Flashcard> searchFlashcardsByKeyword(@Param("keyword") String keyword);

  List<Flashcard> findByDeckIdAndCategoryIgnoreCase(Long deckId, String category);
  List<Flashcard> findByDeckIdAndQuestionContainingIgnoreCase(Long deckId, String question);
 @Query("SELECT f FROM Flashcard f WHERE f.deck.id = :deckId AND (LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Flashcard> searchFlashcardsInDeckByKeyword(@Param("deckId") Long deckId, @Param("keyword") String keyword);


}
