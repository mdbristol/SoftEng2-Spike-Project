#!/usr/bin/python
# -*- coding: UTF-8 -*-

import cgi

# enable debugging
import cgitb
cgitb.enable()

print "Content-type: text/html\n\n";c


import MySQLdb

# Open database connection
db = MySQLdb.connect("php.radford.edu","pknouff","******","pknouff", port=3306 )

# prepare a cursor object using cursor() method
cursor = db.cursor()



	
	def header(title):
		    print "<html>\n<head>\n<title>%s</title>\n</head>\n<body>\n" % (title)

			def footer():
				    print "</BODY></HTML>"
						


						form = cgi.FieldStorage()

						qy = "SELECT username FROM Users WHERE username ='" + form["login"].value + "'"
						qt = "SELECT password FROM Users WHERE username ='" + form["login"].value + "'"
						q = cursor.execute(qy)
						row = cursor.fetchone()
						r = cursor.execute(qt)
						row2 = cursor.fetchone()

						if not form:
							    header("Login Response")
							elif form.has_key("login") and form["login"].value != "" and form.has_key("password") :
									if form["login"].value == row[0] and form["password"].value == row2[0]:
												header("Connected ...")
														print "<center><hr><h3>Welcome back," , form["login"].value, ".</h3><hr></center>"
																print r"""<form><input type="hidden" name="session" value="%s"></form>""" % (form["login"].value)
																		print "<a href=http:www.radford.edu>RU homepage</a> <br />"
																				print "<a href=https://php.radford.edu/~pknouff/upload.html>add photos</a>"
																					else:
																								header("No success!")
																										print "<h3>Please go back and enter a valid login.</h3>"
																									else:
																										    header("No success!")
																											    print "<h3>Please go back and enter a valid login.</h3>"

																												footer()	
																													
																													# disconnect from server
																													db.close()



																													#To do: 
																													#	secure passwords (also implement in database)
																													#	input validation (javascript?)
																													#	empty form handling
																													#	create session
																													#	passwrod reset/forgotten password
																													#	stylesheet

