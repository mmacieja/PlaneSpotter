package pl.coderslab.planespotter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {


    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 15, message = "Username must have between 3 and 15 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must have 6 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

}
