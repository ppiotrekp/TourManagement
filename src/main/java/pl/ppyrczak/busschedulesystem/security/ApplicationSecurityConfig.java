package pl.ppyrczak.busschedulesystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.ppyrczak.busschedulesystem.auth.UserService;
import pl.ppyrczak.busschedulesystem.jwt.JwtConfig;
import pl.ppyrczak.busschedulesystem.jwt.JwtCredentialsAuthenticationFilter;
import pl.ppyrczak.busschedulesystem.jwt.JwtTokenVerifier;

import javax.crypto.SecretKey;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtCredentialsAuthenticationFilter jwtCredentialsAuthenticationFilter =
                new JwtCredentialsAuthenticationFilter(authenticationManagerBean());
        jwtCredentialsAuthenticationFilter.setFilterProcessesUrl("api/login"); //override

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/registration/**").permitAll()
                .and().authorizeRequests().antMatchers("/login/**", "/api/token/refresh/**").permitAll()
                .and().authorizeRequests().antMatchers(POST, "/role").permitAll()
                .and().authorizeRequests().antMatchers(GET, "/users").permitAll()
                .and().authorizeRequests().antMatchers(GET, "/schedules").hasAnyAuthority("ROLE_USER")
//                .and()
//                .authorizeRequests().antMatchers(GET, "/users").hasAnyAuthority("USER")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().addFilter(new JwtCredentialsAuthenticationFilter(authenticationManagerBean()))
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
