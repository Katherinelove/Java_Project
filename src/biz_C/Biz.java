package biz_C;

import java.io.Serializable;
import java.util.Map;

import entity_M.BookInfo;

/**
 * ҵ����ĸ���--����������Ϊ���ܹ�ʵ�ֶ������л�����������̳�Serializable��
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��14�� ����9:05:47
 */

/**
 * ��ĸ���   ʹ�÷��ͱ��
 */
public interface Biz<T> extends Serializable{
	//������������������--ʹ�÷���;���ʹ�����أ�BookInfoBiz������ʵ��User�Ľӿڷ���
//	boolean add(BookInfo/User/Reader);   
	public boolean add(T t);
	public boolean del(T t);   //����ʵ����Ψһ��ʶɾ��
	public T update(T t);      //���ظ��º�Ķ���
	public T findByid(String id); 
	//Map<String,BookInfo>    Map<String,User>  ...
	public Map<String,T> findAll();     //���ļ��н����ж���ȡ�������ڴ��ܴ洢ΪMap
}
