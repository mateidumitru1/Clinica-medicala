package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import date.Medicamente;
import date.Tratamente;
import date.TratamenteMedicamente;
import db.DatabaseConnection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

public class TratamenteTab extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private DatabaseConnection db;
	private MyTable table;
	private JPanel mainPan = new JPanel();
	private JPanel statPan = new JPanel();

	public TratamenteTab() throws SQLException {
		
		mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
		mainPan.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		statPan.setLayout(new BorderLayout());
		
		JLabel statTitle = new JLabel("Tratamente");
		statTitle.setFont(new Font("", 1, 20));
		mainPan.add(statTitle);
		mainPan.add(Box.createRigidArea(new Dimension(10, 30)));
		
		mainPan.add(statPan);
		
		getTratamente();
	}
	
	private void getTratamente() throws SQLException {
		
		try {
			db = new DatabaseConnection();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Nu s-a putut realiza conexiunea cu baza de date.");
		}
		ArrayList<Tratamente> t = db.selectTratamente();
		
		createTable(t);
	}
	
	private void createTable(ArrayList<Tratamente> t) throws SQLException {
		
		String rowData[][] = new String[t.size()][3];
		for(int i = 0; i < t.size(); i++) {
			rowData[i][0] = String.valueOf(t.get(i).getId());
			rowData[i][1] = t.get(i).getAfectiune();
			rowData[i][2] = db.getDenumireCategorie(t.get(i).getIdCategorie());
		}
		
		String columnNames[] = {"ID", "Afectiune", "Categorie Tratament"};
		
		
		table = new MyTable(rowData, columnNames);
		
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt) {
				
				if(evt.getClickCount() == 2) {
					
					JFrame detaliiTratamente = new JFrame();
					
					ArrayList<Object> ob = null;
					ArrayList<Medicamente> m = new ArrayList<Medicamente>();
					ArrayList<TratamenteMedicamente> tm = new ArrayList<TratamenteMedicamente>();
					
					try {
						ob = db.getMedicamente(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0)));
					} catch (NumberFormatException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					for(int i = 0; i < ob.size(); i = i + 2) {
						m.add((Medicamente) ob.get(i));
						tm.add((TratamenteMedicamente) ob.get(i + 1));
					}
					
					JPanel pan = new JPanel();
					pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
					
					String rowData[][] = new String[m.size()][6];
					for(int i = 0; i < m.size(); i++) {
						rowData[i][0] = String.valueOf(m.get(i).getId());
						rowData[i][1] = m.get(i).getDenumire();
						rowData[i][2] = m.get(i).getSubstantaActiva();
						rowData[i][3] = String.valueOf(tm.get(i).getPerioada());
						rowData[i][4] = String.valueOf(tm.get(i).getCantitate());
						rowData[i][5] = String.valueOf(tm.get(i).getIntervalOrar());
					}
					String columnNames[] = {"ID", "Denumire", "Substanta Activa", "Perioada", "Cantitate", "Interval Orar"};
					MyTable t = new MyTable(rowData, columnNames);
					
					JPanel container = new JPanel();
					JLabel title = new JLabel("Medicamente");
					title.setFont(new Font("", 1, 18));
					container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
					container.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
					container.add(title);
					container.add(Box.createVerticalStrut(10));
					container.add(pan);
					
					pan.add(new JScrollPane(t));
					
					JPanel legend = new JPanel();
					legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
					legend.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));
					legend.add(new JLabel("Perioada - numarul de zile de administrare zile;"));
					legend.add(new JLabel("Cantitate - numarul de comprimate/pufuri;"));
					legend.add(new JLabel("Interval orar - numarul de ore intre 2 administrari;"));
					pan.add(legend, BorderLayout.SOUTH);
					
					detaliiTratamente.setTitle((String)table.getValueAt(table.getSelectedRow(), 1));
					detaliiTratamente.add(container);
					detaliiTratamente.pack();
					detaliiTratamente.setLocationRelativeTo(null);
					detaliiTratamente.setVisible(true);
				}
			}
		});
		
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(table);
		statPan.add(scrollPane, BorderLayout.CENTER);
		add(mainPan);
		
	}
}
