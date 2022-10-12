package pl.ppyrczak.busschedulesystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.ppyrczak.busschedulesystem.jwt.JwtCredentialsAuthenticationFilter;
import pl.ppyrczak.busschedulesystem.jwt.JwtTokenVerifier;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtCredentialsAuthenticationFilter jwtCredentialsAuthenticationFilter =
                new JwtCredentialsAuthenticationFilter(authenticationManagerBean());
        jwtCredentialsAuthenticationFilter.setFilterProcessesUrl("/api/login"); //override

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/registration/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").not().hasAuthority("ROLE_UNCONFIRMED")
                //.and().authorizeRequests().antMatchers(POST, "/api/login").hasAnyAuthority("ROLE_ADMIN")
                .and().authorizeRequests().antMatchers(POST, "/role").permitAll()
                .and().authorizeRequests().antMatchers(GET, "/users").permitAll()
                .and().authorizeRequests().antMatchers(GET, "/schedules").hasAnyAuthority("ROLE_ADMIN")
//                .and()
//                .authorizeRequests().antMatchers(GET, "/users").hasAnyAuthority("USER")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().addFilter(jwtCredentialsAuthenticationFilter)
                .addFilterBefore(new JwtTokenVerifier(), JwtCredentialsAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
