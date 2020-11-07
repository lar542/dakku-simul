package com.simul.dakku.modules.commons.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class FileUtile {

	/**
	 * 특정 리소스의 파일 전체 목록 조회
	 * @param servletContext
	 * @param path				:	조회할 디렉토리
	 * @return	해당 디렉토리의 파일 리스트
	 * @throws IOException
	 */
	public static Map getPathFiles(ServletContext servletContext, String path) throws IOException {
		Map resultMap = new HashMap();
		File dir = new File(servletContext.getRealPath(path));
		if(dir.isFile()) {
			resultMap.put("resultCode", "isFile");
			return resultMap;
		}
		File[] files = dir.listFiles();
		resultMap.put("resultCode", "success");
		resultMap.put("files", Arrays.asList(files));
		return resultMap;
	}
}
