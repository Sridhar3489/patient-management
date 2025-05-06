package com.patient.api_gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
//extending AbstractGatewayFilterFactory to create custom filters
public class JWTValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient; //to call AuthService

    public JWTValidationGatewayFilterFactory(
            WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authServiceUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build(); //to have timeouts, multiple environment support
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if(token == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return webClient.get().uri("/validate")
                    .header("token", token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }

}
