package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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


public class PacientiTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private DatabaseConnection db;
	private JPanel mainPan = new JPanel();
	private JPanel statPan = new JPanel();
	private MyTable table;
	private MyFunctionButton addButton = new MyFunctionButton("Adauga", Color.green, false);
	private MyFunctionButton deleteButton = new MyFunctionButton("Sterge", Color.magenta, false);
	private MyFunctionButton searchButton = new MyFunctionButton("Cauta", Color.pink, false);
	private MyFunctionButton editButton = new MyFunctionButton("Modifica", Color.orange, false);
	Pacienti pacient;
	
	
	public PacientiTab() throws SQLException, ClassNotFoundException {
		
		mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
		mainPan.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		JLabel statTitle = new JLabel("Pacienti");
		statTitle.setFont(new Font("", 1, 20));
		mainPan.add(statTitle);
		mainPan.add(Box.createRigidArea(new Dimension(10, 30)));
		mainPan.add(new JSeparator());

		
		mainPan.add(statPan);
		
		getPacienti();
		createAddButton();
		createDeleteButton();
		createSearchButton();
		createEditButton();
		
	}
	
	private void getPacienti() throws SQLException {

		try {
			db = new DatabaseConnection();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Nu s-a putut realiza conexiunea cu baza de date.");
		}

		ArrayList<Pacienti> p = db.selectPacienti();
		
		createTable(p);
	}

	private void createTable(ArrayList<Pacienti> p) {
		
		statPan.setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.add(addButton);
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(searchButton);
		buttonsPanel.add(editButton);
		statPan.add(buttonsPanel, BorderLayout.NORTH);
		
		String rowData[][] = new String[p.size()][10];
		for(int i = 0; i < p.size(); i++) {
			rowData[i][0] = String.valueOf(p.get(i).getId());
			rowData[i][1] = p.get(i).getNume();
			rowData[i][2] = p.get(i).getPrenume();
			rowData[i][3] = p.get(i).getCnp();
			rowData[i][4] = p.get(i).getSex();
			rowData[i][5] = String.valueOf(p.get(i).getVarsta());
			rowData[i][6] = String.valueOf(p.get(i).getGreutate());
			rowData[i][7] = p.get(i).getOras();
			rowData[i][8] = p.get(i).getStrada();
			rowData[i][9] = String.valueOf(p.get(i).getNumar());
		}
		
		String columnNames[] = {"ID", "Nume", "Prenume", "CNP", "Sex", "Varsta", "Greutate", "Oras", "Strada", "Numar"};
		
		table = new MyTable(rowData, columnNames);
		
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt) {
				
				if(evt.getClickCount() == 2) {
					
					pacient = new Pacienti(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0)), (String) table.getValueAt(table.getSelectedRow(), 1),
					(String) table.getValueAt(table.getSelectedRow(), 2), (String) table.getValueAt(table.getSelectedRow(), 3), (String) table.getValueAt(table.getSelectedRow(), 4),
					Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 5)), Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 6)), (String) 
					table.getValueAt(table.getSelectedRow(), 7), (String) table.getValueAt(table.getSelectedRow(), 8), Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 5)));

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
		statPan.add(scrollPane);
		
		setLayout(new BorderLayout(0, 0));
			
		add(mainPan);
	}
	
	private void createDetailsPanel() throws SQLException {
		
		JLabel title = new JLabel("Medicii lui " + pacient.getNume() + " " + pacient.getPrenume());
		title.setFont(new Font("", 1, 20));

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 00));
		
		titlePanel.add(title);
		statPan.setLayout(new BoxLayout(statPan, BoxLayout.Y_AXIS));
		statPan.add(titlePanel);
		
		ArrayList<Medici> medic = db.getMedici(pacient.getId());
		ArrayList<String> s = db.getTratamentebyPacient(pacient.getId());
		
		JPanel mediciPanel = new JPanel();
		mediciPanel.setLayout(new BorderLayout());
		
		String columnNames[] = {"Nume Medic", "Prenume", "Specializare", "Diagnostic", "Tratament", "Data", "Observatii"};
		
		int j = 0;
		
		String[][] rowData = new String[medic.size()][7];
		for(int i = 0; i < medic.size(); i++) {
			rowData[i][0] = medic.get(i).getNume();
			rowData[i][1] = medic.get(i).getPrenume();
			rowData[i][2] = medic.get(i).getSpecializare();
			rowData[i][3] = s.get(j);
			rowData[i][4] = s.get(++j);
			rowData[i][5] = s.get(++j);
			rowData[i][6] = s.get(++j);
			j = j + 2;
		}
		
		MyTable mediciTable = new MyTable(rowData, columnNames);
		mediciTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt) {
				
				if(evt.getClickCount() == 2) {
					
					JFrame detaliiTratamente = new JFrame();
					
					ArrayList<Object> ob = null;
					ArrayList<Medicamente> m = new ArrayList<Medicamente>();
					ArrayList<TratamenteMedicamente> tm = new ArrayList<TratamenteMedicamente>();
					
					try {

						ob = db.getMedicamente(pacient.getId(), medic.get(mediciTable.getSelectedRow()).getId(), Integer.parseInt(s.get((mediciTable.getSelectedRow() + 1) * 5 - 1)));
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
					
					detaliiTratamente.setTitle((String)mediciTable.getValueAt(mediciTable.getSelectedRow(), 3));
					detaliiTratamente.add(container);
					detaliiTratamente.pack();
					detaliiTratamente.setLocationRelativeTo(null);
					detaliiTratamente.setVisible(true);
				}
			}
		});
		
		mediciPanel.add(new JScrollPane(mediciTable));
		statPan.add(mediciPanel);
		
	}

	private void updateTable() throws SQLException, ClassNotFoundException {
		
		removeAll();
		statPan.removeAll();
		getPacienti();
		
		repaint();
		revalidate();
	}
	
	private void createAddButton() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame dialog = new JFrame();
				String s[] = {"M", "F"};
				JPanel inputPanel = new JPanel();
				JLabel numel = new JLabel("Nume*: ");
				JTextField numet = new JTextField(30);
				JLabel prenumel = new JLabel("Prenume*: ");
				JTextField prenumet = new JTextField(30);
				JLabel CNPl = new JLabel("CNP*: ");
				JTextField CNPt = new JTextField(13);
				JLabel sexl = new JLabel("Sex*: ");
				JComboBox<String> sext = new JComboBox<>(s);
				JLabel varstal = new JLabel("Varsta*: ");
				JTextField varstat = new JTextField(4);
				JLabel greutatel = new JLabel("Greutate*: ");
				JTextField greutatet = new JTextField(4);
				JLabel orasl = new JLabel("Oras: ");
				JTextField orast = new JTextField(30);
				JLabel stradal = new JLabel("Strada: ");
				JTextField stradat = new JTextField(30);
				JLabel numarl = new JLabel("Numar: ");
				JTextField numart = new JTextField(4);
				
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
				
				JPanel sexp = new JPanel();
				sexp.add(sexl);
				sexp.add(sext);
				
				JPanel varstap = new JPanel();
				varstap.add(varstal);
				varstap.add(varstat);
				
				JPanel greutatep = new JPanel();
				greutatep.add(greutatel);
				greutatep.add(greutatet);
				
				JPanel orasp = new JPanel();
				orasp.add(orasl);
				orasp.add(orast);
				
				JPanel stradap = new JPanel();
				stradap.add(stradal);
				stradap.add(stradat);
				
				JPanel numarp = new JPanel();
				numarp.add(numarl);
				numarp.add(numart);
				
				inputPanel.add(numep);
				inputPanel.add(prenumep);
				inputPanel.add(cnpp);
				
				inputPanel.add(sexp);
				inputPanel.add(varstap);
				inputPanel.add(greutatep);
				inputPanel.add(orasp);
				inputPanel.add(stradap);
				inputPanel.add(numarp);
				
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						if ( numet.getText().isEmpty() || prenumet.getText().isEmpty() || CNPt.getText().isEmpty() || varstat.getText().isEmpty() || 
							 greutatet.getText().isEmpty() || orast.getText().isEmpty() || stradat.getText().isEmpty() || numart.getText().isEmpty() ) {
							
							JOptionPane.showMessageDialog(null, "Trebuie completate toate campurile!");
							return;
						}
						else if(CNPt.getText().length() != 13 || !CNPt.getText().matches("[0-9]+")) {
							
							JOptionPane.showMessageDialog(null, "CNP-ul trebuie sa aiba exact 13 cifre!");
							return;
						}
						else if(!varstat.getText().matches("[0-9]+")) {
							
							JOptionPane.showMessageDialog(null, "Varsta trebuie sa fie un numar!");
							return;
						}
						else if(!greutatet.getText().matches("[0-9]+")) {
							
							JOptionPane.showMessageDialog(null, "Greutatea trebuie sa fie un numar!");
							return;
						}
						
						pacient = new Pacienti(numet.getText(), prenumet.getText(), CNPt.getText(), String.valueOf(sext.getSelectedItem()), Integer.valueOf(varstat.getText()),
								Integer.valueOf(greutatet.getText()), orast.getText(), stradat.getText(), Integer.valueOf(numart.getText()));
							
						try {
								
							try {
								if (db.insertPacient(pacient) == 1) {
									
									JOptionPane.showMessageDialog(null, "Pacientul a fost adaugat cu succes!");
									updateTable();
								}
								else JOptionPane.showMessageDialog(null, "Pacientul nu a putut fi adaugat!");
							} catch (HeadlessException | ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dialog.dispose();
							
						} catch (SQLException e1) {
								
							JOptionPane.showMessageDialog(null, "Pacientul nu a putut fi adaugat!");
						}	
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
				
				dialog.setResizable(false);
				dialog.setTitle("Adauga Pacient");
				dialog.add(inputPanel);
				dialog.setMinimumSize(new Dimension(600, 600));
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
							db.deletePacient(table.getValueAt(table.getSelectedRow(), 0));
							JOptionPane.showMessageDialog(null, "Pacientul a fost sters cu succes!");
							
							updateTable();
						}
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Trebuie sa selectati pacientul pe care doriti sa il stergeti.");
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
					
					ArrayList<Pacienti> p = db.searchPacient(in);
					
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
					JOptionPane.showMessageDialog(null, "Trebuie sa selectati pacientul pe care doriti sa il modificati.");
					return;
				}
				
				JFrame editBox = new JFrame();
				
				String columnNames[] = {"Nume", "Prenume", "CNP", "Sex", "Varsta", "Greutate", "Oras", "Strada", "Numar"};
				String[][] rowData = new String[2][9];
				
				rowData[0][0] = (String)table.getValueAt(table.getSelectedRow(), 1);
				rowData[0][1] = (String)table.getValueAt(table.getSelectedRow(), 2);
				rowData[0][2] = (String)table.getValueAt(table.getSelectedRow(), 3);
				rowData[0][3] = (String)table.getValueAt(table.getSelectedRow(), 4);
				rowData[0][4] = (String)table.getValueAt(table.getSelectedRow(), 5);
				rowData[0][5] = (String)table.getValueAt(table.getSelectedRow(), 6);
				rowData[0][6] = (String)table.getValueAt(table.getSelectedRow(), 7);
				rowData[0][7] = (String)table.getValueAt(table.getSelectedRow(), 8);
				rowData[0][8] = (String)table.getValueAt(table.getSelectedRow(), 9);
				
				
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
				
				
				pacient = new Pacienti();
				
				
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							
							
							pacient.setId(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0)));
							
							if(editTable.getValueAt(1, 0) != null) pacient.setNume((String)editTable.getValueAt(1, 0));
							else pacient.setNume((String)editTable.getValueAt(0, 0));
							
							if(editTable.getValueAt(1, 1) != null) pacient.setPrenume((String)editTable.getValueAt(1, 1));
							else pacient.setPrenume((String)editTable.getValueAt(0, 1));
							
							if(editTable.getValueAt(1, 2) != null) pacient.setCnp((String)editTable.getValueAt(1, 2));
							else pacient.setCnp((String)editTable.getValueAt(0, 2));
							
							if(editTable.getValueAt(1, 3) != null) pacient.setSex((String)editTable.getValueAt(1, 3));
							else pacient.setSex((String)editTable.getValueAt(0, 3));
							
							if(editTable.getValueAt(1, 4) != null) pacient.setVarsta(Integer.parseInt((String)editTable.getValueAt(1, 4)));
							else pacient.setVarsta(Integer.parseInt((String)editTable.getValueAt(0, 4)));
							
							if(editTable.getValueAt(1, 5) != null) pacient.setGreutate(Integer.parseInt((String)editTable.getValueAt(1, 5)));
							else pacient.setGreutate(Integer.parseInt((String)editTable.getValueAt(0, 5)));
							
							if(editTable.getValueAt(1, 6) != null) pacient.setOras((String)editTable.getValueAt(1, 6));
							else pacient.setOras((String)editTable.getValueAt(0, 6));
							
							if(editTable.getValueAt(1, 7) != null) pacient.setStrada((String)editTable.getValueAt(1, 7));
							else pacient.setStrada((String)editTable.getValueAt(0, 7));
							
							if(editTable.getValueAt(1, 8) != null) pacient.setNumar(Integer.parseInt((String)editTable.getValueAt(1, 8)));
							else pacient.setNumar(Integer.parseInt((String)editTable.getValueAt(0, 8)));
							
							db.editPacient(pacient);
							JOptionPane.showMessageDialog(null, "Pacientul a fost modificat cu succes.");
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
				editBox.setTitle("Modifica Pacient");
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
