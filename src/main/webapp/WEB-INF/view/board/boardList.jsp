<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
  <h2   class="text-center">${boardName}</h2>
  <p  class="text-right">	<a class="btn btn-primary" 	href="boardForm">게시판입력</a></p>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>번호</th>
        <th>작성자</th>
        <th>제목</th>
         <th>날자</th>
         <th>조회수</th>
         <th>파일</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach   var="b"  items="${li }">
      <tr>
        <td>${b.num }</td>
        <td>${b.name }</td>
          <td><a    href="boardInfo?num=${b.num}"      >${b.subject }</a></td>
            <td>${b.regdate }</td>
              <td>${b.readcnt }</td>
                <td>${b.file1 }</td>  
      </tr>
   </c:forEach>
    </tbody>
  </table>

  <ul class="pagination  "  style="justify-content: center">
  <li class="page-item   disabled " ><a class="page-link" href="#">Previous</a></li>
   <li class="page-item active"><a class="page-link" href="#">1</a></li>
  <li class="page-item"><a class="page-link" href="#">2</a></li>
  <li class="page-item"><a class="page-link" href="#">3</a></li>
  <li class="page-item   "><a class="page-link" href="#">Next</a></li>
</ul>
  
  
</div>
</body>
</html>