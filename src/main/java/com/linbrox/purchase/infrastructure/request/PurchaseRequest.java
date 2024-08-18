package com.linbrox.purchase.infrastructure.request;


import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

@Setter
@Getter
public class PurchaseRequest {
    UUID convertionId;
    String fullName;
    String version;
    HyundaiModelEnum model;
    CryptoCurrencyEnum cryptocurrency;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
