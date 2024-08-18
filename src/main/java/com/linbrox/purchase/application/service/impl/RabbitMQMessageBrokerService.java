package com.linbrox.purchase.application.service.impl;

import com.linbrox.purchase.application.service.MessageBrokerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMessageBrokerService implements MessageBrokerService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMessageBrokerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(String exchange, String queue, String message) {
        rabbitTemplate.convertAndSend(exchange, queue, message);
    }
}