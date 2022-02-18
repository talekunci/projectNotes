package ua.goit.notes.note;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Data
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "uuid", updatable = false)
    private String uuid;
    private String name;
    private String body;
    @Enumerated(EnumType.STRING)
    private AccessTypes access;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_notes", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "note_uuid"))
    @ToString.Exclude private Set<User> users = new HashSet<>();
}
