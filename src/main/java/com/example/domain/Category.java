package com.example.domain;

/**
 * categoryのドメイン.
 * 
 * @author kenta_ichiyoshi
 *
 */
public class Category {
	/** カテゴリID */
	private Integer categoryId;
	/** 階層の深さ */
	private Integer depth;
	/** カテゴリ名 nameAll 〇/〇/〇を1つずつ取得したもの */
	private String name;

	public Category() {

	}

	public Category(Integer id, Integer depth, String name) {
		super();
		this.categoryId = id;
		this.depth = depth;
		this.name = name;
	}

	public Integer getId() {
		return categoryId;
	}

	public void setId(Integer id) {
		this.categoryId = id;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + categoryId + ", depth=" + depth + ", name=" + name + "]";
	}

}
