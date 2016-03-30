package com.hib.test;


import java.util.UUID;

import com.hib.entities.User;

class ConnSession{
	public final UUID id;
	public final User usr;
	
	public ConnSession(UUID id, User usr) {
		super();
		this.id = id;
		this.usr = usr;
	}
}