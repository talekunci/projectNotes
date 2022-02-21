package ua.goit.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Profile("prod")
@Configuration
@EnableTransactionManagement
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ProdSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200/"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.authorizeRequests()
                    .antMatchers("/", "/register", "/note/share/*").permitAll()
                    .antMatchers("/swagger-ui/*", "/user", "/user/*").denyAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .defaultSuccessUrl("/note")
                .and()
                    .httpBasic()
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                    .cors().configurationSource(request -> corsConfiguration);
    }
}
