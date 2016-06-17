/**
 * 
 */
package com.alphacmc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alphacmc.domain.Bookmark;
import com.alphacmc.service.BookmarkService;

/**
 * Bookmark RESTコントローラ
 * @author matsumoto
 *
 */
@RestController
@RequestMapping("api/bookmarks")
public class BookmarkRestController {

	@Autowired
	BookmarkService bookmarkService;

	/**
	 * 全件取得処理
	 * @return 取得結果
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Bookmark> getBookmarks() {
		return bookmarkService.findAll();
	}

	/**
	 * 登録処理
	 * @param 登録情報
	 * @return 処理結果
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Bookmark postBookmarks(@Validated @RequestBody Bookmark bookmark) {
		return bookmarkService.save(bookmark);
	}

	/**
	 * 削除処理
	 * @param id 対象ID
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookmarks(@PathVariable("id") Long id) {
		bookmarkService.delete(id);
	}

	/**
	 * 全件取得処理
	 * @param id 対象ID
	 * @return 取得結果
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public Bookmark getBookmark(@PathVariable("id") Long id) {
		return bookmarkService.findOne(id);
	}
}
