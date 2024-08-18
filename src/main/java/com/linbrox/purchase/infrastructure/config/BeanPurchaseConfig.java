package com.linbrox.purchase.infrastructure.config;

import com.linbrox.purchase.application.service.ConversionService;
import com.linbrox.purchase.application.service.MessageBrokerService;
import com.linbrox.purchase.application.service.PurchaseService;
import com.linbrox.purchase.application.service.impl.PurchaseServiceImpl;
import com.linbrox.purchase.domain.repository.PurchaseRepository;
import com.linbrox.purchase.infrastructure.adapter.PurchaseRepositoryAdapter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPurchaseConfig {

    @Bean
    PurchaseService purchaseService(
            final PurchaseRepository purchaseRepository,
            final ConversionService conversionService,
            final MessageBrokerService messageBrokerService
            ){
        return new PurchaseServiceImpl(purchaseRepository, conversionService, messageBrokerService);
    }

    @Bean PurchaseRepository purchaseRepository(PurchaseRepositoryAdapter conversionRepositoryAdapter){
        return conversionRepositoryAdapter;
    }

}
