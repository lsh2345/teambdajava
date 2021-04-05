<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "service.*" %>   
<%ArrayList<RoomVo> room_alist = (ArrayList<RoomVo>)request.getAttribute("room_alist");%> 
<%ArrayList<RoomVo> room_alist1 = (ArrayList<RoomVo>)request.getAttribute("room_alist1");%>  
<%ArrayList<RoomVo> room_alist2 = (ArrayList<RoomVo>)request.getAttribute("room_alist2");%>  
<%ArrayList<RoomVo> room_alist3 = (ArrayList<RoomVo>)request.getAttribute("room_alist3");%>  
 
<%ArrayList<OrderVo> order_alist = (ArrayList<OrderVo>)request.getAttribute("order_alist");%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
DecimalFormat formatter = new DecimalFormat("###,###");
</script>
<script>
	$(function(){
		window.onload = function hide(){
			$("#selected_date_start_hide").css('visibility', 'hidden');
			$("#selected_date_end_hide").css('visibility', 'hidden');
			$(".hide_column").hide();
			$(".checkbox").attr("disabled", true);
			$("#button_a").trigger("click");
			
		}
		
		
		$.datepicker.setDefaults({
			dateFormat:'yy-mm-dd',
			prevText :'이전달',
			nextText :'다음달',
			showMonthAfterYear : true,
			dayNamesMin : ['일','월','화','수','목','금','토'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			numberOfMonths : [1,1],
			yearSuffix : '년',
			minDate : "+1D",
			maxDate : "+3M",
			changeMonth: true			
		});
		
		var dates = $("#calendar_site_start, #calendar_site_end").datepicker({					
				onSelect: function( selectedDate ) {
			    var option = this.id == "calendar_site_start" ? "minDate" : "maxDate";
			      instance = $("#calendar_site_end").data( "datepicker" );
			      date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings );
			      dates.not("#calendar_site_start").datepicker( "option", option, date );
			    
			    var year = date.getFullYear(); //숫자형			    
			    var month = String(Number(date.getMonth())+1).length<2?"0"+(Number(date.getMonth())+1): Number(date.getMonth())+1;  //문자형
			    var Date = String(date.getDate()).length<2?"0"+ date.getDate():  date.getDate(); //숫자형
			    var fullDay = year +"-"+month+"-"+Date;
			    var fullDayNum = Number(year+month+Date);
			    
			    var id = $(this).attr("id");
	    
			    if(id == 'calendar_site_start'){
			    	$("#selected_date_start").val(fullDay);
			    	$("#selected_date_start_hide").val(fullDayNum);
			    }else if(id == 'calendar_site_end'){
			    	if($("#selected_date_start").val() == ""){
			    		alert("체크인 날짜를 먼저 선택하세요.");
			    		location.reload();
			    	}else{
			    	$("#selected_date_end").val(fullDay);
			    	$("#selected_date_end_hide").val(fullDayNum);
			    	}
			    }
			   
			    
			  }
		});
		
		$("#search_result").click(function(){
			var start_section = $("#selected_date_start").val();
			var end_section = $("#selected_date_end").val();
			
			if(start_section=="" || end_section==""){
				alert("체크인과 체크아웃 날짜를 선택하세요.");
			}else{
			$(".checkbox").attr("disabled", false);
			var select_start = Number($("#selected_date_start_hide").val());
			var select_end = Number($("#selected_date_end_hide").val());

			<%for(OrderVo ov : order_alist){%>
				var ostart = Number("<%=ov.getOstart()%>");
				var oend = Number("<%=ov.getOend()%>");
				
			 
				if(oend <= select_start || ostart > select_end){
					
				}else{
					//alert("예약 불가능한 방이 있습니다.");
					var ov_ridx = <%=ov.getRidx()%>;
					$(".ridx").each(function(index, item){
						var ridx = $(this).text();
						if(ov_ridx == Number(ridx)){
							$(this).parent().next().children().attr("disabled",true).css("cursor","default").css("color","gray");
							$(this).parent().prev().children().attr("disabled",true);
							$(this).parent().siblings().css("background-color","lightgray");
						}
					});
				}			
			<%}%>	
			}
		});
		
			
		$('.checkbox').change(function(){
			if($(this).is(":checked")==true){

				
				var ridx = $(this).parent().next().text();
				var rname = $(this).parent().next().next().children().text();
				var rbaseperson = Number($(this).parent().next().next().next().next().text());
				var max_person = Number($(this).parent().next().next().next().next().next().text());
				var rbaseprice = Number($(this).parent().next().next().next().next().next().next().text());
				var addprice = Number($(this).parent().next().next().next().next().next().next().text());
				
				

				
				var addRow = "";
				addRow += "<tr>";
				addRow += "<td class='hide_column'><input type='hidden' name='ridx' value='"+ridx+"'></td>";
				addRow += "<td style='width:15%'>"+rname+"</td>";
				addRow += "<td style='width:15%'>"+rbaseperson+"</td>";
				addRow += "<td style='width:20%'>"+rbaseprice+"</td>";
				addRow += "<td style='width:15%'>"+max_person+"</td>";
				addRow += "<td style='width:15%'><select class='person' name='person' value=''>"
				for(var i=1; i<=max_person; i++){
					addRow += "<option>"+i+"</option>"
				}
				addRow += "</select></td>";	
				
				addRow += "<td style='width:15%'><input style='width:100px; border:none; text-align:center; font-size:16px;' name='totalprice' value='"+rbaseprice+"'></td>";
				addRow += "</tr>";
				
				//select box 안의 값이 rbaseperson보다 크면 select box 값에서 rbasepserson을 뺀 만큼을 addprice에 곱하고 rbaseprice에 더한 것이 합계
				$("#checked").append(addRow);
			}else if($(this).is(":checked")==false){
				var ridx = $(this).parent().next().text();
				
				$("input[name=ridx]").each(function(index){
					var value = $(this).val();		
					if(ridx == value){
						$(this).parent().parent().remove();
					}
					
					var allPrice = 0;
				    
					$("input[name=totalprice]").each(function(){
					    var days = $("#selected_date_end_hide").val() - $("#selected_date_start_hide").val();
						allPrice += Number($(this).val())*days; 
					});
				    
					//합계를 출력
					$("input[name=allPrice]").val(allPrice);	
				});
				

				
				

				//체크가 해제 되면 체크 박스옆 ridx 알아와서 그것과 동일한 값을 가지는 아래의 테이블의 td를 찾고 올라가서 tr을 통째로 삭제
			}
		});
		
		//나중에 추가된 내용에 대한 이벤트를 주려면 on 메소드
		
		$(document).on("change",".checkbox",function(){
			$(".hide_column").hide();	

		});
		
		$(document).on("change",".person",function(){
			var select_person = Number($(this).val());
			var rbaseperson = Number($(this).parent().prev().prev().prev().text());

			if(rbaseperson < select_person){
				var rbaseprice = Number($(this).parent().prev().prev().text());
				var totalprice = rbaseprice + (select_person-rbaseperson)*20000;			
				var last_totalprice = $(this).parent().next().children().val(totalprice);	
			}else if(rbaseperson >= select_person){
				var rbaseprice = Number($(this).parent().prev().prev().text());
				$(this).parent().next().children().val(rbaseprice);
			}
			
			

				var allPrice = 0;
			    
				$("input[name=totalprice]").each(function(){
				    var days = $("#selected_date_end_hide").val() - $("#selected_date_start_hide").val();
					allPrice += Number($(this).val())*days; 
				});
			    
				//합계를 출력
				
				$("input[name=allPrice]").val(allPrice);		

		});
		
	
	});
	
