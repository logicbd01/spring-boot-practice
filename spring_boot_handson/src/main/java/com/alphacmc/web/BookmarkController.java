/**
 * 
 */
package com.alphacmc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alphacmc.domain.Bookmark;
import com.alphacmc.service.BookmarkService;

/**
 * Bookmark WEBコントローラ
 * @author matsumoto
 */
@Controller
@RequestMapping("bookmark")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	/**
	 * フォームの初期化
	 * @return 初期化後のクラス
	 */
	@ModelAttribute
	Bookmark setUp() {
		// フォームオブジェクトの初期化 (ほんとはフォーム用クラスを別に作る)
		Bookmark bookmark = new Bookmark();
		return bookmark;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	String list(Model model) {
		// 一覧取得処理
		List<Bookmark> bookmarks = bookmarkService.findAll();
		// 引数のModelクラスは固定 addAttributeすると画面から参照できる
		model.addAttribute("bookmarks", bookmarks);
		// ビュー名を返却する この場合は、「tempate/bokkmark/list.html」を使用する
		return "bookmark/list";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	String create(@Validated Bookmark bookmark, BindingResult bindingResult, Model model) {
		// 入力チェックエラーがあれば、listメソッドを実行して一覧画面へ
		if (bindingResult.hasErrors()) {
			return list(model);
		}
		// 登録処理
		bookmarkService.save(bookmark);
		// 一覧画面へリダイレクト
		return "redirect:/bookmark/list";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	String delete(@RequestParam("id") Long id) {
		// 削除処理
		bookmarkService.delete(id);
		// 一覧画面へリダイレクト
		return "redirect:/bookmark/list";
	}

}
