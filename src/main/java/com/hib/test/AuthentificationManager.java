package com.hib.test;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hib.entities.User;


public class AuthentificationManager {
	static List<ConnSession> sessions = new ArrayList<ConnSession>();
	
	public static UUID addSession(User usr){
		if(isSessionActive(usr)){
			removeSession(usr);
		}
		
		ConnSession c = new ConnSession(UUID.randomUUID(), usr);
		sessions.add(c);
		
		return c.id;
	}

	public static boolean isSessionActive(User usr){
		for(ConnSession ses : sessions){
			if(usr.getUsername().compareTo(ses.usr.getUsername()) == 0){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean sessionsExists(String sid){
		for(ConnSession ses : sessions){
			if(sid.compareTo(ses.id.toString()) == 0){
				return true;
			}
		}
		
		return false;
	}
	
	public static User getUserFromSID(String sid){
		for(ConnSession ses : sessions){
			if(sid.compareTo(ses.id.toString()) == 0){
				return ses.usr;
			}
		}
		
		return null;
	}
	
	
	
	public static void removeSession(User usr){
		int index = 0;
		for(ConnSession ses : sessions){
			if(usr.getUsername().compareTo(ses.usr.getUsername()) == 0){
				break;
			}
			index++;
		}
		
		if(index < sessions.size()){
			sessions.remove(index);
		}
	}
	
	
	public static void debugSessions(){
		for(ConnSession ses : sessions){
			System.out.println(ses.usr.getUsername() + " on session: " + ses.id);
		}
	}
}
