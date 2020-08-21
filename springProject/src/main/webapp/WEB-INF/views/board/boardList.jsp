<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
    <style>
       .pagination {
            justify-content: center;
        }
        #searchForm{
            position: relative;
        }

        #searchForm>*{
            top : 0;
        }
        
        .boardTitle > img{
           width: 50px;
           height: 50px;
        }
          
        .board-list{ margin: 100px auto;}
        
        #list-table td{
        	cursor: pointer;
        	
        }
   </style>
   
</head>
<body>
   <jsp:include page="../common/header.jsp"/>
   
   <div class="container board-list">
      
      <h1>${boardList[0].boardName}</h1>
      
        <div style="height:530px">
            <table class="table table-hover table-striped" id="list-table">
                <thead>
                    <tr>
                        <th>글번호 </th>
                        <th>카테고리 </th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                   <c:choose>
                      <c:when test="${empty boardList}">
                         <tr>
                         <td colspan="6">존재하는 게시글이 없습니다.</td>
                         </tr>
                      </c:when>
                      
                      <c:otherwise>
                         
                         <c:forEach var="board" items="${boardList}">
                        <!--  boardList에 있는 요소를 반복접근하여
                              board라는 변수에 저장하여 내부에서 사용
                         -->
                         <tr>
                            <td>${board.boardNo}</td>
                            <td>${board.boardCategory}</td>
                            <td class="boardTitle">${board.boardTitle}</td>
                            <td>${board.boardWriter}</td>
                            <td>${board.readCount}</td>
                            <td>
                                  <jsp:useBean id="now" class="java.util.Date"/>
                                  <%-- Date now = new Date(); (현재시간)--%>
                                  
                                  <fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd"/>
                                  <fmt:formatDate var="createDate" value="${board.boardCreateDate}" pattern="yyyy-MM-dd"/>
                                  <fmt:formatDate var="createTime" value="${board.boardCreateDate}" pattern="hh:mm:ss"/>
                               <c:choose>
                                  
                                  <c:when test="${today == createDate}">
                                     ${createTime}
                                  </c:when>
                                  
                                  <c:otherwise>
                                     ${createDate}
                                  </c:otherwise>
                               </c:choose>
                            </td>
                         </tr>
                         </c:forEach>
                      </c:otherwise>
                   </c:choose>
                
                
                </tbody>
            </table>
        </div>

        <hr>
        
        <%-- 로그인이 되어있는 경우에만 글쓰기 버튼 보이게 하기 --%>
        <c:if test="${!empty loginMember}">
       	<a class="btn btn-primary float-right" href="../${boardList[0].boardType}/insert">글쓰기</a>
		</c:if>
         <!--------------------------------- 페이징바  ---------------------------------->
         
      <div class="my-4">
            <ul class="pagination">
            
            	<c:if test="${pInfo.currentPage > pInfo.pagingBarSize }">
                  
                   <li>
                      <!-- 맨 처음으로(<<) -->
                       <a class="page-link text-primary" href="${pInfo.boardType }?cp=1">&lt;&lt;</a>
                   </li>
                   
                   <li>
                      <!-- 이전으로(<) -->
                      <!-- prev 생성 식 : (현재페이지-1)/ 페이징바 사이즈 (10) * 10-->
                      <!-- fmt태그를 이용한 소수점 제거 -->	
                      <fmt:parseNumber var="operand1" 
                      			value="${(pInfo.currentPage-1)/pInfo.pagingBarSize }"
                      			integerOnly="true"/>
                      			
                      <c:set var="prev" value="${operand1 * 10 }"/>
                      
                         <a class="page-link text-primary" href="${pInfo.boardType}?cp=${prev}">&lt;</a>
                   </li>
                </c:if>
                
                <!-- 10개의 페이지 목록 -->
                <c:forEach var="p" begin="${pInfo.startPage }" end="${pInfo.endPage}">
                     <c:choose>  
                        <c:when test="${p==pInfo.currentPage}">
                            <li><a class="page-link">${p}</a></li>
                        </c:when> 
                        <c:otherwise>
                            <li>
                               <a class="page-link text-primary" href="${pInfo.boardType }?cp=${p}">${p}</a>
                            </li>
                        </c:otherwise>
                     </c:choose> 
                </c:forEach>
                
                
                <!-- 다음 페이지로(>) -->
                <!-- next 생성 식 : (현재 페이지 + 9)/10 * 10 + 1 -->
                <c:if test="${pInfo.maxPage > pInfo.endPage}">
                
                
                   <!-- 다음 페이지(>) -->
                   	
                   <li>
                   		<fmt:parseNumber var="operand2" value="${(pInfo.currentPage + pInfo.pagingBarSize-1) / pInfo.pagingBarSize}" integerOnly="true"/>
                   		<c:set var="next" value="${operand2 * pInfo.pagingBarSize +1 }"/>	
                  		<a class="page-link text-primary" href="${pInfo.boardType }?cp=${next}">&gt;</a>
                   </li>
                   
                   <!-- 맨 끝으로(>>) -->
                   <li>
                       <a class="page-link text-primary" href="${pInfo.boardType}?cp=${pInfo.maxPage}">&gt;&gt;</a>
                   </li>
                 </c:if>
            </ul>
        </div>        

        <div>
            <form action="list" method="GET" class="text-center" id="searchForm" style="margin-bottom:100px;">
               <span>
                  카테고리(다중 선택 가능)<br>
                   <label for="exercise">운동</label> 
                   <input type="checkbox" name="searchCategory" value="운동" id="exercise">
                   &nbsp;
                   <label for="movie">영화</label> 
                   <input type="checkbox" name="searchCategory" value="영화" id="movie">
                   &nbsp;
                   <label for="music">음악</label> 
                   <input type="checkbox" name="searchCategory" value="음악" id="music">
                   &nbsp;
                   <label for="cooking">요리</label> 
                   <input type="checkbox" name="searchCategory" value="요리" id="cooking">
                   &nbsp;
                   <label for="game">게임</label> 
                   <input type="checkbox" name="searchCategory" value="게임" id="game">
                   &nbsp;
                   <label for="etc">기타</label> 
                   <input type="checkbox" name="searchCategory" value="기타" id="etc">
                   &nbsp;
                </span>
                <br>
                <select name="searchKey" class="form-control" style="width:100px; display: inline-block;">
                    <option value="title">글제목</option>
                    <option value="content">내용</option>
                    <option value="titcont">제목+내용</option>
                </select>
                <input type="text" name="searchValue" class="form-control" style="width:25%; display: inline-block;">
                <button class="form-control btn btn-primary" style="width:100px; display: inline-block;">검색</button>
            </form>
            
            
        </div>
      
   </div>
   <jsp:include page="../common/footer.jsp"/>
   
   <script>
      // 게시글 상세보기 기능 구현 
      $(function(){
    	 $("#list-table td").on("click", function(){
    		 // 글번호
    		var boardNo = $(this).parent().children().eq(0).text(); 
    	 	
    		 // 게시글 상세조회 요청 주소
    		 var boardUrl = 
    			 "${contextPath}/board/${pInfo.boardType}/"+ boardNo + "?cp=${pInfo.currentPage}"; 
    			 
    			 // @@PathVariable 방식 : 구분되어야하는 리소스를 호출하는 url로 사용
    			 // spring/board/1/500?cp=3
    					 
    			 // 파라미터(쿼리스트링) : 정렬, 필터링
			 	 // spring/board?type=1&boardNo=500&cp=3

			 	// 게시글 상세조회 요청 		 	
			 	location.href = boardUrl;		 
    			 
    	 });
      });
   </script>
   
   
   
</body>
</html>