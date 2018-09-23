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
 * �ļ�����--������
 * //�м�����Ϊ׷��ģʽ���мǲ�Ҫ���׸ı伯���е��������ͣ�����ȡ��������cast����
 * @author Administrator
 * @tags   
 * @copyright  katherinelove 
 * @date  2018��9��13�� ����6:04:15
 */
public class FileUtil {
	public static String Pathname="E:\\teacher_liao\\java\\BookManagerSystem\\Files\\BookInfo.dat";
	public static String Pathname1="E:\\teacher_liao\\java\\BookManagerSystem\\Files\\UserInfo.dat";
	
	//���﴿����Ϊ���ģ�����Set���ϴ洢�û����󼯺�
	public static void saveUserInfoMap(Map<String,User> userInfoMap) {
		saveObeject(Pathname1, userInfoMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,User> readUserInfoMap(){
		Object userInfoMap=readObject(Pathname1);
		if(null==userInfoMap) return null;
		return (Map<String,User>) userInfoMap;
	}
	
	/*Ϊ�˴�洢����飬�Լ��ϴ洢**/
	//�洢�ͼ��ص��ļ�·������һ��
	public static void saveBookInfoMap(Map<String, BookInfo> bookInfoMap) {
		saveObeject(Pathname, bookInfoMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, BookInfo> readBookInfoMap(){
		Object obj=readObject(Pathname);
		if(null==obj) return null;
		return (Map<String, BookInfo>) obj;
	}
	
	/*����Ϊ�洢��������--ͨ�÷���**/
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
			//�м�����Ϊ׷��ģʽ���мǲ�Ҫ���׸ı伯���е��������ͣ�����ȡ��������cast����
			//ʹ�ö�������װһ��
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
