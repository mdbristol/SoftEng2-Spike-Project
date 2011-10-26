#!/usr/bin/python
import cgi
import MySQLdb
print "Content-Type: text/HTML\n\n"

def userexists(usr):
	db = MySQLdb.connect(host="php.radford.edu",user="gottman",passwd="spike123",db="gottman", port=3306 )
	cursor = db.cursor()
	q1 = """SELECT USERNAME FROM USERS WHERE USERNAME = '""" + usr + """'"""
	cursor.execute(q1)
	row = cursor.fetchone()
	if row:
		return True
	else:
		return False
	cursor.close()
	db.close()

def adduser(usr, pas):
	db = MySQLdb.connect(host="php.radford.edu",user="gottman",passwd="spike123",db="gottman", port=3306 )
	cursor = db.cursor()
	q1 = """INSERT INTO USERS VALUES('""" + usr + """','""" + pas + """')"""
	q2 = """commit"""
	cursor.execute(q1)
	cursor.execute(q2)
	cursor.close()
	db.close()

def footer():
	print "</BODY></HTML>"


def check_reg():
	form = cgi.FieldStorage()
		
	if not form.has_key("login") or not form.has_key("password"):
		regform()
		footer()
	
	elif userexists(form["login"].value):
		regform()
		print "Username already exists!"
		footer()

	elif form.has_key("login") and not userexists(form["login"].value):
		try:
			adduser(form["login"].value, form["password"].value)
			regform()
			print "user added successfully"
			footer()
		except:
			regform()
			print "problem adding user"
			footer()

def regform():
	print """
		<HTML>
		<HEAD><TITLE>Registration Page</TITLE></HEAD>
		<BODY>
		<a href="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=home.py"> <-Back to Home </a>
		<FORM method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=register.py">
		<P><FONT SIZE="5" COLOR = "#990000"> Spike Project Registration</FONT><br />
		<TABLE BOREDER="0" WIDTH="271">
		<TR>
		<TD WIDTH ="48%">
			<P ALIGN="RIGHT"> Username you would like: <br />
		</TD>
		<TD WIDTH="1%">&nbsp;</TD>
		<TD WIDTH="51%"> <input type="text" name="login" SIZE ="12"></TD>
		</TR>
		<TR>
		<TD WIDTH ="48%">
			<P ALIGN="RIGHT"> Password you would like: <br />
		</TD>
		<TD WIDTH="1%">&nbsp;</TD>
		<TD WIDTH="51%"> <input type=password name="password" SIZE ="12"></TD>
		</TR>		
		<TR>
		<TD WIDTH="48%">&nbsp;</TD>
		<TD WIDTH="1%">&nbsp;</TD>
		<TD WIDTH="51%"><input type="submit" value="Connect">
		</TR>
		</TABLE>
		</FORM>
		"""
	
check_reg()
