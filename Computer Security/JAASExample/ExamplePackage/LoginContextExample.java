package ExamplePackage;
import java.io.File;
import java.io.FilePermission;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Set;
import java.security.Principal;
import java.util.Scanner;
import javax.security.auth.Subject;
import javax.security.auth.login.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import ExamplePackage.RootAction; 
import java.util.LinkedList;

/* 
 * This class demonstrates 
 *        how to create a logincontext;
 *        how to invoke the login method; 
 *        how to store and extract the logged in subject.
 * This class is also the driver program. 
 * This class uses: 
 *       class LoginModuleExample: implements login method 
 *                                 to perform authentication, 
 *                                 creates a Subject object, and
 *                                 implements the logout method.                     
 */

public class LoginContextExample 
{
	
		
    static LoginContext lc; 	
	
    public static void main(String[] args) 
	{
		
		Scanner scan = new Scanner(System.in);
		menu(scan);
		
		
	}	
	public static void login()
	{
		LoginContextExample lce = new LoginContextExample();
		
		/* Create a call back handler. This call back handler will be populated with
				 * different callbacks by the various login modules. For example, 
				 * if a login module implements a username/password style login, it populates this object
				 * with NameCallback (to get username) and PasswordCallback (which gets password).
		 */
		CallBackHandlerExample cbe = new CallBackHandlerExample(); 
		
		/* Create a new login context. 
		 * @param Policy Name : We defined a policy in the file JAASPolicy.txt 
		 *                      and it is called "JAASExample"
		 * @param Call Back Handler
		 */
		try 
		{
			lc = new LoginContext("JAASExample", cbe);
		}
		catch (LoginException e) 
		{
			System.err.println("Login exception."); 
		}
		
		/* 
		 * Perform login.
		 */
		try 
		{
			lc.login(); 
			
			// If we reach this point then login has succeeded.
			System.out.println("You are now logged in!");
			/* 
			 * Print the various Principals attached with the logged Subject.
			 * In this example, we attach only one principal with each subject.
			 */
			Subject loggedUser = lc.getSubject();
			Set principals = lc.getSubject().getPrincipals();
			Iterator i = principals.iterator();
			while (i.hasNext()) 
			{
				/* 
				 *  An example of how to perform authorization actions. 
				 *  E.g., class RootAction contains the methods that a root can 
				 *  perform. 
				 */
				LoggedAction la = new LoggedAction();
				la.runAsUser(loggedUser);
				lc.logout();
			}
		}
				
		catch (LoginException e) 
		{
			System.out.println("Username/password incorrect! " + e);
			
		}
		catch (SecurityException e) 
		{
			System.out.println(" " + e);
		}
	}
	public static void changePassLogin()
	{
		LoginContextExample lce = new LoginContextExample();
		
		/* Create a call back handler. This call back handler will be populated with
				 * different callbacks by the various login modules. For example, 
				 * if a login module implements a username/password style login, it populates this object
				 * with NameCallback (to get username) and PasswordCallback (which gets password).
		 */
		CallBackHandlerExample cbe = new CallBackHandlerExample(); 
		
		/* Create a new login context. 
		 * @param Policy Name : We defined a policy in the file JAASPolicy.txt 
		 *                      and it is called "JAASExample"
		 * @param Call Back Handler
		 */
		try 
		{
			lc = new LoginContext("JAASExample", cbe);
		}
		catch (LoginException e) 
		{
			System.err.println("Login exception."); 
		}
		
		/* 
		 * Perform login.
		 */
		try 
		{
			lc.login(); 
			
			// If we reach this point then login has succeeded.
			System.out.println("Your password will be updated");
			/* 
			 * Print the various Principals attached with the logged Subject.
			 * In this example, we attach only one principal with each subject.
			 */
			Subject loggedUser = lc.getSubject();
			Set principals = lc.getSubject().getPrincipals();
			Iterator i = principals.iterator();
			while (i.hasNext()) 
			{
				/* 
				 *  An example of how to perform authorization actions. 
				 *  E.g., class RootAction contains the methods that a root can 
				 *  perform. 
				 */
				LoginModuleExample lme = new LoginModuleExample();
				lme.updatePassword(loggedUser);
				lc.logout();
			}
		}
				
		catch (LoginException e) 
		{
			System.out.println("Username/password incorrect! " + e);
			
		}
		catch (SecurityException e) 
		{
			System.out.println(" " + e);
		}
	}

	public static void menu(Scanner scan)
	{
		boolean sentinel = true; 
		System.out.println("\n  Possible Commands: \n1 - Create Account\n2 - Change Password\n3 - Login\n4 - Quit\n");
		while(sentinel)
		{
			System.out.print("> ");
			String com = (scan.nextLine());
			if(com.equals("help")||com.equals("man"))
			{
				System.out.println("\n  Possible Commands: \n1 - Create Account\n2 - Change Password\n3 - Login\n4 - Quit\n");
			}
			else if(com.equals("1")) // Create Account
			{
				LoginModuleExample lme = new LoginModuleExample();
				lme.createAccount(scan);
			}
			else if(com.equals("2")) // Change Password
			{
				 changePassLogin();
			}			
			else if(com.equals("3")) // Login
			{
				login();
			}
			else if(com.equals("4")||com.equals("quit")||com.equals("exit"))
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
