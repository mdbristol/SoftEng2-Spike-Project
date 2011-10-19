#!/usr/bin/perl
 
 @tmp = split("/",$ENV{"SCRIPT_NAME"});
 $user = $tmp[3];
 $ip =  $ENV{"REMOTE_ADDR"};
  
  print "Content-Type:  text/html\n\n";
   
   print "<H1>Errors for $user</H1><BR>\n";
   @errors = `/bin/grep $user /usr/local/apache2/logs/php_error_log`;
   foreach $line (@errors) {
     print $line."<BR>\n";
     }
     print "<BR>\n";
      
      print "<H1>Errors for IP $ip</H1><BR>\n";
      @errors = `/bin/grep $ip /usr/local/apache2/logs/php_error_log`;
      foreach $line (@errors) {
        print $line."<BR>\n";
        }
         
