package biz_C.biz_impl;
import java.io.File;
/**
 * BookInfoBiz接口的实现类
 */
import java.util.Map;


import biz_C.BookInfoBiz;
import entity_M.BookInfo;
import util.FileUtil;

public class BookInfoBIzImplV1 implements BookInfoBiz {
	private static final long serialVersionUID = 6299396032835537501L;

	@Override
	public boolean add(BookInfo bookInfo) {
		//有参数时必选判断
		if(null==bookInfo) return false;
		//读取书库（文件）里面的所有书(Map)
		//判断这本书书库里面是否存在--添加此书
		//将所有书（Map）写回文件中去
		/**虽然只有一句话 封装方法（房方便以后，更改方便）*/
		Map<String, BookInfo> bookInfoMap=findAll();  
		if(null==bookInfoMap) return false;      //加强逻辑，可能读取数据为空！
		//isbn为唯一标识（的好处），直接查看map中的键值是否相等（List/Set中必须判断是否包含对象）
		if(bookInfoMap.containsKey(bookInfo.getIsbn())) return false;
		//向Map中添加此书籍 ，相当于add
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		//写回文件
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	/**
	 * 删除同一书名的书
	 */
	public boolean del(BookInfo bookInfo) {
		//与添加书籍的逻辑一致
		if(null==bookInfo) return false;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return false;      //加强逻辑，可能读取数据为空！
		
		//包含才能删除
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())) return false;
		
		//删除
		bookInfoMap.remove(bookInfo.getIsbn());
		
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public BookInfo update(BookInfo bookInfo) {
		//与添加书籍的逻辑一致
		if(null==bookInfo) return null;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return null;      //加强逻辑，可能读取数据为空！
		
		//包含才能删除
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())) return null;
		
		//更新   ，重新添加此键对象相当于更新
		//有此键则更新  没有则添加
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		
		FileUtil.saveBookInfoMap(bookInfoMap);
		return bookInfo;
	}

	@Override
	public BookInfo findByid(String id) {
		//BookInfo里面没有id,只有isbn;
		//1.0直接提示方法不可用
		//2.0手动抛出不支持的操作异常
		//如果一个方法在子类中没有意义，可抛出一下异常
		throw new UnsupportedOperationException
		("BookInfoBiz中不支持根据id的查询，请调用 findByIsbn");
//		return null;    不会运行到这里来
	}

	@Override
	public Map<String, BookInfo> findAll() {
		return FileUtil.readBookInfoMap();
	}

	@Override
	public boolean outStore(String isbn, int outCount) {
		//出库
		if(null==isbn||outCount<=0) return false;
		BookInfo bookInfo=findByIsbn(isbn);
		if(null==bookInfo) return false;
		
		if(bookInfo.getInStoreCount()<outCount) return false;
		
		//表示出库条件符合
		//出库
		bookInfo.setInStoreCount(bookInfo.getInStoreCount()-outCount);
		Map<String, BookInfo> bookInfoMap=findAll(); //上面的findByIsbn(isbn)已经验证过了
		//更新
		bookInfoMap.put(isbn, bookInfo);
		//写入文件
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public boolean inStore(String isbn, int inCount) {
		if(null==isbn||"".equals(isbn)) return false;
		if(inCount<=0) return false;
		BookInfo bookInfo=findByIsbn(isbn);
		if(null==bookInfo) return false; 
		//入库
		Map<String, BookInfo> bookInfoMap=findAll();
		
		bookInfo.setInStoreCount(bookInfo.getInStoreCount()+inCount);
		bookInfoMap.put(isbn, bookInfo);
		
		//千万别忘记写入文件中区
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public BookInfo findByIsbn(String isbn) {
		if(null==isbn||"".equals(isbn)) return null;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return null;      //加强逻辑，可能读取数据为空！
		
		if(!bookInfoMap.containsKey(isbn)) return null;
		return bookInfoMap.get(isbn);
	}

}
