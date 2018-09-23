package entity_M;

import java.io.Serializable;

/**
 * 实体书对象 Book对象 包含BookInfo对象 一个BookIfo对应多个BooK对象
 * 
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018年9月13日 下午3:20:52
 */
public class Book implements Serializable{
	private static final long serialVersionUID = 4250767291677144507L;
	// 具体书对象特有的
	private String isbn;
	private String bookID; // 同一类别的书，利用bookid区分
	private BookState bookState;
	// 同时拥有BookInfo信息
	private BookInfo bookInfo;
	
	@Override
	public boolean equals(Object obj) {
		//在这里验证传入的obj对象是不是跟本对象是否是同一本书
		//同一本书的判定条件是：两个对象的内部编号是否一致（BookId）
		if(null==obj) return false;
		if(!(obj instanceof Book)) return false;
		Book book=(Book) obj;
 		return bookID.equals(book.getBookID());
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public BookState getBookState() {
		return bookState;
	}

	public void setBookState(BookState bookState) {
		this.bookState = bookState;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}

}

enum BookState {
	CanBorrow, CannotBorrow, maintain, lost
}