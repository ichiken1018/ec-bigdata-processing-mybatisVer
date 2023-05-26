package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.mapper.UserMapper;

/**
 * ユーザー登録を操作するサービス.
 * 
 * @author kenta_ichiyoshi
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	UserMapper userMapper;

	/**
	 * ユーザー登録をする.
	 * 
	 * @param user ユーザー情報
	 */
	public void registerUser(User user) {
		userMapper.insert(user);
	}
}
