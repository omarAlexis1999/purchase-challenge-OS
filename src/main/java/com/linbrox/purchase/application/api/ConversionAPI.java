package com.linbrox.purchase.application.api;

import com.linbrox.purchase.application.api.response.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class ConversionAPI {
    private final Logger logger = Logger.getLogger(ConversionAPI.class.getName());

    private final WebClient webClient;

    @Autowired
    public ConversionAPI(WebClient.Builder builder, Environment env) {
        String baseURL = env.getProperty("conversion.api");
        logger.info("Creating ConversionAPI with base "+baseURL);
        this.webClient = builder.baseUrl(baseURL).build();
    }

    public ConversionAPI(String baseURL){
        logger.info("Creating ConversionAPI via BaseURL");
        this.webClient = WebClient.create(baseURL);
    }

    public Mono<ConversionResponse> retrieveByUUID(String uuid){
        logger.info("Retrieving conversion by uuid: "+uuid);
        return webClient.get()
                .uri("/"+uuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ConversionResponse.class);
    }
}
