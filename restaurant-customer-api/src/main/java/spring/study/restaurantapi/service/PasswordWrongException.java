package spring.study.restaurantapi.service;

public class PasswordWrongException extends RuntimeException {

    PasswordWrongException(){
        super("Password is wrong");
    }
}
