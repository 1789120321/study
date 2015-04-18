package stock.test;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Luonanqin on 4/7/15.
 */
public class Base64Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String st = new String("Wwp7IlN1cGVySHVnZUJ1eU51bSI6Ii04NTUzOSIsIlN1cGVySHVnZUJ1eSI6IjMwLjM1In0sCnsiSHVnZUJ1eU51bSI6Ii0xODY1NDEiLCJIdWdlQnV5IjoiNDMuMjQifSwKeyJCdXlOdW0iOiItMTI4MDE1IiwiQnV5IjoiMTEuODMifSwKeyJTbWFsbEJ1eU51bSI6Ii01NzkzMyIsIlNtYWxsQnV5IjoiMTQuNTgifSwKeyJTdXBlckh1Z2VTZWxsTnVtIjoiLTEwMTg4IiwiU3VwZXJIdWdlU2VsbCI6IjUwLjE2In0sCnsiSHVnZVNlbGxOdW0iOiI0NDk0MSIsIkh1Z2VTZWxsIjoiMzUuNDcifSwKeyJTZWxsTnVtIjoiMjAxMDcyIiwiU2VsbCI6IjcuODkifSwKeyJTbWFsbFNlbGxOdW0iOiIxNjUxMzkiLCJTbWFsbFNlbGwiOiI2LjQ4In0KXQ==");
		String st = new String("BE1zMEGK8z8zMvVLCw1lZlOlLCmmtMnUACHYkcHG+rXG1QfqACj8ASGmcmVNYOQwZfNk8z0zCEZfMEQnalNOdso92fVJ0nokdsU9TsYkdfGO0jkOdsGldnhJ0nokdsdlTsYkdsd1dXkkdso90f0J0nokdfGOTsYkdso10vkOdsoO0shJdsoL0sw5TsYkdsoO0vkOdsok0l4J8m51CfNidLZWZX8laWQuBfI58z8NaW1zBfUkdfIk0sGNdfUi0lG9");

		byte[] utf8s = Base64.decodeBase64(st.getBytes("gbk"));
		String stn = new String(utf8s, "utf8");
		System.out.println(stn);
	}
}
