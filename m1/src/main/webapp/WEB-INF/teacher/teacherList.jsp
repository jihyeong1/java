<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>
<%
	// 현재페이지
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	// sql 메서드들이 있는 클래스의 객체 생성
	TeacherDao tDao = new TeacherDao();
	// 전체 행의 수
	int totalRow = tDao.selectTeacherCnt();
	// 페이지 당 행의 수
	int rowPerPage = 10;
	// 시작 행 번호
	int beginRow = (currentPage-1) * rowPerPage;
	// 마지막 페이지 번호
	int lastPage = totalRow / rowPerPage;
	// 표시하지 못한 행이 있을 경우 페이지 + 1
	if(totalRow % rowPerPage != 0) {
		lastPage = lastPage + 1;
	}
	// 현재 페이지에 표시 할 리스트
	ArrayList<HashMap<String, Object>> list = tDao.selectTeacherListByPage(beginRow, rowPerPage);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherList</title>
<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container">
	<h1>강사 리스트</h1>
	<div class="d-grid">
		<a href="<%=request.getContextPath()%>/">
			<button type="button" class="btn btn-dark btn-block">추가</button>
		</a>
	</div>
		<table class="table table-bordered">
			<tr>
				<th class="table-dark">강사ID</th>
				<th class="table-dark">강사이름</th>
				<th class="table-dark">담당과목</th>
			</tr>
			<%
				for(HashMap<String, Object> m : list) {
			%>
					<tr>
						<td><%=m.get("teacherId")%></td>
						<td>
							<a href="<%=request.getContextPath()%>/teacher/teacherOne.jsp?teacherNo=<%=m.get("teacherNo")%>">
								<%=m.get("teacherName")%>
							</a>		
						</td>
						<td><%=m.get("teacherSubjectName")%></td>
					</tr>
			<%		
				}
			%>
		</table>

	<%
		// 페이징 수
		int pagePerPage = 10;
		// 최소 페이지
		int minPage = (currentPage-1) / pagePerPage * pagePerPage + 1;
		// 최대 페이지
		int maxPage = minPage + pagePerPage - 1;
		// 최대 페이지가 마지막 페이지 보다 크면 최대 페이지 = 마지막 페이지
		if(maxPage > lastPage) {
			maxPage = lastPage;
		}
		// 이전 페이지
		// 최소 페이지가 1보타 클 경우 이전 페이지 표시
		if(minPage>1) {
	%>
			<a href="<%=request.getContextPath()%>/teacher/teacherList.jsp?currentPage=<%=minPage-pagePerPage%>">이전</a>
	<%			
		}
		// 최소 페이지부터 최대 페이지까지 표시
		for(int i = minPage; i<=maxPage; i=i+1) {
			if(i == currentPage) {	// 현재페이지는 링크 비활성화
	%>	
			<%=i%>
	<%			
			}else {					// 현재페이지가 아닌 페이지는 링크 활성화
	%>	
				<a href="<%=request.getContextPath()%>/teacher/teacherList.jsp?currentPage=<%=i%>"><%=i%></a>
	<%				
			}
		}
		// 다음 페이지
		// 최대 페이지가 마지막 페이지와 다를 경우 다음 페이지 표시
		if(maxPage != lastPage) {
	%>
			<a href="<%=request.getContextPath()%>/teacher/teacherListjsp?currentPage=<%=minPage+pagePerPage%>">다음</a>
	<%	
		}
	%>
	</div>
</body>
</html>