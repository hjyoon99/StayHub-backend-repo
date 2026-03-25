package stayhub.domain.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stayhub.domain.reservation.dto.ReservationRequestDto;
import stayhub.domain.reservation.dto.ReservationResponseDto;
import stayhub.domain.room.Room;
import stayhub.domain.room.RoomRepository;
import stayhub.domain.user.User;
import stayhub.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        // 1. 유저 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        // 2. 방 조회
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("방 없음"));

        // 3. 날짜 겹침 체크
        boolean exists = reservationRepository
                .existsByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                        room.getId(),
                        request.getCheckOutDate(),
                        request.getCheckInDate()
                );

        if (exists) {
            throw new IllegalStateException("이미 예약된 방입니다");
        }

        // 4. 예약 생성
        Reservation reservation = new Reservation(
                user,
                room,
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        // 5. 저장
        reservationRepository.save(reservation);

        return new ReservationResponseDto(reservation.getId(), "CONFIRMED");
    }
}