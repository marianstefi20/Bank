package dev;

import java.io.EOFException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Banca extends Observable implements java.io.Serializable {
	public Map<Persoana, List<Cont>> map;
	public double TAXA_RETRAGERE = 0.01;
	public double DOBANDA = 0.01;
	
	public Banca() {
		this.map =  new HashMap<Persoana, List<Cont>>();
		this.deserializeBanca();
		adaugaObservatori();
	}
	
	private void adaugaObservatori() {
		Set<Persoana> set = this.map.keySet();
		for(Persoana pers: set) {
			this.addObserver(pers);
		}
	}
	
	public void setTaxaRetragere(double taxa) {
        this.TAXA_RETRAGERE = taxa;
        setChanged();
        notifyObservers();
	}
	public double getTaxa() {
		return TAXA_RETRAGERE;
	}
	
	public void setDobanda(double d) {
		this.DOBANDA = d;
		setChanged();
		notifyObservers();
	}
	public double getDobanda() {
		return DOBANDA;
	}
	
	public void initBanca() {
		DefaultTableModel model = Serializable.generator("persoana", GUI.DIRNAME);
		Vector<Vector<Object>> allData = model.getDataVector();
		for(Vector data: allData) 
			for(Object obj: data) {
				Persoana cont = (Persoana)obj;
				System.out.println("Am reusit sa ajungem la persoana cu numele: \n" + cont.nume);
			}
	}
	
	public void serializeBanca() {
		Serializable.serialize(map, "banca");
	}
	
	private void deserializeBanca() {
		try {
			map = (HashMap)Serializable.deserialize(map, "banca.ser");
		} catch(Exception e) {
			System.out.println("Am prins exceptia!");
		}
		if(map == null) {
			System.out.println("Chiar nu este nimic\n");
			map = new HashMap<Persoana, List<Cont>>();
		}
	}
}
