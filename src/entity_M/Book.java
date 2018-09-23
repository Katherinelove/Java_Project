package entity_M;

import java.io.Serializable;

/**
 * ʵ������� Book���� ����BookInfo���� һ��BookIfo��Ӧ���BooK����
 * 
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018��9��13�� ����3:20:52
 */
public class Book implements Serializable{
	private static final long serialVersionUID = 4250767291677144507L;
	// ������������е�
	private String isbn;
	private String bookID; // ͬһ�����飬����bookid����
	private BookState bookState;
	// ͬʱӵ��BookInfo��Ϣ
	private BookInfo bookInfo;
	
	@Override
	public boolean equals(Object obj) {
		//��������֤�����obj�����ǲ��Ǹ��������Ƿ���ͬһ����
		//ͬһ������ж������ǣ�����������ڲ�����Ƿ�һ�£�BookId��
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