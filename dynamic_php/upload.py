#!/usr/bin/python 

import cgi
import os
import cgitb; cgitb.enable()
import MySQLdb
import homeaftrlogin

form = cgi.FieldStorage()

if form.has_key("name") and form.has_key("caption") and form.has_key("album") and form.has_key("file"):
	fileitem = form['file']
	
	if fileitem.filename:
		fn = os.path.basename(fileitem.filename)
		fn = os.path.basename(fileitem.filename.replace("\\", "/" ))
		d = os.path.dirname("tmp/" + form["album"].value + "/")
		if not os.path.exists(d):
			os.makedirs(d)
	
		open("tmp/" + form["album"].value + """/""" + fn, 'wb').write(fileitem.file.read())
		message = 'The file "' + fn + '" was uploaded successfully'
		
	else:
		message = 'No file was uploaded'

	print "Content-Type: text/html\n\n"

	homeaftrlogin.page(True, form["name"].value)
	print """
	<html>
	<body>
	<p>%s</p>
	""" % (message,)

	db = MySQLdb.connect(host="php.radford.edu",user="gottman",passwd="spike123",db="gottman", port=3306 )
	cursor = db.cursor()
	q1 = "insert into images values( 'https://php.radford.edu/~gottman/tmp/" + form["album"].value + "/" + fn  + "','" + form["caption"].value + "','" + form["album"].value + "','" + form["name"].value + "')"
	q2 = "commit"	
	try:
		cursor.execute(q1)
		cursor.execute(q2)
	except:
		print "\nBut it already exists!" 
	
	q3 = "select * from images where image = 'https://php.radford.edu/~gottman/tmp/" + form["album"].value + "/" + fn + "'\
			AND user = '" + form["name"].value + "'\
			AND caption = '" + form["caption"].value + "'\
			AND album = '" + form["album"].value + "'"
	cursor.execute(q3)
	row = cursor.fetchone()
	if row:	
		print """<br />Caption: """ + row[1]\
				+ """<br />Album: """ + row[2]\
				+ """<br />User: """ + row[3]\
				+ """<br /><a href="\
				""" + row[0] + """\
				" ><img src="\
				""" + row[0] + """\
				" height="50%" width="50%"></a>"""
	cursor.close()
	db.close()
