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
                            <td>${board.boardNo }</td>
		                			<td>${board.boardCategory}</td>
		                			<td class="boardTitle">
		                				<c:forEach items="${thList}" var="th">
		                					<c:if test="${th.parentBoardNo == board.boardNo}">
		                					  <%-- 현재 출력 중인 게시글 목록 번호와 일치하는 썸네일 목록이 있을 경우  --%>
		                						<c:set var="src" value="${contextPath}${th.filePath}/${th.fileChangeName}"/>
		                						<img src="${src}">
		                					</c:if>
		                					
                            	</c:forEach>
                            	
                            	${board.boardTitle}</td>
                            	
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
            <div  class="text-center" id="searchForm" style="margin-bottom:100px;">
                <span>
                    카테고리(다중 선택 가능)<br>
                    <label for="exercise">운동</label> 
                    <input type="checkbox" name="ct" value="운동" id="exercise">
                    &nbsp;
                    <label for="movie">영화</label> 
                    <input type="checkbox" name="ct" value="영화" id="movie">
                    &nbsp;
                    <label for="music">음악</label> 
                    <input type="checkbox" name="ct" value="음악" id="music">
                    &nbsp;
                    <label for="cooking">요리</label> 
                    <input type="checkbox" name="ct" value="요리" id="cooking">
                    &nbsp;
                    <label for="game">게임</label> 
                    <input type="checkbox" name="ct" value="게임" id="game">
                    &nbsp;
                    <label for="etc">기타</label> 
                    <input type="checkbox" name="ct" value="기타" id="etc">
                    &nbsp;
                </span>
                <br>
                <select name="sKey" class="form-control" style="width:100px; display: inline-block;">
                    <option value="tit">글제목</option>
                    <option value="con">내용</option>
                    <option value="tit-con">제목+내용</option>
                </select>
                <input type="text" name="sVal" class="form-control" style="width:25%; display: inline-block;">
                <button class="form-control btn btn-primary" id="searchBtn" type="button" style="width:100px; display: inline-block;">검색</button>
            </div>
            
            
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
      

  	// --------------------------------- 검색 버튼 동작 -----------------------------------
  	$("#searchBtn").on("click", function(){
  		// 검색 값에 따라 url을 조합하여 저장할 변수
  		var searchUrl = "";
  		
  		// 검색에 필요한 요소(카테고리, 검색 조건, 검색어) 읽어 오기
  		var $ct = $("input[name='ct']:checked");
  		var $sKey = $("select[name='sKey']");
  		var $sVal = $("input[name='sVal']");
  		
  		
  		// 1) 검색에 필요한 카테고리 또는 검색어가 입력 되었는지 확인
  		// - 입력이 되지 않은 경우 -> 해당 게시판 첫 페이지로 돌아가는 url
  		// - 하나라도 입력된 경우 -> 검색 url 생성(쿼리스트링 조합 작업 필요) 
  		
  		// 선택된 카테고리의 개수가 0이고, 입력된 검색어의 길이가 0인경우
  		// == 카테고리 체크 x, 검색어 입력 x 상태로 검색버튼을 클릭한 경우
  		// -> 해당 게시판의 첫 페이지로 이동
  		if ($ct.length == 0 && $sVal.val().trim().length == 0) {
  			searchUrl = "${pInfo.boardType}";
  			location.reload();
  		} 
  		// 카테고리가 체크 되었거나, 검색어가 입력돈 경우 또는 둘다
  		else {
  			searchUrl = "../search/${pInfo.boardType}?";
  			
  			// 카테고리가 체크된 경우
  			if ($ct.length != 0) {
  				// $ct 배열에 반복 접근 하여 쿼리스트링에 추가
  				$ct.each(function(index, item){
  					if (index != 0) searchUrl += "&";
  					searchUrl += "ct=" + $(item).val();
  				});
  				
  				// 카테고리 반복 접근이 끝난 후
  				// 검색어가 있을 경우 쿼리스트링을 이어서 작성할 수 있도록 '&' 기호 추가	
  				if ($sVal.val().trim().length != 0) searchUrl += "&";
  			}
  			
  			// 검색어가 입력된 경우
  			if ($sVal.val().trim().length != 0) {
  				searchUrl += "sKey=" + $sKey.val() + "&sVal=" + $sVal.val();
  			}	
  			
  		} // else end
  		
  		location.href = searchUrl;
  		
  		// 2) location.href를 통해 검색 요청 전달
  		
  	});
        		
      // ------------------ 검색 값 유지 -----------------------------------
      
      $(function(){
      	var sKey = "${param.sKey}";
      	var sVal = "${param.sVal}";
      	// EL 구문에서 값이 없을 경우 ""(빈문자열)이 반환됨
      	
      	if (sKey != "" && sVal != "") {
      		// 검색어 세팅
      		$("input[name='sVal']").val(sVal);
      		console.log(sVal);
      		
      		// 검색 조건 세팅
      		$("select[name='sKey'] > option").each(function(index, item){
      			if ($(item).val() == sKey) {
      				$(item).prop("selected", true);
      			}
      		});
      	}
      	
      	// 카테고리(체크박스) 값 세팅
      	// script 태그 내에 EL/JSTL 사용
      	
      	// HTML, JS, jQuery, Scriptlet(Java), EL / JSTL
      	// 서버 동작 시 JSP 파일 코드 해석 순서
      	// 1) Java, 2) EL/JSTL, 3) HTML, 4) JS/jQuery
      	
      	
      	// EL / JSTL 구문은 JS/jQuery보다 해석이 빠르므로
      	// JS구문 내에 EL/JSTL 구문을 작성하여 혼용할 수 있다
      	<c:forEach var="ctName" items="${paramValues.ct}">
      		$("input[name='ct']").each(function(index, item){
      			
      			if ($(item).val() == "${ctName}") {
      				$(item).prop("checked", true);
      			}
      		});
      	</c:forEach>
      });
      
      // ----------------- 검색창 엔터 이벤트 -----------------------
      $("input[name='sVal']").on("keyup", function(event){
      	console.log(event.keyCode); // 키업 이벤트가 발생할 경우 입력한 키코드 출력됨
      	if (event.keyCode == 13) { // 엔터키가 눌러진 경우
      		$("#searchBtn").click();
      	}
      });
  	
  	</script>
 	
 	
   
   
   
</body>
</html>