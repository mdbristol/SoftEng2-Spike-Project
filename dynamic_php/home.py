#!/usr/bin/python 
import cgi

print "Content-Type: text/HTML\n\n"


def page():
	print """
	<HTML>
	<HEAD><TITLE>Home</TITLE></HEAD>
	<BODY>
	<CENTER><FONT SIZE="5" COLOR="#990000">Home Page</FONT><br />
	<img src="http://www.attendconference.com/blog/wp-content/uploads/2011/02/python-icon.jpg">
	<FONT SIZE="1"> Image from attendconference.com </font>
	</CENTER>
	<br /><br /><br />
	<a href="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=login.py"> Click here to Login </a>
	<br /><br />
	<a href="https://php.radford.edu/cgi-bin/cgiwrap?user=gottman&script=register.py"> Click here to Register </a>

	</BODY>
	</HTML>
	"""

page()
