package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import date.Medicamente;
import date.Medici;
import date.Pacienti;
import date.TratamenteMedicamente;
import db.DatabaseConnection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class MediciTab extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatabaseConnection db;
	private MyTable table;
	private JPanel statPan = new JPanel();
	private JPanel mainPan = new JPanel();
	private MyFunctionButton addButton = new MyFunctionButton("Adauga", Color.green, false);
	private MyFunctionButton deleteButton = new MyFunctionButton("Sterge", Color.magenta, false);
	private MyFunctionButton searchButton = new MyFunctionButton("Cauta", Color.pink, false);
	private MyFunctionButton editButton = new MyFunctionButton("Modifica", Color.orange, false);
	private Medici medic;
	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public MediciTab() throws SQLException {
		
		mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
		mainPan.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		JLabel statTitle = new JLabel("Medici");
		statTitle.setFont(new Font("", 1, 20));
		mainPan.add(statTitle);
		mainPan.add(Box.createRigidArea(new Dimension(10, 30)));
		mainPan.add(new JSeparator());

		
		mainPan.add(statPan);
		
		getMedici();
		createAddButton();
		createDeleteButton();
		createSearchButton();
		createEditButton();
	}

	public void getMedici() throws SQLException {
		
		try {
			db = new DatabaseConnection();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Nu s-a putut realiza conexiunea cu baza de date.");
		}
		ArrayList<Medici> p = db.selectMedici();
		
		createTable(p);
	}
	
	private void createTable(ArrayList<Medici> p) {
		
		statPan.setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.add(addButton);
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(searchButton);
		buttonsPanel.add(editButton);
		statPan.add(buttonsPanel, BorderLayout.BEFORE_FIRST_LINE);
		
		String rowData[][] = new String[p.size()][5];
		for(int i = 0; i < p.size(); i++) {
			rowData[i][0] = String.valueOf(p.get(i).getId());
			rowData[i][1] = p.get(i).getNume();
			rowData[i][2] = p.get(i).getPrenume();
			rowData[i][3] = p.get(i).getCnp();
			rowData[i][4] = p.get(i).getSpecializare();
		}
		
		String columnNames[] = {"ID", "Nume", "Prenume", "CNP", "Specializare"};
		setLayout(new BorderLayout(0, 0));

		
		table = new MyTable(rowData, columnNames);
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt) {
				
				if(evt.getClickCount() == 2) {
					
					medic = new Medici(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0)), (String) table.getValueAt(table.getSelectedRow(), 1),
					(String) table.getValueAt(table.getSelectedRow(), 2), (String) table.getValueAt(table.getSelectedRow(), 3), (String) table.getValueAt(table.getSelectedRow(), 4));

					removeAll();
					statPan.removeAll();
					
					try {
						createDetailsPanel();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					add(statPan);
					repaint();
					revalidate();
				}
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		statPan.add(scrollPane, BorderLayout.CENTER);
		add(mainPan);
	}
	
		protected void createDetailsPanel() throws SQLException {
			
			JLabel title = new JLabel("Pacientii lui " + medic.getNume() + " " + medic.getPrenume());
			title.setFont(new Font("", 1, 20));

			JPanel titlePanel = new JPanel();
			titlePanel.setLayout(new FlowLayout());
			titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 00));
			
			titlePanel.add(title);
			statPan.setLayout(new BoxLayout(statPan, BoxLayout.Y_AXIS));
			statPan.add(titlePanel);
			
			ArrayList<Pacienti> pacienti = db.getPacienti(medic.getId());
			ArrayList<String> s = db.getTratamentebyMedic(medic.getId());
			
			JPanel mediciPanel = new JPanel();
			mediciPanel.setLayout(new BorderLayout());
			
			String columnNames[] = {"Nume Pacient", "Prenume", "Diagnostic", "Tratament", "Data", "Observatii"};
			
			int j = 0;
			
			String[][] rowData = new String[pacienti.size()][6];
			for(int i = 0; i < pacienti.size(); i++) {
				rowData[i][0] = pacienti.get(i).getNume();
				rowData[i][1] = pacienti.get(i).getPrenume();
				rowData[i][2] = s.get(j);
				rowData[i][3] = s.get(++j);
				rowData[i][4] = s.get(++j);
				rowData[i][5] = s.get(++j);
				j = j + 2;
			}
			
			MyTable pacientiTable = new MyTable(rowData, columnNames);
			pacientiTable.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent evt) {
					
					if(evt.getClickCount() == 2) {
						
						JFrame detaliiTratamente = new JFrame();
						
						ArrayList<Object> ob = null;
						ArrayList<Medicamente> m = new ArrayList<Medicamente>();
						ArrayList<TratamenteMedicamente> tm = new ArrayList<TratamenteMedicamente>();
						
						try {

							ob = db.getMedicamente(pacienti.get(pacientiTable.getSelectedRow()).getId(), medic.getId(), Integer.parseInt(s.get((pacientiTable.getSelectedRow() + 1) * 5 - 1)));
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
						
						detaliiTratamente.setTitle((String)pacientiTable.getValueAt(pacientiTable.getSelectedRow(), 0) + " " + (String)pacientiTable.getValueAt(pacientiTable.getSelectedRow(), 1));
						detaliiTratamente.add(container);
						detaliiTratamente.pack();
						detaliiTratamente.setLocationRelativeTo(null);
						detaliiTratamente.setVisible(true);
					}
				}
			});
			
			mediciPanel.add(new JScrollPane(pacientiTable));
			statPan.add(mediciPanel);
			
		
	}

		private void updateTable() throws SQLException, ClassNotFoundException {
		
		removeAll();
		statPan.removeAll();
		getMedici();
		
		repaint();
		revalidate();
	}
	
	private void createAddButton() {

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame dialog = new JFrame();
				JPanel inputPanel = new JPanel();
				JLabel numel = new JLabel("Nume: ");
				JTextField numet = new JTextField(30);
				JLabel prenumel = new JLabel("Prenume: ");
				JTextField prenumet = new JTextField(30);
				JLabel CNPl = new JLabel("CNP: ");
				JTextField CNPt = new JTextField(13);
				JLabel specializarel = new JLabel("Specializare: ");
				JTextField specializaret = new JTextField(30);
				
				inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
				inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				
				JPanel numep = new JPanel();
				numep.add(numel);
				numep.add(numet);
				
				JPanel prenumep = new JPanel();
				prenumep.add(prenumel);
				prenumep.add(prenumet);
				
				JPanel cnpp = new JPanel();
				cnpp.add(CNPl);
				cnpp.add(CNPt);
				
				JPanel specializarep = new JPanel();
				specializarep.add(specializarel);
				specializarep.add(specializaret);
				
				inputPanel.add(numep);
				inputPanel.add(prenumep);
				inputPanel.add(cnpp);
				inputPanel.add(specializarep);
				
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {

						if( numet.getText().isEmpty() || prenumet.getText().isEmpty() || CNPt.getText().isEmpty() || specializaret.getText().isEmpty() ) {
							
							JOptionPane.showMessageDialog(null, "Trebuie completate toate campurile!");
							return;
						}
						
						else if(CNPt.getText().length() != 13 || !CNPt.getText().matches("[0-9]+")) {
							
							JOptionPane.showMessageDialog(null, "CNP-ul trebuie sa aiba exact 13 cifre!");
							return;
						}
						
						Medici medic = new Medici(numet.getText(), prenumet.getText(), CNPt.getText(), specializaret.getText());
						try {
							if(db.insertMedic(medic) == 1) {
								
								JOptionPane.showMessageDialog(null, "Medicul a fost adaugat cu succes.");
								updateTable();
							}
						} catch (HeadlessException | SQLException | ClassNotFoundException e1) {
							JOptionPane.showMessageDialog(null, "Medicul nu a putut fi adaugat!");
						}
						dialog.dispose();
					}
				});
				
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						
					}
					
				});
				JPanel buttonsPanel = new JPanel();
				buttonsPanel.add(okButton);
				buttonsPanel.add(cancelButton);
				buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));
				buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
				
				inputPanel.add(buttonsPanel);
				
				dialog.setTitle("Adauga Medic");
				dialog.add(inputPanel);
				dialog.setMaximumSize(new Dimension(400, 400));
				dialog.setResizable(false);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}	
		});
	}
	
	private void createDeleteButton() {
		
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(!table.getSelectionModel().isSelectionEmpty()) {
						int r = JOptionPane.showConfirmDialog(new JFrame(), "Sunteti sigur?", "", JOptionPane.YES_NO_OPTION);
						if(r == JOptionPane.YES_OPTION) {
							db.deleteMedic(table.getValueAt(table.getSelectedRow(), 0));
							JOptionPane.showMessageDialog(null, "Medicul a fost sters cu succes!");
							
							updateTable();
						}
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Trebuie sa selectati medicul pe care doriti sa il stergeti.");
						return;
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			
			}
			
		});
	}
	
	private void createSearchButton() {
		
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					String in = JOptionPane.showInputDialog("Nume");
					
					ArrayList<Medici> p = db.searchMedic(in);
					
					if(in == null) return;
					
					if(p.isEmpty()) JOptionPane.showMessageDialog(null, "Pacientul nu exista.");
					else {
						
						removeAll();
						statPan.removeAll();
						
						createTable(p);
						
						repaint();
						revalidate();
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	private void createEditButton() {
		
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Trebuie sa selectati medicul pe care doriti sa il modificati.");
					return;
				}
				
				JFrame editBox = new JFrame();
				
				String columnNames[] = {"Nume", "Prenume", "CNP", "Specializare"};
				String[][] rowData = new String[2][9];
				
				rowData[0][0] = (String)table.getValueAt(table.getSelectedRow(), 1);
				rowData[0][1] = (String)table.getValueAt(table.getSelectedRow(), 2);
				rowData[0][2] = (String)table.getValueAt(table.getSelectedRow(), 3);
				rowData[0][3] = (String)table.getValueAt(table.getSelectedRow(), 4);
				
				
				MyTable editTable = new MyTable(rowData, columnNames) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						
						return row == 1;
					}
					
				};
				
				
				
				Medici medic = new Medici();
				
				
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							
							
							medic.setId(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0)));
							
							if(editTable.getValueAt(1, 0) != null) medic.setNume((String)editTable.getValueAt(1, 0));
							else medic.setNume((String)editTable.getValueAt(0, 0));
							
							if(editTable.getValueAt(1, 1) != null) medic.setPrenume((String)editTable.getValueAt(1, 1));
							else medic.setPrenume((String)editTable.getValueAt(0, 1));
							
							if(editTable.getValueAt(1, 2) != null) medic.setCnp((String)editTable.getValueAt(1, 2));
							else medic.setCnp((String)editTable.getValueAt(0, 2));
							
							if(editTable.getValueAt(1, 3) != null) medic.setSpecializare((String)editTable.getValueAt(1, 3));
							else medic.setSpecializare((String)editTable.getValueAt(0, 3));
							
							db.editMedic(medic);
							JOptionPane.showMessageDialog(null, "Medicul a fost modificat cu succes.");
							updateTable();
							editBox.dispose();
							
						} catch (SQLException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				});
				
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						editBox.dispose();
					}
					
				});
				
				JPanel temp = new JPanel();
				temp.setLayout(new BorderLayout());
				
				JPanel buttonsPanel = new JPanel();
				buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 200));
				buttonsPanel.setLayout(new GridLayout(1, 2, 100, 0));
				buttonsPanel.add(ok);
				buttonsPanel.add(cancel);

				temp.add(new JScrollPane(editTable));
				editBox.setTitle("Modifica Medic");
				editBox.add(temp, BorderLayout.CENTER);
				editBox.add(buttonsPanel, BorderLayout.SOUTH);
				editBox.setMinimumSize(new Dimension(703, 400));
				editBox.setResizable(false);
				editBox.pack();
				editBox.setLocationRelativeTo(null);
				editBox.setVisible(true);
			}
		});
	}
}
