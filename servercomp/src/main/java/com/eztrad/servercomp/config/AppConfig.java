package com.eztrad.servercomp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

// step 16 - AppConfig created and worked on filter-chain
@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                // JWT filter before BasicAuthenticationFilter
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // Cookie/session-based auth off
                .csrf(csrf->csrf.disable())
                // CORS error handle
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // generally backend and frontend are loaded from different ports so the 'cors' config tell which frontend can access my backend
    private CorsConfigurationSource corsConfigurationSource() {

        // stc 11 - 'stc' means steps of backend + frontend integration, and here we do cors configuration
        return new CorsConfigurationSource() {
            @Override
            public @Nullable CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        Arrays.asList(
                                "http://localhost:5173",
                                "http://localhost:3000"
                        )
                );
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setExposedHeaders(Arrays.asList(("Authorization")));
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
            // stc 11.1 - go and check with frontend signup console message with signup (fend+bend must be running)
        };
    }
}

// what is jwt token validator -> whenever user make a request on our api it will check that end point is white-listed(public access)
// or authenticatable(private access)