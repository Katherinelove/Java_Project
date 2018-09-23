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
 *  ��ͼ��  (User�����ͼ����ǰд����BookInfo�����˼·�෴)
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����3:57:02
 */
public class BookView implements Serializable{
	private static final long serialVersionUID = -2310942170769171171L;
	private Scanner input=null;
	
	private User loginUser=null;      //�洢��¼֮����û���Ϣ
	private Role loginrole=null;           //�洢��¼֮����û���Ӧ�Ľ�ɫ��Ϣ��������ȡ��Ϣ

	public BookView() {
		input=new Scanner(System.in);
		//��չ --����  
		//���Դ������ļ��ж�ȡ��ǰϵͳ������,Ȼ���ڻ�ӭ������ʾ--�����
		showWelcome();
	}
	
	public void showWelcome() {
		out.println("============��ӭʹ��V10ͼ�����ϵͳ��======");
		out.println("\t1����¼ \t2��ע��\t3���˳�ϵͳ");
		out.println("=====================================");
		out.print("��ѡ��");
		String choose=input.next();
		//�������if-else ����ʹ��switch-case
		if("1".equals(choose)) {//login
			User user=showLogin();
			if(null==user) {
				out.println("�˻����������");
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
		}else if("2".equals(choose)) {//ע��
			regeditUser();
		}else {
			out.println("ϵͳ���˳���");
		}
	}

	public void regeditUser() {
		//�൱�ڰ��û�����д�뱾���ļ�--�����
		User regeditUser=new User();
		out.println("�û�ע��>>>");
		out.print("�û����ƣ�");
		String userName=input.next().trim();
		regeditUser.setUsername(userName);
		out.print("�������û����룺");
		String password=input.next().trim();
		regeditUser.setPassword(password);
		//�������Ĭ��Ȩ�ޣ�����Ȩ����Ҫ��������
		if(regeditUser.getRole().regedit(regeditUser)) {
			out.println("ע��ɹ���");
			showWelcome();   //���ص�¼ҳ��
		}else {
			out.println("ע��ʧ��");
			regeditUser=null;      //��������ڴ�
			regeditUser();
		}
	}
	
	/**
	 * ��¼��Ϻ���Ҫ����
	 * ���Ǳ������ѽ���¼�û�������ֶ������У�����ʡ�Է���ֵ
	 */
	public User showLogin() {
		//��֤�û�--Ӧ������userҵ�������з���
		//��ʾ��¼����--������һ������֤�û����������loginUser
		//��ͨ��loginUer�Ľ�ɫ�������û���Ӧ��ɫ�ĵ�¼����
		
		//��֤�û��Ƿ�Ϸ�������Ϸ�������ǰ�ĵ�¼���û�����������������ã���
		//�����û�������ɫ����ʾ��һ���˵�
		User tempUser=new User();  //Ĭ�Ͻ�ɫ
		System.out.print("�û�����");
		tempUser.setUsername(input.next());
		System.out.print("���룺");
		tempUser.setPassword(input.next());
		
		//������Ĭ�Ͻ�ɫdefault��ͨ����ɫ�еĵ�¼Ȩ�޿���  ����UserBiz.login����
		//��֤�û��Ƿ�Ϸ�������Ϸ�������ǰ�ĵ�¼���û�����������������ã���
		loginUser=tempUser.getRole().login(tempUser);
		if(null==loginUser) {
			return null;
		}else {//��ʾ�Ϸ���¼�û�
			loginrole=loginUser.getRole();  //�����û�������ɫ��key����ʾ��һ���˵�
//			System.out.println(loginrole.getName()+":"+loginrole.getKey());
			return loginUser;
		}
	}
	
	/**
	 * ��ʾ����Ա���˵�
	 */
	public void showMainView_Administrator() {
		out.println("Administrator���˵�>>>");
		out.println("1�������û�");
		out.println("2������ͼ��");
		out.println("3��ע���û�");
		out.print("��ѡ��");
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
		//�˵�
		out.println("ͼ�����>>>");
		out.println("1�����ͼ��\n2��ɾ��ͼ��\n"
				+ "3������ͼ����Ϣ\n4���鿴����ͼ��\n"
				+ "5����ѯͼ��\n6��ͼ�����\n"
				+ "7��ͼ�����\n8���˳�");
		out.print("��ѡ��");
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
		out.print("���������ͼ��isbn��");
		String isbn=input.next().trim();
		out.print("���������ͼ��������");
		int outCount=input.nextInt();
		if(loginrole.outStore(isbn, outCount)) {
			out.println("����ɹ���");
			 find_allbooks();
		}else {
			out.println("����ʧ��");
			showManageBookInfosView();
		}	
	}

	private void instore() {
		out.print("���������ͼ��isbn��");
		String isbn=input.next().trim();
		out.print("���������ͼ��������");
		int inCount=input.nextInt();
		if(loginrole.inStore(isbn, inCount)) {
			out.println("���ɹ���");
			 find_allbooks();
		}else {
			out.println("���ʧ��!");
			showManageBookInfosView();
		}
		
	}

	private void find_by_isbn() {
		out.print("������ͼ��isbn��");
		String isbn=input.next().trim();
		BookInfo bookInfo=loginrole.findByBookIsbn(isbn);
		if(null==bookInfo) {
			out.println("û���ҵ���ͼ�飬����������isbn!");
			//find_by_isbn();  ������ѭ��
			showManageBookInfosView();
		}else {
			out.println("�ҵ�"+bookInfo.getBookName()+"ͼ�飡");
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
		// ������Ϣ
		BookInfo bookInfo=new BookInfo();
		out.print("������ͼ���isbn��");
		bookInfo.setIsbn(input.next().trim());
		bookInfo=loginrole.findByBookIsbn(bookInfo.getIsbn());
		if(null==bookInfo) {
			out.println("�����isbn����û�в��ҵ�ͼ�飡");
			update_book();
		}else {
			out.print("�޸�ͼ������:");
			bookInfo.setBookName(input.next().trim());
			out.print("�޸�ͼ������:");
			bookInfo.setAuthor(input.next().trim());
			out.print("�޸�ͼ�鵥��:");
			bookInfo.setPrice(input.nextDouble());
			out.print("�޸�ͼ������:");
			bookInfo.setInStoreCount(input.nextInt());
			loginrole.updateBook(bookInfo);
			out.println("����ͼ��"+bookInfo.getBookName()+"�ɹ���");
			find_allbooks();
		}
	}

	private void del_book() {//isbn ��ͬ��ɾ��
		BookInfo bookInfo=new BookInfo();
		out.print("������ɾ��ͼ���isbn��");
		bookInfo.setIsbn(input.next().trim());
		bookInfo=loginrole.findByBookIsbn(bookInfo.getIsbn());
		if(loginrole.delBook(bookInfo)) {
			out.println("ɾ��ͼ��"+bookInfo.getBookName()+"�ɹ���");
			find_allbooks();
		}else {
			out.println("ɾ��ͼ��ʧ�ܣ�");
			showManageBookInfosView();
		}
		
	}

	private void add_book() {
		BookInfo bookInfo=new BookInfo();
		Book tempBook=new Book();
		out.print("������ͼ��isbn:");
		bookInfo.setIsbn(input.next().trim());
		//����������ͬ��isbn   �����Ȿ���޷����
		tempBook.setIsbn(bookInfo.getIsbn());
		
		out.print("������ͼ������:");
		bookInfo.setBookName(input.next().trim());
		out.print("������ͼ������:");
		bookInfo.setAuthor(input.next().trim());
		out.print("������ͼ�鵥��:");
		bookInfo.setPrice(input.nextDouble());
		out.print("������ͼ��id:");
		tempBook.setBookID(input.next().trim());
		bookInfo.addBook(tempBook);   //���
		if(loginrole.addBook(bookInfo)) {
			out.println("���ͼ��"+bookInfo.getBookName()+"�ɹ���");
			find_allbooks();
		}else {
			out.println("���ͼ��ʧ�ܣ�");
			showManageBookInfosView();
		}

	
	}

	private void showManageUsersView() {
		//�˵�
		out.println("�û�����>>>");
		out.println("1������û�\n2��ɾ���û�\n"
				+ "3�������û���Ϣ\n4���鿴�����û�\n"
				+ "5����ѯ�û�\n6�������û�Ȩ��\n7���˳�");
		out.print("��ѡ��");
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
	
	

	private void set_role() {//����Ȩ��
		User newUser=new User();
		out.print("�������û�����");
		newUser.setUsername(input.next().trim());
		out.print("���������룺");
		newUser.setPassword(input.next().trim());
		newUser=loginrole.findByUser(newUser);
		if(null!=newUser) {
			out.println("Ȩ�ޣ�");
			out.println("1��administrator\t2��oparator");
			out.print("��ѡ��");
			String choose=input.next().trim();
			switch (choose) {
			case "1":	
				newUser.setRole(new Role("����Ա","administrator"));
				Map<String, User> userMap1=loginrole.findAllUser();
				userMap1.put(newUser.getUsername(), newUser);
				FileUtil.saveUserInfoMap(userMap1);
				out.println("����Ȩ�޳ɹ���");
				break;
			case "2":
				newUser.setRole(new Role("����Ա","oparator"));
				Map<String, User> userMap2=loginrole.findAllUser();
				userMap2.put(newUser.getUsername(), newUser);
				FileUtil.saveUserInfoMap(userMap2);
				out.println("����Ȩ�޳ɹ���");
				break;
			default:
				break;
			}
		}else {
			
		}
	}

	private void find_byname() {
		out.print("�������û�name��");
		String userName=input.next().trim();
		User userByFind=loginrole.findByUserName(userName);
		if(null!=userByFind) {
			out.println(userByFind.getUsername()+"�û����ڣ�");
			out.println(userByFind.getUsername()+":"+userByFind.getRole().getKey());
			
		}else {
			out.println("û��"+userByFind.getUsername()+"�û�");
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
		User newUser=new User();  //Ĭ��Ȩ��
		//��ʱ�Ѿ���¼����
		newUser.setUsername(loginUser.getUsername());
		out.print("���������룺����ʱʵ�ָ���������Ϣ��");
		newUser.setPassword(input.next().trim());
		User tempUser=loginrole.updateUser(newUser);
		if(null==tempUser) {
			out.println("����"+newUser.getUsername()+"�û���Ϣ�ɹ���");
		}else {
			out.println("����"+newUser.getUsername()+"�û���Ϣʧ�ܣ�");
			showManageUsersView();
		}
	}

	private void del_user() {
		User newUser=new User();  //Ĭ��Ȩ��
		out.print("�������û�����");
		newUser.setUsername(input.next().trim());
		out.print("���������룺");
		newUser.setPassword(input.next().trim());
		if(loginrole.delUser(newUser)) {
			out.println("ɾ��"+newUser.getUsername()+"�û��ɹ���");
		}else {
			out.println("ɾ��"+newUser.getUsername()+"�û�ʧ�ܣ�");
			showManageUsersView();
		}
	}

	public void add_user() {
		User newUser=new User();  //Ĭ��Ȩ��
		out.print("�������û�����");
		newUser.setUsername(input.next().trim());
		out.print("���������룺");
		newUser.setPassword(input.next().trim());
		if(loginrole.addUser(newUser)) {
			out.println("���"+newUser.getUsername()+"�û��ɹ���");
		}else {
			out.println("���"+newUser.getUsername()+"�û�ʧ�ܣ�");
			showManageUsersView();
		}
	}

	/**
	 * ��ʾ����Ա���˵�
	 */
	public void showMainView_Oparator() {
		out.println("Oparato���˵�������");
		showManageBookInfosView();
	}
}
