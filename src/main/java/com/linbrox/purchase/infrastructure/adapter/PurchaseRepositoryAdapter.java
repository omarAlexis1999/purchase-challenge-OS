package com.linbrox.purchase.infrastructure.adapter;

import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.domain.repository.PurchaseRepository;
import com.linbrox.purchase.infrastructure.entity.PurchaseEntity;
import com.linbrox.purchase.infrastructure.repository.PurchaseRepositorySpringData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PurchaseRepositoryAdapter implements PurchaseRepository {

    private final PurchaseRepositorySpringData purchaseRepositorySpringData;

    public PurchaseRepositoryAdapter(PurchaseRepositorySpringData purchaseRepositorySpringData) {
        this.purchaseRepositorySpringData = purchaseRepositorySpringData;
    }

    @Override
    public Purchase save(Purchase purchase) {
        PurchaseEntity entity = PurchaseEntity.fromDomainModel(purchase);
        return this.purchaseRepositorySpringData.save(entity).toDomanModel();
    }

    @Override
    public List<Purchase> findAll() {
        return this.purchaseRepositorySpringData.findAll()
                .stream().map(PurchaseEntity::toDomanModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Purchase> findById(UUID uuid) {
        return this.purchaseRepositorySpringData.findById(uuid)
                .map(PurchaseEntity::toDomanModel);
    }
}
