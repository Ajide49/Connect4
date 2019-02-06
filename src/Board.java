import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

//Name: Ahmed Usman 
//StudentID: 1212843669
//Description: In the Board class, the Java GUI and tokens are drawn as well as the 2D array.
//				The players will be able to win the game, clear the board, and exit the JApplet after.

public class Board extends JPanel {
	// boolean variable will be used to display pop-up screen and clear board
	private boolean win, draw;
	// background image for the java GUI
	private BufferedImage background;
	// arraylist of the token
	private ArrayList<Token> tokens;
	// dimensions of the board
	// rows of the 2d array
	private int rows;
	private int columns;
	// array is columns by rows [y][x]
	private int[][] map;
	// records turns of game to get proper color and current currentPlayer
	private int turn;
	// currentPlayer of the game
	private String currentPlayer;
	private int tokenCount;

	public Board() {
		// initialize integer
		rows = 6;
		columns = 7;
		turn = 0;
		tokenCount = 0;

		// initialize currentplayer
		currentPlayer = " ";

		// initialize 2D array
		map = new int[columns][rows];
		// set boolean false
		win = false;
		draw = false;

		// initialize token to arraylist
		tokens = new ArrayList<Token>();

		try {
			// bufferedImage is read from source file in folder
			background = ImageIO.read(Board.class.getResource("/unnamed.png"));
		} // if image doesn't load properly
		catch (IOException e) {
			e.printStackTrace();
		}
		// add MouseListener to JApplet
		addMouseListener(new PointListener());

	}

	public void setToken(int color, int x) {
		// sanitize input, y will be placement of the token in row
		int y = -1;
		// set column equal to column array of map
		//System.out.println("x iss:" + x);
		int[] column = map[x];
		//System.out.println(Arrays.toString( column));
		// loop the length of the array and find the index where its not zero(where the
		// columnIndex(x) has been selected)
		for (int i = 0; i < column.length; i++) {
			if (column[i] != 0) {
				// set your y variable to column number - 1, it will set Y to the proper index
				// of array
				y = i - 1;
				// break out of the loop so it doesn't continue
				break;
			}
			// if the column number is the same as column length minus 1 (5) set y equal to
			// it
			// for tokens at the bottom of the board, we want it to be the last index
			if (i == column.length - 1) {
				y = i;
			}
		}
		/*System.out.println("after");
		System.out.println(Arrays.toString(column));
		System.out.println("y:" + y);*/
		// if the y does not equal -1, then set the token
		if (y != -1) {
			// make that position equal to color value of 1 or 2 will be used to check for
			// win
			map[x][y] = color;
			// add token
			tokens.add(new Token(color, x, y));
			// increase turn and count and repaint the board with new token
			turn++;
			tokenCount++;
			repaint();
		}

	}

	// this method gets x and y at index in hasWon
	public int getMapValue(int x, int y) {
		// if the x or y is out of bounds assume zero
		if (x < 0 || x >= columns && y < 0 || y >= rows)
			return 0;// assume an empty space
		// return x and y
		return map[x][y];
	}

	// checks if a player has won
	public boolean hasWon() {
		// I used 
		System.out.println("CurrentArray: " + Arrays.deepToString(map)); 
		// debug here
		// checks for horizontal win, looks through each rows for a combination of 3
		// that meets if statement
		// columns-3 because only 4 tokens are need to win (7-3)
		for (int row = 0; row < rows; row++) {

			for (int col = 0; col < columns - 3; col++) {

				// call getMapValue with col and row, array
				if (getMapValue(col, row) != 0 && getMapValue(col, row) == getMapValue(col + 1, row)
						&& getMapValue(col, row) == getMapValue(col + 2, row)
						&& getMapValue(col, row) == getMapValue(col + 3, row)) {
					win = true;
				}
			}
		}
		// check for vertical win
		for (int row = 0; row < rows; row++) {

			for (int col = 0; col < columns; col++) {

				if (getMapValue(col, row) != 0 && getMapValue(col, row) == getMapValue(col, row + 1)
						&& getMapValue(col, row) == getMapValue(col, row + 2)
						&& getMapValue(col, row) == getMapValue(col, row + 3)) {
					win = true;
				}
			}
		}
		// check for diagonal win to right
		for (int row = 0; row < rows; row++) {

			for (int col = 0; col < columns - 3; col++) {

				if (getMapValue(col, row) != 0 && getMapValue(col, row) == getMapValue(col + 1, row + 1)
						&& getMapValue(col, row) == getMapValue(col + 2, row + 2)
						&& getMapValue(col, row) == getMapValue(col + 3, row + 3)) {
					win = true;
				}
			}

		}
		// check for diagonal win to the left, must start at row 3 and go negative
		for (int row = 3; row < rows; row++) {

			for (int col = 0; col < columns - 3; col++) {

				if (getMapValue(col, row) != 0 && getMapValue(col, row) == getMapValue(col + 1, row - 1)
						&& getMapValue(col, row) == getMapValue(col + 2, row - 2)
						&& getMapValue(col, row) == getMapValue(col + 3, row - 3)) {
					win = true;
				}
			}
		}
		return win;
	}

