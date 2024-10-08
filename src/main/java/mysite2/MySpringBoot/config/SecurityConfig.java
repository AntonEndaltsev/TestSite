package mysite2.MySpringBoot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import mysite2.MySpringBoot.services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean //возвращаем кастомный MyUserDetailsService, который напишем далее
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/books/**").permitAll() //вход без авторизации
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/v1/**").permitAll()
                        .requestMatchers("/v2/**").permitAll()
                        .requestMatchers("/v4/**").permitAll()
                        .requestMatchers("/people/**").hasAuthority("ROLE_ADMIN")) //с авторизацией и аутентификацией
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }
    @Bean //Ставим степень кодировки, с которой кодировали пароль в базе
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

}