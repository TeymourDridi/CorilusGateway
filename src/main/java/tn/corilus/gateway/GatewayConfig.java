package tn.corilus.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public GatewayFilterFactory<Redirect308GatewayFilterFactory.Config> redirect308GatewayFilterFactory() {
        return new Redirect308GatewayFilterFactory();
    }

    private static class Redirect308GatewayFilterFactory extends AbstractGatewayFilterFactory<Redirect308GatewayFilterFactory.Config> {

        public Redirect308GatewayFilterFactory() {
            super(Config.class);
        }

        @Override
        public GatewayFilter apply(Config config) {
            return (exchange, chain) -> {
                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, config.getRedirectUri());
                return Mono.empty();
            };
        }

        public static class Config {
            private String redirectUri;

            public String getRedirectUri() {
                return redirectUri;
            }

            public void setRedirectUri(String redirectUri) {
                this.redirectUri = redirectUri;
            }
        }
    }
}
