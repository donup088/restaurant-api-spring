package spring.study.restaurantapi.service;

public class EmailNotExistedException extends RuntimeException {

    EmailNotExistedException(String email) {
        super("Email is not registered: " +email);
    }


}
