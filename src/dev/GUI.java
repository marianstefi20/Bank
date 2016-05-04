package dev;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

class GUI extends JFrame {
	// Variabila globala in care se salveaza directorul
	public static final String DIRNAME = "tmp/";
	JPanel mainPanel;
	JTable table;
	
	public GUI() {
		this.prepareGUI();
	}
	
	public void prepareGUI() {
		this.setTitle("Banca");
		this.setSize(1000, 600);
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		this.modelPanel(mainPanel);
		this.add(mainPanel);
	}
	
	public void modelPanel(JPanel panel) {
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        JLabel label1 = new JLabel();
        label1.setText("<html><p style='font-size:32px;text-align:center;font-family:Old English Text MT;font-weight:400'>Banca lui Marian</p></html>");        
        panel.add(label1, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 0.9;
   
        File imageCheck = new File("imagini/refresh-icon.png");

        if(imageCheck.exists()) 
            System.out.println("Am gasit refresh!");
        else 
            System.out.println("Image file not found!");
        
        JButton refresh = new JButton();
        refresh.setBorderPainted(false);
        refresh.setFocusPainted(false);
        refresh.setContentAreaFilled(false);
        try {
        	Image img = ImageIO.read(new FileInputStream("imagini/refresh-icon.png"));
        	refresh.setIcon(new ImageIcon(img));
        } catch(IOException e) {}
        refresh.setActionCommand("refreshPersoana");
        refresh.addActionListener(new Controller(this));
        panel.add(refresh, gbc);
        
        // Butonul de adauga comenzi
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 5;
        gbc.ipadx = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        JPanel fluid = new JPanel(new FlowLayout());
        JButton adaugaProduse = GUI.butonClasic("<html><p style='font-size:30px'>+</p></html>");
        try {
        	BufferedImage img = ImageIO.read(new FileInputStream("imagini/add.png"));
        	adaugaProduse.setIcon(new ImageIcon(img));
            adaugaProduse.setPreferredSize(new Dimension(img.getWidth()+2, img.getHeight()+2));
        } catch(IOException e) {}
        adaugaProduse.setActionCommand("adaugaClienti");
        adaugaProduse.addActionListener(new Controller(this));
        fluid.add(adaugaProduse, gbc);
        
        JButton editareProduse = GUI.butonClasic("<html><p style='font-size:30px;line-height: 0;padding:0;'>&curren;</p></html>");
        try {
        	BufferedImage img = ImageIO.read(new FileInputStream("imagini/edit.png"));
        	editareProduse.setIcon(new ImageIcon(img));
            editareProduse.setPreferredSize(new Dimension(img.getWidth()+2, img.getHeight()+2));
        } catch(IOException e) {}
        editareProduse.setActionCommand("editareClienti");
        editareProduse.addActionListener(new Controller(this));
        fluid.add(editareProduse);
        
        JButton stergereClienti = GUI.butonClasic("<html><p style='font-weight:600;font-size:20px;line-height: 0;padding:0;'>x</p></html>");
        try {
        	BufferedImage img = ImageIO.read(new FileInputStream("imagini/delete.png"));
        	stergereClienti.setIcon(new ImageIcon(img));
            stergereClienti.setPreferredSize(new Dimension(img.getWidth()+2, img.getHeight()+2));
        } catch(IOException e) {}
        stergereClienti.setActionCommand("stergereClienti");
        stergereClienti.addActionListener(new Controller(this));
        fluid.add(stergereClienti);
        
        panel.add(fluid, gbc);
        
        // Generatorul tabelei de clienti
        DefaultTableModel model = new DefaultTableModel();
        model = Serializable.generator("persoana", DIRNAME);
        table = new JTable(model);
        
        // Pentru actulizare model
        int sizeRow = model.getRowCount();
        int sizeCol = model.getColumnCount();
        String[] allCelule = new String[sizeCol];
        for(int i=0;i<sizeRow;i++) {
        	for(int j=0;j<sizeCol;j++) {
        	   allCelule[i] = (String) table.getModel().getValueAt(i, j);
        	}
        	//copac.put(allCelule, i);
        }
        
 		// Adaugarea tabelului la un Scrooling Pane
        JTabbedPane tabs = new JTabbedPane();
        JScrollPane scrollPane = new JScrollPane( table );
 		table.setPreferredSize(new Dimension(500, table.getRowHeight()*15+1));
 		scrollPane.setPreferredSize(table.getPreferredSize());
 		Dimension d = table.getPreferredSize();
 		gbc.fill = GridBagConstraints.HORIZONTAL;
 	    gbc.ipady = 0;   
 	    gbc.gridx = 0;
 	    gbc.gridy = 2;
 	    gbc.weightx = 1;
 	    tabs.addTab("Clienti", scrollPane);
 		panel.add( tabs, gbc);
 		
 		GridBagConstraints c = new GridBagConstraints();
 		c.gridx = 0;
 		c.gridy = 5;
 		c.fill = GridBagConstraints.BOTH;
 		c.weightx=1;
 		c.weighty=1;
 		c.gridwidth = 2;
 		panel.add(new JLabel(" "),c);
	}
	
	private static JButton butonClasic(String text) {
		JButton button = new JButton(text);
	    button.setForeground(Color.GRAY);
	    button.setBackground(Color.WHITE);
	    button.setOpaque(false);
	    //button.setContentAreaFilled(false);
	    button.setBorderPainted(false);
	    LineBorder line = new LineBorder(Color.WHITE);
	    EmptyBorder margin = new EmptyBorder(5, 15, 5, 15);
	    CompoundBorder compound = new CompoundBorder(line, margin);
	    button.setBorder(compound);
	    return button;
	}
	/**
	 * O metoda personala foarte generica ce cauta automat componentele in structa cu 
	 * TabbedPane. Odata gasite ele sunt revalidate si redesenate. In acest fel se 
	 * gaseste exact ceea ce trebuie modificat fara a reface intreg panoul
	 * */
	public void reimprospatare(String fileType) {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = layout.getConstraints(mainPanel);
		gbc.fill = GridBagConstraints.HORIZONTAL;
 	    gbc.ipady = 0;   
 	    gbc.gridx = 0;
 	    gbc.gridy = 2;
 	    gbc.weightx = 1;
 	    
 	    // Genereaza un model actualizat pe baza locului in care te afli
 		AbstractTableModel resultsModel = Serializable.generator(fileType, DIRNAME);
 	    
		// Se va seta noul model si cateva dimensiuni standard pe table + scrollPane
		table.setModel(resultsModel);
		int index = this.getIndexOfComp(mainPanel, "JTable");
		mainPanel.remove(index);
		mainPanel.add(table, gbc, index);
		mainPanel.revalidate();
		mainPanel.repaint();
 			
	}
	
	
	public DefaultTableModel furaModelul() {
		return (DefaultTableModel)table.getModel();
	}
	
	private int getIndexOfComp(JPanel panel, String compName) {
		Component[] components = panel.getComponents();
		int j = -1;
		for(int i =0; i<components.length; i++) {
			Component comp = components[i];
			if((comp.getClass().getSimpleName()).equals(compName)) {
				j = i;
				return j;
			}
		}
		return j;
	}
	public void showPanel(){ 
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.showPanel();
	}
}