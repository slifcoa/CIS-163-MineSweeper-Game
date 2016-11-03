package dialogBoxes;

import javax.swing.JOptionPane;

	/*
	 * Three types of dialog:
	 * 1) String, double, or integer input dialog
	 * 2) yes / no dialog
	 * 3) message
	 * 
	 * 
	 * @author Larry Kotman
	 * @version January 2016
	 */

public class Dialog163 {
		
		public static String getString( String prompt, String title, String suggestion )
		{
	    //    String response = JOptionPane.showInputDialog( null, prompt, suggestion ); 

			String response =
					(String)JOptionPane.showInputDialog(
							null,                      // a ParentComponent reference
							prompt,                    // dialog prompt
							title,                     // dialog title
							JOptionPane.PLAIN_MESSAGE, // message type
							null,                      // an Icon reference
							null,                      // an array of possible selections
							suggestion                 // default selection
							);

			if (response == null)
			{
				response = "";
			}
			return response;
		}   

		public static double getDouble( String prompt, String title, String suggestion)
		{
			String response = getString( prompt, title, suggestion);

			try
			{
				return Double.parseDouble( response );
			}
			catch (Exception e)
			{
				return 0.0;
			}    }

		public static int getInteger( String prompt, String title, String suggestion)
		{
			String response = getString( prompt, title, suggestion);

			try
			{
				return Integer.parseInt( response );
			}
			catch (Exception e)
			{
				return 0;
			}
		}   

		public static void showMessage( String message, String dialogTitle )
		{
			JOptionPane.showMessageDialog(
					null,                   // a ParentComponent reference
					message,
					dialogTitle,
					JOptionPane.INFORMATION_MESSAGE );
		}

		public static int getYesNo( String prompt )
		{
			int choice;
			String[] options = {"Yes", "No", "Cancel"};

			choice = JOptionPane.showConfirmDialog(
					null,                   // a ParentComponent reference
					prompt,                 // Dialog prompt
					"Confirmation Dialog",  // Dialog title
					JOptionPane.YES_NO_CANCEL_OPTION );

			JOptionPane.showMessageDialog(
					null,                                        // a ParentComponent reference
					"You clicked \"" + options[choice] + "\".",  // Message
					"Message Dialog",                            // Dialog title
					JOptionPane.INFORMATION_MESSAGE );

			return choice;
		}

		public static void main( String[ ] args )
		{
			String response;

			response = (String)JOptionPane.showInputDialog(null, "Enter a string", "default" );
			showMessage( "You entered \"" + response + "\"", "Echo input" );

			response = Dialog163.getString( "Enter a string", "String dialog", "abcdef" );
			showMessage( "You entered \"" + response + "\"", "Dialog title" );

			int reply = Dialog163.getYesNo("Click the YES button");
			showMessage( "You entered \"" + reply + "\"", "Dialog title" );

			reply = Dialog163.getYesNo("Click the NO button");
			showMessage( "You entered \"" + reply + "\"", "Dialog title" );

			reply = Dialog163.getYesNo("Click the CANCEL button");
			showMessage( "You entered \"" + reply + "\"", "Dialog title" );

			double number = 100.0 + Math.random() * 1000.0;  
			number = Dialog163.getDouble( "Enter a double", "Double dialog", "" + number  );  
			showMessage( "You entered \"" + number + "\"", "Dialog title" );

			int num = (int)(100.0 + Math.random() * 1000.0);  
			num = Dialog163.getInteger( "Enter an intger", "Integer dialog", "" + num  );  
			showMessage( "You entered \"" + num + "\"", "Dialog title" );
		}

	}

