#!/usr/bin/python
import cgi
import MySQLdb
import homeaftrlogin

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

def correctpass(usr, pas):
	db = MySQLdb.connect(host="php.radford.edu",user="gottman",passwd="spike123",db="gottman", port=3306 )
	cursor = db.cursor()
	q1 = """SELECT PASSWORD FROM USERS WHERE USERNAME = '""" + usr  + """' AND PASSWORD = '""" + pas + """'"""
	cursor.execute(q1)
	row = cursor.fetchone()
	if row:
		return True
	else:
		return False
	cursor.close()
	db.close()


def footer():
    print "</BODY></HTML>"


def check_login():
		form = cgi.FieldStorage()
		
		if not form.has_key("login") or not form.has_key("password"):
			form1()
			footer()
			
		elif userexists(form["login"].value) and correctpass(form["login"].value, form["password"].value):
			homeaftrlogin.page(True)

		elif form.has_key("login") and not userexists(form["login"].value):
			form1()
			print "<H3>Incorrect User or Password.</H3>"
			footer()

		elif form.has_key("password") and not correctpass(form["login"].value, form["password"].value):
			form1()
			print "<H3>Incorrect User or Password.</H3>"
			footer()

def form1():
	print """
		<HTML>
		<HEAD><TITLE>Login Page</TITLE></HEAD>
		<BODY>
		<FORM method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=login.py">
		<P><FONT SIZE="5" COLOR = "#990000"> Spike Project Login</font><br />
		<TABLE BOREDER="0" WIDTH="271">
		<TR>
		<TD WIDTH ="48%">
			<P ALIGN="RIGHT"> Login name: <br />
		</TD>
		<TD WIDTH="1%">&nbsp;</TD>
		<TD WIDTH="51%"> <input type="text" name="login" SIZE ="12"></TD>
		</TR>
		<TR>
		<TD WIDTH ="48%">
			<P ALIGN="RIGHT"> Password: <br />
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
		</form> 
		or <a href="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=register.py"> Register Here </a>
		"""
		
check_login()
footer()
