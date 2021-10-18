package home.marco.booksapp.service;

import home.marco.booksapp.controller.dto.UserRegistrationDto;
import home.marco.booksapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveUser(UserRegistrationDto registrationDto);
}
