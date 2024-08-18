package com.linbrox.purchase.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linbrox.purchase.application.api.response.ConversionResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.UUID;

class ConversionAPITest {
    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();
    private ConversionAPI conversionAPI;

    @BeforeAll
    static void setUp( ) throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown( ) throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize( ) {
        String baseUrl1 = String.format("http://localhost:%s", mockBackEnd.getPort());
        conversionAPI = new ConversionAPI(baseUrl1);
    }


    @Test
    @DisplayName("Should return a mono error value when external expoint is not reached out")
    void shouldReturnZeroValue( ) {
        // Create a mock response with a success status code and a JSON body
        // Create a mock response with a success status code and the JSON body
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value());

        // Enqueue the mock response
        mockBackEnd.enqueue(mockResponse);

        // Execute the retrieveVehicles method and verify the behavior
        Mono<ConversionResponse> responseMono = conversionAPI.retrieveByUUID(UUID.randomUUID().toString());
        // Use StepVerifier to assert the behavior
        StepVerifier.create(responseMono)
                .expectError()
                .verify();
    }
}