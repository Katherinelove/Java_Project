package auth;
/**
 * ����--USer�����һ��Role�������ý�ɫ�����е�Ȩ�޷���
 * Ȩ��-��ɫ��
 * ˼·--��ͬ�Ľ�ɫ��administrator oparator default��
 * ��Ȩ�޼�����  ͨ��Key��ȡ�ַ���Ȩ��
 * ���ַ���Ȩ�޴���list�����д���
 * 
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����9:41:17
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
	private String name; // ��ɫ���ƣ�չʾ��ϵͳ�û�����
	private String key; // ��Ӧ�����ļ��е�Ȩ��key
	private List<String> permisstions; // �洢Ȩ�޼����е�Ȩ�� Role_permisstions.properties
	
	private BookInfoBiz bookInfobiz; // ���ڸɻ��ҵ����
	private UserBiz userBiz;     //�����ɻ��ҵ����
	
	
	public Role() {
		setName("Ĭ�Ϲ���Ա");
		setKey("default");
		//�����ɻ��ҵ������ڹ�������ʵ����
//		bookInfobiz=new BookInfoBIzImplV1();  //Ӳ������ʽ,����ʵ����ʱ���������ʵ�ʴ����е�λ��
		//������������ù���ģʽ--���������ʵ��
		//����ģʽ�����ϼӹ�������Factor��һ���ַ���������������ַ���������Ӧ������ʵ��
		//���������λ�ã���ʲô�ӹ��ͷ�ʲôλ��
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
	 * ��ȡȨ�޼��ϴ洢�������ֶ���
	 * @param key
	 */
	public void getPermissionsArray(String key) {
		//��ȡ�����ļ��е�Ȩ��
		Properties prop=new Properties();
		//����
		try {
			prop.load(Role.class.getResourceAsStream("Role_permisstions.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ͨ��Ȩ��keyȡ�ö�Ӧ��Ȩ�ޣ��ԣ��ָ
		//bookinfobiz.*,userbiz.*
		String strPermisstions=prop.getProperty(key);
		
		//***����1
		//����Ȩ�޼���--��δʵ����
		if(null==permisstions) permisstions=new ArrayList<>();
		//�������  ������� ���
		permisstions.clear();
		
		//������ӽ��ָ����ַ���ת��Ϊ���ϣ����ϲ���������Ԫ�أ�ÿ��ȡ��ʱ������trim����
//		permisstions.addAll(Arrays.asList(strPermisstions.split(".")))
		//***����2    ԭʼ��
		String[] permissionsArray=strPermisstions.split(",");
		for (String permission : permissionsArray) {
			if("".equals(permission)) continue;
			permisstions.add(permission.trim());
		}
	}
	
	/**
	 * ��֤��ǰ��ɫ�Ƿ���Ȩ�޽����������ҵ�񷽷���
	 * @return
	 */
	private boolean checkPermissions(String optName) {
		optName=optName.toLowerCase();
	
		if(null==optName||"".equals(optName)) return false;
		
		//�������
		//1.administrator=bookinfobiz.add, bookinfobiz.del,bookinfobiz.findall,bookinfobiz.findbyisbn,bookinfobiz.instore,bookinfobiz.outstore
		//2.administrator=bookinfobiz.*,userbiz.*
		for (String permisstion: permisstions) {
			//�����ж�
			if(optName.equals(permisstion)) return true;
			//�жϼ������Ƿ����"bookinfobiz.*"������֤�����optName
			if("bookinfobiz.*".equals(permisstion)&&optName.startsWith("bookinfobiz")) return true;
			if("userbiz.*".equals(permisstion)&&optName.startsWith("userbiz"))  return true;
		}
		return false;     //����������������
	}
	
	/**
	 * ����Ȩ�ޣ����µ���Biz����
	 * @param isbn
	 * @param inCount
	 * @return
	 */
	//UserBizȨ��
	public User findByUser(User tempUser) {
		if(checkPermissions("UserBiz.findByUser")) {
			return userBiz.findByUser(tempUser);
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.findByUser����Ȩ��");
		}
	}
	
	public boolean modifPassword(User user) {
		if(checkPermissions("UserBiz.modifPassword")) {
			return userBiz.modifPassword(user);
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.modifPassword����Ȩ��");
		}
	}
	
	public boolean regedit(User newUser) {
		if(checkPermissions("UserBiz.regedit")) {
			return userBiz.regedit(newUser);
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.regedit����Ȩ��");
		}
	}
	
	/**
	 * �����Ƿ��е�¼Ȩ��---������ͨ�û�û�е�¼Ȩ�޵�
	 * @param tempUser
	 * @return
	 */
	public User login(User tempUser) {
		if(checkPermissions("UserBiz.login")) {
//			System.err.println("�е�¼Ȩ��");
			return userBiz.login(tempUser);
		}else {
			throw new NoSuchPermissionException(tempUser.getUsername()+"û�в�����UserBiz.login����Ȩ��");
		}
	}
	
	public boolean addUser(User user) {
		if(checkPermissions("UserBiz.add")) {
			userBiz.add(user);
			return true;
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.add����Ȩ��");
		}
	}
	
	public boolean delUser(User user) {
		if(checkPermissions("UserBiz.del")) {
			userBiz.del(user);
			return true;
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.del����Ȩ��");
		}
	}
	
	public User updateUser(User user) {
		if(checkPermissions("UserBiz.update")) {
			return userBiz.update(user);
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.update����Ȩ��");
		}
	}
	
	public Map<String, User> findAllUser(){
		if(checkPermissions("UserBiz.findAll")) {
			return userBiz.findAll();
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.findAll����Ȩ��");
		}
	}
	
	public User findByUserName(String userName) {
		if(checkPermissions("UserBiz.findByName")) {
			return userBiz.findByName(userName);
		}else {
			throw new NoSuchPermissionException("û�в�����UserBiz.findByName����Ȩ��");
		}
	}
	
	//BookinfoBizȨ��
	public boolean inStore(String isbn, int inCount) {
		//��֤��ǰȨ��
		if(checkPermissions("BookInfoBiz.instore")) {
			bookInfobiz.inStore(isbn, inCount);
			return true;
		}else {
			//�������
			//����3  �׳��Զ����쳣
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.inStore����Ȩ��");
		}
		
	}
	
	public boolean outStore(String isbn, int outCount) {
		//��֤��ǰȨ��
		if(checkPermissions("BookInfoBiz.outStore")) {
			bookInfobiz.outStore(isbn, outCount);
			return true;
		}else {
			//�������
			//����3  �׳��Զ����쳣
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.outCount����Ȩ��");
		}
	}
	
	public boolean delBook(BookInfo bookInfo) {
		//��֤��ǰȨ��
		if(checkPermissions("BookInfoBiz.del")) {
			bookInfobiz.del(bookInfo);
			return true;
		}else {
			//�������
			//����3  �׳��Զ����쳣
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.del����Ȩ��");
		}
	}
	
	public boolean addBook(BookInfo bookInfo) {
		//��֤��ǰȨ��
		if(checkPermissions("BookInfoBiz.add")) {
			bookInfobiz.add(bookInfo);
			return true;
		}else {
			//�������
			//����3  �׳��Զ����쳣
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.add����Ȩ��");
		}
	}
	
	public BookInfo findByBookIsbn(String isbn) {
		if(checkPermissions("BookInfoBiz.findByIsbn")) {
			return bookInfobiz.findByIsbn(isbn);
		}else {
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.findByIsbn����Ȩ��");
		}
	}
	
	public Map<String, BookInfo> findAllBook(){
		if(checkPermissions("BookInfoBiz.findAll")) {
			return bookInfobiz.findAll();
		}else {
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.findAll����Ȩ��");
		}
	}
	
	public BookInfo updateBook(BookInfo bookInfo) {
		//��֤��ǰȨ��
		if(checkPermissions("BookInfoBiz.update")) {
			return bookInfobiz.update(bookInfo);
		}else {
			//�������
			//����3  �׳��Զ����쳣
			throw new NoSuchPermissionException("û�в�����BookInfoBiz.update����Ȩ��");
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
