package biz_C;

import entity_M.BookInfo;

/**
 * 子类接口--设置特有方法
 * BookInfo业务类  操作BookIn对象（同一书名的集合）
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月14日 上午9:19:36
 */
public interface BookInfoBiz extends Biz<BookInfo> {
	//BookInfo特有方法
	public BookInfo findByIsbn(String isbn);
	//入库
	public boolean outStore(String isbn,int outCount);
	//出库
	public boolean inStore(String isbn,int inCount);
}
