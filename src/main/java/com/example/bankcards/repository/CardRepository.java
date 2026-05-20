package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Optional<Card> findByIdAndOwnerUsername(Long id, String username);
    boolean existsByNumberHash(String numberHash);
    List<Card> findByOwnerUsername(String username);
}
