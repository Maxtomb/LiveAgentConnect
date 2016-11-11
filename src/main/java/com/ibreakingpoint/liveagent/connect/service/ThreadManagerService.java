package com.ibreakingpoint.liveagent.connect.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@Service
@Scope("singleton")
public class ThreadManagerService {
		private static Map<String,MessageThread> threadMap = new HashMap<String,MessageThread>();
		public static MessageThread getThread(String name){
			return threadMap.get(name);
		}
		public static void setThread(String name, MessageThread t){
			t.start();
			threadMap.put(name, t);
		}
		public static void removeThread(String name){
			threadMap.remove(name);
		}
		public static int getThreadCount(){
			return threadMap.size();
		}

}
