<%@page import="util.Utility"%>
<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="java.util.List"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login");	// 리턴값이 오브젝트이므로 형변환
	if(login == null) {
		%>
		<script>
			alert('로그인 후 이용해주세요');
			location.href = "login.jsp"
		</script>
		<%
	}
%>   
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

String choice = (String)request.getAttribute("choice");
String search = (String)request.getAttribute("search");


List<BbsDto> list = (List<BbsDto>)request.getAttribute("bbslist");
int pageBbs = (Integer)request.getAttribute("pageBbs");
int pageNumber = (Integer)request.getAttribute("pageNumber");
%>

<h1>게시판</h1>

<div align="center">
	<table border="1">
		<col width="70">
		<col width="600">
		<col width="100">
		<col width="150">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>조회수</th>
				<th>작성자</th>
			</tr>
		</thead>
		<tbody>
			<%
			if(list == null || list.size() == 0) {
				%>
				<!-- 글이 없을 때 -->
				<tr>
					<td colspan="4">작성된 글이 없습니다</td>
				</tr>
				<%
			} else {
				
				for(int i = 0; i < list.size(); i++) {
					BbsDto dto = list.get(i);
				%>
				<!-- 글이 있을 때 -->
				<tr>
					<th><%=i+1 %></th>
					<td>
						<%=Utility.arrow(dto.getDepth()) %>
							<%	
								if(dto.getDel() == 1) {
									%>
										<font color="#ff0000">** 이 글은 관리자에 의해 삭제된 글입니다.</font>
									<%
								} else {
									%>
									<a href="bbs?param=bbsdetail&seq=<%=dto.getSeq() %>">
										<input type="hidden" name="seq" value="<%=dto.getSeq() %>">
										<%=dto.getTitle() %>
									</a>
									<%
								}
							%>
					</td>
					<td><%=dto.getReadcount() %></td>
					<td><%=dto.getId() %></td>
				</tr>
				<%
				}
			}
			%>
		</tbody>
	</table>
	<br>
		<%
			for(int i = 0; i < pageBbs; i++) {
				// 현재 페이지
				if(pageNumber == i) {
					%>
					<span style="font-size:15pt;color:#0000ff;font-weight:bold;">
						<%=i+1 %>
					</span>
					<%
				} else {
					// 나머지 페이지
					%>
					<a href="#none" title="<%=i+1 %>페이지" onclick="goPage(<%=i %>)" 
						style="font-size: 15pt;color: #000;font-weight: bold;text-decoration: none;">
						[<%=i+1 %>]
					</a>
					<%	
				}
			}
		%>
	
	<br><br>
	<form action="bbs?param=bbslist" method="post">
	<select id="choice" name="choice">
		<option value="" >검색</option>
		<option value="title">제목</option>
		<option value="content">내용</option>
		<option value="writer">작성자</option>
	</select>
	
	<input type="text" id="search" value="" name="search">
	
	<button type="submit">검색</button>
	</form>
	<br><br>
	
	<a href="bbs?param=bbswrite">글쓰기</a>
</div>

<script type="text/javascript">

function searchBtn() {
	let choice = document.getElementById("choice").value;
	let search = document.getElementById("search").value;

	location.href = "bbs?param=bbslist&choice=" + choice + "&search=" + search;
}

function goPage(pageNumber) {
	location.href = "bbs?param=bbslist&choice=<%=choice %>&search=<%=search %>&pageNumber=" + pageNumber;
}
</script>

</body>
</html>