</script>
	<style>
		#out_table{
			width:80%;
			margin:auto;
		}
		#selected_room_list{
			width:80%;
			height:200px;
			margin-right:auto;
			margin-left:auto;
			margin-top:50px;;
		}
		.calendar{
			width:45%;
		}
		.room_list{
			width:45%;
		}
		table{
			width:100%;
			text-align:center;
			border-collapse: collapse;	
		}
		table.outer, tbody#checked{
			border:solid 1px;
		}
		tr,td,th{			
			padding:5px;
		}		
		a{
			text-decoration:none;
			color:black;
		}
		input#selected_date_start, input#selected_date_end{
			text-align:center;
		}
		td.1{width:5%;}
		td.2{width:45%;}
		td.3{width:50%;}
		button#reserve_confirm, button#reserve_pay, button#search_result{
			width:200px; 
			height:50px; 
			background-color:pink;
		}
		div#buttons{
		margin-left:1200px;
		position:fixed;
		margin-top:260px;
		}
		#select_room_type button{
			width:100px;
			height:50px;
		}
		table#left_table{
			margin-top:0px;		
		}
		img.room_img{
			width:100px;
		}
		header{
			padding-bottom:100px;
		}
		.allPrice{
			border:none;
			color:red;
			font-size:30px;
		}
		input.allPrice{
			width:200px;
		}
		td#i1, td#i3, td#i4{
			margin-top:50px;
		}

	</style>
