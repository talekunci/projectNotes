package ua.goit.notes.users;

import lombok.*;
import ua.goit.notes.note.Note;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_id_seq")
    @Column(nullable = false)
    private Long id;
    private String password;
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Note> notes;

    public User(Long id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(password, user.password)
                && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name);
    }
}

