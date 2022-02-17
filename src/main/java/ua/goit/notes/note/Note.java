package ua.goit.notes.note;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Data
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "body", nullable = false, length = 10000)
    private String body;
    @Column(name = "access", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AccessTypes access;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_notes", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"))
    private Set<User> users = new HashSet<>();
}
