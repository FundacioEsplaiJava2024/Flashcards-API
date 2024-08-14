package flashcards.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, name = "front")
    private String front;

    @Column(name = "backside")
    private String backside;

    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "isFavourite")
    private boolean favourite;

    @ManyToOne
    @JsonProperty("collection_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection collection;

    @ManyToOne
    @JsonProperty("user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
