package task14_5_1.zadanye8.models;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Quotes2\"")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "\"quoteid\"", nullable = false)
    private Integer quoteid;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Integer getQuoteid() { return quoteid; }

    public void setQuoteid(Integer quoteid) { this.quoteid = quoteid; }

}
