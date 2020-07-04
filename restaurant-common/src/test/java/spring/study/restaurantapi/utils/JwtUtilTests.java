package spring.study.restaurantapi.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilTests {
    private static final String SECRET="12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil=new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        String token=jwtUtil.createToken(123L,"dong");

        assertEquals(token.contains("."),true);
    }

    @Test
    public void getClaims(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEyMywibmFtZSI6ImRvbmcifQ.Xg1kWlaBcwl6V2rSX5BFSV6ruS_UYJ9e6ugGjcxL5Ug";
        Claims claims=jwtUtil.getClaims(token);

        assertEquals(claims.get("name"),"dong");
        assertEquals(claims.get("userId",Long.class),123L);
    }
}