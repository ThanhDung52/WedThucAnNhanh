package com.zosh.online_food_ordering.config;

import jakarta.persistence.Embeddable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.lang.reflect.Array;
import java.security.Security;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                               .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
                               .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigrationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigrationSource() {

//        return new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                CorsConfiguration cfg = new CorsConfiguration();
//
//                cfg.setAllowedOrigins(Arrays.asList(
//                        "https://zosh-food.vercel.app",
//                        "http://localhost:8080",
//                        "http://localhost:3000"
//                ));
//                cfg.setAllowedMethods(Collections.singletonList("*"));
//                cfg.setAllowCredentials(true);
//                cfg.setAllowedHeaders(Collections.singletonList("*"));
//                cfg.setExposedHeaders(Arrays.asList("Authorization"));
//                cfg.setMaxAge(3600L);
//                return cfg;
//            }
//        };

        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();

            // Thêm nguồn gốc cụ thể
            cfg.setAllowedOrigins(Arrays.asList(
                    "https://zosh-food.vercel.app",
                    "http://localhost:8080",
                    "http://localhost:3000"
            ));
            // Cho phép tất cả phương thức HTTP
            cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

            // Cho phép credentials
            cfg.setAllowCredentials(true);

            // Cho phép tất cả các header được gửi
            cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

            // Cho phép các header được expose
            cfg.setExposedHeaders(Arrays.asList("Authorization"));

            // Đặt thời gian tối đa cho các yêu cầu preflight (OPTIONS)
            cfg.setMaxAge(3600L);

            return cfg;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
