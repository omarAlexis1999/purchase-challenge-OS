package com.linbrox.purchase.application.service.impl;

import com.linbrox.purchase.application.api.response.ConversionResponse;
import com.linbrox.purchase.application.api.response.ConversionVersion;
import com.linbrox.purchase.application.service.ConversionService;
import com.linbrox.purchase.application.service.MessageBrokerService;
import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.domain.repository.PurchaseRepository;
import com.linbrox.purchase.infrastructure.config.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PurchaseServiceImplTest {
    private PurchaseServiceImpl purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private MessageBrokerService messageBrokerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseService = new PurchaseServiceImpl(purchaseRepository, conversionService, messageBrokerService);
    }

    @Test
    void create_Successful() {
        // Prepare test data
        String uuidConvertion = "b6023e8a-16b1-4ee5-bf0e-52eb8a6cb019";
        String fullName = "Joaquin Sabina";
        String version = "Alguna version";
        HyundaiModelEnum modelEnum = HyundaiModelEnum.ACCENT;
        CryptoCurrencyEnum cryptoCurrency = CryptoCurrencyEnum.BTC;
        Date todayDate = new Date();
        String today = String.valueOf(todayDate);
        UUID conversionId = UUID.fromString("b6023e8a-16b1-4ee5-bf0e-52eb8a6cb019");
        ConversionResponse response = new ConversionResponse();
        response.setId(conversionId);
        response.setCreatedAt(today);
        response.setConversionVersionList(new ArrayList<>());
        ConversionVersion conversionVersion = new ConversionVersion();
        conversionVersion.setCryptoCurrency(cryptoCurrency.name());
        conversionVersion.setPriceUSD(1d);
        conversionVersion.setPriceCryptoCurrency(0.01d);
        conversionVersion.setVersion(version);
        conversionVersion.setHyundaiModel(modelEnum.name());
        response.getConversionVersionList().add(conversionVersion);

        // Mock the ConversionService response
        when(conversionService.callExternalAPI(UUID.fromString(uuidConvertion))).thenReturn(Mono.just(response));

        // Mock the PurchaseRepository save method
        Purchase purchase = Purchase.builder()
                .convertionId(conversionId)
                .version(version)
                .createdAt(todayDate)
                .cryptoCurrencyEnum(cryptoCurrency)
                .priceUSD(1d)
                .hyundaiModel(modelEnum)
                .priceCryptoCurrency(0.01d)
                .fullName(fullName).build();
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        // Perform the test
        Purchase result = purchaseService.create(uuidConvertion, fullName, version, modelEnum, cryptoCurrency);

        // Verify the result
        assertNotNull(result);

        // Verify the ConversionService and MessageBrokerService interactions
        verify(conversionService).callExternalAPI(UUID.fromString(uuidConvertion));
        verify(messageBrokerService).sendMessage(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, result.toString());

        // Verify the PurchaseRepository save method invocation
        verify(purchaseRepository).save(any(Purchase.class));

        // Verify no more interactions on the mocked objects
        verifyNoMoreInteractions(conversionService, messageBrokerService, purchaseRepository);
    }
}