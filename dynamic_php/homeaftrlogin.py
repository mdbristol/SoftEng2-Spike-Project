#!/usr/bin/python 

import cgi


def page(logged):
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
		</BODY>
		</HTML>
		"""
	if not logged:
		print """
		<HTML>
		<HEAD><TITLE>Home</TITLE></HEAD>
		<BODY>
		NOT LOGGED IN
		</BODY>
		</HTML>
		"""
