package task14_4_1.zadanye3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"userId\"")
    @JsonIgnore
    private User user;

    @Column(name = "\"contactName\"", nullable = false)
    private String contactName;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser() {return user;}

    public void setUser(User user) { this.user = user; }

    public String getContactName() { return contactName; }

    public void setContactName(String contactName) { this.contactName = contactName; }

}
