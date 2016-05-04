package dev;

import java.util.ArrayList;

public class Persoana implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public int id;
	public String nume;
	public String email;
	public String telefon;	
	
	public Persoana(ArrayList<String> data) {
		id = mkID();
		nume = data.get(0); 
		email = data.get(1);
		telefon = data.get(2);
		Serializable.serialize(this, "persoana"+id);
	}
	
	public Persoana(ArrayList<String> data, int oldID) {
		id = oldID;
		nume = data.get(0); 
		email = data.get(1);
		telefon = data.get(2);
		Serializable.serialize(this, "persoana"+id);
	}
	
	private int mkID() {
		return (int) (Math.random()*10000);
	}
}
