package dev;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class Controller implements ActionListener {
	private GUI gui;
	
	public Controller(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * String-ul command contine cuvinte trimise din TabbedPane de catre ascultatori 
		 */
		String command = e.getActionCommand();
		switch(command) {
			case "adaugaClienti":
				mkClient();
				break;
			case "editareClienti":
				editareClient();
				break;
			case "stergereClienti":
				delClient();
				break;
			case "refreshPersoana":
				mkRefresh("persoana");
				break;
			default:
				break;
		}
	}
	
	private void mkRefresh(String whatTo) {
		gui.reimprospatare(whatTo);
	}
	
	private static void mkClient() {
		JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JTextField field3 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(new JLabel("Nume: "));
        panel.add(field1);
        panel.add(new JLabel("Email: "));
        panel.add(field2);
        panel.add(new JLabel("Telefon: "));
        panel.add(field3);
        int result = JOptionPane.showConfirmDialog(null, panel, "Adauga un client",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        	ArrayList<String> data = new ArrayList<String>();
        	data.add(field1.getText());
        	data.add(field2.getText());
        	data.add(field3.getText());
        	
        	Persoana nouClient = new Persoana(data);
            System.out.println("Un nou client a fost adaugat! \n");
        } else {
            System.out.println("Cancelled");
        }
	}
	
	/**
	 * Metoda editareClient() se uita prin model si cauta valori de la indexul corespunzator
	 * */
	private void editareClient() {
		DefaultTableModel model = Serializable.generator("persoana", "tmp/");
		ArrayList<String> items = new ArrayList<String>();
		int rowCount = model.getRowCount();
		System.out.println("Ajung pe aici si val. este " + rowCount +"\n");
		for(int i=0;i<rowCount;i++) {
			model.getValueAt(i, 2);
			items.add(model.getValueAt(i, 1) + ". Nume client: " + model.getValueAt(i, 2));
		}
		// Urmatoarele 3 linii de cod creeaza un array de string-uri
		String[] itemsArr = new String[items.size()];
		itemsArr = items.toArray(itemsArr);
        JComboBox<String> combo = new JComboBox<String>(itemsArr);
        
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JTextField field3 = new JTextField("");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(new JLabel("Alege client"));
        panel.add(combo);
        panel.add(new JLabel("Nume:"));
        panel.add(field1);
        panel.add(new JLabel("Email:"));
        panel.add(field2);
        panel.add(new JLabel("Telefon:"));
        panel.add(field3);
        int result = JOptionPane.showConfirmDialog(null, panel, "Editeaza un client",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        	ArrayList<String> data = new ArrayList<String>();
        	String buffer = combo.getSelectedItem().toString();
        	String temp[] = buffer.split("\\.");
        	if(!temp[0].isEmpty()) {
        		data.add(field1.getText());
            	data.add(field2.getText());
            	data.add(field3.getText());
            	int id = Integer.parseInt(temp[0]);
            	/* Serializarea reprezinta doar crearea unui nou obiect si salvarea acestuia
            	 * cu numele actualului fisier. In felul acesta se suprascriu si gata.*/
            	Persoana nouClient = new Persoana(data, id);
                System.out.println("Clientul a fost editat. \n");
        	}
        } else {
            System.out.println("Cancelled");
        }
	}
	
	private static void delClient() {
		DefaultTableModel model = Serializable.generator("persoana", "tmp/");
		ArrayList<String> items = new ArrayList<String>();
		int rowCount = model.getRowCount();
		for(int i=0;i<rowCount;i++) {
			model.getValueAt(i, 2);
			items.add(model.getValueAt(i, 1) + ". Nume client: " + model.getValueAt(i, 2));
		}
		// Urmatoarele 3 linii de cod creeaza un array de string-uri
		String[] itemsArr = new String[items.size()];
		itemsArr = items.toArray(itemsArr);
        JComboBox<String> combo = new JComboBox<String>(itemsArr);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setPreferredSize(new Dimension(400, 100));
        panel.add(new JLabel("Alege client"));
        panel.add(combo);
        panel.add(new JLabel("Esti absolut sigur ca vrei sa faci stergerea?"));
        int result = JOptionPane.showConfirmDialog(null, panel, "Sterge un client",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        	String buffer = combo.getSelectedItem().toString();
        	String temp[] = buffer.split("\\.");
        	if(!temp[0].isEmpty()) {
            	int id = Integer.parseInt(temp[0]);
            	try{
            		File file = new File("tmp/client"+id+".ser"); 
            		if(file.delete()){
            			System.out.println(file.getName() + " is deleted!");
            		}else{
            			System.out.println("Delete operation is failed.");
            		}
            	}catch(Exception e){
            		e.printStackTrace();
            	}
                System.out.println("Clientul a fost sters definitiv. \n");
        	}
        } else {
            System.out.println("Cancelled");
        }
	}
	
}