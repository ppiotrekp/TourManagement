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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import pl.ppyrczak.busschedulesystem.jwt.JwtCredentialsAuthenticationFilter;
import pl.ppyrczak.busschedulesystem.jwt.JwtTokenVerifier;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .and().authorizeRequests().antMatchers("/registration/**").permitAll()
                .and().authorizeRequests().antMatchers("/role/**").permitAll()
                .and().authorizeRequests().antMatchers("/**").permitAll()
                .and().authorizeRequests().antMatchers("/login", "/api/token/refresh/**").permitAll()
//                .and().authorizeRequests().antMatchers(POST, "/bus").hasAnyAuthority("ADMIN")
//                .and().authorizeRequests().antMatchers(GET, "/users").permitAll()
//                .and().authorizeRequests().antMatchers(GET, "/buses").hasAnyRole("ROLE_ADMIN")
//                .and().authorizeRequests().antMatchers("/**").permitAll()
//                .and()
//                .authorizeRequests().antMatchers(GET, "/users").hasAnyAuthority("USER")
                .and().authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new JwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class);
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
