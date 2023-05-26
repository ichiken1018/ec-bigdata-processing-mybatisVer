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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.domain.Item;

@SpringBootTest
class ItemMapperTest {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private NamedParameterJdbcTemplate template;
	
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
	@DisplayName("全件検索するテスト")
	void testFindAll() {
		Integer offset = 0;
		List<Item>itemList = itemMapper.findAll(offset);
		assertEquals(30,itemList.size(),"商品数が異なります");
		assertEquals("MLB Cincinnati Reds T Shirt Size XL",itemList.get(0).getName(),"商品名が異なります");
		assertEquals("Kendra bundle",itemList.get(29).getName(),"商品名が異なります");
	}
	
	@Test
	@DisplayName("商品数計算するテスト")
	void testCountItems() {
		Integer count = itemMapper.countItems();
		assertEquals(1482538,count,"登録商品数が異なります");
	}
	
	@Test
	@DisplayName("フォーム検索を確認するテスト(名前検索)")
	void testfindByName() {
		String name = "mlb";
		//null,-1だとエラーが発生するためcategory_id("t-shirts")でテスト
		Integer id = 909; 
		String brand = "";
		Integer offset = 0;
		List<Item>itemList = itemMapper.findByForm(name,id,brand,offset);		
		assertEquals(10,itemList.size(),"検索結果が異なります");
		assertEquals("MLB Cincinnati Reds T Shirt Size XL",itemList.get(0).getName(),"検索結果が異なります");
		assertEquals("Chicago Cubs Neon MLB Shirt NWT",itemList.get(9).getName(),"検索結果が異なります");	
	}
	
	@Test
	@DisplayName("フォーム検索を確認するテスト(カテゴリ検索)")
	void testfindByCategory() {
		String name = "";
		Integer id = 909; 
		String brand = "";
		Integer offset = 0;
		List<Item>itemList = itemMapper.findByForm(name,id,brand,offset);		
		//Limit30のため
		assertEquals(30,itemList.size(),"検索結果が異なります");
		assertEquals("MLB Cincinnati Reds T Shirt Size XL",itemList.get(0).getName(),"検索結果が異なります");
		assertEquals("Trump pence 2016 small and large",itemList.get(29).getName(),"検索結果が異なります");	
	}
	
	@Test
	@DisplayName("フォーム検索を確認するテスト(ブランド検索)")
	void testfindByBrand() {
		String name = "";
		Integer id = 909; 
		String brand = "nike";
		Integer offset = 0;
		List<Item>itemList = itemMapper.findByForm(name,id,brand,offset);		
		//Limit30のため
		assertEquals(30,itemList.size(),"検索結果が異なります");
		assertEquals("Nike men's dri-fit sleeveless shirt tee",itemList.get(0).getName(),"検索結果が異なります");
		assertEquals("Nike DriFit What The Kobe 7 Matching Tee",itemList.get(29).getName(),"検索結果が異なります");	
	}

	@Test
	@DisplayName("検索結果数を確認するテスト")
	void testCountByForm() {
		String name1 = "mlb";
		Integer id1 = 909; 
		String brand1 = "";
		Integer count1 = itemMapper.countByForm(name1, id1, brand1);		
		assertEquals(1,count1,"検索結果が異なります");
		
		String name2 = "";
		Integer id2 = 909;
		String brand2 = "";
		Integer count2 = itemMapper.countByForm(name2, id2, brand2);
		assertEquals(6327,count2,"検索結果が異なります");

		String name3 = "";
		Integer id3 = 909;
		String brand3 = "nike";
		Integer count3 = itemMapper.countByForm(name3, id3, brand3);
		assertEquals(83,count3,"検索結果が異なります");
	}
	
	@Test
	@DisplayName("主キー検索を確認するテスト")
	void testLoad() {
		Integer itemId = 0;
		Item item = itemMapper.load(itemId);
		assertEquals("MLB Cincinnati Reds T Shirt Size XL",item.getName(),"検索結果が異なります");
		assertEquals("No description yet",item.getDescription(),"詳細情報が異なります");		
	}

	@Test
	@DisplayName("登録商品数を確認するテスト")
	void testCheckItemId() {
		Integer itemId = itemMapper.checkItemId();
		assertEquals(1482537,itemId,"検索結果が異なります");
	}
	
	@Test
	@DisplayName("商品追加を確認するテスト")
	void testInsert() {
		delete(9999999);
		Item item = new Item();
		item.setItemId(9999999);
		item.setName("test");
		item.setCondition(1);
		item.setCategoryId(1);
		item.setBrand("brand");
		item.setPrice(100);
		item.setShipping(0);
		item.setDescription("description");
		itemMapper.insertItem(item);
		assertEquals(9999999,item.getItemId(),"追加されていません");
		assertEquals("test",item.getName(),"追加されていません");		
		delete(9999999);
	}
	//テスト用で追加したitemを削除する.
	public void delete(int itemId) {
		String sql = "DELETE FROM items WHERE item_id = :itemId";
		MapSqlParameterSource param = new MapSqlParameterSource("itemId",itemId);
		template.update(sql, param);
	}
	
	@Test
	@DisplayName("商品更新を確認するテスト")
	void testUpdate() {
		String name = "test";
		double price = 30;
		Item updateItem = itemMapper.load(1482537);
		updateItem.setName(name);
		updateItem.setPrice(price);
		itemMapper.updateItem(updateItem);
		assertEquals(name,updateItem.getName(),"更新後の情報");
		assertEquals(price,updateItem.getPrice(),"更新後の情報");
	}

}
