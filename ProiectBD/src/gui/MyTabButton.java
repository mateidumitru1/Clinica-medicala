package gui;

import java.awt.Color;

import javax.swing.JButton;

public class MyTabButton extends JButton {

	private static final long serialVersionUID = 1L;
	public MyTabButton(String s) {
		
		super(s);
		setBorder(new RoundedBorder(5));
		setBackground(new Color(216, 243, 105));
		setForeground(Color.DARK_GRAY);
		setOpaque(true);
		setFocusable(false);
	}
}
