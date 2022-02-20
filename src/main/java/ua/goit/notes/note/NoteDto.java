package ua.goit.notes.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class NoteDto {

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank
    @Size(min = 5, max = 10000)
    private String body;

    @NotEmpty
    @Size(max = 10)
    private AccessTypes access;

}
