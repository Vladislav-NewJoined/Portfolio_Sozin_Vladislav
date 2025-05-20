package task14_4_1.zadanye4.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Contact> contacts = new LinkedHashSet<>();

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Contact> getContacts() { return contacts; }

    public void setContacts(Set<Contact> contacts) {this.contacts = contacts; }
}
