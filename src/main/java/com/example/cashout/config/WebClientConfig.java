package com.example.cashout.config;

import com.example.cashout.exception.Exception400Exception;
import com.example.cashout.services.interfaces.IPaymentsRestClient;
import com.example.cashout.services.interfaces.ITransactionHistoryRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class WebClientConfig {


    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8091")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, clientResponse ->
                    clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new Exception400Exception(errorBody)))
                )
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, clientResponse ->
                    clientResponse.
                            bodyToMono(String.class)
                            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                            .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .build();
    }


    @Bean
    public IPaymentsRestClient clientRestIPaymentsRestClientBuild(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client)).build();
        return  factory.createClient(IPaymentsRestClient.class);
    }

    @Bean
    public ITransactionHistoryRestClient clientRestITransactionHistoryRestClientBuild(WebClient client){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client)).build();
        return  factory.createClient(ITransactionHistoryRestClient.class);
    }


}
