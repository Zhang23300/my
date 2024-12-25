<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>上传书籍信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newbook.css">
</head>
<body>
    <div class="div_01">
        <h1>上传书籍信息</h1>
    </div>

    <div class="div_02">
        <form action="${pageContext.request.contextPath}/uploadBook" method="post" enctype="multipart/form-data">
            <label for="bookname">书名:</label>
            <input type="text" id="bookname" name="bookname" required aria-label="书名" />

            <label for="bookwriter">作者:</label>
            <input type="text" id="bookwriter" name="bookwriter" required aria-label="作者" />

            <label for="bookprice">价格:</label>
            <input type="text" id="bookprice" name="bookprice" required aria-label="价格" />

            <label for="bookpublisher">出版社:</label>
            <input type="text" id="bookpublisher" name="bookpublisher" required aria-label="出版社" />

            <label for="bookbriefly">简介:</label>
            <input type="text" id="bookbriefly" name="bookbriefly" required aria-label="简介" />

            <label for="imageFile">选择图片:</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" required aria-label="选择图片" onchange="previewImage(event)" />

            <!-- 用于显示选中的图片 -->
            <img id="preview" src="" alt="Image Preview" />

            <input type="submit" value="上传" />
            
        </form>
        <form action="${pageContext.request.contextPath}/BookList" method="get" enctype="multipart/form-data">
	        <input type="submit" value="返回管理员窗口" />
	    </form>
    </div>



    <script>
        function previewImage(event) {
            const file = event.target.files[0]; // 获取文件
            const preview = document.getElementById('preview'); // 获取图片元素

            if (file) {
                const reader = new FileReader(); // 创建 FileReader 对象
                reader.onload = function(e) {
                    preview.src = e.target.result; // 设置图片源
                    preview.style.display = 'block'; // 显示图片
                }
                reader.readAsDataURL(file); // 读取文件为 Data URL
            } else {
                preview.src = ''; // 清空图片源
                preview.style.display = 'none'; // 隐藏图片
            }
        }
    </script>
</body>
</html>
