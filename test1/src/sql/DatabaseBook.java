package sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBook implements AutoCloseable {
    private Connection conn = null;
    private PreparedStatement stmt = null;

    // 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/train_db?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    // 构造函数
    public DatabaseBook() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    // 连接到数据库
    public void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public Connection getConnection() {
        return conn; // 返回当前连接
    }

    // 插入数据的方法
    public boolean insertBook(String bookname, String bookwriter, String bookprice, String bookpublisher, String bookbriefly, InputStream bookdata) throws SQLException {
        String sql = "INSERT INTO book (bookname, bookwriter, bookprice, bookpublisher, bookbriefly, bookdata) VALUES (?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, bookname);
        stmt.setString(2, bookwriter);
        stmt.setString(3, bookprice);
        stmt.setString(4, bookpublisher);
        stmt.setString(5, bookbriefly);
        stmt.setBlob(6, bookdata); // 将图像数据插入数据库

        return stmt.executeUpdate() > 0; // 返回插入是否成功
    }

    // 关闭资源
    @Override
    public void close() {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
