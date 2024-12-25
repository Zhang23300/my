package Servlet;

import sql.DatabaseBook;
import book.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/editBook")
public class EditBookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("id");
        try (DatabaseBook db = new DatabaseBook()) {
            db.connect();
            String sql = "SELECT * FROM book WHERE bookid = ?";
            try (Connection conn = db.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(bookId)); // 转换为 int 类型
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Book book = new Book(
                        rs.getInt("bookid"),
                        rs.getString("bookname"),
                        rs.getString("bookwriter"),
                        rs.getString("bookprice"),
                        rs.getString("bookpublisher"),
                        rs.getString("bookbriefly"),
                        rs.getString("bookdata") // 假设这里是 Base64 编码
                    );
                    request.setAttribute("book", book);
                } else {
                    request.setAttribute("errorMessage", "未找到该书籍。");
                    request.getRequestDispatcher("/error.jsp").forward(request, response); // 跳转到错误页面
                    return; // 确保后续代码不执行
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "获取书籍信息时发生错误：" + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response); // 跳转到错误页面
        }
        request.getRequestDispatcher("/jsp/editBook.jsp").forward(request, response); // 跳转到修改页面
    }
}
