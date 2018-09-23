package biz_C;

import biz_C.biz_impl.BookInfoBIzImplV1;
import biz_C.biz_impl.UserBizImplV1;

/**
 * 业务工厂类
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 上午10:07:48
 */
public class BizFactor {
	//一个静态方法即可
	/**
	 * 根据业务类名称，获得相应业务类的实例
	 * @param bizName
	 * @return
	 */
	public static Biz getBiz(String bizName) {
		switch (bizName.toLowerCase()) {
		//字符串比较的时同一用小写  ,方便比较
		case "bookinfobiz":
			return new BookInfoBIzImplV1();
		case "userbiz":
			return new UserBizImplV1();
		default:
			return null;
		}
	}
}
