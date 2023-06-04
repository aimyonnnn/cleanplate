<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- 이부분은 지우면 안됩니다 -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>store_Reservation</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
		<link href="top.css" rel="stylesheet" type="text/css">

    <!-- 이부분은 지우면 안됩니다 -->
</head>
<body>
	<!-- 공통 상단바 구역 -->
<%@ include file="../common/common_header.jsp" %>
   	<!-- 공통 상단바 구역 -->

    <!-- 제목 구역 -->
	<div class="container">
        <div class="row">
          <div class="col">
            <h2>점주 마이페이지</h2>
          </div>
        </div>
    </div>

    <!-- 사이드바, 내용 넣는 구역!-->
    <div class="container mt-5">
        <div class="row d-flex justify-content-center">
            <div class="col-md-2 align-items-center d-flex">
                <!-- 왼쪽 사이드바 구역 -->
                <div class="btn-group-vertical btn-group-lg d-flex align-self-start" role="group" aria-label="Vertical button group">
                    <!-- 프로필 사진 -->
                    <div class="input-group mb-5 d-flex shadow-lg d-flex justify-content-center pe-3" style="border-radius: 10px;">
                        <img src="../image/profile.png" alt="" style="width: 100px; height: 100px;" class="justify-content-center">
                        <p class="d-flex align-items-center"><span>님 환영합니다</span></p>

                    </div>
                    <!-- 왼쪽 사이드바 버튼들-->
                    <!-- 클릭된 버튼은 색으로 따로 표시함-->
					<button type="button" class="btn btn-outline-secondary text-black p-3" onclick="location.href='CeoMypage'">내 정보</button>
                    <button type="button" class="btn btn-outline-secondary active mt-4 text-black p-3" onclick="location.href='StoreMypage'">가게 등록</button>
                    <button type="button" class="btn btn-outline-secondary text-black p-3" onclick="location.href='StoreReservation'">예약관리</button>
                    <button type="button" class="btn btn-outline-secondary text-black p-3" onclick="location.href='StoreSales'">매출관리</button>
                    <button type="button" class="btn btn-outline-secondary text-black p-3" onclick="location.href='StoreCoupon'">쿠폰/이벤트</button>
                    <button type="button" class="btn btn-outline-secondary text-black p-3" onclick="location.href='CeoMypageDelete'">회원탈퇴</button>
                </div>
            </div>

            <!-- 내용 구역 -->
            <div class="col-md-8">
              <div class="card" style="margin-top: 20px;">
              
              <div class="card-body" ><br>
              	<h5 class="card-title">전체예약</h5>

             <table class="table table-hover" style = "text-align:center;">
			  <thead>
			    <tr>
			      <th scope="col">예약일</th>
			      <th scope="col">인원</th>
			      <th scope="col">총금액</th>
			      <th scope="col">예약상태</th>
			      <th scope="col">선택</th>
			    </tr>
			  </thead>
			  <tbody>
			    <tr>
			      <th scope="row">
			      		<%
		                session.setAttribute("visiting_Date", "visit");
		                session.setMaxInactiveInterval(60*5);
		                
		                String visit = (String) session.getAttribute("visiting_Date");
		                out.print(visit);
		                %>
			      </th>
			      <td>
						<%
		                session.setAttribute("number_People", "people");
		                session.setMaxInactiveInterval(60*5);
		                
		                String people = (String) session.getAttribute("number_People");
		                out.print(people);
		                %>
				  </td>
			      <td>
			      		<%
		                session.setAttribute("total", "sales");
		                session.setMaxInactiveInterval(60*5);
		                
		                String sales = (String) session.getAttribute("total");
		                out.print(sales);
		                %>
			      </td>
			      <td>
			      	<button type="button" class="btn btn-outline-dark" onclick="location.href='StoreReservationEdit'">수정</button> 
			      </td>
			      <td><input type = "checkbox"></td>
			    </tr>
			    <tr>
			    <tr>
			      <th scope="row">
			      		<%
		                session.setAttribute("visiting_Date", "visit2");
		                session.setMaxInactiveInterval(60*5);
		                
		                String visit2 = (String) session.getAttribute("visiting_Date");
		                out.print(visit2);
		                %>
			      </th>
			      <td>
						<%
		                session.setAttribute("number_People", "people2");
		                session.setMaxInactiveInterval(60*5);
		                
		                String people2 = (String) session.getAttribute("number_People");
		                out.print(people2);
		                %>
				  </td>
			      <td>
			      		<%
		                session.setAttribute("total", "sales2");
		                session.setMaxInactiveInterval(60*5);
		                
		                String sales2 = (String) session.getAttribute("total");
		                out.print(sales2);
		                %>
			      </td>
			      <td>
			      	<button type="button" class="btn btn-outline-dark" onclick="location.href='StoreReservationEdit'">수정</button> 
			      </td>
			      <td><input type = "checkbox"></td>
			    </tr>
			    <tr>
			    <tr>
			      <th scope="row">
			      		<%
		                session.setAttribute("visiting_Date", "visit3");
		                session.setMaxInactiveInterval(60*5);
		                
		                String visit3 = (String) session.getAttribute("visiting_Date");
		                out.print(visit3);
		                %>
			      </th>
			      <td>
						<%
		                session.setAttribute("number_People", "people3");
		                session.setMaxInactiveInterval(60*5);
		                
		                String people3 = (String) session.getAttribute("number_People");
		                out.print(people3);
		                %>
				  </td>
			      <td>
			      		<%
		                session.setAttribute("total", "sales3");
		                session.setMaxInactiveInterval(60*5);
		                
		                String sales3 = (String) session.getAttribute("total");
		                out.print(sales3);
		                %>
			      </td>
			      <td>
			      	<button type="button" class="btn btn-outline-dark" onclick="location.href='StoreReservationEdit'">수정</button> 
			      </td>
			      <td><input type = "checkbox"></td>
			    </tr>
			   </tbody>
			  </table>
            </div>
            		<br><br>
					<div style="margin:0 auto">
					<input type="button" value="삭제" >
					<input type="submit" value="저장">&nbsp;&nbsp;
					</div>
					<br><br>
            </div>
            </div>             
           </div>
        </div>
    <!-- 하단 부분 include 처리영역 -->
    <hr class="mt-5">
<%@ include file="../common/common_footer.jsp" %>
    <!-- 하단 부분 include 처리영역 -->
    
    <!-- 이부분은 지우면 안됩니다 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html> 