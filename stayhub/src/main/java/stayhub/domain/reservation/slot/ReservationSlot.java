package stayhub.domain.reservation.slot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stayhub.domain.reservation.Reservation;
import stayhub.domain.room.Room;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"room_id", "date"})
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReservationSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    private LocalDate date;

    @ManyToOne
    private Reservation reservation;

    public ReservationSlot(Room room, LocalDate date, Reservation reservation) {
        this.room = room;
        this.date = date;
        this.reservation = reservation;
    }
}