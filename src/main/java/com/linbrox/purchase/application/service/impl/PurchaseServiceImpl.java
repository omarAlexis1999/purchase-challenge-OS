package com.linbrox.purchase.application.service.impl;

import com.linbrox.purchase.application.api.response.ConversionResponse;
import com.linbrox.purchase.application.api.response.ConversionVersion;
import com.linbrox.purchase.application.service.ConversionService;
import com.linbrox.purchase.application.service.MessageBrokerService;
import com.linbrox.purchase.application.service.PurchaseService;
import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.domain.repository.PurchaseRepository;
import com.linbrox.purchase.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.UUID;

public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ConversionService conversionService;
    private final MessageBrokerService messageBrokerService;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ConversionService conversionService, MessageBrokerService messageBrokerService) {
        this.purchaseRepository = purchaseRepository;
        this.conversionService = conversionService;
        this.messageBrokerService = messageBrokerService;
    }

    @Override
    public Purchase create(String uuidConvertion, String fullName, String version, HyundaiModelEnum modelEnum, CryptoCurrencyEnum cryptoCurrency) {
        ConversionResponse response = this.conversionService.callExternalAPI(UUID.fromString(uuidConvertion)).block();
        for(ConversionVersion conversionVersion: response.getConversionVersionList()){
            if(conversionVersion.getVersion().equals(version) &&
                    conversionVersion.getHyundaiModel().equals(modelEnum.name()) &&
                    conversionVersion.getCryptoCurrency().equals(cryptoCurrency.name())){
                Purchase purchase = Purchase.builder()
                        .fullName(fullName)
                        .version(conversionVersion.getVersion())
                        .hyundaiModel(modelEnum)
                        .priceUSD(conversionVersion.getPriceUSD())
                        .priceCryptoCurrency(conversionVersion.getPriceCryptoCurrency())
                        .cryptoCurrencyEnum(CryptoCurrencyEnum.valueOf(conversionVersion.getCryptoCurrency()))
                        .convertionId(response.getId())
                        .createdAt(new Date())
                        .build();
                messageBrokerService.sendMessage(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, purchase.toString());
                return this.purchaseRepository.save(purchase);
            }
        }
        return null;
    }
}
