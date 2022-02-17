package ua.goit.notes.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "The password cannot be empty")
    @Size(min = 8, max = 100, message = "The password must be between {min} and {max} characters long")
    private String password;

    @NotEmpty
    @NotBlank(message = "The username cannot be empty")
    @Pattern(regexp = "[a-zA-Z0-9]+")
    @Size(
            min = 5,
            max = 50,
            message = "The username must be between {min} and {max} characters long and contains only numbers and english letters")
    private String name;

    private Set<Note> notes;
}
