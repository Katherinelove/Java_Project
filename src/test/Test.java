package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import auth.Role;
import biz_C.BookInfoBiz;
import biz_C.UserBiz;
import biz_C.biz_impl.BookInfoBIzImplV1;
import biz_C.biz_impl.UserBizImplV1;
import entity_M.Book;
import entity_M.BookInfo;
import entity_M.User;
import util.FileUtil;
import view_V.BookView;

public class Test {

	public static void main(String[] args) {
//		测试BookInfoBiz();
//		测试角色();
//		测试用户类();
//		测试user();
		测试view() ;
//		查看注册用户及其权限();
	}
	
	
	public static void 查看注册用户及其权限() {
		UserBiz userBiz=new UserBizImplV1();
		Map<String, User> userinfoMap=userBiz.findAll();
		遍历userMap(userinfoMap);

		
	}
	public static void 测试view() {
		BookView bookView=new BookView();
	}
	
	public static void 测试user() {
		UserBiz userBiz=new UserBizImplV1();
		User user1=new User();
		user1.setUsername("舒淇");
		user1.setPassword("shuqi");
		
		
		
		User user2=new User();
		user2.setUsername("katherine");
		user2.setPassword("123456");
		//设置角色b
		
		user2.setRole(new Role("卡瑟琳","oparator"));
		
		User user3=new User();
		user3.setUsername("齐天大圣");
		user3.setPassword("123456");
		//设置角色b
		user3.setRole(new Role("齐天大圣","administrator"));
		
		
		User user5=new User();
		user5.setUsername("曾帅");
		user5.setPassword("katherine");
		//设置角色b
		user5.setRole(new Role("自由之神","administrator"));
		
		
		
	
		Map<String,User> userInfoMap=new HashMap<>();
		userInfoMap.put(user1.getUsername(), user1);
		userInfoMap.put(user2.getUsername(), user2);
		userInfoMap.put(user5.getUsername(), user5);
		FileUtil.saveUserInfoMap(userInfoMap);
		System.out.println("写入成功！");
		
		System.out.println("========加载数据：");
		userInfoMap=userBiz.findAll();
		遍历userMap(userInfoMap);
		
		System.out.println("========add():");
		if(userBiz.add(user3)) {
			System.out.println("添加user3成功！");
		}else {
			System.out.println("添加失败");
		}
		
		System.out.println("=======加载数据：");
		userInfoMap=userBiz.findAll();
		遍历userMap(userInfoMap);
		
		System.out.println("========del():");
		if(userBiz.del(user3)) {
			System.out.println("删除user3成功！");
		}else {
			System.out.println("删除失败");
		}
		System.out.println("=======加载数据：");
		userInfoMap=userBiz.findAll();
		遍历userMap(userInfoMap);
//		
//		System.out.println("========modiypasswoed():");
//		User user4=new User();
//		user4.setUsername("katherine");
//		user4.setPassword("654321");
//		userBiz.update(user4);
//		System.out.println("=======加载数据：");
//		userInfoMap=userBiz.findAll();
//		遍历userMap(userInfoMap);	
	}
	
	public static void 测试用户类() {
		User user=new User(new Role("齐天大圣", "administrator"));
		user.setUsername("孙悟空");
		user.setPassword("123456");
		Map<String, User> userInfoMap=new HashMap<>();
		userInfoMap.put(user.getUsername(),user);
		//写入用户
		FileUtil.saveUserInfoMap(userInfoMap);
		//读取
		userInfoMap=FileUtil.readUserInfoMap();
		if(null==userInfoMap) System.out.println("没有用户文件！");
//		for (User temp_user : userInfoMap) {
//			System.out.println(temp_user.getUsername()+":"+temp_user.getRole().getKey());
//		}
	}
	
	public static void 测试角色() {
		Role role1=new Role();
		System.out.println(role1.getName()+"\t\t"+role1.getKey());
		System.out.println("权限集合"+"\t\t"+role1.getPermisstions());		
		
		Map<String, BookInfo>bookInfoMap=FileUtil.readBookInfoMap();
		System.out.println("==============");
		遍历Map(bookInfoMap);
		
		if(role1.inStore("123-4564",1)) {
			System.out.println("successful");
		}else {
			System.out.println("failed");
		}
		BookInfoBiz bookInfoBiz=new BookInfoBIzImplV1();
		BookInfo book1=bookInfoBiz.findByIsbn("123-4564");
		System.out.println(book1.getBookName()+"--"+book1.getInStoreCount());
		
		
//		Role role2=new Role("操作员","oparator");
//		System.out.println(role2.getName()+"\t\t"+role2.getKey());
//		System.out.println("权限集合"+"\t\t"+role2.getPermisstions());
	
	
	}
	
