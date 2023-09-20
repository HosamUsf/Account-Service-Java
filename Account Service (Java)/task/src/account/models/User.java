package account.models;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private  Long id ;

    @NotEmpty
    @JsonView(Views.Public.class)
    private String name ;

    @NotEmpty
    @JsonView(Views.Public.class)
    private String lastname ;



    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@acme\\.com$")
    @JsonView(Views.Public.class)
    @NotEmpty
    private String email ;



    @NotEmpty
    @JsonView(Views.Private.class)
    private String password ;

    public User(User user ) {
        this.id = user.id;
        this.name = user.name;
        this.lastname = user.lastname;
        this.email = user.email;
        this.password = user.password;
    }

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
