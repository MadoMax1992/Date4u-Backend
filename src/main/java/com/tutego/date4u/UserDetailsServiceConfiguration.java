package com.tutego.date4u;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user").password("{noop}pass")
                .roles("USER").build();

        InMemoryUserDetailsManager userDetailsManager =
                new InMemoryUserDetailsManager( user1 );

        UserDetails user2 = User.withUsername("user2").password("{noop}pass2")
                .roles("ADMIN").build();
        userDetailsManager.createUser( user2 );

        return userDetailsManager;
    }
}