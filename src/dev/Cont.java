package dev;

import java.util.ArrayList;

public class Cont implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public static final boolean contEconomii = true;
	public static final boolean contCheltuieli = false;
	private double fonduri;
	private String numeCont;
	private Banca banca;
	private boolean tipCont;
	
	public Cont(double bani, String numeCont, Banca bank) {
		this.fonduri = bani;
		this.numeCont = numeCont;
		this.banca = bank;
	}
	
	public Cont(ArrayList<String> data, Banca bank) {
		this.banca = bank;
		this.numeCont = data.get(0);
		this.fonduri = Double.parseDouble(data.get(1));
		this.tipCont = (data.get(2) == "Economii") ? Cont.contEconomii : Cont.contCheltuieli;
	}
	
	// Adaugare de bani la portofel
	 /**
     * Setare fonduri pe cont
     *
     * @param  suma
     * @throws IllegalArgumentException if suma <= 0 or
     * 
     */
	public void addFonduri(double suma) {
		assert suma > 0;
		this.fonduri += suma;
	}
	
	public void initContEconomii() {
		this.tipCont = Cont.contEconomii;
	}
	
	public void initContCheltuieli() {
		this.tipCont = Cont.contCheltuieli;
	}
	
	public boolean getTipCont() {
		return tipCont;
	}
	
	/**
	* returns true if and only if the list is empty
	* @pre true
	* @post @result <=> getSize() > 0
	* @post @nochange
	*/
	// Scoatere de bani din portofel
	public double retrageBani(int suma) {
		double sumaReala = suma + suma * banca.getTaxa();
		if(sumaReala > this.fonduri) 
			return -1;
		else {
			this.fonduri -= sumaReala;
		}
		return suma;
	}
	
	/**
	* returneaza ok daca exista bani in cont
	* @pre true
	* @post @result <=> getSize() > 0
	* @post @nochange
	*/
	public double getBani() {
		return this.fonduri;
	}
	
	public String getNume() {
		return this.numeCont;
	}
}
