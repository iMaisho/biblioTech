package fr.formation.spring.bibliotech.config;

import fr.formation.spring.bibliotech.security.ApiKeyAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final ApiKeyAuthFilter apiKeyAuthFilter;

    public FilterConfig(ApiKeyAuthFilter apiKeyAuthFilter) {
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> registerApiKeyFilter() {
        FilterRegistrationBean<ApiKeyAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(apiKeyAuthFilter);

        // Appliquer ce filtre uniquement aux endpoints de création,
        // modification et suppression des livres.
        registration.addUrlPatterns(
                "/api/v1/books",      // Protège POST
                "/api/v1/books/*",    // Protège PUT, DELETE
                "/api/v1/authors",      // Protège POST
                "/api/v1/authors/*"     // Protège PUT, DELETE
        );
        return registration;
    }
}