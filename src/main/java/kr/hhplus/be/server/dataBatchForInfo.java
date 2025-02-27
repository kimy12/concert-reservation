package kr.hhplus.be.server;

import kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus;

import java.sql.*;

public class dataBatchForInfo {
    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/hhplus?characterEncoding=UTF-8&serverTimezone=UTC";
        String url = "jdbc:mysql://mysql:3306/hhplus?characterEncoding=UTF-8&serverTimezone=UTC";
        String user = "application";
        String password = "application";

        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            // 1. 데이터베이스 연결
            connection = DriverManager.getConnection(url, user, password);

            // 2. 자동 커밋 비활성화 (트랜잭션 관리)
            connection.setAutoCommit(false);

            // 3. SQL 준비
            String sql = "INSERT INTO concert_info (title) VALUES (?)";
            pstmt = connection.prepareStatement(sql);

            int batchSize = 1000;  // 한 번에 처리할 배치 크기
            int totalData = 1000;  // 삽입할 총 데이터 수

            ConcertSeatStatus[] statuses = ConcertSeatStatus.values();

            String title = "title";
            for (int i = 1; i <= totalData; i++) {
                // 4. 데이터 설정


                pstmt.setString(1, title + i);

                // 5. 배치에 추가
                pstmt.addBatch();

                // 6. 배치 실행 조건 체크
                if (i % batchSize == 0) {
                    pstmt.executeBatch();  // 배치 실행
                    connection.commit();  // 커밋
                    System.out.println(i + " records inserted.");
                }
            }

            // 7. 남은 배치 처리
            pstmt.executeBatch();
            connection.commit();

            System.out.println("All records inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();  // 오류 발생 시 롤백
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // 8. 자원 해제
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}
