package com.example.repository;

import com.example.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByTitleContainingIgnoreCase(String title);
    List<Deck> findByDescriptionContainingIgnoreCase(String description);
    @Query("SELECT d FROM Deck d WHERE LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Deck> searchDecksByKeyword(@Param("keyword") String keyword);


}
