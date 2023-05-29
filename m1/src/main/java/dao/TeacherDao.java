package dao;

import vo.*;
import util.*;
import java.sql.*;
import java.util.*;

public class TeacherDao {
	//강사 목록 조회(강의하는 강의명 전부 포함)
	public ArrayList<HashMap<String, Object>> selectTeacherListByPage(int beginRow, int rowPerPage) throws Exception{
		//반환 할 리스트 생성
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		//db접속
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		//sql 전송 후 결과셋 반환받아 리스트에 저장
		String sql = "SELECT t.teacher_no teacherNo, t.teacher_id teacherId, t.teacher_name teacherName, nvl(GROUP_CONCAT(s.subject_name), ' ') teacherSubjectName FROM teacher t LEFT OUTER JOIN teacher_subject ts ON t.teacher_no = ts.teacher_no LEFT OUTER JOIN subject s ON ts.subject_no = s.subject_no GROUP BY t.teacher_no, t.teacher_id, t.teacher_name LIMIT ?, ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, beginRow);
		stmt.setInt(2, rowPerPage);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("teacherNo", rs.getInt("teacherNo"));
			m.put("teacherId", rs.getString("teacherId"));
			m.put("teacherName", rs.getString("teacherName"));
			m.put("teacherSubjectName", rs.getString("teacherSubjectName"));
			list.add(m);		
		}
		return list;
	}
	//전체 강사 수 조회
	public int selectTeacherCnt() throws Exception{
		//반환할 전체 행의 수
		int row = 0;
		//db 접속
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		// sql 전송 후 결과셋 반환받아 값 저장
		PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM teacher");
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			row=rs.getInt(1);
		}
		return row;
	}
	//강사 1명 조회
	public Teacher selectTeacherOne(int teacherNo) throws Exception{
		//반환할 객체
		Teacher teacher = new Teacher();
		//db 연결
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		// sql 전송 후 결과셋 반환받아 리스트에 저장
		String sql="SELECT t.teacher_no teacherNo, t.teacher_id teacherId, t.teacher_name teacherName, t.teacher_history teaherHistory, t.updatedate, t.createdate, \r\n"
				+ "nvl(GROUP_CONCAT(s.subject_name), ' ') teacherSubjectName \r\n"
				+ "FROM teacher t \r\n"
				+ "LEFT OUTER JOIN teacher_subject ts \r\n"
				+ "ON t.teacher_no = ts.teacher_no \r\n"
				+ "LEFT OUTER JOIN subject s \r\n"
				+ "ON ts.subject_no = s.subject_no \r\n"
				+ "WHERE t.teacher_no = ?\r\n"
				+ "GROUP BY t.teacher_no, t.teacher_id, t.teacher_name";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, teacherNo);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			teacher.setTeacherNo(rs.getInt("teacherNo"));
			teacher.setTeacherId(rs.getString("teacherId"));
			teacher.setTeacherName(rs.getString("teacherName"));
			teacher.setTeacherHistory(rs.getString("teaherHistory"));
			teacher.setUpdatedate(rs.getString("updatedate"));
			teacher.setCreatedate(rs.getString("createdate"));
			teacher.setTeacherSubjectName(rs.getString("teacherSubjectName"));
		}
		return teacher;
	}
	//강사 수정
	public int updateTeacher(Teacher teacher) throws Exception{
		// 수정 성공여부 반환 값
		int row = 0;
		//db 접속
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		// sql 전송 후 결과셋 반환받아 리스트 저장
		String sql="UPDATE teacher SET teacher_id = ?, teacher_name = ?, teacher_history = ?, createdate = NOW() WHERE teacher_no = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, teacher.getTeacherId());
		stmt.setString(2, teacher.getTeacherName());
		stmt.setString(3, teacher.getTeacherHistory());
		stmt.setInt(4, teacher.getTeacherNo());
		row=stmt.executeUpdate();
		
		return row;
	}
	//강사 삭제
	public int deleteTeacher(int teacherNo) throws Exception{
		// 수정 성공여부 반환 값
		int row = 0;
		//db연결
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		//sql 전송 후 결과셋 반환받아 리스트 저장
		String sql="DELETE FROM teacher WHERE teacher_no=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, teacherNo);
		row = stmt.executeUpdate();
		
		return row;
	}
}	
