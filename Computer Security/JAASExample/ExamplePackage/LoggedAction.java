package ExamplePackage;
import java.io.File;
import java.io.FilePermission;
import java.security.PrivilegedAction;
import java.security.Principal;
import javax.security.auth.Subject;
import java.util.Iterator;
import java.util.Set;
import java.util.Scanner;
/* 
 * This class contains an example of an action that we want a subject to execute. 
 * The action involves reading the test2 file. If the subject is a root, then it will be successful, else
 * it will fail. 
 */

public class LoggedAction 
{

		public void runAsUser(Subject loggedUser) 
		{
			Set principals = loggedUser.getPrincipals();
			Iterator i = principals.iterator();
			System.out.println("Running as " + ((Principal)i.next()).getName());
			Scanner scan = new Scanner(System.in);
			menu(scan);
		}

	public static void menu(Scanner scan)
	{
		boolean sentinel = true; //sets loop check to run infinately until quit command is used to exit.
		System.out.println("\n  Possible Commands:\n1 - Show Personal Data for Self\n2 - Show Personal Data for Employees [Only for VP and Managers]\n3 - Change Employee Salary [Only for VP and Managers]\n4 - Change Employee Job Ttile [Only for VP and Managers]\n5 - Logout\n");
		while(sentinel)
		{
			System.out.print("> ");
			String com = (scan.nextLine());
			if(com.equals("help")||com.equals("man"))
			{
				System.out.println("\n  Possible Commands:\n1 - Show Personal Data for Self\n2 - Show Personal Data for Employees [Only for VP and Managers]\n3 - Change Employee Salary [Only for VP and Managers]\n4 - Change Employee Job Ttile [Only for VP and Managers]\n5 - Logout\n");
			}
			else if(com.equals("1")) // Show Personal Data for Self
			{
				
			}
			else if(com.equals("2")) // Show Personal Data for Employees
			{
				
			}
			else if(com.equals("3")) // Change Salary
			{
				
			}
			else if(com.equals("4")) // Change Job Title
			{
				
			}
			else if(com.equals("5")||com.equals("quit")||com.equals("exit")) 
			{
				sentinel = false;
			} 
			else
			{
				System.out.println("Command not recognized, input 'help' for a list of commands");
			}
		}
	}
}