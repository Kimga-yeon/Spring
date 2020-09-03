<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
/*댓글*/
.replyWrite>table {
	width: 90%;
	margin-top : 100px;
}
#replyContentArea {
	width: 90%;
}
#replyContentArea>textarea {
	resize: none;
	width: 100%;
}
#replyBtnArea {
	width: 100px;
	text-align: center;
}
.rWriter {
	display : inline-block;
	margin-right: 30px;
	vertical-align: top;
	font-weight: bold;
	font-size: 1.2em;
}
.rDate {
	display : inline-block;
	font-size: 0.5em;
	color: gray;
}
#replyListArea {
	list-style-type: none;
}
.rContent, .btnArea{
	display: inline-block;
	box-sizing: border-box;
}
.rContent {
	height: 100%;
	width : 100%;
	word-break:normal;
}
.btnArea {
	height: 100%;
	width : 180px;
	text-align: right;
	float: right;
    margin-bottom: 10px;
}
hr{
    clear: both;
}
.replyUpdateContent{
	resize: none;
	width: 70%;
}
/* 답글 */
.reply2-li{
	padding-left: 50px;  
}
.reply2Area{
	padding-top : 30px;
	width : 80%;
}
.reply2Content{
	resize: none;  
	width : 100%; 
}
</style>
<div id="reply-area ">
	<!-- 댓글 작성 부분 -->
	<div class="replyWrite">
		<table align="center">
			<tr>
				<td id="replyContentArea"><textArea rows="3" id="replyContent"></textArea>
				</td>
				<td id="replyBtnArea">
					<button class="btn btn-primary" id="addReply">댓글<br>등록</button>
				</td>
			</tr>
		</table>
	</div>


	<!-- 댓글 출력 부분 -->
	<div class="replyList mt-5 pt-2">
		<ul id="replyListArea">
		</ul>
	</div>

	
</div>

