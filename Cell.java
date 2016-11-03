package project2;


public class Cell
{
	public  int     mineCount;		// the number of neighboring cells laid with a land mine, less than 9.
	private boolean flagged;		// a flagged cell is not alterable.
	private boolean exposed;		// an exposed cell shows a count of adjacent cells with land mines
	private boolean hasMine;		// the cell contains a land mine
		
	
	public Cell( )
	{
		this.reset( );
	}
	
	public void clear( )
	{
		this.reset( );
	}
	
	public void reset( )
	{
		this.mineCount = 0;
		this.flagged   = false;
		this.exposed   = false;
		this.hasMine   = false;
	}	
	
	public int getMineCount( )
	{
		return this.mineCount;
	}

	public void toggleFlag( )
	{
		this.flagged = !this.flagged;
	}
	
	public boolean isFlagged( )
	{
		return this.flagged;
	}

	public boolean isExposed( )
	{
		return this.exposed;
	}
	
	public void setExposed( )
	{
		this.exposed = true;
	}

	public boolean hasMine( )
	{
		return this.hasMine;
	}
	
	public void layMine( )
	{
		this.hasMine = true;
	}
	
}
