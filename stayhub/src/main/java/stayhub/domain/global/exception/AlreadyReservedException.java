package stayhub.domain.global.exception;

public class AlreadyReservedException extends RuntimeException {

    public AlreadyReservedException() {
        super("이미 예약된 날짜입니다.");
    }
}