package entity_M;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ʵ����--ͬһisdn�ļ��ϣ���Ϊ��������ͬ����
 * ��ͬһ���Ķ���
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018��9��13�� ����3:10:15
 */

public class BookInfo implements Serializable{
	private static final long serialVersionUID = 3160880720635003843L;
	private String isbn;
	private String bookName;
	private String type; // ��ϣ���������޸�����-1��������� 2.��ʹ��ö������
	private String author;
	private String publisher;
	private int inStoreCount;
	private Date publishDate;
	private double price;
	//ͨ��������һ��BookIfo��Ӧ���BooK����
	private List<Book> bookList;
	
	
	//�����ڹ��췽������дequals��tostring����
	
	public List<Book> getBookList() {
		return bookList;
	}


	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}


	//��isbn����������ʵ��ͼ�����
	public void addBook(Book book) {
		if(null==bookList) {
			bookList=new ArrayList<>();
		}
		if(null==book) return;
		if(!isbn.equals(book.getIsbn())) return;
		//contains()���������˲���--book.equal��������
		if(bookList.contains(book)) return;
		bookList.add(book);
		inStoreCount=getInStoreCount()+1;    //����һ���飬�����+1
		//�������ɺ�Ϊ��ӵ�ͼ�����ͼ����Ϣ
		book.setBookInfo(this);
//		inStoreCount=getInStoreCount()+1;    //���¾�����
	}
	
	
	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getInStoreCount() {
		return inStoreCount;
	}

	public void setInStoreCount(int inStoreCount) {
		this.inStoreCount = inStoreCount;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
