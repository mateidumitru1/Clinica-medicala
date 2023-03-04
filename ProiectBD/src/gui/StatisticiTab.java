package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import date.Medicamente;
import date.Medici;
import date.Pacienti;
import db.DatabaseConnection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class StatisticiTab extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private DatabaseConnection db;
	private JPanel mainPan = new JPanel();
	private JPanel statPan = new JPanel();
	
	

	
	public StatisticiTab() throws SQLException {
		
		try {
			db = new DatabaseConnection();
		} catch (ClassNotFoundException e1) {
			JOptionPane.showConfirmDialog(null, "Nu s-a putut realiza conexiunea cu baza de date.");
		}
		
		mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
		mainPan.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		JLabel statTitle = new JLabel("Statistici");
		statTitle.setFont(new Font("", 1, 20));
		mainPan.add(statTitle);
		mainPan.add(Box.createRigidArea(new Dimension(10, 30)));
		mainPan.add(new JSeparator());
		
		mainPan.add(statPan);
		
		statPan.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));

		statPan.add(createMostTreatedPacientStatisticsByOras());
		statPan.add(createMostActiveMedicStatisticsBySpecializare());
		statPan.add(createMostTreatedPacientStatistics());
		statPan.add(createMostActiveMedicStatistics());
		statPan.add(createTop3Medicamente());
		statPan.add(create0Medici());
		
		add(mainPan);
	}
	
	private Component create0Medici() {
		
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		
		MyFunctionButton topMedic = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Medici fara pacienti"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topMedic);
		
		topMedic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame statisticsFrame = new JFrame();
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Medici> m = null;
				try {
					m = db.get0Medici();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[m.size()][3];
				String[] columnNames = {"Nume", "Prenume", "Specializare"};
				
				for(int i = 0; i < m.size(); i++) {
					rowData[i][0] = m.get(i).getNume();
					rowData[i][1] = m.get(i).getPrenume();
					rowData[i][2] = m.get(i).getSpecializare();
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle("Cel mai activ medic");
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
		});
		return q;
	}

	private Component createTop3Medicamente() {
			
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		MyFunctionButton topMedicament = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Top 3 medicamente"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topMedicament);
		
		topMedicament.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame statisticsFrame = new JFrame();
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Medicamente> m = null;
				try {
					m = db.getTop3Medicamente();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[m.size()][2];
				String[] columnNames = {"Denumire", "Numar"};
				
				for(int i = 0; i < m.size(); i++) {
					rowData[i][0] = m.get(i).getDenumire();
					rowData[i][1] = String.valueOf(m.get(i).getId());
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle("Top 3 medicamente");
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
		});
		return q;
	}

	private Component createMostActiveMedicStatistics() {
		
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		
		MyFunctionButton topMedic = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Cel mai activ medic"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topMedic);
		
		topMedic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame statisticsFrame = new JFrame();
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Medici> m = null;
				try {
					m = db.getTopMedici();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[m.size()][4];
				String[] columnNames = {"Nume", "Prenume", "Specializare", "Numar rapoarte"};
				
				for(int i = 0; i < m.size(); i++) {
					rowData[i][0] = m.get(i).getNume();
					rowData[i][1] = m.get(i).getPrenume();
					rowData[i][2] = m.get(i).getSpecializare();
					rowData[i][3] = String.valueOf(m.get(i).getId());
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle("Cel mai activ medic");
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
		});
		return q;
	}

	private Component createMostTreatedPacientStatistics() {
		
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		
		
		MyFunctionButton topPacient = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Cel mai tratat pacient"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topPacient);
		
		topPacient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame statisticsFrame = new JFrame();
				
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Pacienti> p = null;
				try {
					p = db.getTopPacienti();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[p.size()][4];
				String[] columnNames = {"Nume", "Prenume", "Oras", "Numar rapoarte"};
				
				for(int i = 0; i < p.size(); i++) {
					rowData[i][0] = p.get(i).getNume();
					rowData[i][1] = p.get(i).getPrenume();
					rowData[i][2] = p.get(i).getOras();
					rowData[i][3] = String.valueOf(p.get(i).getId());
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle("Cel mai tratat pacient");
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
			
		});
		
		return q;
	}

	private JPanel createMostTreatedPacientStatisticsByOras() throws SQLException {
		
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		ArrayList<String> s = db.getOrase();
		String[] a = new String[s.size()];
		for(int i = 0; i < s.size(); i++)
			a[i] = s.get(i);
		
		JComboBox<String> cb = new JComboBox<String>(a);
		
		MyFunctionButton topPacient = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Cel mai tratat pacient"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(cb);
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topPacient);
		
		topPacient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame statisticsFrame = new JFrame();
				
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Pacienti> p = null;
				try {
					p = db.getTopPacientiByOras(String.valueOf(cb.getSelectedItem()));
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[p.size()][3];
				String[] columnNames = {"Nume", "Prenume", "Numar rapoarte"};
				
				for(int i = 0; i < p.size(); i++) {
					rowData[i][0] = p.get(i).getNume();
					rowData[i][1] = p.get(i).getPrenume();
					rowData[i][2] = String.valueOf(p.get(i).getId());
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle(String.valueOf(cb.getSelectedItem()));
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
			
		});
		
		return q;
	}

	private JPanel createMostActiveMedicStatisticsBySpecializare() throws SQLException {
		
		JPanel q = new JPanel();
		q.setLayout(new BoxLayout(q, BoxLayout.Y_AXIS));
		
		ArrayList<String> s = db.getSpecializari();
		String[] a = new String[s.size()];
		for(int i = 0; i < s.size(); i++)
			a[i] = s.get(i);
		
		JComboBox<String> cb = new JComboBox<String>(a);
		
		MyFunctionButton topMedic = new MyFunctionButton("Show", Color.white, true);
		
		q.add(new JLabel("Cel mai activ medic"));
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(cb);
		q.add(Box.createRigidArea(new Dimension(10, 10)));
		q.add(topMedic);
		
		topMedic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame statisticsFrame = new JFrame();
				JPanel statisticsPanel = new JPanel();
				statisticsPanel.setLayout(new BorderLayout());
				
				ArrayList<Medici> m = null;
				try {
					m = db.getTopMediciBySpecializare(String.valueOf(cb.getSelectedItem()));
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				String[][] rowData = new String[m.size()][3];
				String[] columnNames = {"Nume", "Prenume", "Numar rapoarte"};
				
				for(int i = 0; i < m.size(); i++) {
					rowData[i][0] = m.get(i).getNume();
					rowData[i][1] = m.get(i).getPrenume();
					rowData[i][2] = String.valueOf(m.get(i).getId());
				}
				
				MyTable statisticsTable = new MyTable(rowData, columnNames);
				
				statisticsPanel.add(new JScrollPane(statisticsTable));
				statisticsFrame.add(statisticsPanel);
				
				statisticsFrame.setTitle(String.valueOf(cb.getSelectedItem()));
				statisticsFrame.pack();
				statisticsFrame.setLocationRelativeTo(null);
				statisticsFrame.setVisible(true);
			}
		});
		return q;
	}
}