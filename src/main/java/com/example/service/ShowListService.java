package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchItemForm;
import com.example.mapper.CategoryMapper;
import com.example.mapper.ItemMapper;

/**
 * 商品一覧表示を操作するサービス.
 * 
 * @author kenta_ichiyoshi
 *
 */
@Service
@Transactional
public class ShowListService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * 商品一覧を表示する
	 * 
	 * @param page ページ数
	 * @param form 検索入力フォーム
	 * @return 検索された商品一覧
	 */
	public List<Item> showItemList(Integer page) {
		Integer offset = 30 * (page - 1);
		List<Item> itemList = itemMapper.findAll(offset);
		itemList = setCategoryList(itemList);
		return itemList;
	}

	/**
	 * 商品数を計算する.
	 * 
	 * @return 全商品数
	 */
	public Integer countItems() {
		return itemMapper.countItems();
	}

	/**
	 * 階層に紐付くカテゴリ情報を取得する.
	 * 
	 * @param depth 階層
	 * @return 検索されたカテゴリ情報
	 */
	public List<Category> pickUpCategoryListByDepth(Integer depth) {
		List<Category> categoryList = categoryMapper.findByDepth(depth);
		if (categoryList.size() == 0) {
			throw new RuntimeException("categoriesテーブルに該当カテゴリが存在しません。");
		}
		return categoryList;
	}

	/**
	 * 子idに紐付くカテゴリ情報を取得する.
	 * 
	 * @param childId 子id
	 * @return 検索されたカテゴリ情報
	 */
	public List<Category> pickUpCategoryListByChildId(Integer childId) {
		List<Category> categoryList = categoryMapper.findByChildId(childId);
		return categoryList;
	}

	/**
	 * 親idと階層に紐付くカテゴリ情報を取得する.
	 * 
	 * @param parentId 親id
	 * @param depth    階層
	 * @return 検索されたカテゴリ情報
	 */
	public List<Category> pickUpCategoryListByParentIdAndDepth(Integer parentId, Integer depth) {
		List<Category> categoryList = categoryMapper.findByParentIdAndDepth(parentId, depth);
		return categoryList;
	}

	/**
	 * 商品を検索する.
	 * 
	 * @param form フォーム
	 * @param page ページ
	 * @return 検索された商品
	 */
	public List<Item> showListByForm(SearchItemForm form, Integer page) {
		Integer offset = 30 * (page - 1);

		String name = form.getName();

		String brand = form.getBrand();

		Integer id = null;
		if (Integer.parseInt(form.getGrandChildId()) > 0) {
			id = Integer.parseInt(form.getGrandChildId());
		} else if (Integer.parseInt(form.getChildId()) > 0) {
			id = Integer.parseInt(form.getChildId());
		} else if (Integer.parseInt(form.getParentId()) > 0) {
			id = Integer.parseInt(form.getParentId());
		}

		List<Item> itemList = itemMapper.findByForm(name, id, brand, offset);
		itemList = setCategoryList(itemList);
		return itemList;
	}

	/**
	 * 検索商品数を計算する.
	 * 
	 * @param form 入力フォーム
	 * @return 検索された商品数.
	 */
	public Integer countByForm(SearchItemForm form) {
		String name = form.getName();

		String brand = form.getBrand();

		Integer id = null;
		if (Integer.parseInt(form.getGrandChildId()) > 0) {
			id = Integer.parseInt(form.getGrandChildId());
		} else if (Integer.parseInt(form.getChildId()) > 0) {
			id = Integer.parseInt(form.getChildId());
		} else if (Integer.parseInt(form.getParentId()) > 0) {
			id = Integer.parseInt(form.getParentId());
		}

		Integer count = itemMapper.countByForm(name, id, brand);
		return count;
	}

	// itemドメインのcategoryListにつめるメソッド
	public List<Item> setCategoryList(List<Item> itemList) {
		for (Item item : itemList) {
			Integer categoryId = item.getCategoryId();
			List<Category> categoryList = categoryMapper.findByChildId(categoryId);
			item.setCategoryList(categoryList);
		}
		return itemList;
	}
}
