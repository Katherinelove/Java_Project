package exception;
/**
 * 自定义异常，继承自Run
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月18日 下午3:07:44
 */
public class NoSuchPermissionException extends RuntimeException{
	private static final long serialVersionUID = 3888098919968884802L;
	public NoSuchPermissionException() {
		super();
		System.out.println("此操作没有权限");
	}
	
	public NoSuchPermissionException(String message) {
		super(message);
	}
	
}
