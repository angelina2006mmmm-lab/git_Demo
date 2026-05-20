package com.example.bankcards.service;

import com.example.bankcards.dto.BalanceResponse;
import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.CardUpdateRequest;
import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardNumberCrypto;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardNumberCrypto crypto;

    public CardService(CardRepository cardRepository,
                       UserRepository userRepository,
                       CardNumberCrypto crypto) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.crypto = crypto;
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        String hash = crypto.hash(request.cardNumber());
        if (cardRepository.existsByNumberHash(hash)) {
            throw new ApiException(HttpStatus.CONFLICT, "Card already exists");
        }
        AppUser owner = userRepository.findByUsername(request.ownerUsername())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Owner not found"));

        Card card = new Card();
        card.setEncryptedNumber(crypto.encrypt(request.cardNumber()));
        card.setNumberHash(hash);
        card.setNumberLast4(request.cardNumber().substring(12));
        card.setOwner(owner);
        card.setExpirationDate(request.expirationDate());
        card.setBalance(request.balance());
        card.setStatus(request.expirationDate().isBefore(LocalDate.now()) ? CardStatus.EXPIRED : CardStatus.ACTIVE);
        return toResponse(cardRepository.save(card));
    }

    @Transactional(readOnly = true)
    public Page<CardResponse> findAll(CardStatus status, String search, Pageable pageable) {
        return cardRepository.findAll(specification(null, status, search), pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CardResponse> findMyCards(String username, CardStatus status, String search, Pageable pageable) {
        return cardRepository.findAll(specification(username, status, search), pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CardResponse getById(Long id) {
        return toResponse(findCard(id));
    }

    @Transactional
    public CardResponse update(Long id, CardUpdateRequest request) {
        Card card = findCard(id);
        if (request.expirationDate() != null) {
            card.setExpirationDate(request.expirationDate());
        }
        if (request.status() != null) {
            card.setStatus(request.status());
        }
        if (request.balance() != null) {
            card.setBalance(request.balance());
        }
        return toResponse(card);
    }

    @Transactional
    public CardResponse block(Long id) {
        Card card = findCard(id);
        card.setStatus(CardStatus.BLOCKED);
        card.setBlockRequested(false);
        return toResponse(card);
    }

    @Transactional
    public CardResponse activate(Long id) {
        Card card = findCard(id);
        if (card.getExpirationDate().isBefore(LocalDate.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cannot activate expired card");
        }
        card.setStatus(CardStatus.ACTIVE);
        card.setBlockRequested(false);
        return toResponse(card);
    }

    @Transactional
    public void delete(Long id) {
        cardRepository.delete(findCard(id));
    }

    @Transactional
    public CardResponse requestBlock(String username, Long cardId) {
        Card card = cardRepository.findByIdAndOwnerUsername(cardId, username)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Card not found"));
        card.setBlockRequested(true);
        return toResponse(card);
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(String username, Long cardId) {
        Card card = cardRepository.findByIdAndOwnerUsername(cardId, username)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Card not found"));
        return new BalanceResponse(card.getId(), crypto.maskByLast4(card.getNumberLast4()), card.getBalance());
    }

    public Card findCard(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Card not found"));
    }

    public CardResponse toResponse(Card card) {
        CardStatus status = card.getExpirationDate().isBefore(LocalDate.now()) ? CardStatus.EXPIRED : card.getStatus();
        return new CardResponse(
                card.getId(),
                crypto.maskByLast4(card.getNumberLast4()),
                card.getOwner().getUsername(),
                card.getOwner().getFullName(),
                card.getExpirationDate(),
                status,
                card.getBalance(),
                card.isBlockRequested()
        );
    }

    private Specification<Card> specification(String username, CardStatus status, String search) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null && !username.isBlank()) {
                predicates.add(cb.equal(root.get("owner").get("username"), username));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (search != null && !search.isBlank()) {
                String like = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("numberLast4")), like),
                        cb.like(cb.lower(root.get("owner").get("username")), like),
                        cb.like(cb.lower(root.get("owner").get("fullName")), like)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public void validateOwnedActiveCard(Card card, String username) {
        if (!card.getOwner().getUsername().equals(username)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Card does not belong to current user");
        }
        if (card.getExpirationDate().isBefore(LocalDate.now()) || card.getStatus() == CardStatus.EXPIRED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Card is expired");
        }
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Card is blocked");
        }
    }

    public void decreaseBalance(Card card, BigDecimal amount) {
        if (card.getBalance().compareTo(amount) < 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
        card.setBalance(card.getBalance().subtract(amount));
    }

    public void increaseBalance(Card card, BigDecimal amount) {
        card.setBalance(card.getBalance().add(amount));
    }
}
