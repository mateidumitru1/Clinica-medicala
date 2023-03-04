package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import date.Medicamente;
import db.DatabaseConnection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;

public class MedicamenteTab extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private DatabaseConnection db;
	private MyTable table;
	private JPanel mainPan = new JPanel();
	private JPanel statPan = new JPanel();
	
	public MedicamenteTab() throws SQLException {
		
		mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
		mainPan.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		JLabel statTitle = new JLabel("Medicamente");
		statTitle.setFont(new Font("", 1, 20));
		mainPan.add(statTitle);
		mainPan.add(Box.createRigidArea(new Dimension(10, 30)));
		
		mainPan.add(statPan);
		
		
		try {
			db = new DatabaseConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<Medicamente> p = db.selectMedicamente();
		
		String rowData[][] = new String[p.size()][3];
		for(int i = 0; i < p.size(); i++) {
			rowData[i][0] = String.valueOf(p.get(i).getId());
			rowData[i][1] = p.get(i).getDenumire();
			rowData[i][2] = p.get(i).getSubstantaActiva();
		}
		
		String columnNames[] = {"ID", "Denumire", "Substanta Activa"};
		
		
		table = new MyTable(rowData, columnNames); 
		
		statPan.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout(0, 0));
		statPan.add(new JScrollPane(table), BorderLayout.CENTER);
		
		add(mainPan);
		
	}

}
