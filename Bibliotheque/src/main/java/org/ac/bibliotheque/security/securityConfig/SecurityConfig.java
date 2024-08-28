package org.ac.bibliotheque.security.securityConfig;


import org.ac.bibliotheque.security.sec_filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final TokenFilter tokenFilter;

    public SecurityConfig(TokenFilter filter) {
        this.tokenFilter = filter;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").hasAnyRole("ROLE_ADMI","ROLE_USER","ROLE_LIBRARY")
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users").hasAnyRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/set-role-admin").hasAnyRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/block").hasAnyRole("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/users/{email}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users").hasAnyRole("ROLE_ADMIN" , "ROLE_USER", "ROLE_LIBRARY")
                        .requestMatchers(HttpMethod.PUT, "/users/unlock").hasAnyRole("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cart/{userId}/books/{bookId}").hasAnyRole("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/cart/{userId}").hasAnyRole("ROLE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/cart/{userId}/book/{bookId}").hasAnyRole("ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/reserved/user/{userId}").hasAnyRole("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/reserved/user/{userId}").hasAnyRole("ROLE_USER","ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/reserved/library/{libraryId}").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/reserved/delete-book/users/{userId}/book/{bookId}/library/{libraryId}").hasAnyRole("ROLE_ADMIN","ROLE_LIBRARY")
                        .requestMatchers(HttpMethod.POST, "/libraries/register").hasAnyRole("ROLE_LIBRARY")
                        .requestMatchers(HttpMethod.GET, "/libraries/{id}").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/libraries/all").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/libraries").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/libraries").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/libraries/{id}").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/books/upload").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/books").hasAnyRole("ROLE_LIBRARY")
                        .requestMatchers(HttpMethod.PUT, "/books").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasAnyRole("ROLE_LIBRARY","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/books/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/library/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/title={title}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/isbn={isbn}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/author={author}").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:5173"); // Разрешаем доступ с вашего React приложения
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }


}
