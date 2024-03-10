package com.db.crudpessoabackend.infra.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.AllArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityChain {
    
    private CorsConfigurationSource corsConfigurationSource;
    private TokeSecurityFilter tokeSecurityFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
            requests.requestMatchers("/pessoas/**").authenticated()
                    .requestMatchers("/enderecos/**").authenticated()
                    .requestMatchers("/contatos/**").authenticated()
                    .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .addFilterBefore(tokeSecurityFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
