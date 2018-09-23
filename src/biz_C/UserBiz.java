package biz_C;

import entity_M.User;

/**
 * Userbiz 接口
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 下午5:23:53
 */
public interface UserBiz extends Biz<User>{
	//特有方法
	//1.登录时，临时创建一个用户（角色为默认defalut）
	//2.检验用户合法性，遍历已经注册的用户，判断用户和密码
	//3.如果本地文件中有此用户，则提取出来，作为返回值
	public User login(User tempUser);
	public User findByName(String userName);
	public User findByUser(User tempUser);
	public boolean modifPassword(User user);
	public boolean regedit(User newUser);
}
