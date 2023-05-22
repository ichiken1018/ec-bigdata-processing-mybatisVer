package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Item;

@Mapper
public interface ItemMapper {

	/**
	 * 全件検索
	 * 
	 * @param offset ページング用のオフセット値
	 * @return 全商品情報
	 */
	List<Item> findAll(Integer offset);

	/**
	 * 全商品数計算
	 * 
	 * @return 全商品数
	 */
	Integer countItems();

	/**
	 * 商品検索
	 * 
	 * @param name   商品名
	 * @param id     カテゴリid
	 * @param brand  ブランド名
	 * @param offset ページング用のオフセット値
	 * @return 検索された商品情報
	 */
	List<Item> findByForm(@Param("name") String name, @Param("id") Integer id, @Param("brand") String brand,
			@Param("offset") Integer offset);

	/**
	 * 検索された商品数計算
	 * 
	 * @param name  商品名
	 * @param id    カテゴリid
	 * @param brand ブランド名
	 * @return 検索結果数
	 */
	Integer countByForm(@Param("name") String name, @Param("id") Integer id, @Param("brand") String brand);

	/**
	 * 主キー検索
	 * 
	 * @param itemId 商品id
	 * @return 商品詳細情報
	 */
	Item load(Integer itemId);

	/**
	 * 新商品追加
	 * 
	 * @param item 登録する商品情報
	 */
	void insertItem(@Param("item") Item item);

	/**
	 * 最新のitemIdを確認
	 * 
	 * @return 最新の商品id(itemIdの最大値)
	 */
	Integer checkItemId();

	/**
	 * 商品情報を更新
	 * 
	 * @param item 更新する商品情報
	 */
	void updateItem(@Param("item") Item item);

	/**
	 * itemsのitemIdにindexを貼る
	 */
	void createIndexForItemId();

	/**
	 * itemsのitemIdを削除する
	 */
	void deleteIndexForItemId();
}
