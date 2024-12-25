//@WebServlet("/uploadBook")
//@MultipartConfig
//UploadBookServlet
package Servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import sql.DatabaseBook;

@WebServlet("/uploadBook")
@MultipartConfig
public class UploadBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookname = request.getParameter("bookname");
        String bookwriter = request.getParameter("bookwriter");
        String bookprice = request.getParameter("bookprice");
        String bookpublisher = request.getParameter("bookpublisher");
        String bookbriefly = request.getParameter("bookbriefly");

        Part filePart = request.getPart("imageFile");
        
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("message", "请选择一个文件上传");
            request.getRequestDispatcher("/jsp/newbook.jsp").forward(request, response);
            return;
        }

        InputStream inputStream = filePart.getInputStream();

        try (DatabaseBook dbConnectorBook = new DatabaseBook()) {
            dbConnectorBook.connect();

            if (dbConnectorBook.insertBook(bookname, bookwriter, bookprice, bookpublisher, bookbriefly, inputStream)) {
                // 上传成功，重定向到书籍列表
                response.sendRedirect(request.getContextPath() + "/BookList");
            } else {
                request.setAttribute("message", "上传书籍失败");
                request.getRequestDispatcher("/newbook.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "上传过程中发生错误：" + e.getMessage());
            request.getRequestDispatcher("/jsp/newbook.jsp").forward(request, response);
        }
    }
}
