package ua.goit.notes.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.goit.notes.users.User;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@ToString
@Getter
@Setter
@Table(name = "note")
public class Note {

    @Id
    @Column(name = "uuid", updatable = false)
    private String uuid;

    private String name;
    private String body;

    @Enumerated(EnumType.STRING)
    private AccessTypes access;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Note(String uuid, String name, String body, AccessTypes access) {
        this.uuid = uuid;
        this.name = name;
        this.body = body;
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(uuid, note.uuid) && Objects.equals(name, note.name) && Objects.equals(body, note.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, body);
    }
}
