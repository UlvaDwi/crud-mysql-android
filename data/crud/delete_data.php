<?php 
	include_once('connection.php');

	$nim = $_POST['nim']; 

	$getdata = mysqli_query($koneksi,"SELECT * FROM tb_mahasiswa WHERE nim ='$nim'");
	$rows = mysqli_num_rows($getdata);

	$delete = "DELETE FROM tb_mahasiswa WHERE nim ='$nim'";
	$exedelete = mysqli_query($koneksi,$delete);

	$respose = array();

	if($rows > 0)
	{
		if($exedelete)
		{
			$respose['code'] =1;
			$respose['message'] = "Delete Success";
		}
		else
		{
		$respose['code'] =0;
		$respose['message'] = "Failed Delete";
		}
	}
	else
	{
		$respose['code'] =0;
		$respose['message'] = "Failed Delete, data not found";
	}
	
	echo json_encode($respose);

 ?>