package project2;

import java.util.*;

import javax.swing.JOptionPane;
//import javax.tools.JavaFileManager.Location;

import dialogBoxes.Dialog163;

public class MineSweeperGame {
	private CellMS[][] board;
	/** represents the board */

	private GameStatus status;
	/** GameStatus.win, lose, or notOverYet */

	private int size;
	/** size of the board */

	// private ArrayList<RowColumn> mineFieldMap;

	private int wins;
	/** number of times player wins */

	private int losses;
	/** number of times player loses */

	private static int suggestedMineCount = 1;

	public MineSweeperGame(int size) {
		this.size = size;
		this.reset();
	}

	public void reset() {
		// resets the game status
		this.status = GameStatus.notOverYet;
		// create's the board if needed or clear's the board
		if (this.board == null) {
			this.createBoard();
		} else {
			this.clearBoard();
		}
		// lay's down the mines when the game starts/restarts
		this.layMines();
	}

	/*
	 * 1) Creates the board field, as a two dimensional array. 2) Populates
	 * board, in a nested for loop, for the rows and columns, where each
	 * board[row][column] is assigned a reference to a new CelMS.
	 */
	private void createBoard() {
		// set's the board to the requested size
		this.board = new CellMS[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.board[i][j] = new CellMS();
			}
		}
	}

	/*
	 * Clears each mine sweeper cell, of CellMS class type, in the board. You
	 * should have a "clear" method in your Cell class.
	 */
	private void clearBoard() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.board[i][j].clear();
			}
		}
	}

	/*
	 * Complete the following method with a for loop, that iterates minesToLay
	 * times, to 1) identify a random cell (i.e. random row and random column)
	 * 2) if board[row][column], a CellMS reference, does not have a land mine
	 * A) lay a land mine on that cell (See the Cell class methods) B) increment
	 * the neighborhood mine counts
	 * 
	 */
	private void layMines() {

		Random random = new Random();

		int minesToLay = Dialog163.getInteger("Please enter number of mines", "MineSweeperGame",
				"" + suggestedMineCount);

		for (int i = 0; i < minesToLay; i++) {

			int r = random.nextInt(this.size);
			int c = random.nextInt(this.size);

			if (!this.board[r][c].hasMine()) {
				this.board[r][c].layMine();
				this.incrementNeighborhoodMines(r, c);
				suggestedMineCount++;

			}

		}
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public CellMS getCell(int row, int column) {
		return board[row][column];
	}

	public void toggleFlag(RowColumn x) {
		this.board[x.r][x.c].toggleFlag();
	}

	public GameStatus getGameStatus() {
		return status;
	}

	/*
	 * This method is called because it is located next to a cell with a mine.
	 * 
	 * This method checks that r and c are legitimate row and column values, and
	 * if this cell at r, c does not have a mine. In that case, increase its
	 * mineCount
	 * 
	 */
	private void updateCellMineCount(int r, int c) {
		if (r >= 0 && r < this.size && c >= 0 && c < this.size) {
			if ((getCell(r, c).hasMine() == false)) {
				this.board[r][c].mineCount++;
			}
		}
	}

	/*
	 * A better name for this method would be updateNeighborhoodMineCounts
	 * 
	 * This method is called because the cell, board[x.r][x.c], has a land mine.
	 * Consequently, this method needs to call updateCellMineCount 8 times for
	 * each of its 8 neighbors.
	 * 
	 * updateCellMineCount(x.r-1, x.c-1), etc.
	 * 
	 * Let updateCellMineCount check if the specified cell is legitimately on
	 * the board.
	 */
	private void incrementNeighborhoodMines(int r, int c) {

		this.updateCellMineCount(r - 1, c - 1);
		this.updateCellMineCount(r - 1, c);
		this.updateCellMineCount(r - 1, c + 1);

		this.updateCellMineCount(r, c - 1);
		this.updateCellMineCount(r, c + 1);

		this.updateCellMineCount(r + 1, c - 1);
		this.updateCellMineCount(r + 1, c);
		this.updateCellMineCount(r + 1, c + 1);

	}

	/*
	 * Precise terminology in the design of each class in a programming project
	 * is critical for clear thinking and skillfully written code. This can be
	 * immediately appreciated in the MineSweeperProject.
	 * 
	 * TERMINOLGY interior cell contiguous region boundary cells
	 * 
	 * For the following methods, let interior cell denote a cell with a
	 * mineCount of 0, and let a contiguous region refer to the collection of
	 * adjacent interior cells.
	 * 
	 * The boundary of a contiguous region consists of boundary cells, with a
	 * positive mine count, but do not themselves have a mine.
	 * 
	 */

	/*
	 * If r, c denotes a valid square in the game, this method returns true if
	 * the cell is an interior cell which has previously been exposed, and
	 * otherwise, false.
	 */
	private boolean isAnExposedInteriorCell(int r, int c) {
		// Establish a local boolean
		boolean exposed = false;
		// If the cell is valid and interior
		if ((r < this.size && r >= 0) && (c < this.size && c >= 0)) {
			// If it's already exposed return true
			if (this.getCell(r, c).isExposed() && this.getCell(r, c).mineCount == 0) {
				exposed = true;
				// If it's not exposed return false
			} else {
				exposed = false;
			}
		}
		return exposed;
	}

	/*
	 * This method repeatedly calls IsAnExposedInteriorCell( int r, int c ) and
	 * returns true if anyone of its, possibly 8, neighbors is an exposed
	 * interior cell, otherwise it returns false.
	 * 
	 * Let IsAnExposedInteriorCell check if the specified r, c location is
	 * legitimately on the board.
	 * 
	 */
	private boolean hasAnExposedInteriorNeighbor(int r, int c) {
		if (this.isAnExposedInteriorCell(r - 1, c - 1)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r - 1, c)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r - 1, c + 1)) {
			return true;
		}

		if (this.isAnExposedInteriorCell(r, c - 1)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r, c + 1)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r + 1, c - 1)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r + 1, c)) {
			return true;
		}
		if (this.isAnExposedInteriorCell(r + 1, c + 1)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method exposes a cell, either an interior or a boundary cell, that
	 * has not previously been exposed, but is a neighbor to some exposed
	 * interior cell, and returns true if the change is made.
	 * 
	 * It returns false, if no change is made.
	 */
	private boolean exposeThisContiguousCell(int r, int c) {
		// If the cell is not exposed and has an exposed neighbor then expose it
		if ((!this.board[r][c].isExposed()) && (this.hasAnExposedInteriorNeighbor(r, c))) {
			this.board[r][c].setExposed();
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 * This algorithm REPEATLY makes an exhaustive search over the board until
	 * there are no changes made to the board.
	 * 
	 * This can be done within a while loop which is flagged by a boolean
	 * variable for stillUpdating the board.
	 * 
	 * For each iteration within the while, a nested "for" loop calls invokes
	 * exposeThisContiguousCell( int r, int c ) for each cell on the board. Note
	 * that this method returns true or false depending upon it exposing a cell.
	 * 
	 */
	private void exposeContiguousCells() {
		// establish a local variable that is set to true
		boolean stillUpdating = true;
		while (stillUpdating) {
			//runs through each cell 
			for (int row = 0; row < this.size; row++) {
				for (int col = 0; col < this.size; col++) {
					//true if cell has a neighbor that is exposed with a mine count of zero, false if it does not.
					stillUpdating = this.exposeThisContiguousCell(row, col);
				}
			}
		}
	}

	/*
	 * The method returns GameStatus.win, GameStatus.lost, or
	 * GameStatus.notOverYet.
	 * 
	 * If the selected cell has a land mine the game is lost.
	 * 
	 * If a search over all cells on the board detects a cell that is not
	 * exposed and does not contain a line mine, the game is not over. If the
	 * search is exhausted, by default the game is won.
	 */
	private GameStatus determineGameStatus(int r, int c) {
		//If you clicked a mine then expose all cells game over
		if (this.board[r][c].hasMine()) {
			
			
			JOptionPane.showMessageDialog(null, "You Lost!");
			this.reset();
			this.losses++;
			return GameStatus.lost;
		}
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				if (!this.board[row][col].hasMine() && !this.board[row][col].isExposed()) {
					return GameStatus.notOverYet;
				}
			}
		}
		JOptionPane.showMessageDialog(null, "You Won!");
		this.reset();
		this.wins++;
		return GameStatus.won;
	}

	public void select(RowColumn x) {
		if (x != null) {
			this.select(x.r, x.c);
		}
	}
	/*
	 * This method is called by selectTheCell in the MinesSweeperPanel, after
	 * the JButton, at board[r][c], has been clicked, i.e. selected.
	 * 
	 *
	 * If the cell at r, c is not flagged, the method should: 1) expose the
	 * cell, (A click on a JButton for a cell with a positive mine count exposes
	 * that cell, only.) 2) if the cell is interior to a contiguous region,
	 * expose the continuous region, 3) determine and set the game status.
	 */

	public void select(int r, int c) {
		// If the cell is not exposed
		if (!this.board[r][c].isExposed()) {
			// If flagged then do nothing
			if (this.board[r][c].isFlagged()) {
				return;
			}
			// If the mine count's greater than 0 and the cell doesn't have a
			// mine
			if (this.board[r][c].getMineCount() > 0 && !this.board[r][c].hasMine()) {
				this.board[r][c].setExposed();
			}
			// If the cell has a mine count of 0, expose and expose contiguous
			// cells
			if (this.board[r][c].getMineCount() == 0 && !this.board[r][c].hasMine()) {
				this.board[r][c].setExposed();
				this.exposeContiguousCells();
			}
			if(this.board[r][c].hasMine()){
				this.board[r][c].setExposed();
				
			}
		}
		status = this.determineGameStatus(r, c);
	}

}