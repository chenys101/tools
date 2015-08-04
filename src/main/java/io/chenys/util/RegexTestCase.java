package io.chenys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTestCase {
	
	/**
	 *	Greed数量词
	 * 	X? 		X，一次或一次也没有[0,1]
	 *	X* 		X，零次或多次 [0+]
	 *	X+ 		X，一次或多次 [1+]
	 *	X{n} 	X，恰好 n 次 
	 *	X{n,} 	X，至少 n 次 
	 *	X{n,m} 	X，至少 n 次，但是不超过 m 次 
	 */
	public static void testGreed(){
		//整数开头，结尾至少出现三个最多五个"哈"
		String regex = "^-?\\d+哈{3,5}$",input="-11111哈哈哈哈哈";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			System.out.println("matched..."+input);
		}else {
			System.err.println("could not matched...");
		}
	}
	
	public static void testLogic(){
		String regex = "^(你|我|他)?.*",input="你1我他";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			System.out.println("matched..."+input);
		}else {
			System.err.println("could not matched...");
		}
		matcher = Pattern.compile("^\\w{3,}$").matcher("a2_11_");
		System.err.println(matcher.find());
	}
	
	/**
	 * 预定义字符
	 * 	. 	任何字符（与行结束符可能匹配也可能不匹配） 
	 * 	\d 	数字：[0-9] 
	 * 	\D 	非数字： [^0-9] 
	 * 	\s 	空白字符：[ \t\n\x0B\f\r] 
	 * 	\S 	非空白字符：[^\s] 
	 * 	\w 	单词字符：[a-zA-Z_0-9] 
	 * 	\W 	非单词字符：[^\w] 
	 */
	public static void predefinedTest(){
		//字母开头，可以包括字母，数字，下划线，8-10位
		String regex = "^[a-zA-Z]\\w{7,10}$",input="a45_6123_";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		System.err.println(matcher.find());
	}
	
	/**
	 * 特殊构造（非捕获）(?:X)
	 */
	public static void specialConstructionsTest(){
		//(?=X)
		Pattern p0 = Pattern.compile("(?=hopeful)hope");
		String str0 = "hopeful";//从"hopeful"开始
		Matcher m0 = p0.matcher(str0);
		while(m0.find()){
			System.out.println("0-"+m0.group());
		}
		//(?<=X)
		Pattern p1 = Pattern.compile("(?<=hopeful)hope");
		String str1 = "hopefulhope";//从"hopeful"后开始
		Matcher m1 = p1.matcher(str1);
		while(m1.find()){
			System.out.println("1-"+m1.group());
		}
		//字母开头，必须同时包括字母，数字，下划线，8-15位
		String regex = "(?=[a-zA-Z])(?=.*[0-9])(?=.*[_])[\\w]{8,15}$", input="a11111_q";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		System.out.println("2-"+matcher.find());
	}
	
	public static void main(String[] args) {
		specialConstructionsTest();
	}
	
}
