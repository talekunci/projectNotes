package ua.goit.notes.note;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Data
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "body", nullable = false)
    private String body;
    @Column(name = "access", nullable = false)
    private String access;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "users_notes", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "note_id"))
//    private Set<User> users = new HashSet<>();
}
