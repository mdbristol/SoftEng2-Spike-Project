#!/usr/bin/python 

import cgi
import os
import cgitb; cgitb.enable()
import MySQLdb
import homeaftrlogin

print "Content-Type: text/html\n\n"


form = cgi.FieldStorage()

print """
	<HTML>
	<HEAD><TITLE>""" + form["user"].value.strip() + """'s """ + form["album"].value.strip() + """ Album </TITLE></HEAD>
	<BODY>
	<CENTER><FONT SIZE="5" COLOR="#990000">""" + form["user"].value.strip() + """'s """ + form["album"].value.strip() + """ Album</FONT><br />
	<br /><br /><br />
	"""

db = MySQLdb.connect("php.radford.edu","gottman","spike123","gottman", port=3306)
cursor = db.cursor()
album = form["album"].value
user = form["user"].value
album.replace(' ','')
user.replace(' ','')
q1 = "select image from images where album = '"+album.strip()+"' and user = '"+user.strip()+"'"

cursor.execute(q1)

rows = cursor.fetchall()

for row in rows:
	print """<img src='\
		""" + row[0] + """\
			'>
		"""	
	 

cursor.close()
db.close()
