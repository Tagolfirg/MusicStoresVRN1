package com.kobylkin.musicstoresvrn;

public class Store {
	int id;
	String name;
	String address;
	String phone;
	int latitude;
	int longitude;
	
	public Store(int id, String name, String address, String phone,
			int latitude, int longitude) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

}
