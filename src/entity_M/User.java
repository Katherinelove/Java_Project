package entity_M;

import java.io.Serializable;
import java.util.Date;

import auth.Role;

/**
 * �û����󣬰�����ɫ
 * 
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018��9��18�� ����4:21:21
 */
public class User implements Serializable {
	private static final long serialVersionUID = 6817900724962856310L;
	
	private String username;
	private String password;
	private Role role; // ������Ҫ������--��ɫ�������û���Ȩ�ޣ�
	private Date lastLoginTime; // �ϴε�¼��Ϣʱ��
	
	//���ǹ�����  1.�����ֶ��Ƿ����ó�ʼ��  2.����ʱ��׼������
	//����Ϊÿ���û����һ����ɫ
	public User() {//Ĭ���û�---����ʵ�ֽ�ɫ����Ҫ--Ȩ��
		role=new Role(); // Ĭ���û�  Ĭ�Ͻ�ɫ
	}
	
	public User(Role role) {
		if(null==role) {
			role=new Role();
		}
		setRole(role);
		System.out.println("���ý�ɫ�ɹ���");
	}
	
	
	//�ж��û��Ƿ���Ե�¼,���Ѿ�ע�᣻���õ��ж��Ƿ�Ϊͬһ�û���
	@Override
	public boolean equals(Object obj) {
		if(null==obj) return false;
		if(!(obj instanceof User)) return false;
		User user=(User)obj;
		return username.equals(user.getUsername())&&password.equals(user.getPassword());
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
