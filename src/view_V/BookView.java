package view_V;

import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import auth.Role;
import entity_M.Book;
import entity_M.BookInfo;
import entity_M.User;
import util.FileUtil;

import static java.lang.System.out;

/**
 *  视图层  (User类从视图层往前写，与BookInfo类设计思路相反)
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 下午3:57:02
 */
public class BookView implements Serializable{
	private static final long serialVersionUID = -2310942170769171171L;
	private Scanner input=null;
	
	private User loginUser=null;      //存储登录之后的用户信息
	private Role loginrole=null;           //存储登录之后的用户对应的角色信息，方便提取信息

	public BookView() {
		input=new Scanner(System.in);
		//扩展 --功能  
		//可以从配置文件中读取当前系统的名称,然后在欢迎界面显示--待完成
		showWelcome();
	}
	
	public void showWelcome() {
		out.println("============欢迎使用V10图书管理系统：======");
		out.println("\t1、登录 \t2、注册\t3、退出系统");
		out.println("=====================================");
		out.print("请选择：");
		String choose=input.next();
		//这里采用if-else 可以使用switch-case
		if("1".equals(choose)) {//login
			User user=showLogin();
			if(null==user) {
				out.println("账户或密码错误！");
				showWelcome();
			}
//			System.out.println(loginUser.getUsername()+":"+loginrole.getKey());
			switch (loginrole.getKey().toLowerCase()) {
			case "administrator":
				showMainView_Administrator();
				break;
			case "oparator":
				showMainView_Oparator();
				break;
			default:
				break;
			}
		}else if("2".equals(choose)) {//注册
			regeditUser();
		}else {
			out.println("系统已退出！");
		}
	}

	public void regeditUser() {
		//相当于把用户对象写入本地文件--待完成
		User regeditUser=new User();
		out.println("用户注册>>>");
		out.print("用户名称：");
		String userName=input.next().trim();
		regeditUser.setUsername(userName);
		out.print("请输入用户密码：");
		String password=input.next().trim();
		regeditUser.setPassword(password);
		//这里给定默认权限，其他权限需要后续操作
		if(regeditUser.getRole().regedit(regeditUser)) {
			out.println("注册成功！");
			showWelcome();   //返回登录页面
		}else {
			out.println("注册失败");
			regeditUser=null;      //清除对象内存
			regeditUser();
		}
	}
	
	/**
	 * 登录完毕后，需要返回
	 * 但是本方法已将登录用户存放在字段属性中，可以省略返回值
	 */
	public User showLogin() {
		//验证用户--应答昂属于user业务类特有方法
		//显示登录界面--并返回一个已验证用户，并赋予给loginUser
		//并通过loginUer的角色，给出用户对应角色的登录界面
		
		//验证用户是否合法，如果合法，将当前的登录的用户保存起来（后面待用），
		//根据用户所属角色，显示下一步菜单
		User tempUser=new User();  //默认角色
		System.out.print("用户名：");
		tempUser.setUsername(input.next());
		System.out.print("密码：");
		tempUser.setPassword(input.next());
		
		//这里是默认角色default，通过角色中的登录权限可以  进行UserBiz.login方法
		//验证用户是否合法，如果合法，将当前的登录的用户保存起来（后面待用），
		loginUser=tempUser.getRole().login(tempUser);
		if(null==loginUser) {
			return null;
		}else {//表示合法登录用户
			loginrole=loginUser.getRole();  //根据用户所属角色的key，显示下一步菜单
//			System.out.println(loginrole.getName()+":"+loginrole.getKey());
			return loginUser;
		}
	}
	
	/**
	 * 显示管理员主菜单
	 */
	public void showMainView_Administrator() {
		out.println("Administrator主菜单>>>");
		out.println("1、管理用户");
		out.println("2、管理图书");
		out.println("3、注销用户");
		out.print("请选择：");
		String choose=input.next();
		switch (choose) {
		case "1":
			showManageUsersView();
			break;
		case "2":
			showManageBookInfosView();
			break;
		case "3":
			showWelcome();
			break;
		default:
			showMainView_Administrator();
			break;
		}
	}
	private void showManageBookInfosView() {
		//菜单
		out.println("图书管理>>>");
		out.println("1、添加图书\n2、删除图书\n"
				+ "3、更新图书信息\n4、查看所有图书\n"
				+ "5、查询图书\n6、图书入库\n"
				+ "7、图书出库\n8、退出");
		out.print("请选择：");
		String choose=input.next().trim();
		switch (choose) {
		case "1":
			add_book();
			showManageBookInfosView();
			break;
		case "2":
			del_book();
			showManageBookInfosView();
			break;
		case "3":
			update_book();
			showManageBookInfosView();
			break;
		case "4":
			find_allbooks();
			showManageBookInfosView();
			break;
		case "5":
			find_by_isbn();
			showManageBookInfosView();
			break;
		case "6":
			instore();
			showManageBookInfosView();
			break;
		case "7":
			outstore();
			showManageBookInfosView();
			break;
		case "8":
			showMainView_Administrator();
			break;
		default:
			showMainView_Administrator();
			break;
		}
	}

