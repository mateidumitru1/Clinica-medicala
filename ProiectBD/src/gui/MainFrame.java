package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JSplitPane containerPanel;
	private JPanel tabPanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	private StatisticiTab firstTabPanel;
	private PacientiTab secondTabPanel;
	private MediciTab thirdTabPanel;
	private TratamenteTab fourthTabPanel;
	private MedicamenteTab fifthTabPanel;
	
	private MyTabButton tabButton1 = new MyTabButton("Statistici");
	private MyTabButton tabButton2 = new MyTabButton("Pacienti");
	private MyTabButton tabButton3 = new MyTabButton("Medici");
	private MyTabButton tabButton4 = new MyTabButton("Tratamente");
	private MyTabButton tabButton5 = new MyTabButton("Medicamente");
	
	public MainFrame() {

		tabPanel.setLayout(new GridLayout(5,1, 0, 1));
		try {
			mainPanel.add(new StatisticiTab());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		tabButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		mainPanel.removeAll();
        		try {
					firstTabPanel = new StatisticiTab();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        		mainPanel.add(firstTabPanel);
        		mainPanel.repaint();
        		mainPanel.revalidate();
			}
		});
		
		tabButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		mainPanel.removeAll();
        		try {
					secondTabPanel = new PacientiTab();
				} catch (SQLException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
        		mainPanel.add(secondTabPanel);
        		mainPanel.repaint();
        		mainPanel.revalidate();
			}
		});
		
		tabButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		mainPanel.removeAll();
        		try {
					thirdTabPanel = new MediciTab();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        		mainPanel.add(thirdTabPanel);
        		mainPanel.repaint();
        		mainPanel.revalidate();
			}
		});
		tabButton4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		mainPanel.removeAll();
        		try {
					fourthTabPanel = new TratamenteTab();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		mainPanel.add(fourthTabPanel);
        		mainPanel.repaint();
        		mainPanel.revalidate();
			}
		});
		tabButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
        		mainPanel.removeAll();
        		try {
					fifthTabPanel = new MedicamenteTab();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        		mainPanel.add(fifthTabPanel);
        		mainPanel.repaint();
        		mainPanel.revalidate();
			}
		});
		

		tabPanel.add(tabButton1); 
		tabPanel.add(tabButton2);
		tabPanel.add(tabButton3);
		tabPanel.add(tabButton4);
		tabPanel.add(tabButton5);
		
		
		mainPanel.setLayout(new CardLayout());  
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
		mainPanel.setBackground(new Color(238, 238, 238));
		
		containerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabPanel, mainPanel);
		containerPanel.setDividerSize(0);
		containerPanel.setResizeWeight(0.1);
		add(containerPanel);
	
		StatusBar sb = new StatusBar();
		sb.getStatusText().setText("");
		
		add(sb, BorderLayout.SOUTH);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setTitle("Proiect");
		setMinimumSize(new Dimension(1705, 700));
		pack();
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
 	
	
}
