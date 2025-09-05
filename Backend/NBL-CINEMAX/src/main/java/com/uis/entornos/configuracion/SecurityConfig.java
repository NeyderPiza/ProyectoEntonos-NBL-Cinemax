package com.uis.entornos.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Desactiva la protección CSRF, común en APIs REST sin estado
            .csrf(csrf -> csrf.disable())
            
            // Define las reglas de autorización para cada endpoint
            .authorizeHttpRequests(authRequest ->
                authRequest
                    // Permite el acceso público a los endpoints de autenticación y a la consulta de películas
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/peliculas/**").permitAll() // Hacemos público el CRUD de películas
                    .requestMatchers("/api/ciudades/**").permitAll()   // Hacemos público el CRUD de ciudades
                    
                    // Para cualquier otra petición, requiere autenticación
                    .anyRequest().authenticated()
            )
            
            // Configura la gestión de sesiones como SIN ESTADO (stateless)
            .sessionManagement(sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Asocia nuestro proveedor de autenticación personalizado
            .authenticationProvider(authProvider)
            
            // Añade nuestro filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
            .build();
    }
}