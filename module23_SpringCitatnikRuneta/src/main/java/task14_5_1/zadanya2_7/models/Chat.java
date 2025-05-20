package task14_5_1.zadanya2_7.models;

import jakarta.persistence.*;

//import javax.persistence.*;

@Entity
@Table(name = "\"Chats2\"")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "\"chatId\"")
    private long chatId;

    @Column(name = "\"lastId\"")
    private Integer lastId;

    public long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public long getChatId() { return chatId; }

    public void setChatId(Long chatId) { this.chatId = chatId; }

    public Integer getLastId() { return lastId; }

    public void setLastId(Integer lastId) { this.lastId= lastId; }
}
