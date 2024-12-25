<%@ page language="java" import="book.Book" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>修改书籍</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editBook.css">
</head>
<body>
    <div class="container">
        <h1>修改书籍信息</h1>
        <form action="${pageContext.request.contextPath}/updateBook" method="post">
            <input type="hidden" name="bookid" value="${book.bookid}" /> <!-- 添加 bookid 隐藏字段 -->
            <input type="hidden" name="bookname" value="${book.bookname}" />
            <div class="form-group">
                <label for="bookwriter">作者:</label>
                <input type="text" id="bookwriter" name="bookwriter" value="${book.bookwriter}" required />
            </div>

            <div class="form-group">
                <label for="bookprice">价格:</label>
                <input type="text" id="bookprice" name="bookprice" value="${book.bookprice}" required />
            </div>

            <div class="form-group">
                <label for="bookpublisher">出版社:</label>
                <input type="text" id="bookpublisher" name="bookpublisher" value="${book.bookpublisher}" required />
            </div>

            <div class="form-group">
                <label for="bookbriefly">简介:</label>
                <input type="text" id="bookbriefly" name="bookbriefly" value="${book.bookbriefly}" required />
            </div>

            <input type="submit" value="更新书籍" class="submit-button" />
        </form>
        </br>
        <form action="${pageContext.request.contextPath}/BookList" method="get" enctype="multipart/form-data">
            <input type="submit" value="返回管理员窗口" />
        </form>
    </div>
</body>
</html>
