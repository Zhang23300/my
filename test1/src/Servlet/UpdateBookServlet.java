package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import book.Book;
import sql.DatabaseBook;

@WebServlet("/updateBook")
public class UpdateBookServlet extends HttpServlet {
    private static final String UPDATE_SQL = "UPDATE book SET bookname = ?, bookwriter = ?, bookprice = ?, bookpublisher = ?, bookbriefly = ? WHERE bookid = ?";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookid = request.getParameter("bookid");
        String bookname = request.getParameter("bookname");
        String bookwriter = request.getParameter("bookwriter");
        String bookprice = request.getParameter("bookprice");
        String bookpublisher = request.getParameter("bookpublisher");
        String bookbriefly = request.getParameter("bookbriefly");

        // 检查 bookid 是否为空
        if (isNullOrEmpty(bookid)) {
            forwardWithError(request, response, "书籍ID不能为空。");
            return;
        }

        try (DatabaseBook db = new DatabaseBook()) {
            db.connect();
            try (Connection conn = db.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, bookname);
                stmt.setString(2, bookwriter);
                stmt.setString(3, bookprice);
                stmt.setString(4, bookpublisher);
                stmt.setString(5, bookbriefly);
                stmt.setInt(6, Integer.parseInt(bookid)); // 转换为 int 类型
                int rowsUpdated = stmt.executeUpdate();
                
                if (rowsUpdated == 0) {
                    forwardWithError(request, response, "未能更新书籍信息，可能是书籍ID无效。");
                    return;
                }
            }
        } catch (SQLException e) {
            forwardWithError(request, response, "更新书籍信息时发生错误：" + e.getMessage());
        } catch (NumberFormatException e) {
            forwardWithError(request, response, "书籍ID格式不正确。");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            forwardWithError(request, response, "数据库连接错误：" + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/BookList"); // 重定向到书籍列表
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}
