## 플로우 차트
<details>
<summary>콘서트 예약</summary>
<div markdown="1">

![flowChart.png](docs/images/flowChart.png)

</div>
</details>


## 시퀀스 다이어그램
<details>
<summary>콘서트 검색</summary>
<div markdown="1">

![sequence_1.png](docs/images/sequence_1.png)

</div>
</details>

<details>
<summary>유저 토큰 발급</summary>
<div markdown="1">

![sequence_2.png](docs/images/sequence_2.png)

</div>
</details>

<details>
<summary>예약 가능한 날짜 및 좌석</summary>
<div markdown="1">

![sequence_3.png](docs/images/sequence_3.png)

</div>
</details>

<details>
<summary>(임시)좌석 예약 요청</summary>
<div markdown="1">

![sequence_4.png](docs/images/sequence_4.png)

</div>
</details>

<details>
<summary>잔액 충전 및 조회</summary>
<div markdown="1">

![sequence_5.png](docs/images/sequence_5.png)

</div>
</details>

<details>
<summary>결제</summary>
<div markdown="1">

![sequence_6.png](docs/images/sequence_6.png)

</div>
</details>


## erd
![erd.png](docs/images/erd.png)

- - -

## 패키지 구조

```test
api
├─ common
│  ├─ exception                 
│  └─ response              # api 공통 response 
│  
├─ 도메인                     # point, token, concert 등 도메인 별 패키지
│  ├─ application
│  │   └─ facade
│  │
│  ├─ domain
│  │  ├─ service
│  │  ├─ repository(interface)
│  │  └─ domain
│  │
│  ├─ infrastructure
│  │  ├─ jpaRepository
│  │  └─ repositoryImpl  
│  │
│  └─ presentation  
│     ├─ controller
│     └─ dto
```




