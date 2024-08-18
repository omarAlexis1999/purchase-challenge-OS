package com.linbrox.purchase.domain.model;

import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
public class Purchase {
    private UUID id;
    private String fullName;
    private String version;
    private HyundaiModelEnum hyundaiModel;
    private CryptoCurrencyEnum cryptoCurrencyEnum;
    private Double priceUSD;
    private Double priceCryptoCurrency;
    private Date createdAt;
    private UUID convertionId;

    public Purchase(UUID id, String fullName, String version, HyundaiModelEnum hyundaiModel, CryptoCurrencyEnum cryptoCurrencyEnum, Double priceUSD, Double priceCryptoCurrency, Date createdAt, UUID convertionId) {
        this.id = id;
        this.fullName = fullName;
        this.version = version;
        this.hyundaiModel = hyundaiModel;
        this.cryptoCurrencyEnum = cryptoCurrencyEnum;
        this.priceUSD = priceUSD;
        this.priceCryptoCurrency = priceCryptoCurrency;
        this.createdAt = createdAt;
        this.convertionId = convertionId;
    }

    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
