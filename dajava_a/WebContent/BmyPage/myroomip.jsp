<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String roomnum = (String) request.getAttribute("roomnum");  %>
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
			$(location).attr('href','<%=request.getContextPath()%>/bmypage/breserve_pres.do');					
		}
	});	    
});

function fnGetdata(){
    var obj = $("[name=rsupply1]");
    var chkArray = new Array(); // 배열 선언
	
    $('input:checkbox[name=rsupply1]:checked').each(function() { // 체크된 체크박스의 value 값을 가지고 온다.
        chkArray.push(this.value);
    });
    $('#rsupply').val(chkArray);
   
}

	function check(){

		if(document.frm.rbaseperson.value == ""){
			alert("기준인원을 입력해주세요");
			document.frm.rbaseperson.focus();
			return;
		}else if(document.frm.rfullperson.value == ""){
			alert("최대인원을 입력해주세요");
			document.frm.rfullperson.focus();
			return;
		}else if(document.frm.rbaseprice.value == ""){
			alert("기준인원가격을 입력해주세요");
			document.frm.rbaseprice.focus();
			return;
		}else if(document.frm.raddperson.value == ""){
			alert("초과시 1인당 가격을 입력해주세요");
			document.frm.raddperson.focus();
			return;
		}else if(document.frm.roffseason1.value == ""){
			alert("비성수기평일가격을 입력해주세요");
			document.frm.roffseason1.focus();
			return;
		}else if(document.frm.roffseason2.value == ""){
			alert("비성수기주말가격을 입력해주세요");
			document.frm.roffseason2.focus();
			return;
		}else if(document.frm.rbusyseason1.value == ""){
			alert("성수기평일가격을 입력해주세요");
			document.frm.rbusyseason1.focus();
			return;
		}else if(document.frm.rbusyseason2.value == ""){
			alert("성수기주말가격을 입력해주세요");
			document.frm.rbusyseason2.focus();
			return;
		}
		
		alert("수정합니다!!");                                          
		document.frm.action ="<%=request.getContextPath()%>/bmypage/roomModifyAction.do?roomnum=<%=roomnum%>";
		document.frm.method ="post";
		document.frm.enctype="multipart/form-data";      
		document.frm.submit();
		return;
	}
	</script>
<style>

.menuname{
 	display:flex;
	align-items:center;
}

.sel{
	width:7rem;
	height:2rem;
	margin-left:1rem;
	font-size:1rem;
	padding-left:1rem;
}
.btn{
	 background-color:pink;
	 border-color:pink;
}

.menu1{
	background-color:pink;
}

.tabletype2 input{
	margin:0;
	padding:0;
	width:70%;
}

.roomnum{
	text-align:center;
	height:2rem;
	width:7rem; 
	font-size:1.3rem;
}
</style>
</head>
<body>
<form name="frm">
<section>
	<a href="<%=request.getContextPath()%>/bmypage/bmyinfo.do" class="mypagemain"><h1>마이페이지(관리자)</h1></a>
	<nav>
		<hr/>
		<div class="nav_menu" id="first">관리자정보</div>
		<hr/>
		<div class="nav_menu">회원 관리</div>
		<hr/>
		<div class="nav_menu menu1">객실 관리</div>
		<hr/>
		<div class="nav_menu">예약 관리</div>	
		<hr/>																
	</nav>
	<article>
		<div id="contentdiv">
		<div class="menuname"><h2>객실 번호</h2>&nbsp;&nbsp;&nbsp;
			<input type="text" class="roomnum" value="<%=roomnum%>">
		</div>
		<hr/>	
		<p/>
			<div class="sub">
			<div class="submenuname"><b>객실 기본 정보</b></div>		
			<hr/>	
			<div class="tabletype2">	
				<table width="66rem">
					<tr class="firstcolumn">
						<th width="25%">기준 인원</th>
						<th width="25%">최대 인원</th>
						<th width="25%">기준인원 가격</th>
						<th width="25%">초과시 1인당 가격</th>
					</tr>
					<tr>
						
						<td><input name="rbaseperson"></td>
						<td><input name="rfullperson"></td>
						<td>&#8361; <input name="rbaseprice"></td>
						<td>&#8361; <input name="raddperson"></td>
					</tr>	
					<tr class="firstcolumn">
						<th>비성수기평일</th>
						<th>비성수기주말</th>
						<th>성수기평일</th>
						<th>성수기주말</th>
					</tr>
					<tr>
						<td>&#8361; <input name="roffseason1"></td>
						<td>&#8361; <input name="roffseason2"></td>
						<td>&#8361; <input name="rbusyseason1"></td>
						<td>&#8361; <input name="rbusyseason2"></td>
					</tr>							
				</table>
			</div>	
			</div>
			
			<div class="sub">
			<div class="submenuname"><b>객실 내 구비용품</b>&nbsp;&nbsp;</div>		
			<hr/>	
			<div class="tabletype1">	
				<table class="t1" width="80%;">
					<tr>
						<td><input type="checkbox" name="rsupply1" value="1" >TV</td>
						<td><input type="checkbox" name="rsupply1" value="2">식탁</td>
						<td><input type="checkbox" name="rsupply1" value="3">냉장고</td>
						<td><input type="checkbox" name="rsupply1" value="4">드라이기</td>
						<td><input type="checkbox" name="rsupply1" value="5">취사도구</td>
						<td><input type="checkbox" name="rsupply1" value="6">전기밥솥</td>
					</tr>
					<tr>
						<td><input type="checkbox" name="rsupply1" value="7">인터넷</td>
						<td><input type="checkbox" name="rsupply1" value="8">욕실용품</td>
						<td><input type="checkbox" name="rsupply1" value="9">옷장</td>
						<td><input type="checkbox" name="rsupply1" value="10">에어컨</td>
						<td colspan="2"><input type="button" name="btn" class="btn" value="구비용품입력완료확인" onclick="fnGetdata();">
						<input type="hidden" name="rsupply" id="rsupply"></td>
					</tr>							
				</table>
			</div>	
			</div>
			<div class="sub">
			<div class="submenuname"><b>객실사진</b></div>		
			<hr/>
				<input type="file" name="fileName" >	
			</div>	
			<button class="btn_c" style="width:20%; height:25px" onclick="check();">등록하기</button>
			</div>			
	</article>
</section>	
</form>
</body>
</html>