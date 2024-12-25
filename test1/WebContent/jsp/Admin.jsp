<%@ page language="java" import="java.sql.*, java.util.*, book.Book" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>管理员窗口</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Admin.css">
</head>
<body>
    <div class="div_01">
        <h1>管理员</h1>
    </div>
    <div class="div_02">
		欢迎您, 亲爱的管理员<br/>
		获取对应的名字
        <span class="span_01">
        	<%= session.getAttribute("userName") %>
        </span><br />
                     当前时间：<span id="span_02"></span>
    </div>
    <div class="div_03">
        <table>
            <tr>
                <th>书名</th>
                <th>作者</th>
                <th>标价</th>
                <th>出版社</th>
                <th>简介</th>
                <th>图像</th>
                <th>操作</th> <!-- 添加操作列 -->
            </tr>
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books == null || books.isEmpty()) {
            %>
            <tr>
                <td colspan="7">
                	<div>
		            	<a href="${pageContext.request.contextPath}/BookList">展开</a>
		        	</div>
                </td>
            </tr>
            <%
                } else {
                    for (Book book : books) {
            %>
            <tr>
                <td><%= book.getBookname() %></td>
                <td><%= book.getBookwriter() %></td>
                <td><%= book.getBookprice() %></td>
                <td><%= book.getBookpublisher() %></td>
                <td><%= book.getBookbriefly() %></td>
                <td><img src="data:image/png;base64,<%= book.getBase64Image() %>" alt="<%= book.getBookname() %>" style="max-width: 100px; max-height: 100px;"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/editBook?id=<%= book.getBookid() %>">修改</a>
                    <a href="${pageContext.request.contextPath}/deleteBook?id=<%= book.getBookid() %>" onclick="return confirm('确定要删除这本书吗？');">删除</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <br />
        <div>
            <a href="${pageContext.request.contextPath}/jsp/newbook.jsp">【上新图书】</a>
        </div>
    </div>
    <script type="text/javascript">
        function updateTime() {
            var date = new Date();
            var time = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " +
                       date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            document.getElementById("span_02").innerHTML = time;
        }
        window.setInterval(updateTime, 1000); // 每秒更新一次时间
    </script>
</body>
</html>
