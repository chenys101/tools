package io.chenys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTestCase {
	
	/**
	 *	Greed������
	 * 	X? 		X��һ�λ�һ��Ҳû��[0,1]
	 *	X* 		X����λ��� [0+]
	 *	X+ 		X��һ�λ��� [1+]
	 *	X{n} 	X��ǡ�� n �� 
	 *	X{n,} 	X������ n �� 
	 *	X{n,m} 	X������ n �Σ����ǲ����� m �� 
	 */
	public static void testGreed(){
		//������ͷ����β���ٳ�������������"��"
		String regex = "^-?\\d+��{3,5}$",input="-11111����������";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			System.out.println("matched..."+input);
		}else {
			System.err.println("could not matched...");
		}
	}
	
	public static void testLogic(){
		String regex = "^(��|��|��)?.*",input="��1����";
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
	 * Ԥ�����ַ�
	 * 	. 	�κ��ַ������н���������ƥ��Ҳ���ܲ�ƥ�䣩 
	 * 	\d 	���֣�[0-9] 
	 * 	\D 	�����֣� [^0-9] 
	 * 	\s 	�հ��ַ���[ \t\n\x0B\f\r] 
	 * 	\S 	�ǿհ��ַ���[^\s] 
	 * 	\w 	�����ַ���[a-zA-Z_0-9] 
	 * 	\W 	�ǵ����ַ���[^\w] 
	 */
	public static void predefinedTest(){
		//��ĸ��ͷ�����԰�����ĸ�����֣��»��ߣ�8-10λ
		String regex = "^[a-zA-Z]\\w{7,10}$",input="a45_6123_";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		System.err.println(matcher.find());
	}
	
	/**
	 * ���⹹�죨�ǲ���(?:X)
	 */
	public static void specialConstructionsTest(){
		//(?=X)
		Pattern p0 = Pattern.compile("(?=hopeful)hope");
		String str0 = "hopeful";//��"hopeful"��ʼ
		Matcher m0 = p0.matcher(str0);
		while(m0.find()){
			System.out.println("0-"+m0.group());
		}
		//(?<=X)
		Pattern p1 = Pattern.compile("(?<=hopeful)hope");
		String str1 = "hopefulhope";//��"hopeful"��ʼ
		Matcher m1 = p1.matcher(str1);
		while(m1.find()){
			System.out.println("1-"+m1.group());
		}
		//��ĸ��ͷ������ͬʱ������ĸ�����֣��»��ߣ�8-15λ
		String regex = "(?=[a-zA-Z])(?=.*[0-9])(?=.*[_])[\\w]{8,15}$", input="a11111_q";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		System.out.println("2-"+matcher.find());
	}
	
	public static void main(String[] args) {
		specialConstructionsTest();
	}
	
}
