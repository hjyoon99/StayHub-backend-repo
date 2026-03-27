package stayhub.domain.reservation.slot;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stayhub.domain.reservation.Reservation;
import stayhub.domain.reservation.dto.ReservationRequestDto;
import stayhub.domain.room.Room;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSlotService {

    private final ReservationSlotRepository reservationSlotRepository;

    @Transactional
    public void saveSlots(Reservation reservation, Room room, ReservationRequestDto request) {

        List<LocalDate> dates = request.getCheckInDate()
                .datesUntil(request.getCheckOutDate())
                .toList();

        List<ReservationSlot> slots = dates.stream()
                .map(date -> new ReservationSlot(room, date, reservation))
                .toList();

        reservationSlotRepository.saveAll(slots);
    }
}
