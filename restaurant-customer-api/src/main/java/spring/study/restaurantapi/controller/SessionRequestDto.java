package spring.study.restaurantapi.controller;

import lombok.Data;

@Data
public class SessionRequestDto {

    private String email;

    private String password;
}
