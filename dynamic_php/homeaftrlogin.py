#!/usr/bin/python 

import cgi
import MySQLdb

def page(logged, usr):
	if logged:
		print """
		<HTML>
		<HEAD><TITLE>Home</TITLE></HEAD>
		<BODY>
		<CENTER><FONT SIZE="5" COLOR="#990000">Home Page</FONT><br />
		<img src="http://www.attendconference.com/blog/wp-content/uploads/2011/02/python-icon.jpg">
		<FONT SIZE="1"> Image from attendconference.com </font>
		</CENTER>
		<br /><br /><br />
		Upload an Image:
		<br />
		<form enctype="multipart/form-data" method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=upload.py">
		<p>File: <input type="file" name="file"</p>
		<p>Caption: <input type="text" name="caption" SIZE ="12"></p>
		<p>Album: <input type="text" name="album" SIZE ="12"></p>
		<input type="hidden" name="name" value= "
		""" + usr + """
		" SIZE ="12">
		<p><input type="submit" value="Connect"></p>
		</form>	
		</BODY>
		</HTML>
		"""
