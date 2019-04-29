<?php 
include "config/koneksi.php";
?>
<form method="post">
<table style="border:1px solid gray;" cellpadding="20" align="center">
  <tr>
    <td>
      <div class="utama">

    <table align="center">
      <tr>
        <td width="7%" rowspan="3" align="center" valign="top"><img src="1.png" style="width:75px; height:70px;"></td>
        <td width="93%" valign="top">&nbsp;<strong>STRUK ATUN CELLULAR</strong></td>
      </tr>
      <tr>
        <td valign="top" align="center">Jl. Raya Wangun Kel. Sindangsari</td>
      </tr>
    </table>
    <hr>
    <table cellspacing="0" border="1">
      <tr>
        <th>ID Pembeli</th> 
        <th>Merek Handphone</th>
        <th>Warna Handphone</th>
        <th>Harga Satuan Handphone</th>
        <th>Jumlah Beli</th>
        <th>Total Harga</th>
      </tr>
      <?php
        $sql = "SELECT * FROM keranjang";
        $query = mysqli_query($con,$sql);
        while ($data = mysqli_fetch_array($query)){
      ?>
    <tr>
        <td> <?= $data['id_beli'] ?></td>
        <td> <?= $data['merek_hp'] ?></td>
        <td> <?= $data['warna_hp'] ?></td>
        <td>Rp. <?= $data['harga_hp'] ?></td>
        <td> <?= $data['jumlah_beli'] ?></td>
        <td>Rp. <?= $data['total_harga'] ?></td>
      </tr>
      <?php } ?>
    </table>
    <hr>
    <?php  

      $sql = "SELECT * FROM pembelian";
      $result = mysqli_query($con, $sql);
      $rows = [];
      while ( $row = mysqli_fetch_array($result) ) {
        $rows[] = $row;

    ?>
    <table border="1" cellspacing="0">
      <tr>
        <th>Total</th>
        <td>:</td>
        <td>Rp. <?= $row['total_keseluruhan'] ?> </td>
      </tr>
      <tr>
        <th>Uang Pembayaran</th>
        <td>:</td>
        <td>Rp. <?= $row['uang_masuk'] ?> </td>
      </tr>
      <tr>
        <th>Uang Kembalian</th>
        <td>:</td>
        <td>Rp. <?= $row['uang_kembali'] ?> </td>
      </tr>
    </table>
    <?php } ?>
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