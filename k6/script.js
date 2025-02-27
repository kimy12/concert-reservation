import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = "http://host.docker.internal:8080";

export const options = {
    scenarios: {
        concert_reservation: {
            executor: 'constant-arrival-rate',
            rate: 50,
            timeUnit: '1s',
            duration: '1m30s',
            preAllocatedVUs: 50,
            maxVUs: 150,
        },
    },
    thresholds: {
        http_req_duration: ['p(95)<1000', 'p(99)<1500'],
        http_req_failed: ['rate<0.005'],
        http_reqs: ['rate>100'],
        'http_req_duration{status:200}': ['p(95)<800'],
        'http_req_duration{status:500}': ['p(95)<2500'],
    },
    ext: {
        influxdb: {
            enabled: true,
            address: 'http://localhost:8086',
            database: 'k6',
            tags: { environment: 'staging' },
        },
        console: {
            enabled: true,
            summaryTrendStats: ["avg", "p(90)", "p(95)", "p(99)", "max"],
        },
    },
};

export default function () {
    const userId = Math.floor(Math.random() * 100000) + 1;
    const payload = JSON.stringify({ userId: userId });

    const params = {
        headers: { 'Content-Type': 'application/json' },
    };

    console.log(`요청 데이터: ${payload}`);

    const res = http.post(`${BASE_URL}/token/api/v1`, payload, params);

    check(res, {
        '응답 코드가 201 Created': (r) => r.status === 201,
        '토큰 값이 존재': (r) => {
            try {
                return r.json().token !== undefined;
            } catch (e) {
                return false;
            }
        },
        '상태가 PENDING': (r) => r.json().status === "PENDING",
    });

    // 1~1000 사이의 랜덤한 CONCERT_ID 설정
    const CONCERT_ID = ((__VU * 10 + __ITER) % 1000) + 1;
    console.log(`요청할 콘서트 ID: ${CONCERT_ID}`);

    sleep(10);

    // 콘서트 날짜 조회
    let concertRes = http.get(`${BASE_URL}/concerts/api/v1/${CONCERT_ID}/availableDates`);

    check(concertRes, {
        '콘서트 날짜 조회 성공': (r) => r.status === 200,
    });

    let scheduleId = null;
    try {
        let concertData = JSON.parse(concertRes.body);
        if (concertData.data && concertData.data.length > 0) {
            scheduleId = concertData.data[0].scheduleId;  // 첫 번째 scheduleId 선택
        }
    } catch (e) {
        console.log("응답 데이터 파싱 실패: ", e);
    }

    sleep(10);

    // scheduleId가 있을 경우 예약 가능한 좌석 조회
    if (scheduleId) {
        console.log(`요청할 scheduleId: ${scheduleId}`);
        let seatRes = http.get(`${BASE_URL}/concerts/api/v1/${scheduleId}/availableSeats`);

        check(seatRes, {
            '좌석 조회 성공': (r) => r.status === 200,
        });
    } else {
        console.log("유효한 scheduleId가 없어 좌석 조회를 건너뜁니다.");
    }

    sleep(1);
}
