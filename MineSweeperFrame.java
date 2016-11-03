package project2;

import javax.swing.*;
import dialogBoxes.Dialog163;


public class MineSweeperFrame extends JFrame
{
	static final long serialVersionUID = 0;

	public MineSweeperFrame( String title )
	{	
		super( title );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		try 
		{
		    String name = JOptionPane.showInputDialog( null, "Please enter YOUR name", " Adam Slifco" ); 
			this.setTitle( name + " -- MineSweeper !");
			
			int size = Dialog163.getInteger( "Please enter size of the board:", "MineSweeperFrame", "10 " );

			this.getContentPane( ).add( new MineSweeperPanel( 10 ) );

			this.setSize( 10 * 50 , 10 * 50 );
			this.setVisible( true );
		}
		catch (Exception e)
		{
			System.out.println( e );
		}
	}

	public static void main( String[ ] args )
	{	
		new MineSweeperFrame( "Mine Sweeper!" );
	}
}

