package com.linbrox.purchase.domain.repository;

import com.linbrox.purchase.domain.model.Purchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository {
        Purchase save(Purchase purchase);
        List<Purchase> findAll();
        Optional<Purchase> findById(UUID uuid);
}
