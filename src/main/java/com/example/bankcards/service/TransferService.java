package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.dto.TransferResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;
    private final CardService cardService;

    public TransferService(CardRepository cardRepository,
                           TransferRepository transferRepository,
                           CardService cardService) {
        this.cardRepository = cardRepository;
        this.transferRepository = transferRepository;
        this.cardService = cardService;
    }

    @Transactional
    public TransferResponse transfer(String username, TransferRequest request) {
        if (request.fromCardId().equals(request.toCardId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cards must be different");
        }
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Amount must be greater than zero");
        }

        Card fromCard = cardRepository.findById(request.fromCardId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Source card not found"));
        Card toCard = cardRepository.findById(request.toCardId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Destination card not found"));

        cardService.validateOwnedActiveCard(fromCard, username);
        cardService.validateOwnedActiveCard(toCard, username);
        cardService.decreaseBalance(fromCard, request.amount());
        cardService.increaseBalance(toCard, request.amount());

        Transfer transfer = new Transfer();
        transfer.setFromCard(fromCard);
        transfer.setToCard(toCard);
        transfer.setAmount(request.amount());
        Transfer saved = transferRepository.save(transfer);

        return new TransferResponse(
                saved.getId(),
                saved.getFromCard().getId(),
                saved.getToCard().getId(),
                saved.getAmount(),
                saved.getCreatedAt()
        );
    }
}
