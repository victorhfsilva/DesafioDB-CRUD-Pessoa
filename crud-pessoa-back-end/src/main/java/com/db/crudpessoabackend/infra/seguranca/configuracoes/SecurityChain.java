package com.db.crudpessoabackend.infra.seguranca.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityChain {
    
    private CorsConfigurationSource corsConfigurationSource;
    private TokenSecurityFilter tokenSecurityFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
            requests.requestMatchers("/pessoas/**").hasAuthority("ADMIN")
                    .requestMatchers("/enderecos/**").hasAuthority("ADMIN")
                    .requestMatchers("/contatos/**").hasAuthority("ADMIN")
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/registrar/admin").hasAnyAuthority("ADMIN")
                    .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .addFilterBefore(tokenSecurityFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
