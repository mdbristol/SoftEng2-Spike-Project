import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class EncryptionTest
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		String pass = scan.next();
		System.out.println(pass);
		System.out.println("=");
		System.out.println(encrypt(pass));
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
	
//	public static void menu(Scanner scan)
//	{
//		boolean sentinel = true; //sets loop check to run infinately until quit command is used to exit.
//		System.out.println("\n  Possible Commands: \n   1 - List Possible Commands.\n   2 - Login.\n   3 - Query/Change personal information.\n   4 - Create an account.\n   5 - Change a password.\n   6 - Quit.\n");
//		while(sentinel)
//		{
//			System.out.print("> ");
//			String com = (scan.nextLine());
//			if(com.equals("1")||com.equals("help")||com.equals("man"))
//				{
//				System.out.println("  Possible Commands: \n   1 - List Possible Commands.\n   2 - Login.\n   3 - Query/Change personal information.\n   4 - Create an account.\n   5 - Change a password.\n   6 - Quit.\n");
//				}
//			else if(com.equals("2")) // Login
//				{
//					System.out.print("Username> ");
//					String user = scan.nextLine();
//					System.out.print("Password> ");
//					String passhash = encrypt(scan.nextLine());
//					//System.out.println(passhash);
//				}
//			else if(com.equals("3")) // Query Personal Info
//				{
//					System.out.println("\n  Query/Change personal information: \n\n   1 - Show personal info for self.\n   2 - Show personal info for employees.\n   3 - Change salary of an employee.\n   4 - Change job title of an employee.\n");
//					System.out.print("> ");
//					String com2 = (scan.nextLine());
//					if(com2.equals("1")) // Self Personal Info
//						{}
//					else if(com2.equals("2")) // Employee Personal Info
//						{}
//					else if(com2.equals("3")) // Change Employee Salary
//						{}
//					else if(com2.equals("4")) // Change Job Title Employee
//						{}
//					else
//						{System.out.println("Command not recognized.");}
//				}
//			else if(com.equals("4")) // Create an acct
//				{}
//			else if(com.equals("5")) // Change pw
//				{}
//			else if(com.equals("6")||com.equals("quit")||com.equals("exit")) // Quit
//				{sentinel = false;} // Sets sentinel boolean to false so loop will no longer execute.
//			else
//				{System.out.println("Command not recognized, input 'help' or '1' for a list of commands.");}
//		}
//	}
}
