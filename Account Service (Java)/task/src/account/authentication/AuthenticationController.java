package account.authentication;

import account.models.User;
import account.models.Views;
import account.repositories.UserRepository;
import account.request.ChangePasswordRequest;
import account.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/")

public class AuthenticationController {

    private final UserService service ;
    private final UserRepository repo;

    public AuthenticationController(UserService service, UserRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping("auth/signup")
    @JsonView(Views.Public.class)
    public User signup(@RequestBody @Valid User user){
        return service.signup(user);
    }


    @PostMapping("auth/changepass")
    public ResponseEntity<?> changePassword(@RequestBody  @Valid ChangePasswordRequest changePasswordRequest, Authentication authentication){
        return service.changePassword(changePasswordRequest.newPassword(),authentication.getName());
    }


    //? testing Auth
    @GetMapping("empl/payment")
    @JsonView(Views.Public.class)
    public Optional<User> getAuthenticatedUserDetails(@CurrentUsername String email){
        return service.getAuthUserDetails(email);
    }
}
