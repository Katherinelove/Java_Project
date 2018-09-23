package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity_M.BookInfo;
import entity_M.User;

/**
 * 文件操作--工具类
 * //切记若改为追加模式，切记不要轻易改变集合中的数据类型，否则取出将发生cast错误
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018年9月13日 下午6:04:15
 */
public class FileUtil {
	public static String Pathname="E:\\teacher_liao\\java\\BookManagerSystem\\Files\\BookInfo.dat";
	public static String Pathname1="E:\\teacher_liao\\java\\BookManagerSystem\\Files\\UserInfo.dat";
	
	//这里纯粹是为无聊，采用Set集合存储用户对象集合
	public static void saveUserInfoMap(Map<String,User> userInfoMap) {
		saveObeject(Pathname1, userInfoMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,User> readUserInfoMap(){
		Object userInfoMap=readObject(Pathname1);
		if(null==userInfoMap) return null;
		return (Map<String,User>) userInfoMap;
	}
	
	/*为了存存储多个书，以集合存储**/
	//存储和加载的文件路径必须一致
	public static void saveBookInfoMap(Map<String, BookInfo> bookInfoMap) {
		saveObeject(Pathname, bookInfoMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, BookInfo> readBookInfoMap(){
		Object obj=readObject(Pathname);
		if(null==obj) return null;
		return (Map<String, BookInfo>) obj;
	}
	
	/*以下为存储单个对象--通用方法**/
	public static void saveObeject(String pathName,Object obj) {
		if(null==obj) return;
		File file =new File(pathName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try(
			FileOutputStream fOutput=new FileOutputStream(pathName,false);  
			//切记若改为追加模式，切记不要轻易改变集合中的数据类型，否则取出将发生cast错误
			//使用对象流包装一下
			ObjectOutputStream objOut=new ObjectOutputStream(fOutput)
			){
			objOut.writeObject(obj);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object readObject(String pathName) {
		File file=new File(pathName);
		try(
				FileInputStream fin=new FileInputStream(file);
				ObjectInputStream objIn=new ObjectInputStream(fin);
			){
			
			return objIn.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
