package biz_C;

import biz_C.biz_impl.BookInfoBIzImplV1;
import biz_C.biz_impl.UserBizImplV1;

/**
 * ҵ�񹤳���
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����10:07:48
 */
public class BizFactor {
	//һ����̬��������
	/**
	 * ����ҵ�������ƣ������Ӧҵ�����ʵ��
	 * @param bizName
	 * @return
	 */
	public static Biz getBiz(String bizName) {
		switch (bizName.toLowerCase()) {
		//�ַ����Ƚϵ�ʱͬһ��Сд  ,����Ƚ�
		case "bookinfobiz":
			return new BookInfoBIzImplV1();
		case "userbiz":
			return new UserBizImplV1();
		default:
			return null;
		}
	}
}
