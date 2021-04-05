<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="service.*" %>
<%@ page import ="domain.*" %>
<%
	ArrayList<OrderDto> alist = (ArrayList<OrderDto>)request.getAttribute("alist"); 
 	PageMaker pm = (PageMaker)request.getAttribute("pm");
%>  


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자마이페이지</title>
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

	$(document).ready(function(){	
    	$('.submenu_span').each(function(index){
  				$(this).attr('submenu-index',index);
		}).click(function(){
			var index = $(this).attr('submenu-index');
			$('.submenu_span[submenu-index='+index+']').addClass('menu_clicked');
			$('.submenu_span[submenu-index!='+index+']').removeClass('menu_clicked');
			if(index==0){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/breserve_pres.do');	
			}else if(index==1){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/breserve_canc.do');				
			}else if(index==2){
				$(location).attr('href','<%=request.getContextPath()%>/bmypage/breserve_past.do');			
			}
		}); 
    	
    	$(".ostate").each(function(){
    		var state = $(this).val();
    		if(state=="Y"){
    			$(this).next().text("완료");
    		}else if(state=="N"){
    			$(this).next().text("대기");
    		}else{
    			$(this).next().text(state);
    		}
    	});
	});  	
	
	function go(){             
		frm.action="<%=request.getContextPath()%>/bmypage/breserve_pres.do";
		frm.method="get";
		frm.submit();
	}
</script>	        
<style>
.menu1,.subm{
	background-color:pink;
}

.firstcolumn{
	color:#fff;
	background-color:#6c6c6c;
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
		<div class="nav_menu">객실 관리</div>
		<hr/>
		<div class="nav_menu  menu1">예약 관리</div>	
		<hr/>																
	</nav>
	<article>
		<div id="contentdiv">
		<div class="menuname"><h2>예약 정보</h2></div>
		<hr/>
        <p/>		
		<div class="submenu">
			<span class="submenu_span first subm">예약 중</span> <span class="submenu_span">취소/환불</span> <span class="submenu_span">지난 예약</span>
		<hr class="submenubottom"/>		
		</div>  

		<div class="infotable">		
			
			<table>
				<tr>
					<td><input type="text" name="keyword"></td>
					<td><input type="button" name="btn" value="이름검색" onclick="go();"></td>   
				</tr>
			</table>	
			
			<div class="tabletype2" id="sub1">
				<table>
					<tr class="firstcolumn">
						<th width="10%">일련번호</th>
						<th width="13%;">예약자</th>
						<th width="15%;">연락처</th>
						<th width="14%;">객실(호수)</th>
						<th width="20%;">예약날짜</th>																		
						<th width="14%;">예약상태</th>																		
						<th width="14%;">예약취소</th>																		
					</tr>
					
					
					<%
						int count = alist.size();
						for(OrderDto odv : alist){
					%>
					<tr>
						<%-- <td><%=pm.getTotalCount()-((alist.indexOf(odv)+1)+(pm.getScri().getPage()-1)*pm.getScri().getPerPageNum())+1 %></td> --%>
						<td><%=count %></td>
						<td><%=odv.getName() %></td>
						<td><%=odv.getPhone() %></td>
						<td><%=odv.getRoomnum() %></td>
						<td><%=odv.getOstart().substring(0,10) %>~<%=odv.getOend().substring(0,10) %></td>
						<td><input type="hidden" class="ostate" value="<%=odv.getOstate() %>"><sapn class="re_ostate"></sapn></td>
						<td><button type="button" onclick="location.href='<%=request.getContextPath()%>/bmypage/cancelState.do?oidx=<%=odv.getOidx()%>'">취소</button></td>																
					</tr>	
					<% count--;} %>								
				</table>
				<table>
					<tr>
						<td><%if (pm.isPrev()==true){ %>
						<a href="<%=request.getContextPath()%>/bmypage/breserve_pres.do?page=<%=pm.getStartPage()-1%>&keyword=<%=pm.encoding(pm.getScri().getKeyword())%>">◀</a>
						<%} %>
						</td>
						<td>
						<% for(int i=pm.getStartPage(); i<=pm.getEndPage(); i++){ %>
						<a href="<%=request.getContextPath()%>/bmypage/breserve_pres.do?page=<%=i%>&keyword=<%=pm.encoding(pm.getScri().getKeyword())%>"><%=i %></a>
						<%} %>
						</td>
						<td><%if (pm.isNext() && pm.getEndPage()>0){ %>
						<a href="<%=request.getContextPath()%>/bmypage/breserve_pres.do?page=<%=pm.getEndPage()+1%>&keyword=<%=pm.encoding(pm.getScri().getKeyword())%>">▶</a>
						<%} %>
						</td>
					</tr>
				</table>				
			</div>	
			
		</div>	
		</div>			
	</article>
</section>	
	</form>		
</body>
</html>