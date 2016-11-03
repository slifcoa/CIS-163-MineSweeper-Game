package project2;

public class CellMS extends Cell
{
	public CellMS( )
	{
		super( );
	}

	public String toString( boolean showMines )
	{
		if (this.hasMine() && showMines)
		{
			return "X";
		}
		if (this.isFlagged()) 	
		{
			return "*";
		}
		else if (this.mineCount == 0) 
		{
			return "";
		}
		else if (this.isExposed() ) // && cell.isFlagged()) 
		{
			return "" + this.mineCount;
		}	
		return "";
	}
	
	public String toString(  )
	{
		if (this.hasMine())
		{
			return "X";
		}
		if (this.isFlagged()) 	
		{
			return "*";
		}
		else if (this.mineCount > 0)
		{
			return "" + this.mineCount;
		}	
		else
		{
			return "";
		}
	}	
}
