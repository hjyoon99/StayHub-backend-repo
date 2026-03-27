package stayhub.domain.global.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 예약 중복 예외
    @ExceptionHandler(AlreadyReservedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyReserved(AlreadyReservedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("ALREADY_RESERVED", e.getMessage()));
    }

    // DB unique constraint 예외 (혹시 서비스에서 못 잡았을 때 대비)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("DATA_INTEGRITY", "이미 예약된 데이터입니다."));
    }

    // 모든 예외 fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", e.getMessage()));
    }
}