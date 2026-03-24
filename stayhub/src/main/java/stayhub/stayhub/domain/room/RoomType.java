package stayhub.stayhub.domain.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // STANDARD, DELUXE

    private int price;

    public RoomType(String name, int price) {
        this.name = name;
        this.price = price;
    }
}