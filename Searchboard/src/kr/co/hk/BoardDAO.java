package kr.co.hk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	public static Connection getConn() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "hr";
			String pw = "hkitedu";
			con = DriverManager.getConnection(url, id, pw);	
			System.out.println("연결 허용!");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static int getBoardPagingCount(String searchType, String searchWord) {
		int page = 1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		searchWord = "%" + searchWord + "%";
		try {
			con = getConn();
			String sql = " SELECT ceil(count(*) / ?) FROM s_board ";
			if(searchType.equals("0")) { //전체검색
				sql += " WHERE board_title like ? or board_content like ?";
				
			} else if (searchType.equals("1")) { //제목 검색
				sql += " WHERE board_title like ?";
				
			} else if (searchType.equals("2")) { //내용 검색
				sql += " WHERE board_content like ?";
			}
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, viewCount);
			ps.setString(2, searchWord);
			if(searchType.equals("0")) {
				ps.setString(3, searchWord);
			} 
			rs = ps.executeQuery();
			
			if(rs.next()) {
				page = rs.getInt(1);
			}
		} catch(Exception e) {
			
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return page;
	}

	private static int viewCount = 5;
	public static List<SBoardModel> getBoardList(String searchType, String searchWord, int page) {
		
		int startIndex = (page - 1) * viewCount + 1;
		int endIndex = viewCount * page;
		
		List<SBoardModel> list = new ArrayList<SBoardModel>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		searchWord = "%" + searchWord + "%";
		try {
			con = getConn();
			String sql = " SELECT * FROM ("
					+ " SELECT A.*, ROW_NUMBER() OVER(ORDER BY board_no DESC) AS NUM "
					+ " FROM s_board A ";			
			if(searchType.equals("0")) { //전체검색
				sql += " WHERE board_title like ? or board_content like ?";
				
			} else if (searchType.equals("1")) { //제목 검색
				sql += " WHERE board_title like ?";
				
			} else if (searchType.equals("2")) { //내용 검색
				sql += " WHERE board_content like ?";
			}
			
			sql += " ) WHERE NUM BETWEEN ? AND ? ";
						
			int idx = 0;
			ps = con.prepareStatement(sql);
			ps.setString(++idx, searchWord);
			if(searchType.equals("0")) {
				ps.setString(++idx, searchWord);
			} 
			ps.setInt(++idx, startIndex);
			ps.setInt(++idx, endIndex);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				SBoardModel model = new SBoardModel();
				model.setBoard_no(rs.getInt("board_no"));
				model.setBoard_title(rs.getString("board_title"));
				model.setBoard_content(rs.getString("board_content"));
				model.setRegdate(rs.getString("regdate"));
				model.setCnt(rs.getInt("cnt"));				
				list.add(model);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return list;
	}
}