</head>
<body>
<%@ include file="header.jsp" %>

<form name="frm1">	

	<table id="out_table">
		<tr>
			<td>날짜 선택</td>
			<td>객실 선택</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div id="select_room_type">
				<button type="button" onclick="a()" id="button_a">A-Type</button> <button type="button" onclick="c()">C-Type</button> <button type="button" onclick="d()">Special</button>
				</div>
			</td>
		</tr>
		<tr>
			<td class="calendar">
				<table id="left_table" style="margin-bottom:0px;">
					<tr>
						<td>체크인<br/>
							<div><input type="text" id="selected_date_start" name="ostart"></div>
							<div><input type="text" id="selected_date_start_hide"></div>
						</td>
						<td>~</td>
						<td>체크아웃<br/>
							<div><input type="text" id="selected_date_end" name="oend"></div>
							<div><input type="text" id="selected_date_end_hide"></div>
						</td>
					</tr>
					<tr>
						<td style="height:300px;"><div style="margin-top:0px;" type="text" id="calendar_site_start"> </div></td>
						<td></td>
						<td><div   type="text" id="calendar_site_end"></div></td>
					</tr>	
				</table>				
			</td>

			<td id="i1">
				<table class="room_list" style="margin:auto; width:80%;">
					<tr>
						<td class="1">선택</td><td class="hide_column">객실 번호</td><td class="2">객실명</td><td class="3">객실 사진</td><td class="4">기준 인원</td><td class="5">최대 인원</td><td class="6">기준 금액</td>
					</tr>
					<%for(RoomVo rv : room_alist){%>
					<tr>
						<td class="1" style="border:solid 1px; width:10%;"><input type="checkbox" class="checkbox"></td>						
						<td style="border:solid 1px;" class="hide_column"><a href="#" class="ridx"><%=rv.getRidx()%></a></td>				
						<td class="2" style="border:solid 1px; width:20%;"><a href="#" class="rname"><%=rv.getRoomnum()%></a></td>
						<td class="3" style="border:solid 1px;"><img class="room_img" src="/0316-origin/res_pay/room_img.png"></td>
						<td class="4" style="border:solid 1px;"><%=rv.getRbaseperson()%></td>
						<%int max = Integer.parseInt(rv.getRbaseperson())+Integer.parseInt(rv.getRaddperson()); %>
						<td class="5" style="border:solid 1px;"><%=rv.getRfullperson()%></td>
						<td class="6" style="border:solid 1px;"><%=rv.getRbaseprice()%></td>
					</tr>
				<%} %>					
				</table>				
			</td>
					
			<td id="i3" style="display:none;">
				<table class="room_list" style="margin:auto; width:80%;">
					<tr>
						<td class="1">선택</td><td class="hide_column">객실 번호</td><td class="2">객실명</td><td class="3">객실 사진</td><td class="4">기준 인원</td><td class="5">최대 인원</td><td class="6">기준 금액</td>
					</tr>
					<%for(RoomVo rv : room_alist2){%>
					<tr >
						<td class="1" style="border:solid 1px; width:10%;"><input type="checkbox" class="checkbox"></td>						
						<td style="border:solid 1px;" class="hide_column"><a href="#" class="ridx"><%=rv.getRidx()%></a></td>				
						<td class="2" style="border:solid 1px; width:20%;"><a href="#" class="rname"><%=rv.getRoomnum()%></a></td>
						<td class="3" style="border:solid 1px;"><img class="room_img" src="/0316-origin/res_pay/room_img.png"></td>
						<td class="4" style="border:solid 1px;"><%=rv.getRbaseperson()%></td>
						<%int max = Integer.parseInt(rv.getRbaseperson())+Integer.parseInt(rv.getRaddperson()); %>
						<td class="5" style="border:solid 1px;"><%=rv.getRfullperson()%></td>
						<td class="6" style="border:solid 1px;"><%=rv.getRbaseprice()%></td>
					</tr>
				<%} %>					
				</table>		
			</td>
			
			<td id="i4" style="display:none;" >
				<table class="room_list" style="margin:auto; width:80%;">
					<tr>
						<td class="1">선택</td><td class="hide_column">객실 번호</td><td class="2">객실명</td><td class="3">객실 사진</td><td class="4">기준 인원</td><td class="5">최대 인원</td><td class="6">기준 금액</td>
					</tr>
					<%for(RoomVo rv : room_alist3){%>
					<tr >
						<td class="1" style="border:solid 1px; width:10%;"><input type="checkbox" class="checkbox"></td>						
						<td style="border:solid 1px;" class="hide_column"><a href="#" class="ridx"><%=rv.getRidx()%></a></td>				
						<td class="2" style="border:solid 1px; width:20%;"><a href="#" class="rname"><%=rv.getRoomnum()%></a></td>
						<td class="3" style="border:solid 1px;"><img class="room_img" src="/0316-origin/res_pay/room_img.png"></td>
						<td class="4" style="border:solid 1px;"><%=rv.getRbaseperson()%></td>
						<%int max = Integer.parseInt(rv.getRbaseperson())+Integer.parseInt(rv.getRaddperson()); %>
						<td class="5" style="border:solid 1px;"><%=rv.getRfullperson()%></td>
						<td class="6" style="border:solid 1px;"><%=rv.getRbaseprice()%></td>
					</tr>
				<%} %>					
				</table>		
			</td>			
		</tr>
		<tr>
			<td>
				<input type="button" value="검색초기화" id="reset_button" onclick="window.location.reload()">
			</td>			
			<td>
			
			</td>
		</tr>
		<tr>
			<td>
				<button type=button id="search_result">예약 가능한 객실 보기</button>				
			</td>
			<td>			
			</td>
		</tr>	
	</table>	

	