	private void outstore() {
		out.print("请输入出库图书isbn：");
		String isbn=input.next().trim();
		out.print("请输入出库图书数量：");
		int outCount=input.nextInt();
		if(loginrole.outStore(isbn, outCount)) {
			out.println("出库成功！");
			 find_allbooks();
		}else {
			out.println("出库失败");
			showManageBookInfosView();
		}	
	}

	private void instore() {
		out.print("请输入入库图书isbn：");
		String isbn=input.next().trim();
		out.print("请输入入库图书数量：");
		int inCount=input.nextInt();
		if(loginrole.inStore(isbn, inCount)) {
			out.println("入库成功！");
			 find_allbooks();
		}else {
			out.println("入库失败!");
			showManageBookInfosView();
		}
		
	}

	private void find_by_isbn() {
		out.print("请输入图书isbn：");
		String isbn=input.next().trim();
		BookInfo bookInfo=loginrole.findByBookIsbn(isbn);
		if(null==bookInfo) {
			out.println("没有找到此图书，请重新输入isbn!");
			//find_by_isbn();  容易死循环
			showManageBookInfosView();
		}else {
			out.println("找到"+bookInfo.getBookName()+"图书！");
			out.println("isbn\tbookname\tauthor\tcount\tprice");
			out.println(bookInfo.getIsbn()+"\t"+
					bookInfo.getBookName()+"\t"+
					bookInfo.getAuthor()+"\t"+
					bookInfo.getInStoreCount()+"\t"+
					bookInfo.getPrice());
		}
		
	}

	private void find_allbooks() {
		 Map<String, BookInfo> bookInfoMap=loginrole.findAllBook();
		 out.println("isbn\tbookname\tauthor\tcount\tprice");
		 Set<Entry<String, BookInfo>> entrys=bookInfoMap.entrySet();
		 for (Entry<String, BookInfo> entry : entrys) {
			out.println(entry.getKey()+"\t\t"+
					entry.getValue().getBookName()+"\t"+
					entry.getValue().getAuthor()+"\t"+
					entry.getValue().getInStoreCount()+"\t"+
					entry.getValue().getPrice());
		}
	}

	private void update_book() {
		// 更新信息
		BookInfo bookInfo=new BookInfo();
		out.print("请输入图书的isbn：");
		bookInfo.setIsbn(input.next().trim());
		bookInfo=loginrole.findByBookIsbn(bookInfo.getIsbn());
		if(null==bookInfo) {
			out.println("输入的isbn有误，没有查找到图书！");
			update_book();
		}else {
			out.print("修改图书名称:");
			bookInfo.setBookName(input.next().trim());
			out.print("修改图书作者:");
			bookInfo.setAuthor(input.next().trim());
			out.print("修改图书单价:");
			bookInfo.setPrice(input.nextDouble());
			out.print("修改图书数量:");
			bookInfo.setInStoreCount(input.nextInt());
			loginrole.updateBook(bookInfo);
			out.println("更新图书"+bookInfo.getBookName()+"成功！");
			find_allbooks();
		}
	}

	private void del_book() {//isbn 相同即删除
		BookInfo bookInfo=new BookInfo();
		out.print("请输入删除图书的isbn：");
		bookInfo.setIsbn(input.next().trim());
		bookInfo=loginrole.findByBookIsbn(bookInfo.getIsbn());
		if(loginrole.delBook(bookInfo)) {
			out.println("删除图书"+bookInfo.getBookName()+"成功！");
			find_allbooks();
		}else {
			out.println("删除图书失败！");
			showManageBookInfosView();
		}
		
	}

	private void add_book() {
		BookInfo bookInfo=new BookInfo();
		Book tempBook=new Book();
		out.print("请输入图书isbn:");
		bookInfo.setIsbn(input.next().trim());
		//必须设置相同的isbn   否则这本书无法添加
		tempBook.setIsbn(bookInfo.getIsbn());
		
		out.print("请输入图书名称:");
		bookInfo.setBookName(input.next().trim());
		out.print("请输入图书作者:");
		bookInfo.setAuthor(input.next().trim());
		out.print("请输入图书单价:");
		bookInfo.setPrice(input.nextDouble());
		out.print("请输入图书id:");
		tempBook.setBookID(input.next().trim());
		bookInfo.addBook(tempBook);   //添加
		if(loginrole.addBook(bookInfo)) {
			out.println("添加图书"+bookInfo.getBookName()+"成功！");
			find_allbooks();
		}else {
			out.println("添加图书失败！");
			showManageBookInfosView();
		}

	
	}

