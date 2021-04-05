<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.*" %>
<%MypageVo mv = (MypageVo)request.getAttribute("mv"); 
String name = (String)session.getAttribute("name");
int midx = (int)session.getAttribute("midx");%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보</title>
<link href="../mypagestyle.css" rel="stylesheet" type="text/css">
<script src="../jquery-3.5.1.min.js"></script>  
<script type="text/javascript">
	$(document).ready(function(){	
	    $('.nav_menu').each(function(index){
	  		$(this).attr('menu-index',index);
		}).click(function(){
			var index = $(this).attr('menu-index');
			$('.nav_menu[menu-index='+index+']').addClass('menu_clicked');
			$('.nav_menu[menu-index!='+index+']').removeClass('menu_clicked');			
			if(index==0){
				$(location).attr('href','<%=request.getContextPath()%>/mypage/myinfo.do');
			}else if(index==1){
				$(location).attr('href','<%=request.getContextPath()%>/mypage/reserve_pres.do');				
			}else if(index==2){
				$(location).attr('href','<%=request.getContextPath()%>/mypage/point.do');					
			}else if(index==3){
				$(location).attr('href','<%=request.getContextPath()%>/mypage/myreview.do');					
			}else if(index==4){
				$(location).attr('href','<%=request.getContextPath()%>/mypage/myqa.do');					
			}
		});	    
	});
	
	function check(){
		
		if(document.frm.id.value == ""){
			alert("아이디를 입력해주세요");
			document.frm.id.focus();
			return;
		}else if(document.frm.password.value == ""){
			alert("비밀번호를 입력해주세요");
			document.frm.password.focus();
			return;
		}else if(document.frm.email.value == ""){
			alert("내용을 입력해주세요");
			document.frm.email.focus();
			return;
		}else if(document.frm.phone.value == ""){
			alert("작성자를 입력해주세요");
			document.frm.phone.focus();
			return;
		}else if(document.frm.addr.value == ""){
			alert("비밀번호를 입력해주세요");
			document.frm.addr.focus();
			return;
		}
		
		alert("수정합니다!!test");                                          
		var url = '<%=request.getContextPath()%>';
		document.frm.action =url+"/mypage/infoModifyAction.do";
		document.frm.method ="post";
	//	document.frm.enctype="multipart/form-data";      
		document.frm.submit();
		return;
	}
</script>
<style>
.menu1{
	background-color:pink;
}

.btn_c{
 	width:20%; 
 	height:3rem; 
 	background:pink;
 	border:none;
 	font-size:1.1rem;
 
}
</style>
</head>
<body>
<form name="frm">
<section>
	<a href="<%=request.getContextPath()%>/mypage/myinfo.do" class="mypagemain"><h1>마이페이지(일반회원)</h1></a>
	<nav>
		<hr/>
		<div class="nav_menu menu1" id="first">내 정보</div>
		<hr/>
		<div class="nav_menu">예약 정보</div>
		<hr/>
		<div class="nav_menu">적립금</div>
		<hr/>
		<div class="nav_menu">나의 이용후기</div>	
		<hr/>
		<div class="nav_menu">나의 질문</div>
		<hr/>																
	</nav>
	<article>
		<div class="memberinfo">
	
	     	<b><%=name %>ddd</b> 님 
		</div>
		<p/>
		<div id="contentdiv">
		<div class="menuname"><h2>내 정보</h2></div>
		<hr/>	
		<p/>				
		<div class="tabletype1">		
			<table>
				<tr>
					<th width="30%;">아이디</th>
					<td colspan="2"><input name="id"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td width="50%;"><input type="password" name="password""></td>
						
				</tr>
				<tr>
					<th>E-mail</th>
					<td><input name="email"></td>
								
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input name="phone"></td>
									
				</tr>
				<tr>
					<th>주소</th>
					<td><input name="addr"></td>
								
				</tr>								
			</table>
		</div>	
		<button class="btn_c"  onclick="check();">등록하기</button>
	</div>	
	</article>
</section>
</form>
</body>
</html>