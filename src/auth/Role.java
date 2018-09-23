package auth;
/**
 * 总体--USer类包含一个Role对象，利用角色对象中的权限方法
 * 权限-角色类
 * 思路--不同的角色（administrator oparator default）
 * 从权限集合中  通过Key读取字符串权限
 * 将字符串权限存入list集合中待用
 * 
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 上午9:41:17
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import biz_C.BizFactor;
import biz_C.BookInfoBiz;
import biz_C.UserBiz;
import biz_C.biz_impl.BookInfoBIzImplV1;
import entity_M.BookInfo;
import entity_M.User;
import exception.NoSuchPermissionException;

public class Role implements Serializable {
	private static final long serialVersionUID = -5598136736688697196L;
	private String name; // 角色名称：展示给系统用户看的
	private String key; // 对应配置文件中的权限key
	private List<String> permisstions; // 存储权限集合中的权限 Role_permisstions.properties
	
	private BookInfoBiz bookInfobiz; // 正在干活的业务类
	private UserBiz userBiz;     //真正干活的业务类
	
	
	public Role() {
		setName("默认管理员");
		setKey("default");
		//真正干活的业务必须在构造器中实例化
//		bookInfobiz=new BookInfoBIzImplV1();  //硬编码形式,更改实现类时，必须更改实际代码中的位置
		//解决方案：采用工厂模式--来获得子类实现
		//工厂模式：来料加工，传给Factor类一个字符串，工厂类根据字符串返回相应的子类实现
		//工厂类放置位置？对什么加工就放什么位置
		bookInfobiz=(BookInfoBiz) BizFactor.getBiz("BookInfoBiz");
		userBiz=(UserBiz) BizFactor.getBiz("UserBiz");
		getPermissionsArray(key);
	}
	
	public Role(String name,String key) {
		setName(name);
		setKey(key);
		bookInfobiz=(BookInfoBiz) BizFactor.getBiz("BookInfoBiz");
		userBiz=(UserBiz) BizFactor.getBiz("UserBiz");
		getPermissionsArray(key);
	}

	/**
	 * 获取权限集合存储在属性字段中
	 * @param key
	 */
	public void getPermissionsArray(String key) {
		//获取配置文件中的权限
		Properties prop=new Properties();
		//加载
		try {
			prop.load(Role.class.getResourceAsStream("Role_permisstions.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//通过权限key取得对应的权限（以，分割）
		//bookinfobiz.*,userbiz.*
		String strPermisstions=prop.getProperty(key);
		
		//***亮点1
		//存入权限集合--还未实例化
		if(null==permisstions) permisstions=new ArrayList<>();
		//如果存在  保险起见 清空
		permisstions.clear();
		
		//这样添加将分割后的字符串转化为集合，集合不能再新增元素；每次取出时还必须trim（）
//		permisstions.addAll(Arrays.asList(strPermisstions.split(".")))
		//***亮点2    原始点
		String[] permissionsArray=strPermisstions.split(",");
		for (String permission : permissionsArray) {
			if("".equals(permission)) continue;
			permisstions.add(permission.trim());
		}
	}
	
	/**
	 * 验证当前角色是否有权限进入操作名（业务方法）
	 * @return
	 */
	private boolean checkPermissions(String optName) {
		optName=optName.toLowerCase();
	
		if(null==optName||"".equals(optName)) return false;
		
		//两种情况
		//1.administrator=bookinfobiz.add, bookinfobiz.del,bookinfobiz.findall,bookinfobiz.findbyisbn,bookinfobiz.instore,bookinfobiz.outstore
		//2.administrator=bookinfobiz.*,userbiz.*
		for (String permisstion: permisstions) {
			//遍历判断
			if(optName.equals(permisstion)) return true;
			//判断集合中是否包含"bookinfobiz.*"，并验证传入的optName
			if("bookinfobiz.*".equals(permisstion)&&optName.startsWith("bookinfobiz")) return true;
			if("userbiz.*".equals(permisstion)&&optName.startsWith("userbiz"))  return true;
		}
		return false;     //以上条件均不满足
	}
	
	/**
	 * 设置权限，重新调用Biz方法
	 * @param isbn
	 * @param inCount
	 * @return
	 */
	//UserBiz权限
	public User findByUser(User tempUser) {
		if(checkPermissions("UserBiz.findByUser")) {
			return userBiz.findByUser(tempUser);
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.findByUser》的权限");
		}
	}
	
	public boolean modifPassword(User user) {
		if(checkPermissions("UserBiz.modifPassword")) {
			return userBiz.modifPassword(user);
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.modifPassword》的权限");
		}
	}
	
	public boolean regedit(User newUser) {
		if(checkPermissions("UserBiz.regedit")) {
			return userBiz.regedit(newUser);
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.regedit》的权限");
		}
	}
	
	/**
	 * 检验是否有登录权限---比如普通用户没有登录权限等
	 * @param tempUser
	 * @return
	 */
	public User login(User tempUser) {
		if(checkPermissions("UserBiz.login")) {
//			System.err.println("有登录权限");
			return userBiz.login(tempUser);
		}else {
			throw new NoSuchPermissionException(tempUser.getUsername()+"没有操作《UserBiz.login》的权限");
		}
	}
	
	public boolean addUser(User user) {
		if(checkPermissions("UserBiz.add")) {
			userBiz.add(user);
			return true;
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.add》的权限");
		}
	}
	
	public boolean delUser(User user) {
		if(checkPermissions("UserBiz.del")) {
			userBiz.del(user);
			return true;
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.del》的权限");
		}
	}
	
	public User updateUser(User user) {
		if(checkPermissions("UserBiz.update")) {
			return userBiz.update(user);
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.update》的权限");
		}
	}
	
	public Map<String, User> findAllUser(){
		if(checkPermissions("UserBiz.findAll")) {
			return userBiz.findAll();
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.findAll》的权限");
		}
	}
	
	public User findByUserName(String userName) {
		if(checkPermissions("UserBiz.findByName")) {
			return userBiz.findByName(userName);
		}else {
			throw new NoSuchPermissionException("没有操作《UserBiz.findByName》的权限");
		}
	}
	
	//BookinfoBiz权限
	public boolean inStore(String isbn, int inCount) {
		//验证当前权限
		if(checkPermissions("BookInfoBiz.instore")) {
			bookInfobiz.inStore(isbn, inCount);
			return true;
		}else {
			//可以输出
			//亮点3  抛出自定义异常
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.inStore》的权限");
		}
		
	}
	
	public boolean outStore(String isbn, int outCount) {
		//验证当前权限
		if(checkPermissions("BookInfoBiz.outStore")) {
			bookInfobiz.outStore(isbn, outCount);
			return true;
		}else {
			//可以输出
			//亮点3  抛出自定义异常
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.outCount》的权限");
		}
	}
	
	public boolean delBook(BookInfo bookInfo) {
		//验证当前权限
		if(checkPermissions("BookInfoBiz.del")) {
			bookInfobiz.del(bookInfo);
			return true;
		}else {
			//可以输出
			//亮点3  抛出自定义异常
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.del》的权限");
		}
	}
	
	public boolean addBook(BookInfo bookInfo) {
		//验证当前权限
		if(checkPermissions("BookInfoBiz.add")) {
			bookInfobiz.add(bookInfo);
			return true;
		}else {
			//可以输出
			//亮点3  抛出自定义异常
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.add》的权限");
		}
	}
	
	public BookInfo findByBookIsbn(String isbn) {
		if(checkPermissions("BookInfoBiz.findByIsbn")) {
			return bookInfobiz.findByIsbn(isbn);
		}else {
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.findByIsbn》的权限");
		}
	}
	
	public Map<String, BookInfo> findAllBook(){
		if(checkPermissions("BookInfoBiz.findAll")) {
			return bookInfobiz.findAll();
		}else {
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.findAll》的权限");
		}
	}
	
	public BookInfo updateBook(BookInfo bookInfo) {
		//验证当前权限
		if(checkPermissions("BookInfoBiz.update")) {
			return bookInfobiz.update(bookInfo);
		}else {
			//可以输出
			//亮点3  抛出自定义异常
			throw new NoSuchPermissionException("没有操作《BookInfoBiz.update》的权限");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getPermisstions() {
		return permisstions;
	}

	public void setPermisstions(List<String> permisstions) {
		this.permisstions = permisstions;
	}

	public BookInfoBiz getBookInfobiz() {
		return bookInfobiz;
	}

	public void setBookInfobiz(BookInfoBiz bookInfobiz) {
		this.bookInfobiz = bookInfobiz;
	}
}
