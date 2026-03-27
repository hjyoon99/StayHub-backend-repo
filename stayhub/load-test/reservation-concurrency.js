import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 100, // 동시 유저 수
    duration: '30s', // 테스트 시간
};

export default function () {

    const url = 'http://localhost:8080/reservations';

    const payload = JSON.stringify({
        userId: 1,
        roomId: 1,
        checkInDate: '2025-04-01',
        checkOutDate: '2025-04-08'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, payload, params);

    check(res, {
        'status is 200 or 500': (r) => r.status === 200 || r.status === 500,
    });

    sleep(0.1);
}