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
//		����BookInfoBiz();
//		���Խ�ɫ();
//		�����û���();
//		����user();
		����view() ;
//		�鿴ע���û�����Ȩ��();
	}
	
	
	public static void �鿴ע���û�����Ȩ��() {
		UserBiz userBiz=new UserBizImplV1();
		Map<String, User> userinfoMap=userBiz.findAll();
		����userMap(userinfoMap);

		
	}
	public static void ����view() {
		BookView bookView=new BookView();
	}
	
	public static void ����user() {
		UserBiz userBiz=new UserBizImplV1();
		User user1=new User();
		user1.setUsername("���");
		user1.setPassword("shuqi");
		
		
		
		User user2=new User();
		user2.setUsername("katherine");
		user2.setPassword("123456");
		//���ý�ɫb
		
		user2.setRole(new Role("��ɪ��","oparator"));
		
		User user3=new User();
		user3.setUsername("�����ʥ");
		user3.setPassword("123456");
		//���ý�ɫb
		user3.setRole(new Role("�����ʥ","administrator"));
		
		
		User user5=new User();
		user5.setUsername("��˧");
		user5.setPassword("katherine");
		//���ý�ɫb
		user5.setRole(new Role("����֮��","administrator"));
		
		
		
	
		Map<String,User> userInfoMap=new HashMap<>();
		userInfoMap.put(user1.getUsername(), user1);
		userInfoMap.put(user2.getUsername(), user2);
		userInfoMap.put(user5.getUsername(), user5);
		FileUtil.saveUserInfoMap(userInfoMap);
		System.out.println("д��ɹ���");
		
		System.out.println("========�������ݣ�");
		userInfoMap=userBiz.findAll();
		����userMap(userInfoMap);
		
		System.out.println("========add():");
		if(userBiz.add(user3)) {
			System.out.println("���user3�ɹ���");
		}else {
			System.out.println("���ʧ��");
		}
		
		System.out.println("=======�������ݣ�");
		userInfoMap=userBiz.findAll();
		����userMap(userInfoMap);
		
		System.out.println("========del():");
		if(userBiz.del(user3)) {
			System.out.println("ɾ��user3�ɹ���");
		}else {
			System.out.println("ɾ��ʧ��");
		}
		System.out.println("=======�������ݣ�");
		userInfoMap=userBiz.findAll();
		����userMap(userInfoMap);
//		
//		System.out.println("========modiypasswoed():");
//		User user4=new User();
//		user4.setUsername("katherine");
//		user4.setPassword("654321");
//		userBiz.update(user4);
//		System.out.println("=======�������ݣ�");
//		userInfoMap=userBiz.findAll();
//		����userMap(userInfoMap);	
	}
	
	public static void �����û���() {
		User user=new User(new Role("�����ʥ", "administrator"));
		user.setUsername("�����");
		user.setPassword("123456");
		Map<String, User> userInfoMap=new HashMap<>();
		userInfoMap.put(user.getUsername(),user);
		//д���û�
		FileUtil.saveUserInfoMap(userInfoMap);
		//��ȡ
		userInfoMap=FileUtil.readUserInfoMap();
		if(null==userInfoMap) System.out.println("û���û��ļ���");
//		for (User temp_user : userInfoMap) {
//			System.out.println(temp_user.getUsername()+":"+temp_user.getRole().getKey());
//		}
	}
	
	public static void ���Խ�ɫ() {
		Role role1=new Role();
		System.out.println(role1.getName()+"\t\t"+role1.getKey());
		System.out.println("Ȩ�޼���"+"\t\t"+role1.getPermisstions());		
		
		Map<String, BookInfo>bookInfoMap=FileUtil.readBookInfoMap();
		System.out.println("==============");
		����Map(bookInfoMap);
		
		if(role1.inStore("123-4564",1)) {
			System.out.println("successful");
		}else {
			System.out.println("failed");
		}
		BookInfoBiz bookInfoBiz=new BookInfoBIzImplV1();
		BookInfo book1=bookInfoBiz.findByIsbn("123-4564");
		System.out.println(book1.getBookName()+"--"+book1.getInStoreCount());
		
		
//		Role role2=new Role("����Ա","oparator");
//		System.out.println(role2.getName()+"\t\t"+role2.getKey());
//		System.out.println("Ȩ�޼���"+"\t\t"+role2.getPermisstions());
	
	
	}
	
	public static void ����BookInfoBiz() {
		BookInfoBiz bookInfoBiz=new BookInfoBIzImplV1();
		//������5����
		Map<String, BookInfo> bookInfoMap=new HashMap<>();
		for(int j=1;j<=4;j++) {
			BookInfo bookInfo=new BookInfo();
			bookInfo.setBookName("�ҵ������㲻��"+j);
			bookInfo.setIsbn("123-456"+j);
			
			for(int i=0;i<5;i++) {
				Book book=new Book();
				book.setBookID((i+0)+"");   //����ת�ַ���
				book.setIsbn("123-456"+j);
				//�����
				bookInfo.addBook(book);
			}
			bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		}
		//д���ļ�
		FileUtil.saveBookInfoMap(bookInfoMap);
		
//		BookInfo curBookInfo=bookInfoBiz.findByIsbn("123-4561");
//		System.out.println(curBookInfo.getIsbn()+"�Ŀ��:"+curBookInfo.getInStoreCount());
//		
		BookInfo bookInfo=new BookInfo();
		bookInfo.setBookName("�ҵ������㲻��");
		bookInfo.setIsbn("123-123");
		
		for(int i=0;i<5;i++) {
			Book book=new Book();
			book.setBookID((i+0)+"");   //����ת�ַ���
			book.setIsbn("123-123");
			//�����
			bookInfo.addBook(book);
		}
		System.out.println("***ԭʼ��");
		bookInfoMap=bookInfoBiz.findAll();
		����Map(bookInfoMap);
		
		
		bookInfoBiz.add(bookInfo);
		System.out.println("***add��");
		bookInfoMap=bookInfoBiz.findAll();
		����Map(bookInfoMap);
		
		
		System.out.print("���������isbn��");
		String isbn=new Scanner(System.in).nextLine().trim();
		if(bookInfoBiz.inStore(isbn, 20)) {
			System.out.println("���ɹ���");
			bookInfoMap=bookInfoBiz.findAll();
			����Map(bookInfoMap);
		}else{
			System.out.println("���ʧ�ܣ�");
		}
		
		System.out.print("���������isbn��");
		String outisbn=new Scanner(System.in).nextLine().trim();
		if(bookInfoBiz.outStore(outisbn, 20)) {
			System.out.println("����ɹ���");
			bookInfoMap=bookInfoBiz.findAll();
			����Map(bookInfoMap);
		}else{
			System.out.println("����ʧ�ܣ�");
		}
		

		bookInfoBiz.del(bookInfo);
		System.out.println("***del��");
		bookInfoMap=bookInfoBiz.findAll();
		����Map(bookInfoMap);
		
		

//		
//		BookInfo curBookInfo=bookInfoBiz.findByIsbn(isbn);
//		System.out.println("��ǰ���Ϊ��"+curBookInfo.getInStoreCount());
//		
//		System.out.print("���������isbn��");
//		String isbn1=new Scanner(System.in).nextLine();
//		if(!bookInfoBiz.outStore(isbn1, 2)) {
//			System.out.println("����ʧ�ܣ�");
//		};
//		
//		curBookInfo=bookInfoBiz.findByIsbn(isbn);
//		System.out.println("��ǰ���Ϊ��"+curBookInfo.getInStoreCount());
		
//		bookInfoMap=bookInfoBiz.findAll();
//		����Map(bookInfoMap);
		
//		bookInfoBiz.findByid("123");
	}
	
	public static void ����ʵ������ļ�������() {
		BookInfo bookInfo=new BookInfo();
		//��һ���������
		bookInfo.setIsbn("156-456");
		bookInfo.setBookName("��������");
		Book book=new Book();
		book.setIsbn("156-456");
		bookInfo.addBook(book);
		//�Լ��ϵĶ��󣬴洢���л�����
		Map<String, BookInfo> bookInfoMap=new HashMap<>();
		//�򼯺��������Ϣ��key��value��
		bookInfoMap.put(book.getIsbn(), bookInfo);
		System.out.println("�洢֮ǰ��");
		����Map(bookInfoMap);
//		System.out.println(bookInfoMap);
//		for(String isbn:bookInfoMap.keySet()) {
//			System.out.println(isbn);
//		}
		
		FileUtil.saveBookInfoMap(bookInfoMap);
	
		System.out.println(book.getBookInfo().getBookName());
		//�Լ��ϵĶ��󣬼������л�����
		bookInfoMap=FileUtil.readBookInfoMap();
		System.out.println("�洢֮��");
		����Map(bookInfoMap);
//		for(String isbn:bookInfoMap.keySet()) {
//			System.out.println(isbn);
//		}
		
	}
	public static void ����Map(Map<String, BookInfo> map) {
		Set<Entry<String, BookInfo>> entrys=map.entrySet();
		for (Entry<String, BookInfo> entry : entrys) {
			System.out.println(entry.getKey()+":"+entry.getValue().getInStoreCount());
		}
	}
	public static void ����userMap(Map<String, User> map) {
		Set<Entry<String, User>> entrys=map.entrySet();
		for (Entry<String, User> entry : entrys) {
			System.out.println(entry.getKey()+":"+entry.getValue().getPassword()+":"+entry.getValue().getRole().getKey());
		}
	}

}
