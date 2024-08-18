package com.linbrox.purchase.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linbrox.purchase.application.service.PurchaseService;
import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.infrastructure.request.PurchaseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    void create_Successful() throws Exception {
        // Prepare test data
        PurchaseRequest request = new PurchaseRequest();
        request.setConvertionId(UUID.fromString("b6023e8a-16b1-4ee5-bf0e-52eb8a6cb019"));
        request.setCryptocurrency(CryptoCurrencyEnum.ETH);
        request.setModel(HyundaiModelEnum.ACCENT);
        request.setVersion("Algo");
        request.setFullName("Joaquin Sabina");
        Purchase purchase = Purchase.builder()
                .hyundaiModel(request.getModel())
                .createdAt(new Date())
                .version(request.getVersion())
                .convertionId(request.getConvertionId())
                .cryptoCurrencyEnum(request.getCryptocurrency())
                .fullName(request.getFullName())
                .priceCryptoCurrency(1d)
                .priceUSD(2d)
                .build();

        // Mock the PurchaseService response
        when(purchaseService.create("b6023e8a-16b1-4ee5-bf0e-52eb8a6cb019",
                "Joaquin Sabina",
                "Algo",
                HyundaiModelEnum.ACCENT,
                CryptoCurrencyEnum.ETH)).thenReturn(purchase);

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/purchase/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

    }


    @Test
    void create_BadRequest() throws Exception {
        // Prepare test data
        PurchaseRequest request = new PurchaseRequest(/* add necessary fields */);

        // Mock the PurchaseService to throw an exception
        when(purchaseService.create(anyString(), anyString(), anyString(), any(HyundaiModelEnum.class), any(CryptoCurrencyEnum.class))).thenThrow(new RuntimeException());

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/purchase/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
    }

    // Utility method to convert an object to JSON string
    private static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}