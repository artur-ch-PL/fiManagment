package com.fi.fimanagment.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {

	public static List<DummyItem> ITEMS = new ArrayList <DummyItem>();
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
	
	public static class DummyItem {
		public String id;
		public String content;
		public DummyItem (String id, String content){
			this.id = id;
			this.content = content;
		}
	
		public String toString(){
			return content;
		}
	}
	
	private static void addItem(DummyItem item){
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}
	
	static{
		addItem(new DummyItem("1", "Socket 1"));
		addItem(new DummyItem("2", "Socket 2"));
		addItem(new DummyItem("3", "Socket 3"));
		addItem(new DummyItem("4", "Socket 4"));
	}
	
	
}
