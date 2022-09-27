package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.demo.handler.UserAuthenticationSuccessHandler;
import com.example.demo.service.implement.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/index", "/register/**", "/").permitAll()

                .antMatchers("/manager").hasRole("MANAGER")
                .antMatchers("/seller").hasRole("SELLER")
                .antMatchers("/customer").hasRole("CUSTOMER")

                // // Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
                // // Nếu chưa login, nó sẽ redirect tới trang /login.
                // //
                // http.authorizeRequests().antMatchers("/customer").access("hasAnyRole('ROLE_CUSTOMER',
                // // 'ROLE_MANAGER')");

                // // Trang chỉ dành cho ADMIN
                // http.authorizeRequests().antMatchers("/manager").access("hasRole('ROLE_MANAGER')");
                // http.authorizeRequests().antMatchers("/seller").access("hasRole('ROLE_SELLER')");
                // http.authorizeRequests().antMatchers("/customer").access("hasRole('ROLE_CUSTOMER')");

                // // Khi người dùng đã login, với vai trò XX.
                // // Nhưng truy cập vào trang yêu cầu vai trò YY,
                // // Ngoại lệ AccessDeniedException sẽ ném ra.
                // http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

                // Cấu hình cho Login Form.
                .and().formLogin().loginPage("/login")
                .successHandler(userAuthenticationSuccessHandler)
                .failureUrl("/login?error=true")//
                .usernameParameter("username")
                .passwordParameter("password").permitAll();
        //
        // .loginPage("/login")//
        // /* .defaultSuccessUrl("/customer")// */
        // .failureUrl("/login?error=true")//
        // .usernameParameter("username")//
        // .passwordParameter("password");

        // Cấu hình Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

        // http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    // Token stored in Memory (Of Web Server).
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
        return memory;
    }
}