<table style="margin-top:50px; position:fixed">				
<thead style="border:solid 1px;">
	<tr>
		<td class="hide_column">객실번호</td><td style='width:15%'>객실명</td><td style='width:15%'>기준 인원</td><td style='width:20%'>기준 금액</td><td style='width:15%'>최대 인원</td><td style='width:15%'>인원선택</td><td style='width:20%'>합계</td>
	</tr>
</thead>
<tbody id="checked">
</tbody>
</table>


<div id="buttons">
<span class="allPrice">총 결제금액 : &#65510; <input type="text" name="allPrice" class="allPrice" id="allPrice"></span>
<button type="button" id="reserve_confirm" onclick="reserve_insert()">예약하기</button>	
</div>
</form>

<script type="text/javascript">

function a(){
	document.getElementById("i1").style.display="block";

	document.getElementById("i3").style.display="none";
	document.getElementById("i4").style.display="none";	
	}

function c(){
	document.getElementById("i1").style.display="none";

	document.getElementById("i3").style.display="block";
	document.getElementById("i4").style.display="none";						
	}

function d(){
	document.getElementById("i1").style.display="none";

	document.getElementById("i3").style.display="none";
	document.getElementById("i4").style.display="block";						
	}



function reserve_insert(){
	var allPrice = document.getElementById("allPrice").value;
	
	if(allPrice == ""){
		alert("객실 또는 숙박 인원을 선택하세요");
		console.log(allPrice);
	}else{
		document.frm1.action="<%=request.getContextPath() %>/reserve/reserveConfirm.do";
		document.frm1.method="post";
		document.frm1.submit();
	}	


}

	


</script>
</body>
</html>