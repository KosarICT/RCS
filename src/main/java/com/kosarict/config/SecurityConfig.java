package com.kosarict.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.authorizeRequests()
                .antMatchers("/admin**").hasAnyAuthority("ADMIN", "MANAGER", "MIDDLE_MANAGER", "USER_APPRECIATION", "USER_COMPLAINT", "USER_OFFER", "USER_CRITICISM", "USER_NOTIFICATION", "USER_ARCHIVE")
                .antMatchers("/user**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/section**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/adComplain**").hasAnyAuthority("ADMIN", "MANAGER", "USER_COMPLAINT")
                .antMatchers("/adAppreciation**").hasAnyAuthority("ADMIN", "MANAGER", "USER_APPRECIATION")
                .antMatchers("/adCriticism**").hasAnyAuthority("ADMIN", "MANAGER", "USER_CRITICISM")
                .antMatchers("/adOffer**").hasAnyAuthority("ADMIN", "MANAGER", "USER_OFFER")
                .antMatchers("/adArchive**").hasAnyAuthority("ADMIN", "MANAGER", "USER_ARCHIVE")
                .antMatchers("/complaintType**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/hospital**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/adRequestList**").hasAnyAuthority("ADMIN", "MANAGER")

                .antMatchers("/role**").access("hasRole('ADMIN')")
                .antMatchers("/hospitalSection**").access("hasRole('ADMIN')")

                .antMatchers("/").permitAll()
                .antMatchers("/{hospitalId}/**").permitAll()
                .antMatchers("/login**").permitAll()

                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/admin").failureUrl("/login?error")
                .usernameParameter("userName").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout").and().exceptionHandling().accessDeniedPage("/403");

        http.csrf().disable();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        //authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
}
