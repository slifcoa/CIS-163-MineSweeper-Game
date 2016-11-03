package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * The MineSweeperPanel GUI has a 2-dimensional array of buttons, JButton.
 * The MineSweeperGame Model has a 2-dimensional array of cells, CellMS.
 * 
 * The caption of each button in the GUI is determined by the content of
 * the cell in the Model at the same RowColumn location.
 */
public class MineSweeperPanel extends JPanel {

	/**
	 * 
	 */

	private JButton[][] board;

	private int size;
	private MineSweeperGame game;
	private int wins;
	private int losses;
	private boolean exposeMines; // used to toggle mine locations on/off.

	private JLabel winsLabel;
	private JLabel lossesLabel;

	private JButton quitButton;
	private JButton minesButton;
	private JButton cellContentsButton;
	private JButton resetButton;// used purely for diagnostic purposes

	public MineSweeperPanel(int size) {
		this.size = size;

		/***** Due Friday, 12 February 2016 ******/
		// initialize 1) game to be an instance of MineSweeperGame, 2) wins to
		// 0, 3) losses to 0, and 4) exposeMines to false
		this.game = new MineSweeperGame(this.size);
		this.wins = 0;
		this.losses = 0;
		this.exposeMines = false;

		// Declare and create the center panel, set the panel layout,
		// and then add it to this extended JPanel at the BorderLayout.CENTER
		// region.
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(this.size, this.size));
		this.add(center, BorderLayout.CENTER);

		// Declare and instantiate mouseListener as a MyMouseListener
		MyMouseListener mouseListener = new MyMouseListener();

		// Create the board of JButtons, where each JButton is added to the
		// center panel
		this.createBoard(center, mouseListener);
		// Display the board GUI
		this.displayBoard();

