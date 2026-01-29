package com.eztrad.servercomp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

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
        return null;
    }
}

// what is jwt token validator -> whenever user make a request on our api it will check that end point is white-listed(public access)
// or authenticatable(private access)