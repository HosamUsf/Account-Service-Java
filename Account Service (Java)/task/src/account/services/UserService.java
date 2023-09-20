package account.services;

import account.exceptions.PasswordInvalidException;
import account.exceptions.EntityAlreadyExistException;
import account.models.User;
import account.repositories.UserRepository;
import account.response.ChangePasswordResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final PasswordEncoder encoder;


    public User signup(User user) {
        Optional<User> user1 = repository.findUserByEmailIgnoreCase(user.getEmail());
        if (user1.isPresent()) {
            throw new EntityAlreadyExistException("User exist!");
        }
        // Checking  password
       validatePassword(user.getPassword());

        // setting email and encrypted password to the user
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    public Optional<User> getAuthUserDetails(String userEmail) {
        Optional<User> user = repository.findUserByEmailIgnoreCase(userEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        logger.info("User role was found {} ", user);
        return user;
    }

    public ResponseEntity<?> changePassword(String newPassword, String userEmail) {
        validatePassword(newPassword);

        User user = repository.findUserByEmailIgnoreCase(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));


        if (encoder.matches(newPassword, user.getPassword())) {
            throw new PasswordInvalidException("The passwords must be different!");
        }
        String encodedNewPassword = encoder.encode(newPassword);

        logger.info(encodedNewPassword);
        user.setPassword(encodedNewPassword);
        repository.save(user);

        return new ResponseEntity<>(new ChangePasswordResponse(userEmail,
                "The password has been updated successfully"), HttpStatus.OK);
    }



    private void validatePassword(String password) {
        final List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

        if (password.length() < 12) {
            throw new PasswordInvalidException("The password length must be at least 12 chars!");
        }
        if (breachedPasswords.contains(password)) {
            throw new PasswordInvalidException("The password is in the hacker's database!");
        }

    }



}
