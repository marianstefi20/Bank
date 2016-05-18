package dev;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Persoana implements java.io.Serializable, Observer {
	private static final long serialVersionUID = 1L;
	public int id;
	public String nume;
	public String email;
	public String telefon;	
	private Banca banca;
	public String notificari = new String();
	public boolean notify = false;
	
	public Persoana(String email) {
		this.email = email;
	}
	
	public Persoana(String email, Banca banca) {
		this.email = email;
		this.banca = banca;
	}
	
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
	
	 public void update(Observable obs, Object obj) {
		 this.notify = true;
	     System.out.println("update(" + obs + "," + obj + ");");
//		 if (!obs.getClass().getName().isEmpty()) {
//			 notificari = "Banca a actualizat dobanzile!";
//			System.out.println(obs.getClass().getName()); 
//			//notificari = new String("Banca si-a actualizat dobanda la: " + banca.getDobanda() + " si taxa de retragere la: " + banca.getTaxa());
//			System.out.println(notificari);
//		 }
	 }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Persoana other = (Persoana) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
	
	@Override
    public int hashCode() {
        int result = 0;
        result = (int) (email.hashCode() / 11);
        return result;
    }
	 public static int hashCode(String email) {
	        int result = 0;
	        result = (int) (email.hashCode() / 11);
	        return result;
	    }
	
	private int mkID() {
		return (int) (Math.random()*10000);
	}
}
