package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.User;

@Mapper
public interface UserMapper {

	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param user ユーザー情報
	 */
	void insert(@Param("user") User user);

	/**
	 * メールとパスワードからユーザー情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 検索されたユーザー情報
	 */
	User findByMailAddressAndPassword(@Param("mailAddress") String mailAddress,@Param("password") String password);

	/**
	 * メールアドレスからユーザー情報を取得する.
	 * 
	 * @param mailAddress
	 * @return
	 */
	User findByMailAddress(String mailAddress);

}
