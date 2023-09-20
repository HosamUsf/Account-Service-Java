package account.authentication;

import account.models.User;
import account.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository user ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.user.findUserByEmailIgnoreCase(username)
                .map(UserAdapter::new).orElseThrow(() ->  new UsernameNotFoundException("User not found"));
    }


    private static class UserAdapter extends User implements UserDetails{
        private UserAdapter(User user){
            super(user);
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return Collections.emptyList();
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}