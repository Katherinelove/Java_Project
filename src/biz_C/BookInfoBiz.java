package biz_C;

import entity_M.BookInfo;

/**
 * ����ӿ�--�������з���
 * BookInfoҵ����  ����BookIn����ͬһ�����ļ��ϣ�
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��14�� ����9:19:36
 */
public interface BookInfoBiz extends Biz<BookInfo> {
	//BookInfo���з���
	public BookInfo findByIsbn(String isbn);
	//���
	public boolean outStore(String isbn,int outCount);
	//����
	public boolean inStore(String isbn,int inCount);
}
