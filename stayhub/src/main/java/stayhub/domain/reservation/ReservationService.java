package stayhub.domain.reservation;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stayhub.domain.global.exception.AlreadyReservedException;
import stayhub.domain.reservation.dto.ReservationRequestDto;
import stayhub.domain.reservation.dto.ReservationResponseDto;
import stayhub.domain.reservation.slot.ReservationSlotService;
import stayhub.domain.room.Room;
import stayhub.domain.room.RoomRepository;
import stayhub.domain.user.User;
import stayhub.domain.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationSlotService reservationSlotService;

    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("방 없음"));

        Reservation reservation = reservationRepository.save(
                new Reservation(user, room, request.getCheckInDate(), request.getCheckOutDate())
        );

        try {
            reservationSlotService.saveSlots(reservation, room, request);

        } catch (DataIntegrityViolationException e) {

            throw new AlreadyReservedException("이미 예약된 방입니다.");
        }

        return new ReservationResponseDto(reservation.getId(), "CONFIRMED");
    }
}