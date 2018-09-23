package biz_C.biz_impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import biz_C.UserBiz;
import entity_M.User;
import util.FileUtil;

/**
 * UserBizҵ������ʵ����
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����5:30:51
 */
public class UserBizImplV1 implements UserBiz{
	private static final long serialVersionUID = -139240721088567631L;

	@Override
	public boolean add(User user) {
		if(null==user) return false;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return false;
		//���������ͬ���� ����Ϊͬһ�û���   �������
		if(userInfoMap.containsKey(user.getUsername())) return false;
		userInfoMap.put(user.getUsername(), user);
		//����֮��д�뱾���ļ�
		FileUtil.saveUserInfoMap(userInfoMap);
		return true;
	}

	@Override
	public boolean del(User user) {
		if(null==user) return false;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return false;
		//���������ͬ���� ����Ϊͬһ�û���   û���������
		if(!userInfoMap.containsKey(user.getUsername())) return false;
		userInfoMap.remove(user.getUsername());
		//����֮��д�뱾���ļ�
		FileUtil.saveUserInfoMap(userInfoMap);
		return true;
	}

	@Override
	public User update(User user) {//usernameΨһ����  ��������
		if(null==user) return null;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return null;
		//���������ͬ���� ����Ϊͬһ�û���   û���������
		if(!userInfoMap.containsKey(user.getUsername())) return null;
		userInfoMap.put(user.getUsername(), user);
		
		//����֮��д�뱾���ļ�
		FileUtil.saveUserInfoMap(userInfoMap);
		return user;
	}

	@Override
	public User findByid(String id) {
		throw new UnsupportedOperationException("UserBiz�в�֧�ָ���id�Ĳ�ѯ������� findByName");
	}

	@Override
	public Map<String, User> findAll() {
		return FileUtil.readUserInfoMap();
	}

	@Override
	public User login(User tempUser) {
		//��¼֮�󷵻��ļ��д浵�û�
		if(null==tempUser) return null;
		Map<String, User> userInfoMap=findAll();
		if(null==userInfoMap) return null;
		//V1�ж��û��������Ƿ���ȷ   //Ҳ���������ж�
		if(!userInfoMap.containsValue(tempUser))
			return null;
		//get�Ǹ���keyȡֵ
		return userInfoMap.get(tempUser.getUsername());
		
		//V2ת��list
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
	public boolean regedit(User newUser) {//���ļ���д���û�����
		if(null==newUser) return false;
		//�����û��Ƿ���ڣ�����ֻ���û���
		Map<String, User> userMap=findAll();
		if(null==userMap) return false;
		if(userMap.containsKey(newUser.getUsername())) return false;
		//ע��
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
