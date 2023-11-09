package com.billlog.publicweatherapi.global.config;

import com.billlog.publicweatherapi.global.custom.exception.CustomAccessDeniedEntryPoint;
import com.billlog.publicweatherapi.global.custom.exception.CustomAuthenticationEntryPoint;
import com.billlog.publicweatherapi.global.jwt.JwtAuthenticationFilter;
import com.billlog.publicweatherapi.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        /* 필터링 제외 */
        return (web) -> web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and().httpBasic().disable()                                                        // security에서 기본으로 생성하는 login페이지 사용 안 함
                .csrf().disable()                                                                   // csrf 사용 안 함 == REST API 사용하기 때문에
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)         // JWT인증사용하므로 세션 사용  함
                .and()
                .authorizeRequests()                                                                // 다음  요청 리퀘스트에 대한 사용권한 체크
                .antMatchers("/billlog-api/pub-weather/api/v*/my/**").hasAnyRole("USER")
                .anyRequest().permitAll()                                                           // 위 필터링 그 외 나머지 요청은 누구나 접근 가능
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 401 Cusotom Error
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedEntryPoint())        // 403 Cusotom Error
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);       // 내 서버가 응답할 때 json을 JS에서 처리할 수 있게 설정
        configuration.addAllowedOriginPattern("*");    // 모든 ip에 응답을 허용
        configuration.addAllowedHeader("*");           // 모든 header에 응답 허용
        configuration.addAllowedMethod("*");           // 모든 post,get,put,delete,patch 요청허용
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}