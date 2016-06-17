/**
 * 
 */
package com.alphacmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alphacmc.domain.Bookmark;
import com.alphacmc.repository.BookmarkRepository;

/**
 * Bookmarkサービス
 * @author matsumoto
 *
 */
@Service
@Transactional
public class BookmarkService {

	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	/**
	 * 全件取得
	 * @return ブックマークデータ全件
	 */
	public List<Bookmark> findAll() {
		return bookmarkRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}
	
	/**
	 * 新規登録
	 * @param bookmark 登録データ
	 * @return 登録データ
	 */
	public Bookmark save(Bookmark bookmark) {
		return bookmarkRepository.save(bookmark);
	}
	
	/**
	 * 1件削除
	 * @param id 削除対象ID
	 */
	public void delete(Long id) {
		bookmarkRepository.delete(id);
	}

	/**
	 * 1件取得
	 * @param id 取得対象ID
	 * @return 取得結果
	 */
	public Bookmark findOne(Long id) {
		return bookmarkRepository.findOne(id);
	}
}
