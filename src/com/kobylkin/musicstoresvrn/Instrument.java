package com.kobylkin.musicstoresvrn;

public class Instrument {
	int id;
	String brand;
	String model;
	String type;
	int price;
	int quantity;
	
	public Instrument(int id, String brand, String model, String type,
			int price, int quantity) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}
	
}
