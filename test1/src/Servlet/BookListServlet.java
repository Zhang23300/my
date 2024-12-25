package Servlet;

import book.Book;
import sql.DatabaseBook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebServlet("/BookList")
public class BookListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>(); // 初始化书籍列表

        try (DatabaseBook dbConnectorBook = new DatabaseBook()) {
            dbConnectorBook.connect();
            books = fetchBooksFromDatabase(dbConnectorBook); // 从数据库获取书籍列表
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "获取书籍列表时发生错误：" + e.getMessage());
        }

        request.setAttribute("books", books); // 将书籍列表传递到 JSP 页面
        request.getRequestDispatcher("/jsp/Admin.jsp").forward(request, response); // 转发到 JSP 页面
    }

    private List<Book> fetchBooksFromDatabase(DatabaseBook dbConnectorBook) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT bookid, bookname, bookwriter, bookprice, bookpublisher, bookbriefly, bookdata FROM book";

        try (Connection conn = dbConnectorBook.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookid = rs.getInt("bookid"); // 修复这里，使用 getInt 而不是 getint
                String bookname = rs.getString("bookname");
                String bookwriter = rs.getString("bookwriter");
                String bookprice = rs.getString("bookprice");
                String bookpublisher = rs.getString("bookpublisher");
                String bookbriefly = rs.getString("bookbriefly");
                Blob blob = rs.getBlob("bookdata");

                String base64Image = null;
                if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream();
                         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] imageData = outputStream.toByteArray();
                        base64Image = Base64.getEncoder().encodeToString(imageData);
                    } catch (IOException e) {
                        System.err.println("读取 Blob 数据时发生错误: " + e.getMessage());
                    }
                }

                books.add(new Book(bookid, bookname, bookwriter, bookprice, bookpublisher, bookbriefly, base64Image)); // 创建 Book 对象
            }
        } catch (SQLException e) {
            System.err.println("数据库操作时发生错误: " + e.getMessage());
            throw e;
        }
        return books;
    }
}
