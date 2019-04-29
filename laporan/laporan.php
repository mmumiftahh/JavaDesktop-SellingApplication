<?php 
include "config/koneksi.php";
$waktu = date("Y-m-d");
?>
<form method="post">
<table style="border:1px solid gray;" cellpadding="20" align="center">
  <tr>
    <td>
      <div class="utama">
    <table align="center">
      <tr>
        <td width="7%" rowspan="3" align="center" valign="top"><img src="1.png" style="width:75px; height:70px;"></td>
        <td width="93%" valign="top">&nbsp;<strong>LAPORAN ATUN CELLULAR</strong></td>
      </tr>
      <tr>
        <td valign="top" align="center">Jl. Raya Wangun Kel. Sindangsari</td>
      </tr>
    </table>
    <hr>
    <table border="1" cellspacing="0">
      <tr>
        <th>Tanggal</th>
        <th>ID Pembeli</th>
        <th>ID Handphone</th>
        <th>Merek Handphone</th>
        <th>Warna Handphone</th>
        <th>Harga Satuan Handphone</th>
        <th>Jumlah Beli Handphone</th>
        <th>Total Keseluruhan</th>
        <th>Uang Pembayaran</th>
        <th>Uang Kembalian</th>
      </tr>
      <?php
        $sql = "SELECT * FROM struk";
        $query = mysqli_query($con,$sql);
        while ($data = mysqli_fetch_array($query)){
      ?>
    <tr>
        <td> <?= $waktu ?></td>
        <td> <?= $data['id_beli'] ?></td>
        <td> <?= $data['id_hp'] ?></td>
        <td> <?= $data['merek_hp'] ?></td>
        <td> <?= $data['warna_hp'] ?></td>
        <td> <?= $data['harga_hp'] ?></td>
        <td> <?= $data['jumlah_beli'] ?></td>
        <td>Rp. <?= $data['total_keseluruhan'] ?></td>
        <td>Rp. <?= $data['uang_masuk'] ?></td>
        <td>Rp. <?= $data['uang_kembali'] ?></td>
      </tr>
      <?php } ?>
    </table>
    <hr>
    <table align="center">
      <tr>
        <td>&copy; <?php echo date('Y'); ?> Atun Cellular</td>
      </tr>
    </table>
  </div>
    </td>
  </tr>
</table>
</form>