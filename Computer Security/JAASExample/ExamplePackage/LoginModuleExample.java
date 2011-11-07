package ExamplePackage;
import java.security.Principal;
import java.util.*;
import java.lang.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;


public class LoginModuleExample implements LoginModule {

	// Flag to keep track of successful login.
	Boolean successfulLogin = false;

	// Variable that keeps track of the principal.
	Principal examplePrincipal;
	
	/*
	 * Subject keeps track of who is currently logged in.
	 */
	Subject subject;
	
	/*
	 * String username
	 * String password 
	 * Temporary storage for usernames and passwords (before authentication).
	 * After authentication we can clear these variables. 
	 */
	String username, password;
	
	/*
	 * Other variables that are initialized by the login context. 
	 */
	
	CallbackHandler cbh;
	Map sharedState;
	Map options;
	
	/*
	 * This method is called by the login context automatically.
	 * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject, 
	 *         javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
	 */
	public void initialize(Subject subject, 
			CallbackHandler cbh,
			Map sharedState,
			Map options) {
		
		this.subject = subject;
		this.cbh = cbh;
		this.sharedState = sharedState;
		this.options = options;
		
	}
	
	
	
	/*
	 * If a user tries to abort a login then the state is reset. 
	 * @see javax.security.auth.spi.LoginModule#abort()
	 */
	public boolean abort() throws LoginException {
		if (!successfulLogin) {
			
			username = null;
			password = null;
			return false; 
		} else {
			logout(); 
			return true; 
		}
		
	}

	/*
	 * If login is valid, then the commit method is called by the LoginContext object. Here
	 * the logged in user is associated with a "principle". Think of this as a token
	 * that can from now on be used for authorization. 
	 * @see javax.security.auth.spi.LoginModule#commit()
	 */
	public boolean commit() throws LoginException {
		
		if (successfulLogin) {
			
			// Example Principal object stores the logged in user name.
				examplePrincipal = new ExamplePrincipal(username);
				// subject stores the current logged in user.
				subject.getPrincipals().add(examplePrincipal);
				return true; 
		}
		
		return false;
	}

	
	
	/*
	 * The actual login method that performs the authentication
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	public boolean login() throws LoginException 
	{
		// We will use two call backs - one for username and the other
		// for password. 
		Callback exampleCallbacks[] = new Callback[2];
		exampleCallbacks[0] = new NameCallback("username: ");
		exampleCallbacks[1] = new PasswordCallback("password: ", false);
		// pass the callbacks to the handler. 
		try {
			cbh.handle(exampleCallbacks);
		} catch (IOException e) {
			 e.printStackTrace();
		} catch (UnsupportedCallbackException e) {
			e.printStackTrace();
		}
		
		
		// Now populate username/passwords etc. from the handler
		username = ((NameCallback) exampleCallbacks[0]).getName();
		password = new String (
					((PasswordCallback) exampleCallbacks[1]).getPassword());
		
		// Now perform validation. This part, you can either read from a file or a 
		// database. You can also incorporate secure password  handling here. 
		// As an example, we are going to use hard-coded passwords. 
		System.out.println("Checking username and password: " + username +"/" + password);
		
		try
		{
			FileInputStream fstream = new FileInputStream("Users/Users.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			LinkedList<String> name = new LinkedList<String>();
			ArrayList<String> pass = new ArrayList<String>();
			ArrayList<Integer> index = new ArrayList<Integer>();
			while ((strLine = br.readLine()) != null)
			{
				String[] thing = strLine.split(", ");
				name.add(thing[1].trim());
				pass.add(thing[5].trim());
			}
			in.close();
			//see if name exists once
			int curindx = name.indexOf(username);
			if(curindx==-1)
			{
				return false;
			}
			System.out.println(curindx);
			index.add(curindx);
			name.add(curindx, null);
			//see if name exists multiple times
			while(curindx != -1)
			{
				curindx = name.indexOf(username);
				
				if(curindx != -1)
				{
					index.add(curindx);
					name.remove(curindx);
					if(curindx != name.size())
					{
						name.remove(curindx);
						name.add(curindx, null);
					}
				}
			}
			//check the password at indexes where the name exists
			Iterator<Integer> itr = index.iterator();
			while(itr.hasNext())
			{
				int element = itr.next();
				if(pass.get(element).equals(encrypt(password)))
				{
					successfulLogin = true; 
					return true;
				}
			}
			return false;
		}
		catch (Exception e)
		{
			System.err.println("Error: File not Found. \nMake sure that Users/Users.txt exists");
		}
	return false;
		
	}
		


	/*
	 * 
	 * @see javax.security.auth.spi.LoginModule#logout()
	 */
	public boolean logout() throws LoginException {
		username = null;
		password = null;		
		subject.getPrincipals().remove(examplePrincipal);
		return true;
	}
	
