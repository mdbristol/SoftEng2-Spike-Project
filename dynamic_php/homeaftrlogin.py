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
		<br /><br /><br />
		<form Method="POST" action="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=uploadform.py">
		<input type="submit" value="Upload a picture" />
		<input type="hidden" name="user" value= "
		""" + usr + """
		" SIZE ="12">
		</form>
		<form Method="POST" action="http://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=home.py">
		<input type="submit" value="Log Out" />
		</form>
		</CENTER>
		</BODY>
		</HTML>
		"""
	

