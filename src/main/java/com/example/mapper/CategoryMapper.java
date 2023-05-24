package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Category;
@Mapper
public interface CategoryMapper {

	/**
	 * 階層でカテゴリ情報を検索する.
	 * 
	 * @param depth 階層
	 * @return 階層に紐付くカテゴリ情報
	 */
	List<Category> findByDepth(Integer depth);
	
	/**
	 * 親に紐付く子孫のカテゴリ名を検索する
	 * 
	 * @param parentId　親id
	 * @param depth 階層
	 * @return　検索されたカテゴリ名
	 */
	List<Category>findByParentIdAndDepth(@Param("parentId") Integer parentId,@Param("depth") Integer depth);
	
	/**
	 * 子,孫カテゴリを検索する
	 * 
	 * @param childId
	 * @return　検索された子,孫カテゴリ
	 */
	List<Category>findByChildId(Integer childId);
}
