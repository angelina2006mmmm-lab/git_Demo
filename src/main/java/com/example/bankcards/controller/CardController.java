package com.example.bankcards.controller;

import com.example.bankcards.dto.BalanceResponse;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/my")
    public ResponseEntity<Page<CardResponse>> myCards(Authentication authentication,
                                                      @RequestParam(required = false) CardStatus status,
                                                      @RequestParam(required = false) String search,
                                                      Pageable pageable) {
        return ResponseEntity.ok(cardService.findMyCards(authentication.getName(), status, search, pageable));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> balance(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(cardService.getBalance(authentication.getName(), id));
    }

    @PatchMapping("/{id}/block-request")
    public ResponseEntity<CardResponse> requestBlock(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(cardService.requestBlock(authentication.getName(), id));
    }
}
