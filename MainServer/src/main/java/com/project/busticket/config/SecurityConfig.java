package com.project.busticket.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        private final String[] PUBLIC_ENPOINTS_POST = { "/users", "/auth/login", "/payment/server/send-message",
                        "/payment/record" };
        private final String[] PUBLIC_ENPOINTS_GET = { "/busoperator", "/trip", "/busticket/homepage",
                        "/busticket/profile",
                        "/busticket/login", "/busticket/payment/record", "/busticket/logout", "/busticket/admin/logout",
                        "/busticket/check/login", "/ws",
                        "/busticket/schedule", "/busticket/introduce", "/busticket/session-info",
                        "/busticket/user/myinfo", "/send-message", "/topic/notification",
                        "/busoperator/img/*", "/busoperator/name/*" };
        private final String[] STATIC_RESOURSE = { "/css/**", "/js/**", "/imgs/**", "/fonts/**", "/layouts/**",
                        "/admin/**" };

        private final String[] ADMIN_ENPOINTS = { "/busticket/admin", "/busticket/admin/home",
                        "/busticket/admin/busmanage",
                        "/busticket/admin/dashboard", "/busticket/admin/ticketmanage", "/busticket/admin/usermanage" };

        @Value("${jwt.signerKey}")
        protected String SIGNER_KEY;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(request -> request
                                .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS_POST).permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_ENPOINTS_GET).permitAll()
                                .requestMatchers(HttpMethod.GET, STATIC_RESOURSE).permitAll()
                                .requestMatchers(HttpMethod.GET, ADMIN_ENPOINTS).permitAll()
                                .requestMatchers(HttpMethod.GET, "/ws/**").permitAll()
                                .requestMatchers("/busbooking/**").permitAll()
                                .anyRequest().authenticated());

                httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfigure -> jwtConfigure.decoder(jwtDecoder())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

                httpSecurity.csrf(AbstractHttpConfigurer::disable);
                return httpSecurity.build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

                JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
                return jwtAuthenticationConverter;
        }

        @Bean
        JwtDecoder jwtDecoder() {
                SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS521");
                return NimbusJwtDecoder
                                .withSecretKey(secretKeySpec)
                                .macAlgorithm(MacAlgorithm.HS512)
                                .build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }
}