	public void showWon() {
		// String that shows at top of JOptionPane window
		String winner = currentPlayer + " WINS!";
		// sets size of the JOptionPage
		UIManager.put("", new Dimension(500, 500));
		// shows who won and offers to play again or quit
		JLabel label = new JLabel(currentPlayer + " is the winner! Would you like to play again?");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		int n = JOptionPane.showConfirmDialog(null, label, winner, JOptionPane.YES_NO_OPTION);
		// if Yes is picked JApplet board will reset
		if (n < 1) {
			clearBoard();
		} else // the board will close
			System.exit(0);

	}

	public boolean hasDraw() {
		// if count is at max amount of tokens the game has been drawn
		if (tokenCount == 42)
			draw = true;

		return draw;

	}

	public void showDraw() {
		// String that shows at top of JOptionPane window
		String winner = "The game is a draw.";
		// sets size of the JOptionPage
		UIManager.put("", new Dimension(500, 500));
		// shows draw and offers to play again or quit
		JLabel label = new JLabel(" You guys are the real deal, nice draw! Would you like to play again?");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		int n = JOptionPane.showConfirmDialog(null, label, winner, JOptionPane.YES_NO_OPTION);
		// if Yes is picked JApplet board will reset
		if (n < 1) {
			clearBoard();
		} else // the board will close
			System.exit(0);
	}

	public void clearBoard() {
		// get rids of all tokens in the array list
		tokens.clear();
		// sets each index to zero
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 6; y++)
				map[x][y] = 0;
		} // repaints board and sets win and clear to false, turn and tokenCount to 0 as
			// well
		repaint();
		win = false;
		draw = false;
		turn = 0;
		tokenCount = 0;

	}

	public void paintComponent(Graphics g) {
		// paint the component
		super.paintComponent(g);
		// draws background
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		// both TokenWidth and TokenHeight are 100 and Japplet default size
		int tokenWidth = getWidth() / columns;
		int tokenHeight = getHeight() / rows;
		// draws the lines going horizontally on the board
		for (int i = 0; i < rows; i++)
			g.drawLine(0, i * tokenHeight, getWidth(), tokenHeight * i);
		for (int j = 0; j < columns; j++)
			// draws the lines going vertically on the board
			g.drawLine(tokenWidth * j, 0, tokenWidth * j, getHeight());
		// loops through arrayList to
		for (int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			// get color of token from the Token class
			g.setColor(t.getColor());
			// I used the println below to debug and fix my Token information
			// System.out.println("X: " + t.getX() + " W: " + tokenWidth);
			// System.out.println("Y: " + t.getY() + " H: " + tokenHeight);
			// draw the Token to the screen, t.getX() is the index of column, t.getY() is
			// the index of row
			g.fillOval(t.getX() * tokenWidth, t.getY() * tokenHeight, tokenWidth, tokenHeight);
		}
	}

	// this class describes where user clicks screen to
	public class PointListener implements MouseListener {

		public void mousePressed(MouseEvent event) {
			// X coordinate of mouse clicked
			int mouseX = event.getX();
			//System.out.println("info: " + mouseX);
			// tokenWidth is 100 at default JApplet size
			int tokenWidth = getWidth() / columns;
			// the column index is the column number or array index the user clicks
			// it is calculated by dividing the clicked X coordinate by 100 and rounding
			// down
			int column_Index = (int) Math.floor(mouseX / tokenWidth);
			//System.out.println("index: " + column_Index);
			// set color initialized to zero
			int color = 0;
			// if the turn is even then currentPlayer is player1 and color is 1(red)
			if (turn % 2 == 0) {
				currentPlayer = "Player 1";
				color = 1;
			}
			// if the turn is even then currentPlayer is player2 and color is 2(yellow)
			else {
				color = 2;
				currentPlayer = "Player 2";
			}
			// call SetToken, it takes in the color and column_index
			setToken(color, column_Index);
			// if hasWon is true call showWon
			if (hasWon()) {
				showWon();
			} // if the game has been drawn call showDraw
			if (hasDraw()) {
				showDraw();
			}
		}

		// unimplemented mouseClicked methods
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}