	public static String encrypt(String s)
	{
		String md5val = "";
		MessageDigest algorithm = null;
		try
		{
			algorithm = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException nsae)
		{
			System.out.println("Cannot find digest algorithm");

		}

		byte[] defaultBytes = s.getBytes();
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < messageDigest.length; i++)
		{
			String hex = Integer.toHexString(0xFF & messageDigest[i]);

			if (hex.length() == 1)
			{
				hexString.append('0');
			}

			hexString.append(hex);
		}

		md5val = hexString.toString();
		return md5val;
	}
	
	public static void createAccount(Scanner scan)
	{
		try
		{
			String uid, name, position, manager, salary, pass;
			Boolean uid_taken = false;
			Boolean name_taken = false;
			
			System.out.println("\n Please input new UID");
			uid = scan.nextLine();
			System.out.println("\n Please input new Name");
			name = scan.nextLine();
			System.out.println("\n Please input new Position");
			position = scan.nextLine();
			System.out.println("\n Please input new Manager");
			manager = scan.nextLine();
			System.out.println("\n Please input new Salary");
			salary = scan.nextLine();
			System.out.println("\n Please input new Password");
			pass = scan.nextLine();
			
			Scanner br = new Scanner(new File("Users/Users.txt"));
			String strLine;
			LinkedList<String> cur_uids = new LinkedList<String>();
			LinkedList<String>  cur_names = new LinkedList<String>();
			while (br.hasNextLine())
			{
				strLine = br.nextLine();
				String[] thing = strLine.split(", ");
				cur_uids.add(thing[0].trim());
				cur_names.add(thing[1].trim());
			}
				
			Iterator<String> itr1 = cur_uids.iterator();
			while(itr1.hasNext())
			{
				if((itr1.next().trim()).equals(uid))
				{
					uid_taken = true;
				}
			}
			
			Iterator<String> itr2 = cur_names.iterator();
			while(itr2.hasNext())
			{
				if((itr2.next().trim()).equals(name))
				{
					name_taken = true;
				}
			}
			
			if(uid_taken)
			{
				System.out.println("That UID is taken");
			}
			else if(name_taken)
			{
				System.out.println("That Name is taken");
			}
			else
			{
				
				writeFile(uid + ", " + name + ", " + position +", " + manager + ", " + salary+ ", " + encrypt(pass));
			}
		}
		catch (Exception e)
		{
			System.err.println("Error: File not Found. \nMake sure that Users/Users.txt exists \n");
		}
	}
	
	private static void writeFile(String NextLine)
	{
		try
		{
			FileWriter out = new FileWriter("Users/Users.txt", true);
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(NextLine);
			writer.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	public static void updatePassword(Subject loggedUser)
	{
		try
		{
			Set principals = loggedUser.getPrincipals();
			Iterator i = principals.iterator();
			String name = ((Principal)i.next()).getName();
			System.out.println("Please enter new desired Password");
			Scanner scan = new Scanner(System.in);
			String newpass = scan.next();
			String oldpass;	
			Scanner br = new Scanner(new File("Users/Users.txt"));
			String strLine;
			while (br.hasNextLine())
			{
				strLine = br.nextLine();
				System.out.println(strLine);
				String[] thing = strLine.split(", ");
				System.out.println(thing[1]);
				System.out.println(thing[5]);
				if((thing[1].trim()).equals(name))
				{
					oldpass = (thing[5].trim());
					replaceValue(name, oldpass, encrypt(newpass));
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("Error: File not Found. \nMake sure that Users/Users.txt exists \n" + e);
		}
	
	}
	
	private static void replaceValue(String userName, String oldString, String newString)
	{
		String fileName = "Users/Users.txt";
		StringBuilder sb = new StringBuilder();
		String found = "";
		String newline = System.getProperty("line.separator");
		try
		{
			BufferedReader reader =	new BufferedReader(new FileReader(fileName));
			while (reader.ready()) 
			{
				String tempText = reader.readLine();
				if(tempText.contains(userName))
				{
					found = tempText + newline;
				}
				else
				{
					sb.append(tempText + newline);
				}
				tempText = "";
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error Encountered."+e);
		}
		String fileText = sb.toString();

		
		found = found.replaceFirst(oldString, newString);
		String newFile = fileText + found;
		try
		{
			FileWriter out = new FileWriter("Users/Users.txt", false);
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(newFile);
			writer.close();
		}
		catch(IOException e)
		{
			System.err.println("Error Encountered."+e);
		}
	}

}
