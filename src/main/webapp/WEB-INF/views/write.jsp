<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/asset/css/summernote-lite.min.css"/>
<style type="text/css">
	#bbs table {
	    width:580px;
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
	
		
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script src="resources/asset/js/summernote-lite.js"></script><!-- 스크립트에 js와 css에 css파일의 경로를 위치를 시켜줘야 한다. -->
<script src="resources/asset/js/lang/summernote-ko-KR.js"></script><!-- 한글처리를 하고 싶다면 한글 자바스크립트 파일을 추가로 넣어주면 된다. -->
<script type="text/javascript">

	 $(function(){
		$("#content").summernote({
			lang : "ko-KR",
			width : "580",
			height : "100%",
			maxHeight: 500,
			minHeight: 200,
			focus: true, // 커서를 미리 가져다 놓는다.
			toolbar: [
			    ['style', ['style']],
			    ['font', ['bold', 'italic', 'underline', 'clear']],
			    ['fontname', ['fontname']],
			    ['color', ['color']],
			    ['para', ['ul', 'ol', 'paragraph']],
			    ['height', ['height']],
			    ['table', ['table']],
			    ['insert', ['link', 'picture', 'hr']],
			    ['view', ['fullscreen', 'codeview']],
			    ['help', ['help']]
			  ],
			  callbacks:{ // 콜백함수들을 사용할려면 꼭 callbacks 안에서 수행해야한다.
				onImageUpload:function(files,editor){ // onImageUpload는 에디터에 사진이 올라왔을때 수행되는 이벤트이며 사진을 객체로 배열형태로 받게 된다.
										// 함수안에는 파일의 이름과 에디터의 이름을 지정해주면된다.
										// 이미지가 에디터에 추가될 때 마다 수행하는 곳!
										// 추가되에 들어오는 이미지가 배열로 인식됨!
					for(var i=0; i<files.length; i++) // 배열 형태로 들어오기 때문에 반복문을 돌려 함수를 실행을 시킨다.
						goimg(files[i],editor);
				}
			}

		});
		$("#content").summernote("lineHeight",0.7);
	}); 
	function goimg(files,editor){
		// 받은 값들을 이용해서 비동기식 통신을 해야한다.
		var frm = new FormData();
		// 보내고자 하는 자원을 위해서 만든 폼객체에 파라미터로 넣어준다.
		frm.append("s_file",files);
		
		// 비동기식 통신을 시작하자.
		$.ajax({
			url: "saveImg.inc",
			data: frm,
			type: "post",
			contentType: false,
			processData: false,
			cache: false,
			dataType:"json"
		}).done(function(data){
			var url = data.urls; // 이미지가 저장된 경로
			var fname = data.name; // 파일명
			alert(data.path+data.name)
			$("#content").summernote("editor.insertImage",url+"/"+fname);
		}).fail(function(err){
			// 서버에서 오류가 발생시
			alert("왜이래..");
		});
	}

	function sendData(){
		if(document.forms[0].subject.value.trim().length < 0){
			alert("제목을 입력하세요");
			document.forms[0].subject.focus();
			return;//수행 중단
		}
		if(document.forms[0].writer.value.trim().length < 0){
			alert("작성자를 입력하세요");
			document.forms[0].witer.focus();
			return;//수행 중단
		}
		if(document.forms[0].pwd.value.trim().length < 0){
			alert("비밀번호를 입력하세요");
			document.forms[0].pwd.focus();
			return;//수행 중단
		}
		

//		document.forms[0].action = "test.jsp";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<div id="bbs">
	<form action="write.inc" method="post" 
	encType="multipart/form-data">
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45"/></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12"/></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><textarea name="content" cols="50" 
							rows="8" id="content"></textarea></td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td><input type="file" name="file"/></td>
				</tr>

				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" size="12"/></td>
				</tr>

				<tr>
					<td colspan="2">
						<input type="button" value="보내기"
						onclick="sendData()"/>
						<input type="button" value="다시"/>
						<input type="button" value="목록"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
</body>
</html>