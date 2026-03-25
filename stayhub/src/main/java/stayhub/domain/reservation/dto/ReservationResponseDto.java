package stayhub.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationResponseDto {
    private Long reservationId;
    private String status;
}
