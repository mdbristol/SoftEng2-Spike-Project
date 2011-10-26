#!/usr/bin/python 

import cgi
import MySQLdb


form = cgi.FieldStorage()
if form:

	print "Content-Type: text/HTML\n\n"
	print """
	Upload an Image:
	<br />
	<form enctype="multipart/form-data" method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=upload.py">
	<p>File: <input type="file" name="file"</p>
	<p>Caption: <input type="text" name="caption" SIZE ="12"></p>
	<p>Album: <input type="text" name="album" SIZE ="12"></p>
	<input type="hidden" name="name" value= "
	""" + form["user"].value + """
	" SIZE ="12">
	<p><input type="submit" value="Connect"></p>
	</form>	
	"""

