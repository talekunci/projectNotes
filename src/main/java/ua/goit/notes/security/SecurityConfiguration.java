package ua.goit.notes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ua.goit.notes.users.UserDetailsServiceImpl;

import java.util.List;

@Profile("!prod")
@Configuration
@EnableTransactionManagement
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl detailsService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200/"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .addFilterAt(new JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter.class)
                .csrf().ignoringAntMatchers("/**")
            .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
            .and()
                .cors()
            .and()
                .cors().configurationSource(request -> corsConfiguration)
            .and()
                .httpBasic()
                .authenticationEntryPoint(((request, response, e) -> resolver.resolveException(request, response, null, e)));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.detailsService);
    }
}
