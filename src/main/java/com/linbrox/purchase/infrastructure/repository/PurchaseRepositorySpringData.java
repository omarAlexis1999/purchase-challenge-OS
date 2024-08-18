package com.linbrox.purchase.infrastructure.repository;


import com.linbrox.purchase.infrastructure.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepositorySpringData extends JpaRepository<PurchaseEntity, UUID> {
}
