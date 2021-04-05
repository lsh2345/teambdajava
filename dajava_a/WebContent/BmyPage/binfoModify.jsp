<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.*" %>
<%MypageVo mv = (MypageVo)request.getAttribute("mv");
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
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/bmyinfo.do');
			}else if(index==1){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/memberlist.do');				
			}else if(index==2){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/myroomdp1.do');					
			}else if(index==3){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/breserv_pres.do');					
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
		
		alert("수정합니다!!");                                          
		document.frm.action ="<%=request.getContextPath()%>/bmypage/binfoModifyAction.do?midx=midx";
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
	<a href="<%=request.getContextPath()%>/bmypage/bmyinfo.do" class="mypagemain"><h1>마이페이지(관리자)</h1></a>
	<nav>
		<hr/>
		<div class="nav_menu menu1" id="first">관리자정보</div>
		<hr/>
		<div class="nav_menu">회원 관리</div>
		<hr/>
		<div class="nav_menu">객실 관리</div>
		<hr/>
		<div class="nav_menu">예약 관리</div>	
		<hr/>																
	</nav>
	<article>
		<div id="contentdiv">
		<div class="menuname"><h2>관리자 정보</h2></div>
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
					<td width="50%;"><input name="password"></td>
					<td></td>			
				</tr>
				<tr>
					<th>E-mail</th>
					<td><input name="email"></td>
					<td></td>					
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input name="phone"></td>
					<td></td>					
				</tr>
				<tr>
					<th>주소</th>
					<td><input name="addr"></td>
					<td></td>					
				</tr>								
			</table>
		</div>
		<button class="btn_c" onclick="check();">등록하기</button>	
		</div>			
	</article>
</section>
</form>
</body>
</html>