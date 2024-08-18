package com.linbrox.purchase.application.service;

public interface MessageBrokerService {
    void sendMessage(String exchange, String queue, String message);
}