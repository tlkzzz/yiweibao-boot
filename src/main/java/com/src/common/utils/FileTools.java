package com.src.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileTools {

	//
	public static List<String> readLines(InputStream stream) {
		try {
			List<String> list = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String lineString = null;
			
			while ((lineString=reader.readLine())!=null) {
				list.add(lineString);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
