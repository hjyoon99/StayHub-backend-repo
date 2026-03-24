package stayhub.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stayhub.domain.reservation.Reservation;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String status; // SUCCESS, FAIL

    private int amount;

    private LocalDateTime createdAt;

    public Payment(Reservation reservation, int amount) {
        this.reservation = reservation;
        this.amount = amount;
        this.status = "SUCCESS";
        this.createdAt = LocalDateTime.now();
    }

    public void fail() {
        this.status = "FAIL";
    }
}