package com.example.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Category;
@SpringBootTest
class CategoryMapperTest {

	@Autowired
	private CategoryMapper mapper;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("階層でカテゴリ検索するテスト")
	void testFindByDepth() {
		Integer depth = 0;
		List<Category>categoryList = mapper.findByDepth(depth);
//		for(Category category : categoryList) {
//			System.out.println("findByDepth:" + category.getName());
//			System.out.println("id:" + category.getId() );
//		}
		assertEquals(11,categoryList.size(),"検索結果が異なります");
	}

	@Test
	@DisplayName("親idと階層カテゴリ検索するテスト(親情報取得)")
	void testFindByParentIdAndDepth1() {
		Integer parentId = 2;
		Integer depth = 0;
		List<Category>categoryList = mapper.findByParentIdAndDepth(parentId, depth);
//		System.out.println("parentList:" + categoryList.get(0).getName());
		assertEquals("Beauty",categoryList.get(0).getName(),"検索結果が異なります");
	}
	@Test
	@DisplayName("親idと階層カテゴリ検索するテスト(子情報取得)")
	void testFindByParentIdAndDepth2() {
		Integer parentId = 2;
		Integer depth = 1;
		List<Category>categoryList = mapper.findByParentIdAndDepth(parentId, depth);
//		for(Category category:categoryList) {
//			System.out.println("childList:" + category.getName());
//		}
		assertEquals("Bath & Body",categoryList.get(0).getName(),"検索結果が異なります");
	}
	@Test
	@DisplayName("親idと階層カテゴリ検索するテスト(孫情報取得)")
	void testFindByParentIdAndDepth3() {
		Integer parentId = 2;
		Integer depth = 2;
		List<Category>categoryList = mapper.findByParentIdAndDepth(parentId, depth);
//		for(Category category:categoryList) {
//			System.out.println("grandChildList:" + category.getName());
//		}
		assertEquals("Bags & Cases",categoryList.get(0).getName(),"検索結果が異なります");
	}
	
	@Test
	@DisplayName("子idから親,子,孫カテゴリの検索を確認するテスト")
	void testFindByChildId() {
		Integer childId = 4;
		List<Category> categoryList = mapper.findByChildId(childId);
		// カテゴリ情報を取得する. 〇/〇/〇
		assertEquals(3, categoryList.size(), "検索結果が異なります");
		assertEquals("Beauty", categoryList.get(0).getName(), "検索結果が異なります");
		assertEquals("Bath & Body", categoryList.get(1).getName(), "検索結果が異なります");
		assertEquals("Bath", categoryList.get(2).getName(), "検索結果が異なります");
//		for(Category category : categoryList) {
//			System.out.println("childcategory:" + category.getName());
//		}
	}
	

}
