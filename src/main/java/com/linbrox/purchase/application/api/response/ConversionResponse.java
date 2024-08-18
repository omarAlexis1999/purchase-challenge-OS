package com.linbrox.purchase.application.api.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ConversionResponse {
    private UUID id;
    private String convertionTimeLife;
    private String createdAt;
    private List<ConversionVersion> conversionVersionList;
}