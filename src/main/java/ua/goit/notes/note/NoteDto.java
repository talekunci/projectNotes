package ua.goit.notes.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NoteDto {

    @NotBlank
    @Size(max = 36)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uuid;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 10000)
    private String body;
    @NotEmpty
    @Size(max = 10)
    private AccessTypes access;

}
