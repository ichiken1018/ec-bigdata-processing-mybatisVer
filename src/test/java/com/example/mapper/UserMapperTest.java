package com.example.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.example.domain.User;
@SpringBootTest
class UserMapperTest {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private User user;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	@DisplayName("テストユーザー情報をinsert")
	void setUp() throws Exception {
		user = new User();
		user.setName("testMybatis");
		user.setMailAddress("mybatis@sample.com");
		user.setPassword("mybatismy");
		userMapper.insert(user);
		System.out.println("テスト用データを登録しました。");
	}

	@AfterEach
	@DisplayName("テストユーザー情報をdelete")
	void tearDown() throws Exception {
		delete("testMybatis");
		System.out.println("テスト用データを削除しました。");
	}

	public void delete(String name) {
		String sql = "delete from users where name = :name";
		MapSqlParameterSource param = new MapSqlParameterSource("name",name);
		template.update(sql, param);
	}
	
	@Test
	@DisplayName("insertされているか確認するテスト")
	void testInsert() {
		assertEquals("testMybatis",user.getName(),"登録名が異なります");
		assertEquals("mybatis@sample.com",user.getMailAddress(),"登録メールアドレスが異なります");
		assertEquals("mybatismy",user.getPassword(),"登録パスワードが異なります");
	}
	
	@Test
	@DisplayName("メールとパスからユーザー情報を確認するテスト")
	void testFindByMailAddressAndPassword() {
		user = userMapper.findByMailAddressAndPassword(user.getMailAddress(), user.getPassword());
		assertEquals("testMybatis",user.getName(),"登録情報が異なります");
	}

	@Test
	@DisplayName("メールからユーザー情報を確認するテスト")
	void testFindByMailAddress() {
		user = userMapper.findByMailAddress(user.getMailAddress());
		assertEquals("testMybatis",user.getName(),"登録情報が異なります");
	}

}
