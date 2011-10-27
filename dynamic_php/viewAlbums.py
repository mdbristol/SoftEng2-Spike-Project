#!/usr/bin/python 

import cgi
import os
import cgitb; cgitb.enable()
import MySQLdb
import homeaftrlogin

print "Content-Type: text/html\n\n"


print """
	<HTML>
	<HEAD><TITLE>Albums</TITLE></HEAD>
	<BODY>
	<CENTER><FONT SIZE="5" COLOR="#990000">Albums</FONT><br />
	<br /><br /><br />
	"""

form = cgi.FieldStorage()
db = MySQLdb.connect("php.radford.edu","gottman","spike123","gottman", port=3306)
cursor = db.cursor()
q1 = "select distinct album from images where user = '" + form["user"].value.strip() + "'"
cursor.execute(q1)

rows = cursor.fetchall()


for row in rows:
	print """
		<form Method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=viewonealbum.py">
		<input type="submit" value="\
				""" + row[0] + """\
				" />
		<input type="hidden" name="user" value= "
		""" + form["user"].value + """\
		" size ="12">
	 	<input type="hidden" name="album" value= "
		""" + row[0] + """\
		" size ="12">
	       
		</form>
	"""	


cursor.close()
db.close()
