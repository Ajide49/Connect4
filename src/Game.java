
//         Name: Ahmed Usman
//    StudentID: 1212843669
//  Description: Adds Board to JApplet and also sets the size

import java.awt.Color;

import javax.swing.*;

public class Game extends JApplet {
	private Board pb;

	public void init() {
		// create a Board object and add it to the Applet
		pb = new Board();
		getContentPane().add(pb);
		getContentPane().setBackground(Color.GREEN);

		// set Applet size to 700 x 600 it will be columns by rows
		setSize(700, 600);
	}

}