	private void showManageUsersView() {
		//菜单
		out.println("用户管理>>>");
		out.println("1、添加用户\n2、删除用户\n"
				+ "3、更新用户信息\n4、查看所有用户\n"
				+ "5、查询用户\n6、设置用户权限\n7、退出");
		out.print("请选择：");
		String choose=input.next().trim();
		switch (choose) {
		case "1":
			add_user();
			showManageUsersView();
			break;
		case "2":
			del_user();
			showManageUsersView();
			break;
		case "3":
			update_user();
			showManageUsersView();
			break;
		case "4":
			find_all_user();
			showManageUsersView();
			break;
		case "5":
			find_byname();
			showManageUsersView();
			break;
		case "6":
			set_role();
			showManageUsersView();
			break;
		case "7":
			showMainView_Administrator(); 
			break;
		default:
			showManageUsersView();
			break;
		}
		
	}
	
	

	private void set_role() {//设置权限
		User newUser=new User();
		out.print("请输入用户名：");
		newUser.setUsername(input.next().trim());
		out.print("请输入密码：");
		newUser.setPassword(input.next().trim());
		newUser=loginrole.findByUser(newUser);
		if(null!=newUser) {
			out.println("权限：");
			out.println("1、administrator\t2、oparator");
			out.print("请选择：");
			String choose=input.next().trim();
			switch (choose) {
			case "1":	
				newUser.setRole(new Role("管理员","administrator"));
				Map<String, User> userMap1=loginrole.findAllUser();
				userMap1.put(newUser.getUsername(), newUser);
				FileUtil.saveUserInfoMap(userMap1);
				out.println("更改权限成功！");
				break;
			case "2":
				newUser.setRole(new Role("操作员","oparator"));
				Map<String, User> userMap2=loginrole.findAllUser();
				userMap2.put(newUser.getUsername(), newUser);
				FileUtil.saveUserInfoMap(userMap2);
				out.println("更改权限成功！");
				break;
			default:
				break;
			}
		}else {
			
		}
	}

	private void find_byname() {
		out.print("请输入用户name：");
		String userName=input.next().trim();
		User userByFind=loginrole.findByUserName(userName);
		if(null!=userByFind) {
			out.println(userByFind.getUsername()+"用户存在！");
			out.println(userByFind.getUsername()+":"+userByFind.getRole().getKey());
			
		}else {
			out.println("没有"+userByFind.getUsername()+"用户");
		}
	}

	private void find_all_user() {
		Map<String, User> userMap=loginrole.findAllUser();
		foreachUserMap(userMap);
	}
	private void foreachUserMap(Map<String, User> map) {
		Set<Entry<String, User>> entrys=map.entrySet();
		for (Entry<String, User> entry : entrys) {
			System.out.println(entry.getKey()+":"+entry.getValue().getPassword()+":"+entry.getValue().getRole().getKey());
		}
	}


	private void update_user() {
		User newUser=new User();  //默认权限
		//此时已经登录进来
		newUser.setUsername(loginUser.getUsername());
		out.print("请输入密码：（暂时实现更新密码信息）");
		newUser.setPassword(input.next().trim());
		User tempUser=loginrole.updateUser(newUser);
		if(null==tempUser) {
			out.println("更新"+newUser.getUsername()+"用户信息成功！");
		}else {
			out.println("更新"+newUser.getUsername()+"用户信息失败！");
			showManageUsersView();
		}
	}

	private void del_user() {
		User newUser=new User();  //默认权限
		out.print("请输入用户名：");
		newUser.setUsername(input.next().trim());
		out.print("请输入密码：");
		newUser.setPassword(input.next().trim());
		if(loginrole.delUser(newUser)) {
			out.println("删除"+newUser.getUsername()+"用户成功！");
		}else {
			out.println("删除"+newUser.getUsername()+"用户失败！");
			showManageUsersView();
		}
	}

	public void add_user() {
		User newUser=new User();  //默认权限
		out.print("请输入用户名：");
		newUser.setUsername(input.next().trim());
		out.print("请输入密码：");
		newUser.setPassword(input.next().trim());
		if(loginrole.addUser(newUser)) {
			out.println("添加"+newUser.getUsername()+"用户成功！");
		}else {
			out.println("添加"+newUser.getUsername()+"用户失败！");
			showManageUsersView();
		}
	}

	/**
	 * 显示操作员主菜单
	 */
	public void showMainView_Oparator() {
		out.println("Oparato主菜单》》》");
		showManageBookInfosView();
	}
}
