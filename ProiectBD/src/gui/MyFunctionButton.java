package gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyFunctionButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	private boolean over;
	private Color c;

	public MyFunctionButton(String s, Color col, boolean round) {
		
		if(round == true) setBorder(new RoundedBorder(10));
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		c = col;
		setFocusable(false);
		setText(s);
		setBackground(col);
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
            	
                setBackground(Color.cyan);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
            	
                setBackground(c);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
            	
                setBackground(Color.blue);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            	
                if (over) {
                    setBackground(Color.cyan);
                } else {
                    setBackground(c);
                }
            }
		} );
	}

}
