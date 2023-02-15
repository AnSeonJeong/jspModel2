<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String message = (String)request.getAttribute("message");
if(message != null && !message.equals("")) {
	if(message.equals("MEMBER_YES")) {
		%>
		<script type="text/javascript">
			alert("성공적으로 가입되었습니다");
			location.href = "member?param=login";
		</script>
		<%
	}  else {		
		%>
		<script type="text/javascript">
			alert("가입되지 않았습니다. 다시 가입해 주십시오");
			location.href = "member?param=regi";
		</script>
		<%
	}
}

String bbswrite = (String)request.getAttribute("bbswrite");
if(bbswrite != null && !bbswrite.equals("")){
	if(bbswrite.equals("BBS_ADD_OK")){
		%>
		<script type="text/javascript">
		alert("성공적으로 작성되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("다시 작성해 주십시오");
		location.href = "bbs?param=bbswrite";
		</script>
		<%
	}
}

String bbsanswer = (String)request.getAttribute("bbsanswer");
if(bbsanswer != null && !bbsanswer.equals("")){
	if(bbsanswer.equals("ANSWER_OK")){
		%>
		<script type="text/javascript">
			alert("성공적으로 작성되었습니다");
			location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
			alert("다시 작성해 주십시오");
			location.href = "bbs?param=bbsanswer";
		</script>
		<%
	}
}

int seq = (Integer)request.getAttribute("seq");
String bbsUpdate = (String)request.getAttribute("bbsUpdate");
if(bbsUpdate != null && !bbsUpdate.equals("")){
	if(bbsUpdate.equals("UPDATE_OK")){
		%>
			<script type="text/javascript">
			alert("글이 수정되었습니다");
			location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
			alert("다시 수정해 주십시오");
			let seq = "<%=seq %>";
			location.href = "bbs?param=updateBbs&seq=" + seq;
		</script>
		<%
	}
}

String bbsDelete = (String)request.getAttribute("bbsDelete");
if(bbsDelete != null && !bbsDelete.equals("")){
	if(bbsDelete.equals("DELETE_OK")){
		%>
		<script type="text/javascript">
			alert("글이 삭제되었습니다");
			location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
			alert("글이 삭제되지 않았습니다");
			let seq = "<%=seq %>";
			location.href = "bbs?param=bbsdetail&seq=" + seq;
		</script>
		<%
	}
}
%>