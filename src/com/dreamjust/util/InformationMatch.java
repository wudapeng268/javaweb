package com.dreamjust.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.dreamjust.dao.FoundDAO;
import com.dreamjust.dao.LostDAO;
import com.dreamjust.dao.impl.FoundDAOImpl;
import com.dreamjust.dao.impl.LostDAOImpl;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;

public class InformationMatch {

	public static FoundDAO foundDAO = new FoundDAOImpl();
	public static LostDAO lostDAO = new LostDAOImpl();

	public static List<FoundThing> matchFoundThings(
			LostThing lostThing) throws IOException {
		String[] names = null;
		List<FoundThing> foundThings = new ArrayList<FoundThing>();
		try {
			names = part(lostThing.getName()).split("\\|");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Arrays.toString(names));
		for (String name : names) {
			List<FoundThing> f = foundDAO
			.getFoundThingsByAddressAndColor(
					lostThing.getAddress(),
					lostThing.getColor(), name);
			System.out.println("--------------------");
			System.out.println(f);
			foundThings.addAll(f);
		}
		removeexistfound(foundThings);
		String aim = part(lostThing.getDescription());
		double[] scoles = new double[foundThings.size()];
		for (int i = 0; i < scoles.length; i++) {
			scoles[i] = Similarity(aim, part(foundThings
					.get(i).getDescription()));
		}
		sort(scoles, foundThings);
		return foundThings;

	}

	public static List<LostThing> matchLostThings(
			FoundThing foundThing) throws IOException {
		String[] names = null;
		List<LostThing> lostThings = new ArrayList<LostThing>();
		try {
			names = part(foundThing.getName()).split("\\|");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String name : names) {
			lostThings.addAll(lostDAO
					.getLostThingsByAddressAndColor(
							foundThing.getAddress(),
							foundThing.getColor(), name));
		}
		removeexist(lostThings);
		String aim = part(foundThing.getDescription());
		double[] scoles = new double[lostThings.size()];
		for (int i = 0; i < scoles.length; i++) {
			scoles[i] = Similarity(aim, part(lostThings
					.get(i).getDescription()));
		}
		sort(scoles, lostThings);
		return lostThings;
	}

	private static void removeexist(
			List<LostThing> lostThings) {
		// TODO Auto-generated method stub
		Set<LostThing> temp = new HashSet<LostThing>();
		temp.addAll(lostThings);
		lostThings.clear();
		lostThings.addAll(temp);
	}
	
	private static void removeexistfound(
			List<FoundThing> foundThings) {
		// TODO Auto-generated method stub
		Set<FoundThing> temp = new HashSet<FoundThing>();
		temp.addAll(foundThings);
		foundThings.clear();
		foundThings.addAll(temp);
	}

	private static void sort(double[] scoles,
			List<?> foundThings) {
		for (int i = 0; i < scoles.length; i++) {
			for (int j = i + 1; j < scoles.length; j++) {
				if (scoles[i] < scoles[j]) {
					double temp = scoles[i];
					scoles[i] = scoles[j];
					scoles[j] = temp;
					Collections.swap(foundThings, i, j);
				}
			}
		}

	}

	

	/**
	 * 统计文本的相似度
	 * 
	 * @param text1
	 * @param text2
	 * @return 返回相似度数值
	 */
	public static double Similarity(String text1,
			String text2) {
		double similarity = 0.0, numerator = 0.0, denominator1 = 0.0, denominator2 = 0.0;
		int temp1, temp2;
		Map<String, Integer> dictionary1 = getMap(text1);
		Map<String, Integer> dictionary2 = getMap(text2);
		if ((dictionary1.size() < 1)
				|| (dictionary2.size() < 1))// 如果任一篇文章中不含有汉字
		{
			return 0.0;
		}
		Iterator<String> keys1 = dictionary1.keySet()
				.iterator();

		while (keys1.hasNext()) {
			String key = keys1.next();
			temp1 = dictionary1.get(key);
			if (dictionary2.get(key) == null) {
				temp2 = 0;
			}
			else {
				temp2 = dictionary2.get(key);
				dictionary2.remove(key);
				numerator += temp1 * temp2;
				denominator1 += temp1 * temp1;
				denominator2 += temp2 * temp2;
			}
			
		}

		Iterator<String> keys2 = dictionary2.keySet()
				.iterator();
		while (keys2.hasNext()) {
			String key = keys2.next();
			temp2 = dictionary2.get(key);
			denominator2 += temp2 * temp2;
		}
		double t=Math.sqrt(denominator1*denominator2);
		if(Math.abs(t)<0.0001)
		{
			return 0;
		}
		similarity = numerator
				/ t;
		return similarity;
	}

	/**
	 * 统计文章的词频字典
	 * 
	 * @param text
	 * @return
	 */
	public static Map<String, Integer> getMap(String text) {

		Map<String, Integer> dictionary = new HashMap<String, Integer>();
		String regex = "\\|";
		String[] results = text.split(regex);
		int temp = 0;
		for (String word : results) {
			if (dictionary.containsKey(word)) {
				temp = dictionary.get(word);
				temp++;
				dictionary.put(word, temp);
			} else {
				dictionary.put(word, 1);
			}
		}
		return dictionary;
	}

	/**
	 * 中文分词
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String part(String text)
			throws IOException {
		if (text==null||text.length()==0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		StringReader sr = new StringReader(text);
		IKSegmenter ik = new IKSegmenter(sr, true);
		Lexeme lex = null;
		while ((lex = ik.next()) != null) {
			sb.append(lex.getLexemeText() + "|");
		}
		return sb.substring(0,sb.length()-1).toString();

	}
	
	public static void main(String[] args) throws IOException {
//		LostThing lostThing = lostDAO.getLostThingById(1);
//		List<FoundThing> foundThings = matchFoundThings(lostThing);
//		System.out.println(foundThings);
//		System.out.println(Arrays.toString(part("身份证").split("\\|")));
		System.out.println(Similarity("12", "31"));
	}

}