<script>
// 댓글
// 페이지 로딩 완료 시 댓글 목록 호출
$(function(){
	selectReplyList();
});
// 댓글 목록 불러오기
function selectReplyList(){
	var url = "${contextPath}/reply/selectList/${board.boardNo}";
	console.log(url);
	$.ajax({
		url : url,
		type : "POST",
		dataType:"json",
		success:function(rList){
			console.log(123);
			console.log(rList);
			
			// 아이디가 #replyListArea인 요소를 얻어와 $replyListArea 변수에 저장
			var $replyListArea = $("#replyListArea");
			
			$replyListArea.html(""); // 기존 댓글 정보 초기화
			
			// 로그인한 멤버의 아이디를 저장할 변수
			// 로그인 아이디와 댓글 작성자가 같다면 댓글, 수정 삭제 버튼 출력하는 조건문에 사용할 예정
			var loginMemberId = "${loginMember.memberId}";
			
			$.each(rList, function(i){
				console.log(rList[i]);
				var $li = $("<li>").addClass("reply-row").attr("id", rList[i].replyNo);
				
				if(rList[i].replyDepth == 1){
					$li.addClass("reply2-li");
				}
				
				// 작성자, 작성일, 수정일 영역 
				var $div = $("<div>");
				var $rWriter = $("<a>").addClass("rWriter idSelect").html(rList[i].memberId);
				var $rDate = $("<p>").addClass("rDate")
								.html("작성일 : " + rList[i].replyCreateDate + "<br>"
											+ "마지막 수정 날짜 : " + rList[i].replyModifyDate);
				$div.append($rWriter).append($rDate)
				
				
				// 댓글 내용
				var $rContent = $("<p>").addClass("rContent").html(rList[i].replyContent);
				
				
				// 답글, 수정, 삭제 버튼 영역
				var $btnArea = $("<div>").addClass("btnArea");
				
				// 로그인 되어 있는 경우에 답글 버튼 추가
				if(loginMemberId != ""){
					// ** 추가되는 댓글에 onclick 이벤트를 부여하여 버튼 클릭 시 답글창을 생성하는 함수를 이벤트 핸들러로 추가함. 
					var $reply2 = $("<button>").addClass("btn btn-sm btn-primary ml-1 reply2").text("답글").attr("onclick", "addReply2Area(this, "+rList[i].parentReplyNo+")");
					$btnArea.append($reply2);
				}
				
				// 현재 댓글의 작성자와 로그인한 멤버의 아이디가 같을 때 버튼 추가
				if(rList[i].memberId == loginMemberId){
					
					var $showUpdate = $("<button>").addClass("btn btn-sm btn-primary ml-1").text("수정");
					var $deleteReply = $("<button>").addClass("btn btn-sm btn-primary ml-1").text("삭제");
					$btnArea.append($showUpdate, $deleteReply);
				}
				
				
				// 댓글 경계선
				var $hr = $("<hr>");
		
				
				// 댓글 하나로 합치기
				$li.append($div).append($rContent).append($btnArea);
				
				// 댓글 영역을 화면에 배치
				$replyListArea.append($li).append($hr);
			});
			
		}, error : function(request, status, error){
			 	console.log("ajax 통신 오류");
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        
		}
	});
}
//-----------------------------------------------------------------------------------------
// 댓글 등록
$("#addReply").on("click", function(){
	
	// 댓글 내용을 얻어와 변수에 저장
	var replyContent = $("#replyContent").val();
	
	// 로그인이 되어있지 않은 경우
	if(${empty loginMember} == true){
		alert("로그인 후 이용해 주세요.");	
		
	}else{
		// 댓글이 작성되었는지 유효성 검사
		if(replyContent.trim().length == 0){
			alert("댓글 작성 후 클릭해주세요.");
			$("#replyContent").focus();
			
		// 로그인이 되어있고, 댓글이 작성이 된 상태로 댓글 등록 버튼이 클릭된 경우
		}else{
			var url = "${contextPath}/reply/insertReply/${board.boardNo}";
			var memberId = "${loginMember.memberNo}"; // 회원 아이디에 회원 번호를 저장해서 전달
				$.ajax({
					url : url,
					type : "POST",
					data : {"memberId" : memberId,
							"replyContent" : replyContent},
					dataType : "text",
					success : function(result){
						alert(result);
						$("#replyContent").val(""); // 기존 댓글 삭제
						selectReplyList();
					},error : function(){
						console.log("통신 실패");
					}
			});
		}
	} 
});
//-----------------------------------------------------------------------------------------
// 답글 버튼 클릭 동작
function addReply2Area(el, parentReplyNo){
	// el : 클릭된 답글 버튼, // parentReplyNo : 클릭된 답글 버튼이 포함된 댓글의 부모 댓글 번호
	
	// 답글 작성 영역이 여러 개 생기지 않도록 처리
	var check = cancelReply2();
	
	var replyWriter = $(el).parent().prev().prev().children("a").text();
	
	if(check){
		var $div = $("<div>").addClass("reply2Area");
		var $textArea = $("<textarea rows='3'>").addClass("reply2Content").attr("placeholder", replyWriter + "님께 답글 작성하기");
		var $btnArea = $("<div>").addClass("btnArea");
		
		var $insertBtn = $("<button>").addClass("btn btn-sm btn-primary ml-1").text("등록").attr("onclick", "addReply2(this, " + parentReplyNo + ", \'" + replyWriter + "\')");
		var $cancelBtn = $("<button>").addClass("btn btn-sm btn-secondary ml-1 reply-cancel").text("취소").attr("onclick", "cancelReply2()");
		
		$btnArea.append($insertBtn,$cancelBtn);
		
		$div.append($textArea, $btnArea);  
		$(el).parent().after($div);
	}
	
	$(".reply2Content").focus();
}
//-----------------------------------------------------------------------------------------
// 답글 등록
function addReply2(el, parentReplyNo, replyWriter){
	console.log($(el).parent().prev().val());
	console.log(parentReplyNo);
	console.log(replyWriter);
	
	var replyContent = $(el).parent().prev().val();
	var memberId = "${loginMember.memberNo}"; // 답글 작성자 회원 번호
	
	$.ajax({
		url : "${contextPath}/reply/insertReply2/${board.boardNo}",
		data : {"replyContent" : replyContent,
				"parentReplyNo" : parentReplyNo,
				"memberId" : memberId},
		dataType : "text",
		success : function(result){
			alert(result);
			
			selectReplyList();
		},error : function(){
			console.log("통신 실패");
		}
	});  
}
//-----------------------------------------------------------------------------------------
// 답글 취소
function cancelReply2(){
	
	// 다른 답글이 작성된 상태로  새로운 답글이 클릭된 경우 
	// 이미 작성된 답글을 삭제할 것이지 확인하는 작업.
	var tmp = $(".reply2Area").children("textArea").val();
	// -> textarea 값이 작성되어 있지 않으면 ""(빈문자열) 반환
	
	if(tmp == "" || tmp == undefined){
		$(".reply2Area").remove();
		
		return true;
		
	}else{
		var cancelConfirm = confirm("작성된 댓글 내용이 사라집니다. 취소 하시겠습니까?");
		
		if(cancelConfirm){
			$(".reply2Area").remove();
		}
		
		return cancelConfirm;
	}
	
}
</script>