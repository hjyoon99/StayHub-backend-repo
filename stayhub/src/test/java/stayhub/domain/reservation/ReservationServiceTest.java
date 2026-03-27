package stayhub.domain.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stayhub.domain.reservation.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void 동시_예약_테스트() throws InterruptedException {

        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    ReservationRequestDto request = new ReservationRequestDto(
                            1L,
                            1L,
                            LocalDate.of(2026, 3, 28),
                            LocalDate.of(2026, 3, 30)
                    );

                    reservationService.createReservation(request);

                    System.out.println("예약 성공");

                } catch (Exception e) {
                    System.out.println("예약 실패: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }
}