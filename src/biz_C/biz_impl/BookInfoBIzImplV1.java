package biz_C.biz_impl;
import java.io.File;
/**
 * BookInfoBiz�ӿڵ�ʵ����
 */
import java.util.Map;


import biz_C.BookInfoBiz;
import entity_M.BookInfo;
import util.FileUtil;

public class BookInfoBIzImplV1 implements BookInfoBiz {
	private static final long serialVersionUID = 6299396032835537501L;

	@Override
	public boolean add(BookInfo bookInfo) {
		//�в���ʱ��ѡ�ж�
		if(null==bookInfo) return false;
		//��ȡ��⣨�ļ��������������(Map)
		//�ж��Ȿ����������Ƿ����--��Ӵ���
		//�������飨Map��д���ļ���ȥ
		/**��Ȼֻ��һ�仰 ��װ�������������Ժ󣬸��ķ��㣩*/
		Map<String, BookInfo> bookInfoMap=findAll();  
		if(null==bookInfoMap) return false;      //��ǿ�߼������ܶ�ȡ����Ϊ�գ�
		//isbnΪΨһ��ʶ���ĺô�����ֱ�Ӳ鿴map�еļ�ֵ�Ƿ���ȣ�List/Set�б����ж��Ƿ��������
		if(bookInfoMap.containsKey(bookInfo.getIsbn())) return false;
		//��Map����Ӵ��鼮 ���൱��add
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		//д���ļ�
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	/**
	 * ɾ��ͬһ��������
	 */
	public boolean del(BookInfo bookInfo) {
		//������鼮���߼�һ��
		if(null==bookInfo) return false;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return false;      //��ǿ�߼������ܶ�ȡ����Ϊ�գ�
		
		//��������ɾ��
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())) return false;
		
		//ɾ��
		bookInfoMap.remove(bookInfo.getIsbn());
		
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public BookInfo update(BookInfo bookInfo) {
		//������鼮���߼�һ��
		if(null==bookInfo) return null;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return null;      //��ǿ�߼������ܶ�ȡ����Ϊ�գ�
		
		//��������ɾ��
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())) return null;
		
		//����   ��������Ӵ˼������൱�ڸ���
		//�д˼������  û�������
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		
		FileUtil.saveBookInfoMap(bookInfoMap);
		return bookInfo;
	}

	@Override
	public BookInfo findByid(String id) {
		//BookInfo����û��id,ֻ��isbn;
		//1.0ֱ����ʾ����������
		//2.0�ֶ��׳���֧�ֵĲ����쳣
		//���һ��������������û�����壬���׳�һ���쳣
		throw new UnsupportedOperationException
		("BookInfoBiz�в�֧�ָ���id�Ĳ�ѯ������� findByIsbn");
//		return null;    �������е�������
	}

	@Override
	public Map<String, BookInfo> findAll() {
		return FileUtil.readBookInfoMap();
	}

	@Override
	public boolean outStore(String isbn, int outCount) {
		//����
		if(null==isbn||outCount<=0) return false;
		BookInfo bookInfo=findByIsbn(isbn);
		if(null==bookInfo) return false;
		
		if(bookInfo.getInStoreCount()<outCount) return false;
		
		//��ʾ������������
		//����
		bookInfo.setInStoreCount(bookInfo.getInStoreCount()-outCount);
		Map<String, BookInfo> bookInfoMap=findAll(); //�����findByIsbn(isbn)�Ѿ���֤����
		//����
		bookInfoMap.put(isbn, bookInfo);
		//д���ļ�
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public boolean inStore(String isbn, int inCount) {
		if(null==isbn||"".equals(isbn)) return false;
		if(inCount<=0) return false;
		BookInfo bookInfo=findByIsbn(isbn);
		if(null==bookInfo) return false; 
		//���
		Map<String, BookInfo> bookInfoMap=findAll();
		
		bookInfo.setInStoreCount(bookInfo.getInStoreCount()+inCount);
		bookInfoMap.put(isbn, bookInfo);
		
		//ǧ�������д���ļ�����
		FileUtil.saveBookInfoMap(bookInfoMap);
		return true;
	}

	@Override
	public BookInfo findByIsbn(String isbn) {
		if(null==isbn||"".equals(isbn)) return null;
		
		Map<String, BookInfo> bookInfoMap=findAll();
		if(null==bookInfoMap) return null;      //��ǿ�߼������ܶ�ȡ����Ϊ�գ�
		
		if(!bookInfoMap.containsKey(isbn)) return null;
		return bookInfoMap.get(isbn);
	}

}
