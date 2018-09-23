package entity_M;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实体类--同一isdn的集合（因为书名可以同名）
 * 即同一类别的对象
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018年9月13日 下午3:10:15
 */

public class BookInfo implements Serializable{
	private static final long serialVersionUID = 3160880720635003843L;
	private String isbn;
	private String bookName;
	private String type; // 不希望其他人修改类型-1是配置配件 2.是使用枚举数据
	private String author;
	private String publisher;
	private int inStoreCount;
	private Date publishDate;
	private double price;
	//通过分析：一个BookIfo对应多个BooK对象
	private List<Book> bookList;
	
	
	//可以在构造方法中重写equals和tostring方法
	
	public List<Book> getBookList() {
		return bookList;
	}


	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}


	//向本isbn类别中添加真实的图书对象
	public void addBook(Book book) {
		if(null==bookList) {
			bookList=new ArrayList<>();
		}
		if(null==book) return;
		if(!isbn.equals(book.getIsbn())) return;
		//contains()方法调用了查找--book.equal（）方法
		if(bookList.contains(book)) return;
		bookList.add(book);
		inStoreCount=getInStoreCount()+1;    //进来一本书，入库数+1
		//添加书完成后，为添加的图书添加图书信息
		book.setBookInfo(this);
//		inStoreCount=getInStoreCount()+1;    //上下均可以
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
