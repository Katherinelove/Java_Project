package biz_C;

import entity_M.User;

/**
 * Userbiz �ӿ�
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����5:23:53
 */
public interface UserBiz extends Biz<User>{
	//���з���
	//1.��¼ʱ����ʱ����һ���û�����ɫΪĬ��defalut��
	//2.�����û��Ϸ��ԣ������Ѿ�ע����û����ж��û�������
	//3.��������ļ����д��û�������ȡ��������Ϊ����ֵ
	public User login(User tempUser);
	public User findByName(String userName);
	public User findByUser(User tempUser);
	public boolean modifPassword(User user);
	public boolean regedit(User newUser);
}
