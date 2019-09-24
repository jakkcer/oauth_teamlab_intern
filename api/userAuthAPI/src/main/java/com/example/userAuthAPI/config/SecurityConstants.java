package com.example.userAuthAPI.config;

public class SecurityConstants {

	public static final String SECRET = "userauthapisecret";
	public static final long EXPIRATION_TIME = 2880000;
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_ID = "name";
    public static final String PASSWORD = "password";
}