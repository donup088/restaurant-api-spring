package spring.study.restaurantapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.study.restaurantapi.filters.JwtAuthenticationFilter;
import spring.study.restaurantapi.utils.JwtUtil;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        Filter filter= new JwtAuthenticationFilter(
                authenticationManager(),jwtUtil());

        httpSecurity
                .csrf().disable()
                .csrf().disable()//h2 console을 볼 수 있도록 함
                .formLogin().disable()//security에서 기본으로 제공되는 로그인 폼을 없앰.
                .headers().frameOptions().disable()//h2 console를 볼 수 있도록 설정
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean//bean 생성
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
