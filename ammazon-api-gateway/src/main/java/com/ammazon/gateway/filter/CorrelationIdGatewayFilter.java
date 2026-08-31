package com.ammazon.gateway.filter;

import com.ammazon.shared.util.CorrelationIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter for correlation ID propagation.
 */
@Slf4j
@Component
public class CorrelationIdGatewayFilter implements GlobalFilter {
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = CorrelationIdUtil.generateCorrelationId();
        }

        exchange.getResponse().getHeaders().add(CORRELATION_ID_HEADER, correlationId);
        
        return chain.filter(
                exchange.mutate()
                        .request(exchange.getRequest().mutate()
                                .header(CORRELATION_ID_HEADER, correlationId)
                                .build())
                        .build()
        );
    }
}