	public static void 测试BookInfoBiz() {
		BookInfoBiz bookInfoBiz=new BookInfoBIzImplV1();
		//随机添加5本书
		Map<String, BookInfo> bookInfoMap=new HashMap<>();
		for(int j=1;j<=4;j++) {
			BookInfo bookInfo=new BookInfo();
			bookInfo.setBookName("我的世界你不懂"+j);
			bookInfo.setIsbn("123-456"+j);
			
			for(int i=0;i<5;i++) {
				Book book=new Book();
				book.setBookID((i+0)+"");   //整型转字符型
				book.setIsbn("123-456"+j);
				//添加书
				bookInfo.addBook(book);
			}
			bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		}
		//写入文件
		FileUtil.saveBookInfoMap(bookInfoMap);
		
//		BookInfo curBookInfo=bookInfoBiz.findByIsbn("123-4561");
//		System.out.println(curBookInfo.getIsbn()+"的库存:"+curBookInfo.getInStoreCount());
//		
		BookInfo bookInfo=new BookInfo();
		bookInfo.setBookName("我的世界你不懂");
		bookInfo.setIsbn("123-123");
		
		for(int i=0;i<5;i++) {
			Book book=new Book();
			book.setBookID((i+0)+"");   //整型转字符型
			book.setIsbn("123-123");
			//添加书
			bookInfo.addBook(book);
		}
		System.out.println("***原始：");
		bookInfoMap=bookInfoBiz.findAll();
		遍历Map(bookInfoMap);
		
		
		bookInfoBiz.add(bookInfo);
		System.out.println("***add后：");
		bookInfoMap=bookInfoBiz.findAll();
		遍历Map(bookInfoMap);
		
		
		System.out.print("请输入入库isbn：");
		String isbn=new Scanner(System.in).nextLine().trim();
		if(bookInfoBiz.inStore(isbn, 20)) {
			System.out.println("入库成功！");
			bookInfoMap=bookInfoBiz.findAll();
			遍历Map(bookInfoMap);
		}else{
			System.out.println("入库失败！");
		}
		
		System.out.print("请输入出库isbn：");
		String outisbn=new Scanner(System.in).nextLine().trim();
		if(bookInfoBiz.outStore(outisbn, 20)) {
			System.out.println("出库成功！");
			bookInfoMap=bookInfoBiz.findAll();
			遍历Map(bookInfoMap);
		}else{
			System.out.println("出库失败！");
		}
		

		bookInfoBiz.del(bookInfo);
		System.out.println("***del后：");
		bookInfoMap=bookInfoBiz.findAll();
		遍历Map(bookInfoMap);
		
		

//		
//		BookInfo curBookInfo=bookInfoBiz.findByIsbn(isbn);
//		System.out.println("当前库存为："+curBookInfo.getInStoreCount());
//		
//		System.out.print("亲输入出库isbn：");
//		String isbn1=new Scanner(System.in).nextLine();
//		if(!bookInfoBiz.outStore(isbn1, 2)) {
//			System.out.println("出库失败！");
//		};
//		
//		curBookInfo=bookInfoBiz.findByIsbn(isbn);
//		System.out.println("当前库存为："+curBookInfo.getInStoreCount());
		
//		bookInfoMap=bookInfoBiz.findAll();
//		遍历Map(bookInfoMap);
		
//		bookInfoBiz.findByid("123");
	}
	
	public static void 测试实体类和文件工具类() {
		BookInfo bookInfo=new BookInfo();
		//这一类书的书名
		bookInfo.setIsbn("156-456");
		bookInfo.setBookName("三生三世");
		Book book=new Book();
		book.setIsbn("156-456");
		bookInfo.addBook(book);
		//以集合的对象，存储序列化对象
		Map<String, BookInfo> bookInfoMap=new HashMap<>();
		//向集合中添加信息（key：value）
		bookInfoMap.put(book.getIsbn(), bookInfo);
		System.out.println("存储之前：");
		遍历Map(bookInfoMap);
//		System.out.println(bookInfoMap);
//		for(String isbn:bookInfoMap.keySet()) {
//			System.out.println(isbn);
//		}
		
		FileUtil.saveBookInfoMap(bookInfoMap);
	
		System.out.println(book.getBookInfo().getBookName());
		//以集合的对象，加载序列化对象
		bookInfoMap=FileUtil.readBookInfoMap();
		System.out.println("存储之后：");
		遍历Map(bookInfoMap);
//		for(String isbn:bookInfoMap.keySet()) {
//			System.out.println(isbn);
//		}
		
	}
	public static void 遍历Map(Map<String, BookInfo> map) {
		Set<Entry<String, BookInfo>> entrys=map.entrySet();
		for (Entry<String, BookInfo> entry : entrys) {
			System.out.println(entry.getKey()+":"+entry.getValue().getInStoreCount());
		}
	}
	public static void 遍历userMap(Map<String, User> map) {
		Set<Entry<String, User>> entrys=map.entrySet();
		for (Entry<String, User> entry : entrys) {
			System.out.println(entry.getKey()+":"+entry.getValue().getPassword()+":"+entry.getValue().getRole().getKey());
		}
	}

}
