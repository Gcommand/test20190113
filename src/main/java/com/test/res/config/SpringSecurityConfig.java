package com.test.res.config;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SpringSessionBackedSessionRegistry sessionRegistry;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;


//    @Autowired
//    private CustomAuthenticationProvider authProvider;
//
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.authenticationProvider(authProvider);
//    }


    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about", "/testApi/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);

//        http
//                // Enable csrf only on some request matches
//                .csrf()
//                .requireCsrfProtectionMatcher(csrfRequestMatcher)
//                .and()
//                // Other security configurations ...
//                .authorizeRequests()
//                .antMatchers(
//                        "/",
//                        "/signup",
//                        "/user/**")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();

        // Build the request matcher for CSFR
        RequestMatcher csrfRequestMatcher = new RequestMatcher() {

            // Enabled CSFR protection on the following urls:
            private AntPathRequestMatcher[] requestMatchers = {
//                    new AntPathRequestMatcher("/userApi/**"),
//                    new AntPathRequestMatcher("/dataApi/**")
//                    new AntPathRequestMatcher("/userTempOpen/**")
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                // If the request match one url the CSRF protection will be enabled
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) { return true; }
                }
                return false;
            } // method matches

        };
//                http
//
//                        .csrf()
//                        .requireCsrfProtectionMatcher(csrfRequestMatcher)
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/",
//                        "/js/**",
//                        "/css/**",
//                        "/img/**",
//                        "/webjars/**","/userApi/**"
////                        ,"/userTempOpen/**"
////                        , "/dataApi/**"
//                        , "/testApi/**"
//                ).permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .anyRequest().authenticated()
//                        .and().csrf()
//                .and()
//                .formLogin()
//                .loginPage("/login").successHandler(customAuthenticationSuccessHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler)
////                        .and()
////                        .sessionManagement().maximumSessions(1)
////                        .sessionRegistry(sessionRegistry);
//                ;
        CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
        http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);
    }

    // create two users, admin and user
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.authenticationProvider(authProvider);
//    }
}
