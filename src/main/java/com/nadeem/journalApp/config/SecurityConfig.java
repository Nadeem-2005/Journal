package com.nadeem.journalApp.config;

import com.nadeem.journalApp.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // 1 Security Rules
//    http.authorizeRequests(): This tells Spring Security to start authorizing the requests.
//.antMatchers("/hello"').permitAllO): This part specifies that HTTP requests matching the path
//                     /hello should be permitted (allowed) for all users, whether they are authenticated or not.
//.anyRequest().authenticated(): This is a more general matcher that specifies any request
//            (not already matched by previous matchers) should be authenticated, meaning users have
//             to provide valid credentials to access these endpoints.
// .and(): This is a method to join several configurations. It helps to continue the configuration
//          from the root (HttpSecurity).
//          .formLogin(): This enables form-based authentication. By default, it will provide a form for
//          the user to enter their username and password. If the user is not authenticated and they try
//          to access a secured endpoint, they'll be redirected to the default login form.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // disable for APIs
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login").permitAll()//redirect happens automatically
                        .anyRequest().authenticated()

                )
                .formLogin(Customizer.withDefaults()) // Enable login page
                .httpBasic(Customizer.withDefaults()); // Enable basic auth


        return http.build();
    }


    // 2 User Storage (In-Memory)
//    The below are the things needed to be implemented when we need to fetch username and pwrd from DB
    /**
        A User entity to represent the user data model.
        A repository UserRepository to interact with MongoDB.
        UserDetailsService implementation to fetch user details.
        A configuration SecurityConfig to integrate everything with Spring Security.
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User.builder()
//                .username("nadeem")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    When you implement UserDetailsService and annotate it with @Service, Spring automatically uses your class instead of the default one.
// the annotated one can be found in config package under the name Securityconfig


    // 3 Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
/***
 * NOTE:
 * When you log in with Spring Security, it manages your authentication across multiple requests, despite
 * HTTP being stateless.
 * 1.Session Creation: After successful authentication, an HTTP session is formed. Your authentication
 * details are stored in this session.
 * 2.Session Cookie: A JSESSIONID cookie is sent to your browser, which gets sent back with subsequent
 * requests, helping the server recognize your session.
 * 3. SecurityContext: Using the SESSIONID, Spring Security fetches your authentication details for each
 * request.
 * 4. Session Timeout: Sessions have a limited life. If you're inactive past this limit, you're logged out.
 * 5. Logout: When logging out, your session ends, and the related cookie is removed.
 * 6. Remember-Me: Spring Security can remember you even after the session ends using a different
 * persistent cookie (typically have a longer lifespan).
 * In essence, Spring Security leverages sessions and cookies, mainly JSESSIONID, to ensure you remain
 * authenticated across requests.
 *
 * We can logout using /logout endpoint which is provided by default
 */