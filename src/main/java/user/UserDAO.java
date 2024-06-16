package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ? ";
		try {
			pstmt = conn.prepareStatement(SQL);
			// 매개변수로 들어온 userID를 위 SQL 쿼리 ? 자리에 들어갈 수 있도록 함으로써
			// 데이터베이스에 접속하고자 하는 사용자의 정보를 가져옴
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// 아이디가  있는 경우
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}else {
					return 0; // 비밀번호 불일치
				}
			}
			return -1; // 아이디가 없
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; // 데이터베이스 호출
	}
}
