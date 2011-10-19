<!-- Matt Rohr, Wes Conley, Gerald Ottman --!>



<?php

  $dbhost = 'localhost';
  $dbuser = 'team18';
    $dbpass = 'team_18';
    $dbname = 'team18';

	  require_once( 'DB.php' );

	  $db = DB::connect( "mysql://$dbuser:$dbpass@$dbhost/$dbname" );

	    $db->setFetchMode(DB_FETCHMODE_ASSOC);


	    $sql = 'SELECT * FROM students';

		$StudentResult = $db->query($sql);
?>
<html>
<head> <title>ITEC 471 - GRADEBOOK</title></head>
<body style='text-align:center;'>
<img src='http://www.surfsidesoftware.com/newimages/gradebook_product.gif'></img>
<h1> Itec 471 Gradebook </h1>
<table border = 'border' align='center' style='text-align:center;'> 
<tr> <th> First Name </th> <th> Last Name </th> <th> HW1 </th> <th> HW2 </th><th> HW3 </th> <th> Average </th>
</tr>


<?php
		while ($student = $StudentResult->fetchRow()) { ?>
			<tr> <td> <?php echo $student['firstname']; ?> </td>
				<td> <?php echo $student['lastname']; ?> </td>
				<td> <?php echo $student['hw1']; ?> </td>
				<td> <?php echo $student['hw2']; ?> </td>
				<td> <?php echo $student['hw3']; ?> </td>
				<td> <?php echo (($student['hw1']+$student['hw2']+$student['hw3'])/3); ?> </td>
			</tr>


<?php			        }
?>
</table>
</body>
</html>


