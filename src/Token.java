import java.awt.Color;
//Name: Ahmed Usman
//StudentID: 1212843669
//Description: Defines a Token object

public class Token {
	// private variables of a token object
	private Color color;
	private int x;
	private int y;

	public Token(int type, int x, int y) {
		// determines the color of token
		if (type == 1) {
			this.color = Color.RED;
		} else {
			this.color = Color.YELLOW;
		}
		// set x and y to parameters
		this.x = x;
		this.y = y;
	}

	// accessor methods to get color and X or Y coordinate
	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
