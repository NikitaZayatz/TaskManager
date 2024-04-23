package com.tasks.demo.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasks.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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

   /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).build();
        UserDetails user = User.builder().username("user").password(encoder.encode("user")).build();
        UserDetails nikitaBoss = User.builder().username("nikitaBoss").password(encoder.encode("nikitaBoss")).build();
        return new InMemoryUserDetailsManager(user, admin, nikitaBoss);
    }*/

   @Autowired
    private final UserService personDetailsService;

   @Autowired
   public SecurityConfig(UserService personDetailsService) {
       this.personDetailsService = personDetailsService;
   }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChaim(HttpSecurity http) throws Exception {
     return http
             .csrf(csrf -> csrf.disable())
             .formLogin(form ->form.loginPage("/auth/login").permitAll())
             .authorizeHttpRequests(auth->
             {
                 auth.requestMatchers("/static/styles/**","/auth/registration").permitAll();
                 auth.requestMatchers("/auth/**","/**","/assets/**").authenticated();

             /*    auth.requestMatchers("/auth/userPage").hasRole("USER");
                 auth.requestMatchers("/auth/adminPage").hasRole("ADMIN");
                 auth.requestMatchers("/auth/administrationPage").hasRole("ADMINISTRATION");*/
             })
             .formLogin(form ->form.defaultSuccessUrl("/auth/index",true))
             .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

}

