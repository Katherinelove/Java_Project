package biz_C.biz_impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import biz_C.UserBiz;
import entity_M.User;
import util.FileUtil;

/**
 * UserBiz业务类中实现类
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 下午5:30:51
 */
public class UserBizImplV1 implements UserBiz{
	private static final long serialVersionUID = -139240721088567631L;

	@Override
	public boolean add(User user) {
		if(null==user) return false;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return false;
		//如果包含相同名称 （视为同一用户）   则不能添加
		if(userInfoMap.containsKey(user.getUsername())) return false;
		userInfoMap.put(user.getUsername(), user);
		//更新之后写入本地文件
		FileUtil.saveUserInfoMap(userInfoMap);
		return true;
	}

	@Override
	public boolean del(User user) {
		if(null==user) return false;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return false;
		//如果包含相同名称 （视为同一用户）   没有则不能添加
		if(!userInfoMap.containsKey(user.getUsername())) return false;
		userInfoMap.remove(user.getUsername());
		//更新之后写入本地文件
		FileUtil.saveUserInfoMap(userInfoMap);
		return true;
	}

	@Override
	public User update(User user) {//username唯一索引  更新密码
		if(null==user) return null;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return null;
		//如果包含相同名称 （视为同一用户）   没有则不能添加
		if(!userInfoMap.containsKey(user.getUsername())) return null;
		userInfoMap.put(user.getUsername(), user);
		
		//更新之后写入本地文件
		FileUtil.saveUserInfoMap(userInfoMap);
		return user;
	}

	@Override
	public User findByid(String id) {
		throw new UnsupportedOperationException("UserBiz中不支持根据id的查询，请调用 findByName");
	}

	@Override
	public Map<String, User> findAll() {
		return FileUtil.readUserInfoMap();
	}

	@Override
	public User login(User tempUser) {
		//登录之后返回文件中存档用户
		if(null==tempUser) return null;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return null;
		//V1判断用户和密码是否正确   //也可以自行判断
		if(!userInfoMap.containsValue(tempUser))
			return null;
		//get是根据key取值
		return userInfoMap.get(tempUser.getUsername());
		
		//V2转成list
//		List<User> userList=new ArrayList<>(userInfoMap.values());
//		if(!userList.contains(tempUser)) {
//			return null;
//		}
//		return userInfoMap.get(tempUser.getUsername());

	}

	@Override
	public User findByName(String userName) {
		if(null==userName||"".equals(userName)) return null;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return null;
		if(!userInfoMap.containsKey(userName)) return null;
		return userInfoMap.get(userName);
	}

	@Override
	public boolean modifPassword(User user) {
		if(null==user)  return false;
		Map<String, User> userMap=findAll();
		if(null==userMap) return false;
		if(!userMap.containsValue(user)) return false; 
		
		update(user);
		
		return false;
	}

	@Override
	public boolean regedit(User newUser) {//向文件中写入用户数据
		if(null==newUser) return false;
		//看看用户是否存在，这里只看用户名
		Map<String, User> userMap=findAll();
		if(null==userMap) return false;
		if(userMap.containsKey(newUser.getUsername())) return false;
		//注册
		userMap.put(newUser.getUsername(), newUser);
		FileUtil.saveUserInfoMap(userMap);
		return true;
	}

	@Override
	public User findByUser(User tempUser) {
		if(null==tempUser) return null;
		Map<String, User> userMap=findAll();
		if(null==userMap) return null;
		if(!userMap.containsValue(tempUser)) return null;
		return userMap.get(tempUser.getUsername());
	}

}
