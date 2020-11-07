package com.simul.dakku.modules.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simul.dakku.modules.commons.utils.FileUtile;

@RestController
@RequestMapping("/api")
public class ItemController {
	
	@GetMapping("/items/preview/{type}")
	public List preview(HttpServletRequest req, @PathVariable String type) throws IOException {
		String path = "/resources/items/" + type;
		Map itemMap = FileUtile.getPathFiles(req.getServletContext(), path);
		
		if(itemMap.get("resultCode").toString().equals("success")) {
			List<File> files = (List<File>) itemMap.get("files");
			List list = new ArrayList();
			List even = new ArrayList();
			for (int i = 0; i < files.size(); i++) {
				Map item = new HashMap();
				item.put("title", files.get(i).getName());
				String previewPath = path + "/" + files.get(i).getName();
				Map previewMap = FileUtile.getPathFiles(req.getServletContext(), previewPath);
				if(previewMap.get("resultCode").toString().equals("success")) {
					List<File> previewFiles = (List<File>) previewMap.get("files");
					item.put("url", previewPath + "/" + previewFiles.get(0).getName());
				}
				even.add(item);
				if(i % 2 != 0) {
					Map map = new HashMap();
					map.put("key", i);
					map.put("value", even);
					list.add(map);
					even = new ArrayList();
				}
			}
			return list;
		}
		return null;
	}
	
	@GetMapping("/items/{type}/{name}")
	public List items(HttpServletRequest req, @PathVariable String type, @PathVariable String name) throws IOException {
		String path = "/resources/items/" + type + "/" + name;
		Map itemMap = FileUtile.getPathFiles(req.getServletContext(), path);
		
		if(itemMap.get("resultCode").toString().equals("success")) {
			List list = new ArrayList();
			List<File> files = (List<File>) itemMap.get("files");
			for (int i = 0; i < files.size(); i+=12) {
				list.add(rowStikers(path + "/", files.subList(i, i+12 > files.size() ? files.size() : i+12)));
			}
			return list;
		}
		return null;
	}
	
	private List rowStikers(String path, List<File> files) {
		List list = new ArrayList();
		for (File file : files) {
			list.add(path + file.getName());
		}
		return list;
	}
}
