<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.co.hk.*" %>
<%@ page import="java.util.*" %>
<%
	List<SBoardModel> list = (List<SBoardModel>)request.getAttribute("list");
	int pagingCount = (int)request.getAttribute("pagingCount");
%>

<table>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>등록날짜</th>
		<th>조회수</th>
	</tr>
	<% for(SBoardModel model : list) { %>
		<tr>
			<td><%=model.getBoard_no() %></td>
			<td><%=model.getBoard_title() %></td>
			<td><%=model.getRegdate() %></td>
			<td><%=model.getCnt() %></td>
		</tr>
	<% } %>
</table>
<div>
	<% 
		if(pagingCount > 1) {
			for(int i=1; i<=pagingCount; i++)  {
	%>
			<a href="boardList?searchType=${searchType}&searchWord=${searchWord}&page=<%=i%>"><%=i %></a>
	<%		
			}
		}
		
	%>
</div>
<div>
	<form action="boardList" method="get">
		<select name="searchType">
			<option value="0">전체</option>
			<option value="1">제목</option>
			<option value="2">내용</option>
		</select>
		<input type="text" name="searchWord" value="${searchWord}">
		<input type="submit" value="검색">
	</form>
</div>









