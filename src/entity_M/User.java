package entity_M;

import java.io.Serializable;
import java.util.Date;

import auth.Role;

/**
 * 用户对象，包含角色
 * 
 * @author Administrator
 * @tags
 * @copyright katherinelove
 * @date 2018年9月18日 下午4:21:21
 */
public class User implements Serializable {
	private static final long serialVersionUID = 6817900724962856310L;
	
	private String username;
	private String password;
	private Role role; // 极其重要的属性--角色（觉得用户的权限）
	private Date lastLoginTime; // 上次登录信息时间
	
	//考虑构造器  1.属性字段是否需用初始化  2.运行时的准备工作
	//必须为每个用户添加一个角色
	public User() {//默认用户---必须实现角色很重要--权限
		role=new Role(); // 默认用户  默认角色
	}
	
	public User(Role role) {
		if(null==role) {
			role=new Role();
		}
		setRole(role);
		System.out.println("设置角色成功！");
	}
	
	
	//判断用户是否可以登录,即已经注册；需用到判断是否为同一用户、
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
