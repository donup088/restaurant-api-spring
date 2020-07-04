package spring.study.restaurantapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.study.restaurantapi.domain.User;
import spring.study.restaurantapi.domain.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;//passwordEncoder에 대한 bean을 만들어줘야함

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public User registerUser(String email, String name, String password) {
        Optional<User> existed=userRepository.findByEmail(email);
        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }
        String encodedPassword=passwordEncoder.encode(password);

        User user=User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

      return userRepository.save(user);
    }


    public User authenticate(String email, String password) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new EmailNotExistedException(email));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new PasswordWrongException();
        }

        return user;
    }

}
