<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
<title>게시글 수정</title>
    .insert-label {
      display: inline-block;
      width: 80px;
      line-height: 40px
    }
    
    #content-main{ margin: 100px auto;}
</style>
</head>
<body>
	<div class="container">
		<jsp:include page="../common/header.jsp"/>

		<div class="container pb-5 mb-5" id="content-main">

			<h3>게시글 수정</h3>
			<hr>
			<form action="updateAction?cp=${param.cp}" method="post" 
				   enctype="multipart/form-data" role="form" onsubmit="return validate();">
			<!-- enctype="multipart/form-data" -->
				<div class="mb-2">
					<label class="input-group-addon mr-3 insert-label">카테고리</label> 
					<select	class="custom-select" id="category" name="boardCategory" style="width: 150px;">
						<option value="10">운동</option>
						<option value="20">영화</option>
						<option value="30">음악</option>
						<option value="40">요리</option>
						<option value="50">게임</option>
						<option value="60">기타</option>
					</select>
				</div>
				
				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">제목</label> 
					<input type="text" class="form-control" id="title" name="boardTitle" size="70"
						value="${board.boardTitle }">
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">작성자</label>
					<h5 class="my-0" id="writer">${loginMember.memberId}</h5>
				</div>


				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">수정일</label>
					<h5 class="my-0" id="today">
						<jsp:useBean id="now" class="java.util.Date" />
						<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>
					</h5>
				</div>

				<hr>
				
				<!-- 기존 이미지 배치 준비 -->
				<!-- 기존 이미지를 서버에서 조회한 후
					레벨별로 이미지 출력에 필요한 src경로를 담을 변수를 생성 -->
				<c:forEach var="at" items="${files}" varStatus="vs">
					<c:choose>
						<c:when test="${at.fileLevel == 0}">
							<c:set var="titleImgSrc" value="${contextPath }${at.filePath}/${at.fileChangeName }"></c:set>
						</c:when>
						<c:when test="${at.fileLevel == 1}">
							<c:set var="contentImgSrc1" value="${contextPath }${at.filePath}/${at.fileChangeName }"></c:set>
						</c:when>
						<c:when test="${at.fileLevel == 2}">
							<c:set var="contentImgSrc2" value="${contextPath }${at.filePath}/${at.fileChangeName }"></c:set>
						</c:when>
						<c:when test="${at.fileLevel == 3}">
							<c:set var="contentImgSrc3" value="${contextPath }${at.filePath}/${at.fileChangeName }"></c:set>
						</c:when>
					</c:choose>
					
				</c:forEach>	
		
				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">썸네일</label>
					<div class="boardImg" id="titleImgArea">
						<img id="titleImg" width="200" height="200"
							<c:if test="${! empty titleImgSrc}">
								src="${titleImgSrc }";
							</c:if>
						>
						
						<button type="button" class="deleteImg">삭제</button>
					</div>
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg1" width="150" height="150"
							<c:if test="${! empty contentImgSrc1}">
								src="${contentImgSrc1 }";
							</c:if>
						>
						<button type="button" class="deleteImg">삭제</button>
					</div>

					<div class="mr-2 boardImg" id="contentImgArea2">
						<img id="contentImg2" width="150" height="150"
							<c:if test="${! empty contentImgSrc2}">
								src="${contentImgSrc2 }";
							</c:if>
						>
						<button type="button" class="deleteImg">삭제</button>
					</div>

					<div class="mr-2 boardImg" id="contentImgArea3">
						<img id="contentImg3" width="150" height="150"
							<c:if test="${! empty contentImgSrc3}">
								src="${contentImgSrc3 }";
							</c:if>
						>
						<button type="button" class="deleteImg">삭제</button>
					</div>
				</div>
				


				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<!--  multiple 속성
						- input 요소 하나에 둘 이상의 값을 입력할 수 있음을 명시 (파일 여러개 선택 가능)
					 -->
					<input type="file" id="img1" name="thumbnail" onchange="LoadImg(this,1)"> 
					<input type="file" id="img2" name="images" onchange="LoadImg(this,2)">
					<input type="file" id="img3" name="images" onchange="LoadImg(this,3)"> 
					<input type="file" id="img4" name="images" onchange="LoadImg(this,4)">
				</div>

				<div class="form-group">
					<div>
						<label for="content">내용</label>
					</div>
					<textarea class="form-control" id="content" name="boardContent"
						rows="10" style="resize: none;">${board.boardContent }</textarea>
				</div>


				<hr class="mb-4">

				<div class="text-center">
					<button type="submit" class="btn btn-primary">수정</button>
					<a class="btn btn-primary float-right" href="${header.referer}">목록으로</a>
				</div>

			</form>
		</div>

	
	</div>
	<jsp:include page="../common/footer.jsp"/>


	<script>
		var deleteImages = []; // 삭제될 이미지 정보를 담을 배열
		
		// 이미지 삭제 버튼의 수 만큼 배열 요소를 만들고 모두 false로 초기화
		for (var i = 0; i < $(".deleteImg").length; i++) {
			deleteImages.push(false);
		}
		
	
		// 이미지 삭제 버튼 동작
		$(".deleteImg").on("click", function(event){
			// event 매개변수 : 이벤트와 이벤트가 발생한 객체에 대한 모든 정보가 담겨있음.
			event.stopPropagation(); // 삭제버튼 -> 이미지 클릭 -> file태그 클릭 == 이벤트 버블링
									 // 이벤트 버블링을 멈춤(삭제)
			
			
			// 현재 선택한 요소의 이전 요소 선택 
			//console.log($(this).prev());
			var $el = $(this).prev();
			
			// 이미지 태그 삭제
			// img 태그의 src 속성을 제거 (X)
			// $el.removeAttr("src"); -> 이미지 영역은 남지만 이지미가 엑스박스로 뜸
			
			// 이미지 태그 자체를 삭제 시키고, 그자리에 새로운 이미지 태그를 추가 
			var $img = $("<img>",  {id : $el.attr("id"), 
									width : $el.css("width"), 
									height : $el.css("height")});
			
			$el.remove();
			$(this).before($img); // before() : 이전 위치에 요소 삽입
			
			// 삭제된 이미지의 인덱스와 일치하는 deleteImges 배열의 요소 값을 true로 변경
			console.log($(".deleteImg").index(this));
			// ($(".deleteImg")index(this) : 
				// 클래스가 deleteImg인 요소들 중 현재요소(this)의 인덱스 순서를 반환
				
				
			deleteImages[$(".deleteImg").index(this)] = true;
			
			console.log(deleteImages)
			
			
			// input type = "file" 태그에 있는 value값 삭제
          	$("#img"+($(".deleteImg").index(this) + 1)).val("");
			// input type="file" 태그의 value 값은 readonly(읽기 전용)이므로
			// 파일 선택 버튼 시 나타나는 윈도우로만 값을 선택할 수 있음.
			// --> 임의로 value값을 수정하는 것을 불가능
			// 단, 초기화는 가능 (jquery만)
		});
	
		// 카테고리 선택
		$.each($("#category>option"), function(index, item){
			if($(item).text() == "${board.boardCategory}"){
				$(item).prop("selected","true");
			}
		});
		
		// 아이디가 카테고리인 자식 옵션태그에 1개씩 반복접근해서 function(배열수, option){
		//	text값을 얻어와서 db에서 얻어온 카테고리랑 같다면
		//  그 옵션태그에 selected를 추가해라
		
		
		
		
		// 유효성 검사
		function validate() {
			if ($("#title").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#title").focus();
				return false;
			}
			if ($("#content").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#content").focus();
				return false;
			}
			
			
			// deleteImages 배열을 input태그로 만들어 form 태그 내부에 추가하여
			// 서버로 데이터를 전달 
			for (var i = 0; i < deleteImages.length; i++) {
				var $deleteImage = $("<input>", {type : "hidden", name : "deleteImages", value : deleteImages[i]});
				
				// form태그 제일 마지막 자식으로 추가
				$("form").append($deleteImage);
			}
		}
				
		
		 // 이미지 공간을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
	   	    $(function () {
	      $("#fileArea").hide();
	      $("#titleImgArea").click(function () {
	        $("#img1").click();
	      });
	      $("#contentImgArea1").click(function () {
	        $("#img2").click();
	      });
	      $("#contentImgArea2").click(function () {
	        $("#img3").click();
	      });
	      $("#contentImgArea3").click(function () {
	        $("#img4").click();
	      });
	    });
		 
		 
	    // 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	    function LoadImg(value, num) {
	      if (value.files && value.files[0]) {
	        var reader = new FileReader();
	        reader.onload = function (e) {
	          switch (num) {
	            case 1:
	              $("#titleImg").attr("src", e.target.result);
	              break;
	            case 2:
	              $("#contentImg1").attr("src", e.target.result);
	              break;
	            case 3:
	              $("#contentImg2").attr("src", e.target.result);
	              break;
	            case 4:
	              $("#contentImg3").attr("src", e.target.result);
	              break;
	          }
	          
	          // 이미지가 업로드된 경우 삭제 이미지 목록(deleteImages)에서
	          // 해당 index값을 false로 변경
	          deleteImages[num - 1] = false;
	          console.log(deleteImages);
	          
	        }
	        reader.readAsDataURL(value.files[0]);
	      }
	    }
		
	</script>
</body>
</html>