		/***** Due Friday, 12 February 2016 ******/
		// Declare and create the bottom panel, set the panel layout to be 5
		// rows and 1 column,
		// and then add it to this extended JPanel at the BorderLayout.SOUTH
		// region.
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(5, 1));
		this.add(bottom, BorderLayout.SOUTH);

		/***** Due Friday, 12 February 2016 ******/
		// Declare and instantiate listener as a ButtonListener
		ButtonListener listener = new ButtonListener();

		// winsLabel
		this.winsLabel = new JLabel("Wins:   " + wins);
		bottom.add(this.winsLabel);

		/***** Due Friday, 12 February 2016 ******/
		// lossesLabel
		this.lossesLabel = new JLabel("Losses:  " + losses);
		bottom.add(this.lossesLabel);

		// quitButton
		this.quitButton = new JButton("Quit");
		this.quitButton.addActionListener(listener);
		bottom.add(this.quitButton);

		/***** Due Friday, 12 February 2016 ******/
		// minesButton
		this.minesButton = new JButton("Show Mines ");
		this.minesButton.addActionListener(listener);
		bottom.add(this.minesButton);

		/***** Due Friday, 12 February 2016 ******/
		// cellContentsButton
		this.cellContentsButton = new JButton("Cell Contents");
		this.cellContentsButton.addActionListener(listener);
		bottom.add(this.cellContentsButton);
		// New Game/reset button
		this.resetButton = new JButton("New Game");
		this.resetButton.addActionListener(listener);
		bottom.add(this.resetButton);

	}

	/***** Due Friday, 12 February 2016 ******/
	/*
	 * This method creates the 2-dimensional array of JButtons in rows and
	 * columns, equal to size.
	 * 
	 * This method then uses a nested for loop, over rows and columns, 1) to
	 * create a button with the parameter provided mouseListener, 2) place the
	 * button on the board at the row, column position, 3) and then add the
	 * button to the center panel.
	 */
	private void createBoard(JPanel center, MyMouseListener mouseListener) {
		JButton button;

		this.board = new JButton[this.size][this.size];

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				button = createButton(mouseListener);
				this.board[i][j] = button;
				center.add(button);
			}
		}
	}

	/***** Due Friday, 12 February 2016 ******/
	/*
	 * This method creates a button, specifies its size, adds the parameter
	 * provided mouseListener, and then returns the button.
	 */
	private JButton createButton(MyMouseListener mouseListener) {
		JButton b = new JButton("");
		b.setPreferredSize(new Dimension(45, 35));
		b.addMouseListener(mouseListener);
		return b;
	}

	/*
	 * This method uses a nested for loop, over rows and columns, to display the
	 * updated Board GUI array, by setting appropriately the text of each
	 * JButton in the board GUI according to the corresponding cell in the board
	 * Model. See the CellMS class toString methods.
	 * 
	 * Furthermore, with the JButton setEnable method, each JButton in the GUI
	 * is appropriately enabled if the cell in the Model is not yet exposed, or
	 * disabled if is exposed.
	 */
	private void displayBoard() {
		CellMS cell;
		JButton button;

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				cell = this.game.getCell(i, j);
				button = this.board[i][j];

				if (cell.isExposed()) {
					button.setEnabled(false);
				} else {
					button.setEnabled(true);
				}
				button.setText(cell.toString(this.exposeMines));
			}
		}

	}

	/***** Due Friday, 12 February 2016 ******/
	/*
	 * This method uses a nested for loop, over rows and columns, to display the
	 * Board GUI array, where each JButton in the board GUI shows the cell
	 * contents in the board Model.
	 * 
	 * See the CellMS class toString methods.
	 */
	private void displayBoardToShowCellContents() {
		CellMS cell;
		JButton button;
		// Runs through every button
		for (int r = 0; r < this.size; r++) {
			for (int c = 0; c < this.size; c++) {
				// get's the cell location
				cell = this.game.getCell(r, c);
				// get's the button location
				button = this.board[r][c];
				// if contents shown, hides them
				if (button.getText() == null) {
					button.setText(cell.toString());
					cellContentsButton.setText("Hide Cell Contents");
					// displays what each button contains
				} else {
					button.setText(null);
					cellContentsButton.setText("Show Cell Contents");

				}
			}
		}
	}

	// *********************************************************************
	// The following methods pertain to handle events.
	// *********************************************************************

	/***** Due Tuesday, 16 February 2016 ******/
	/*
	 * This method uses a nested for loop, over rows and columns, to locate
	 * which particular JButton on the board was the source of the mouse event.
	 * It creates and returns a RowColumn instance for that JButton location.
	 * 
	 * The method returns null by default.
	 */
	private RowColumn getRowColumnForEventSource(MouseEvent e) {
		RowColumn cell;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.board[i][j] == e.getSource()) {
					cell = new RowColumn(i, j);
					return cell;
				}
			}
		}
		return null;
	}

	/***** Due Tuesday, 16 February 2016 ******/
	/*
	 * This method: 1) invokes getRowColumnForEventSource to get a RowColumn
	 * instance that identifies which JButton on the board was the source of the
	 * mouse event. 2) calls the appropriate method in the game to select the
	 * corresponding cell in the game model 3) records changes to reflect the
	 * game status
	 * 
	 * 4) calls to display the Board GUI
	 */
	private void selectTheCell(MouseEvent e) {
		// After finding the row column for the click event, the select method
		// makes the changes to the cell and determines whether
		// or not the game is over
		this.game.select(getRowColumnForEventSource(e));
		
		this.wins = this.game.getWins();
		this.losses = this.game.getLosses();
		
		winsLabel.setText("Wins: " + wins);
		lossesLabel.setText("Losses: " + losses);
		

		this.displayBoard();
	}

	/***** Due Tuesday, 16 February 2016 ******/
	/*
	 * This method: 1) invokes getRowColumnForEventSource to get a RowColumn
	 * instance that identifies which JButton on the board was the source of the
	 * mouse event. 2) calls the appropriate method in the game to toggle the
	 * flag of the cell at the RowColumn position in the board model. 3) calls
	 * to display the Board GUI
	 * 
	 */
	private void toggleCellFlag(MouseEvent e) {

		// toggle the cells flag
		this.game.toggleFlag(getRowColumnForEventSource(e));

		// Display the board to show changes in the GUI
		displayBoard();
	}

	/***** Due Friday, 12 February 2016 ******/
	/*
	 * This method 1) toggles the value of the field, exposeMines 2) Changes the
	 * caption on the button, appropriately, to either "Hide   mines" or
	 * "Show mines" 3) calls to display the Board GUI
	 */
	private void toggleMineVisibility() {
		// switches the value of all mines
		this.exposeMines = !this.exposeMines;
		// if the value is true then change the label of the mines button to
		// "hide mines"
		if (exposeMines) {
			minesButton.setText("Hide Mines");
			// if the values false then change the label of the mines button to
			// "show mines"
		} else {
			minesButton.setText("Show Mines");
		}
		displayBoard();

	}

	private class MyMouseListener implements MouseListener {
		/*
		 * The following four methods need no further definition for this
		 * application
		 */
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) // identifies the left
														// button click
			{
				/***** Due Tuesday, 16 February 2016 ******/
				// invokes selectTheCell
				selectTheCell(e);
			} else if (e.getButton() == MouseEvent.BUTTON3) // identifies the
															// right click
			{
				/***** Due Tuesday, 16 February 2016 ******/
				// invokes toggleCellFlag
				toggleCellFlag(e);
			}
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (quitButton == e.getSource()) {
				System.exit(0);
			} else if (minesButton == e.getSource()) {
				/***** Due Friday, 12 February 2016 ******/
				// invokes toggleMineVisibility
				toggleMineVisibility();
			} else if (cellContentsButton == e.getSource()) {
				/***** Due Friday, 12 February 2016 ******/
				// invokes displayBoardToShowCellContents
				displayBoardToShowCellContents();
				// reset the game
			} else if (resetButton == e.getSource()) {
				game = new MineSweeperGame(size);
				displayBoard();
			}
		}

	}
}