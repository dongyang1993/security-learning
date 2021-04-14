package com.example.jwt.config;

import com.example.jwt.filter.JwtAuthenticationFilter;
import com.example.jwt.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/auth/**/*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/auth/**/*").permitAll()
                .anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(this.authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(this.authenticationManager(), this.userDetailsService, tokenHeader, tokenHead, secret));

//        http.rememberMe().
        http.exceptionHandling().accessDeniedPage("/auth/forbidden");

        http.csrf().disable();
        /**
         * 因为要改成用JWT,所以调整为不创建和使用Session
         * ALWAYS       总是会新建一个Session
         * NEVER        不会新建HttpSession，但是如果有Session存在，就会使用它
         * IF_REQUIRED  如果有要求的话，会新建一个Session
         * STATELESS    不会新建，也不会使用一个HttpSession
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Bean
//    AccessDeniedHandler accessDeniedHandler() {
//        AccessDeniedHandlerImpl deniedHandler = new AccessDeniedHandlerImpl();
//        deniedHandler.setErrorPage("/auth/forbidden");
//        return deniedHandler;
//    }
}
