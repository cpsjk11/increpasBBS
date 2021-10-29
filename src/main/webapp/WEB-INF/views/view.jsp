<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#bbs table {
	    width:750px;
	    margin-left:10px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	
	.odd {background:silver}
	.hide{display: none;}
		
</style>
</head>
<body>
<c:if test="${vo ne null }">
	<div id="bbs">
	<form method="post" >
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>

			<tbody>
				<tr>
					<th>제목:</th>
					<td>${vo.subject} </td>
				</tr>
				<c:if test="${vo.file_name ne null }">
				<tr>
					<th>첨부파일:</th>
					<td><a href="javascript: down('${vo.file_name}')">
						${vo.file_name }
					</a></td>
				</tr>
				</c:if>
				<tr>
					<th>이름:</th>
					<td>${vo.writer }</td>
				</tr>
				<tr>
					<th>내용:</th>
					<td>${vo.content }</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" value="수정" onclick="up()"/>
						<input type="button" value="삭제" onclick="del()"/>
						<input type="button" value="목록"
							onclick="golist()"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<form method="post" action="ans_write.mo">
		이름:<input type="text" name="writer"/><br/>
		내용:<textarea rows="4" cols="55" name="content"></textarea><br/>
		비밀번호:<input type="password" name="pwd"/><br/>
		
		<%-- 원글을 의미하는 원글의 기본키 --%>
		<input type="hidden" name="ip" value="${ip}">
		<input type="hidden" name="b_idx" value="${vo.b_idx}">
		<input type="hidden" name="cPage" value="${cPage}"/><%-- ans_write.jsp에서 댓글을 저장한 후 다시 view.jsp로 돌아올 때 필요하다. --%>
		<!-- <input type="submit" value="저장하기"/>  -->
		<button type="button" onclick="ans_send()">저장</button>
	</form>
	<p/>
	댓글들<hr/>
	<c:forEach var="co" items="${vo.c_list}" varStatus="st">
			<div id="ans"></div>
			<div>
				이름:${co.writer} &nbsp;&nbsp;
				날짜:${fn:substring(co.write_date,0,10)}<br/>
				내용:${co.content }
			</div>
		<hr/>
	</c:forEach>
	</div>
	<form name="frm" method="post" action="">
		<input type="hidden" name="cPage" value="${param.cPage}"/>
		<input type="hidden" name="b_idx" value="${vo.b_idx}"/>
		<input type="hidden" name="bname" value="${vo.bname }"/>
	</form>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
	function golist(){
		document.frm.action = "list.inc";
		document.frm.submit();
	}
	function del(){
		
		if(confirm("삭제하시겠습니까?")){
		
		document.frm.action = "delete.mo"
		document.frm.submit();
		}
	}
	function up(){
		document.frm.action = "edit.inc"
		document.frm.submit();
	}
	function down(fname){
		location.href="FileDownload?dir=bbs_upload&filename="+fname;
	}
	
	/* 비동기식으로 댓글 달기!! */
	function ans_send(){
		var writer = $('input[name=writer]').val();
		var content = $('textarea[name=content]').val();
		var pwd = $('input[name=pwd]').val();
		var cPage = $('input[name=cPage]').val();
		var ip = $('input[name=ip]').val();
		var b_idx = $('input[name=b_idx]').val();
		var param = "writer="+encodeURIComponent(writer)+"&content="+encodeURIComponent(content)+"&pwd="+encodeURIComponent(pwd)+
					"&cPage="+encodeURIComponent(cPage)+"&ip="+encodeURIComponent(ip)+"&b_idx="+encodeURIComponent(b_idx);
		$.ajax({
			url:"ans_write.mo",
			data:param,
			type:"post",
			dataType:"json",
		}).done(function(data){
			alert(data.content);
			var a = "이름:"+data.writer+"&nbsp;&nbsp;&nbsp;"+"날짜:"+data.date+"<br/>"+"내용:"+data.content;
			$("#ans").html(a);
		}).fail(function(err){
			
		});
	}
</script>
</c:if>
<c:if test="${vo eq null }">
	<script>loaction.href="list.mo";</script>
</c:if>
</body>
</html>