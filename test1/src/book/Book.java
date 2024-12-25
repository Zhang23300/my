package book;

public class Book {
	private int bookid;
    private String bookname;
    private String bookwriter;
    private String bookprice;
    private String bookpublisher;
    private String bookbriefly;
    private String base64Image; 

    public Book(int bookid, String bookname, String bookwriter, String bookprice, String bookpublisher, String bookbriefly, String base64Image) {
        this.bookid = bookid;
    	this.bookname = bookname;
        this.bookwriter = bookwriter;
        this.bookprice = bookprice;
        this.bookpublisher = bookpublisher;
        this.bookbriefly = bookbriefly;
        this.base64Image = base64Image;
    }

    // Getter 方法
    public String getBookname() {
        return bookname;
    }
    
    public int getBookid() {
        return bookid;
    }

    public String getBookwriter() {
        return bookwriter;
    }

    public String getBookprice() {
        return bookprice;
    }

    public String getBookpublisher() {
        return bookpublisher;
    }

    public String getBookbriefly() {
        return bookbriefly;
    }

    public String getBase64Image() {
        return base64Image;
    }
}
