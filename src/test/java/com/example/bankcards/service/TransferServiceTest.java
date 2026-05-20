package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private CardService cardService;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transferMovesMoneyBetweenOwnCards() {
        AppUser owner = owner();
        Card from = card(1L, owner, new BigDecimal("100.00"));
        Card to = card(2L, owner, new BigDecimal("10.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(from));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(to));
        Mockito.doAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            BigDecimal amount = invocation.getArgument(1);
            card.setBalance(card.getBalance().subtract(amount));
            return null;
        }).when(cardService).decreaseBalance(Mockito.eq(from), Mockito.any(BigDecimal.class));
        Mockito.doAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            BigDecimal amount = invocation.getArgument(1);
            card.setBalance(card.getBalance().add(amount));
            return null;
        }).when(cardService).increaseBalance(Mockito.eq(to), Mockito.any(BigDecimal.class));
        Mockito.when(transferRepository.save(Mockito.any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        transferService.transfer("user", new TransferRequest(1L, 2L, new BigDecimal("25.00")));

        Assertions.assertEquals(new BigDecimal("75.00"), from.getBalance());
        Assertions.assertEquals(new BigDecimal("35.00"), to.getBalance());
    }

    @Test
    void transferRejectsSameCards() {
        Assertions.assertThrows(ApiException.class,
                () -> transferService.transfer("user", new TransferRequest(1L, 1L, new BigDecimal("25.00"))));
    }

    private AppUser owner() {
        AppUser owner = new AppUser();
        owner.setUsername("user");
        owner.setFullName("Demo User");
        return owner;
    }

    private Card card(Long id, AppUser owner, BigDecimal balance) {
        Card card = new Card();
        card.setId(id);
        card.setOwner(owner);
        card.setBalance(balance);
        card.setStatus(CardStatus.ACTIVE);
        card.setExpirationDate(LocalDate.now().plusYears(1));
        card.setNumberLast4("1234");
        card.setEncryptedNumber("encrypted");
        card.setNumberHash("hash" + id);
        return card;
    }
}
