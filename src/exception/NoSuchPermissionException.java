package exception;
/**
 * �Զ����쳣���̳���Run
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��18�� ����3:07:44
 */
public class NoSuchPermissionException extends RuntimeException{
	private static final long serialVersionUID = 3888098919968884802L;
	public NoSuchPermissionException() {
		super();
		System.out.println("�˲���û��Ȩ��");
	}
	
	public NoSuchPermissionException(String message) {
		super(message);
	}
	
}
