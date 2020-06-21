package spring.study.restaurantapi.service;

public class EmailExistedException extends RuntimeException{

    public EmailExistedException(String email){
        super("Email is already registered: "+email);
    }
}
