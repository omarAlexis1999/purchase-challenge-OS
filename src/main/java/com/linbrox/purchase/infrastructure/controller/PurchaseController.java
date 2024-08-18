package com.linbrox.purchase.infrastructure.controller;


import com.linbrox.purchase.application.service.PurchaseService;
import com.linbrox.purchase.domain.model.Purchase;
import com.linbrox.purchase.infrastructure.entity.PurchaseEntity;
import com.linbrox.purchase.infrastructure.request.PurchaseRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@Api(tags = "Purchase")
public class PurchaseController {
    private final Logger logger = Logger.getLogger(PurchaseController.class.getName());

    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }


    @ApiOperation(value = "Execute operations of transactions", notes = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Something bad happened")
    })
    @PostMapping("/purchase/create")
    public Mono<Purchase> create(@RequestBody PurchaseRequest request){
        try{
            var purchase = this.purchaseService.create(request.getConvertionId().toString(),
                    request.getFullName(),
                    request.getVersion(),
                    request.getModel(),
                    request.getCryptocurrency());
            if(purchase==null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase not found");
            }
            return Mono.just(purchase);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something is wrong");
        }
    }
}
