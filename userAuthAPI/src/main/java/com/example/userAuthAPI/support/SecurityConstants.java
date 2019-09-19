package com.example.userAuthAPI.support;

public class SecurityConstants {
	public static final String SECRET = "userauthapisecret";
	public static final long EXPIRATION_TIME = 28_800_000;
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGNUP_URL = "/user/signup";
    public static final String LOGIN_URL = "/user/login";
    public static final String LOGIN_ID = "name"; // defalut:username
    public static final String PASSWORD = "password"; // default:password
}
