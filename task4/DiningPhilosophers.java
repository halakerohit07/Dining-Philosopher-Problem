package task4;
import java.util.Scanner;

/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */


	/**
	 * This default may be overridden from the command line
	 */
	
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		
		// *** Task-4 
		int iPhilosophers;
		String numberOfPhilosophers = "4";
		Scanner keyboard = new Scanner(System.in);
		
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			
			
			//*** Task-4 *** Prompting the user for positive number of philosophers.
			iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS ;
			
			
			System.out.println("Do you want to change numberOfPhilosophers or use default (yes/no): ");
			String yesOrNo = keyboard.nextLine();
			
			if(yesOrNo.toUpperCase().equals("YES")) { 
			
				System.out.println("Enter a positive number: ");
				numberOfPhilosophers = keyboard.nextLine();
				
				// If the number is less than zero than throw an exception
				if(Integer.parseInt(numberOfPhilosophers) <= 0) { 
					throw new NegativeNumberException(Integer.parseInt(numberOfPhilosophers) + " is not a positive decimal number");
				}
				
				// Else use that number to generate that number of philosophers.
				else { 
					iPhilosophers = Integer.parseInt(numberOfPhilosophers);
				}
				
			}
			// --------------------------------------------------------------
			
			

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		
		catch(NegativeNumberException e) { 
			
		}
		catch(NumberFormatException e)
		{
			System.err.println(numberOfPhilosophers + " is not a positive number");
			System.exit(1);
		}
		
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
		
		
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
