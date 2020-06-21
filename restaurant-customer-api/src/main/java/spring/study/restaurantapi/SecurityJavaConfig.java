package spring.study.restaurantapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .csrf().disable()//h2 console을 볼 수 있도록 함
                .formLogin().disable()//security에서 기본으로 제공되는 로그인 폼을 없앰.
                .headers().frameOptions().disable();//h2 console를 볼 수 있도록 설정
    }
}
