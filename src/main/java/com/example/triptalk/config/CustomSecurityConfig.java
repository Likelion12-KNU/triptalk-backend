package com.example.triptalk.config;

import com.example.triptalk.security.UserDetailsServiceImpl;
import com.example.triptalk.security.filter.LoginFilter;
import com.example.triptalk.security.filter.TokenCheckFilter;
import com.example.triptalk.security.handler.LoginSuccessHandler;
import com.example.triptalk.service.UserInfoService;
import com.example.triptalk.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoService userInfoService;
    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //정적 자원에 대한 요청 무시
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        // 접근 허용할 도메인, 메서드, 헤더 설정
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        configuration.setAllowCredentials(true);

        // 모든 경로에 대해서 API 호출 허용
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // AuthenticationManager 설정
        AuthenticationManagerBuilder authManagerBuilder=http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        AuthenticationManager authManager=authManagerBuilder.build();
        http.authenticationManager(authManager);


        // LoginFilter 설정
        LoginFilter loginFilter=new LoginFilter("/api/auth/login");
        loginFilter.setAuthenticationManager(authManager);

        LoginSuccessHandler loginSuccessHandler=new LoginSuccessHandler(jwtUtil, userInfoService);
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);

        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);


        // TokenCheckFilter 설정
        TokenCheckFilter tokenCheckFilter=new TokenCheckFilter(jwtUtil, userDetailsService);
        http.addFilterBefore(tokenCheckFilter, UsernamePasswordAuthenticationFilter.class);


        // CORS 처리
        http.cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
