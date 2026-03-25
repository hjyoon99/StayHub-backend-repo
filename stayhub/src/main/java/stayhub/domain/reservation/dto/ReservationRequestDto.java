package stayhub.domain.reservation.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationRequestDto {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
