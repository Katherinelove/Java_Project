package biz_C;

import java.io.Serializable;
import java.util.Map;

import entity_M.BookInfo;

/**
 * 业务类的父类--方便升级（为了能够实现对象序列化操作，必须继承Serializable）
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月14日 上午9:05:47
 */

/**
 * 大的父类   使用泛型编程
 */
public interface Biz<T> extends Serializable{
	//这里操作多个数据类型--使用泛型;如果使用重载，BookInfoBiz还必须实现User的接口方法
//	boolean add(BookInfo/User/Reader);   
	public boolean add(T t);
	public boolean del(T t);   //根据实体类唯一标识删除
	public T update(T t);      //返回更新后的对象
	public T findByid(String id); 
	//Map<String,BookInfo>    Map<String,User>  ...
	public Map<String,T> findAll();     //从文件中将所有对象取出，在内存总存储为Map
}
