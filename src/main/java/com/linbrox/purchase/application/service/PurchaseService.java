package com.linbrox.purchase.application.service;

import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.infrastructure.entity.PurchaseEntity;

public interface PurchaseService {
    Purchase create(String uuidConvertion, String fullName, String version, HyundaiModelEnum modelEnum, CryptoCurrencyEnum cryptoCurrency);
}
