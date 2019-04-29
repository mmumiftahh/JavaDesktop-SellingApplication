/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomePage;

import java.awt.Desktop;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.net.URI;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author Muhamad Miftah
 */
public class pembelian extends javax.swing.JFrame {
    Connection con = new Login.koneksi().getConnection();
    ResultSet rs;
    Statement st;
    PreparedStatement prst;
    String sql;
    public DefaultTableModel table_inputhp;
    public DefaultTableModel table_keranjang;
    public Home hm;
    /**
     * Creates new form pembelian
     */
    public pembelian() {
        initComponents();
        table();
        tampil_table();
        tampil_ketersediaan_hp();
        autokode();
        table1();
        tampil_table1();
        tampil_ketersediaan_hp1();
        
        id_beli.setEnabled(false);
        id_hp.setEnabled(false);
        merek.setEnabled(false);
        warna.setEnabled(false);
        harga_satuan.setEnabled(false);
        total_harga.setEnabled(false);
        uang_kembali.setEnabled(false);
        total_keseluruhan.setEnabled(false); 
    }
    
    public void table(){
        for(int a=0; a < tb_inputhp.getColumnCount(); a++){
            Class <?> b = tb_inputhp.getColumnClass(a);
            tb_inputhp.setDefaultEditor(b, null);
        }
    }
    
    public void tampil_table(){
        Object[]tampil_table = {"ID HP","Merek HP","Warna HP","Harga HP","Stok HP"};
        table_inputhp = new DefaultTableModel(null, tampil_table);
        tb_inputhp.setModel(table_inputhp);
    }
    
    public void tampil_ketersediaan_hp(){
        try{
            st = con.createStatement();
            table_inputhp.getDataVector().removeAllElements();
            table_inputhp.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM input_hp WHERE stok_hp > 0");
            while(rs.next()){
                Object[]data = {
                    rs.getString("id_hp"),
                    rs.getString("merek_hp"),
                    rs.getString("warna_hp"),
                    rs.getString("harga_hp"),
                    rs.getString("stok_hp"),
                };
                table_inputhp.addRow(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void table1(){
        for(int a=0; a < tb_keranjang.getColumnCount(); a++){
            Class <?> b = tb_keranjang.getColumnClass(a);
            tb_keranjang.setDefaultEditor(b, null);
        }
    }
    
    public void tampil_table1(){
        Object[]tampil_table = {"ID Beli","ID HP","Merek HP","Warna HP","Harga HP","Jumlah Beli"};
        table_keranjang = new DefaultTableModel(null, tampil_table);
        tb_keranjang.setModel(table_keranjang);
    }
    
    public void tampil_ketersediaan_hp1(){
        try{
            st = con.createStatement();
            table_keranjang.getDataVector().removeAllElements();
            table_keranjang.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM keranjang WHERE ketera='ada'");
            while(rs.next()){
                Object[]data = {
                    rs.getString("id_beli"),
                    rs.getString("id_hp"),
                    rs.getString("merek_hp"),
                    rs.getString("warna_hp"),
                    rs.getString("harga_hp"),
                    rs.getString("jumlah_beli"),
                   
                };
                ket.setText(rs.getString("ketera"));
                table_keranjang.addRow(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void autokode(){
        try {
            sql = "SELECT * FROM pembelian ORDER BY id_beli DESC";
            prst = con.prepareStatement(sql);
            rs = prst.executeQuery();
            if (rs.next()) {
                String kode_otomatis = rs.getString("id_beli").substring(1);
                String an = ""+(Integer.parseInt(kode_otomatis)+1);
                String nol = "";
                if (an.length() == 1) {
                    nol = "0000";
                }else if(an.length() == 2){
                    nol = "000";
                }else if(an.length() == 3){
                    nol = "00";
                }else if(an.length() == 4){
                    nol = "0";
                }else if(an.length() == 5){
                    nol = "";
                }
                id_beli.setText("P"+nol+an);
            }else{
                id_beli.setText("P00001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reset(){
        id_hp.setText("");
        merek.setText("");
        harga_satuan.setText("");
        warna.setText("");
        jumlah_beli.setText("");
        total_harga.setText("");
    }
    
    public void simpan_keranjang(){
        if (id_beli.getText().equals("") || id_hp.getText().equals("") || merek.getText().equals("") || warna.getText().equals("") 
                || harga_satuan.getText().equals("") || jumlah_beli.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Harap Lengkapi Data!");
        }else{
            try {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO keranjang SET id_beli ='" + id_beli.getText() + "',id_hp ='" + id_hp.getText() + "', merek_hp='" + merek.getText() + "',"
                        + "harga_hp='" + harga_satuan.getText() +"', warna_hp='" + warna.getText() + "', jumlah_beli='" + jumlah_beli.getText() + "', total_harga='" + total_harga.getText() + "', hapus='0', ketera='ada'");
                JOptionPane.showMessageDialog(null, "Data Tersimpan","Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampil_ketersediaan_hp1();
                autokode();
                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void edit(){
        try {
            int bar = tb_inputhp.getSelectedRow();
            String a = tb_inputhp.getValueAt(bar, 0).toString();
            String b = tb_inputhp.getValueAt(bar, 1).toString();
            String c = tb_inputhp.getValueAt(bar, 2).toString();
            String d = tb_inputhp.getValueAt(bar, 3).toString();
            String e = tb_inputhp.getValueAt(bar, 4).toString();


            id_hp.setText(a);
            merek.setText(b);
            warna.setText(c);
            harga_satuan.setText(d);
            stok_tampung.setText(e);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void harga_keseluruhan(){
        try{
            sql = ("SELECT id_beli,SUM(total_harga) as harga_keseluruhan FROM keranjang GROUP BY id_beli");
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                total_keseluruhan.setText(rs.getString("harga_keseluruhan"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void simpan_selesai(){
        try {   
            st = con.createStatement();
            st.executeUpdate("INSERT INTO pembelian SET id_beli='"+id_beli.getText()+"',nama_pembeli='"+nama_pembeli.getText()+"',total_keseluruhan='"+total_keseluruhan.getText()+"',uang_masuk='"+uang_masuk.getText()+"',uang_kembali='"+uang_kembali.getText()+"'");
            st.executeUpdate("update keranjang set ketera='hilang' where id_beli='" +id_beli.getText()+ "'");
            JOptionPane.showMessageDialog(null, "Data Tersimpan","Informasi", JOptionPane.INFORMATION_MESSAGE);
            autokode();
            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        homepage = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        id_hp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        id_beli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        merek = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        uang_kembali = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        warna = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jumlah_beli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_inputhp = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_keranjang = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bselesai = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        nama_pembeli = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        harga_satuan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        total_harga = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        uang_masuk = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btambah_barang = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        stok_tampung = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        total_keseluruhan = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ket = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        bstruk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(24, 199, 224));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Add_Shopping_Cart_96px.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 100));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("BELI HANDPHONE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 340, 60));

        homepage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Menu_48px_1.png"))); // NOI18N
        homepage.setBorder(null);
        homepage.setContentAreaFilled(false);
        homepage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homepageActionPerformed(evt);
            }
        });
        jPanel1.add(homepage, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 100));

        jPanel2.setBackground(new java.awt.Color(216, 216, 251));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("ID Handphone");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        id_hp.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        id_hp.setBorder(null);
        id_hp.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        jPanel2.add(id_hp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 160, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("ID Beli");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        id_beli.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        id_beli.setBorder(null);
        id_beli.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jPanel2.add(id_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 160, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Merk");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        merek.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        merek.setBorder(null);
        merek.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jPanel2.add(merek, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 160, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uang_kembali.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        uang_kembali.setBorder(null);
        uang_kembali.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        uang_kembali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                uang_kembaliKeyTyped(evt);
            }
        });
        jPanel3.add(uang_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 130, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Rp.");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 450, 160, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Harga Satuan");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Warna");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        warna.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        warna.setBorder(null);
        warna.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jPanel2.add(warna, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 160, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("*ketersediaan handphone");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, -1, 20));

        jumlah_beli.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jumlah_beli.setBorder(null);
        jumlah_beli.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jumlah_beli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlah_beliKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jumlah_beliKeyTyped(evt);
            }
        });
        jPanel2.add(jumlah_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 160, 30));

        tb_inputhp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_inputhp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_inputhpMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_inputhpMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_inputhp);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 490, 180));

        tb_keranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tb_keranjang);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 490, 130));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Jumlah Beli");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Total Harga");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bselesai.setBackground(new java.awt.Color(255, 255, 255));
        bselesai.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bselesai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Checkmark_30px.png"))); // NOI18N
        bselesai.setText("Selesai");
        bselesai.setBorder(null);
        bselesai.setContentAreaFilled(false);
        bselesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bselesaiActionPerformed(evt);
            }
        });
        jPanel4.add(bselesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 490, 100, 40));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel12.setText("Nama Pembeli");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, -1, -1));

        nama_pembeli.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nama_pembeli.setBorder(null);
        nama_pembeli.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jPanel2.add(nama_pembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 160, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel13.setText("Uang Kembali");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        harga_satuan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        harga_satuan.setBorder(null);
        harga_satuan.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        harga_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harga_satuanActionPerformed(evt);
            }
        });
        harga_satuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                harga_satuanKeyTyped(evt);
            }
        });
        jPanel5.add(harga_satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 130, 30));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Rp.");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 160, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        total_harga.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        total_harga.setBorder(null);
        total_harga.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        total_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                total_hargaKeyTyped(evt);
            }
        });
        jPanel6.add(total_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 130, 30));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Rp.");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 160, 30));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uang_masuk.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        uang_masuk.setBorder(null);
        uang_masuk.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        uang_masuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                uang_masukKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                uang_masukKeyTyped(evt);
            }
        });
        jPanel7.add(uang_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 130, 30));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Rp.");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 160, 30));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setText("Uang Masuk");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btambah_barang.setBackground(new java.awt.Color(255, 255, 255));
        btambah_barang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btambah_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Shopping_Cart_30px.png"))); // NOI18N
        btambah_barang.setText("Tambah");
        btambah_barang.setBorder(null);
        btambah_barang.setContentAreaFilled(false);
        btambah_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambah_barangActionPerformed(evt);
            }
        });
        jPanel8.add(btambah_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 100, 40));

        jLabel18.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("*keranjang");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, -1, 20));

        stok_tampung.setForeground(new java.awt.Color(216, 216, 251));
        stok_tampung.setText("jLabel19");
        jPanel2.add(stok_tampung, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, -1, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        total_keseluruhan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        total_keseluruhan.setBorder(null);
        total_keseluruhan.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        total_keseluruhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                total_keseluruhanActionPerformed(evt);
            }
        });
        total_keseluruhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                total_keseluruhanKeyTyped(evt);
            }
        });
        jPanel9.add(total_keseluruhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 130, 30));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Rp.");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 160, 30));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel20.setText("Total Keseluruhan");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        ket.setBackground(new java.awt.Color(216, 216, 251));
        ket.setForeground(new java.awt.Color(216, 216, 251));
        ket.setBorder(null);
        ket.setCaretColor(new java.awt.Color(216, 216, 251));
        ket.setDisabledTextColor(new java.awt.Color(216, 216, 251));
        ket.setEnabled(false);
        ket.setSelectedTextColor(new java.awt.Color(216, 216, 251));
        ket.setSelectionColor(new java.awt.Color(216, 216, 251));
        jPanel2.add(ket, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, 60, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bstruk.setBackground(new java.awt.Color(255, 255, 255));
        bstruk.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bstruk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Print_30px.png"))); // NOI18N
        bstruk.setText("Cetak Struk");
        bstruk.setBorder(null);
        bstruk.setContentAreaFilled(false);
        bstruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bstrukActionPerformed(evt);
            }
        });
        jPanel10.add(bstruk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 490, 160, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 950, 570));

        setSize(new java.awt.Dimension(962, 710));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homepageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homepageActionPerformed
        // TODO add your handling code here:
        hm = new Home();
        hm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homepageActionPerformed

    private void uang_kembaliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uang_kembaliKeyTyped
        // TODO add your handling code here;
    }//GEN-LAST:event_uang_kembaliKeyTyped

    private void jumlah_beliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlah_beliKeyTyped
        // TODO add your handling code here:
        char a = evt.getKeyChar();
        if (!(Character.isDigit(a))) {
            evt.consume();
        }
        if (jumlah_beli.getText().length() >=8) {
            evt.consume();
        }
    }//GEN-LAST:event_jumlah_beliKeyTyped

    private void harga_satuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga_satuanKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_harga_satuanKeyTyped

    private void total_hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_hargaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_total_hargaKeyTyped

    private void uang_masukKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uang_masukKeyTyped
        // TODO add your handling code here:
        char a = evt.getKeyChar();
        if (!(Character.isDigit(a))) {
            evt.consume();
        }
        if (uang_masuk.getText().length() >=8) {
            evt.consume();
        }
    }//GEN-LAST:event_uang_masukKeyTyped

    private void tb_inputhpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_inputhpMouseClicked
        edit();
    }//GEN-LAST:event_tb_inputhpMouseClicked

    private void tb_inputhpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_inputhpMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_inputhpMousePressed

    private void btambah_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambah_barangActionPerformed
        // TODO add your handling code here:
        simpan_keranjang();
        harga_keseluruhan();
    }//GEN-LAST:event_btambah_barangActionPerformed

    private void jumlah_beliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlah_beliKeyReleased
        // TODO add your handling code here:
        if (jumlah_beli.getText().toString().equals("")) {
            jumlah_beli.setText("");
            total_harga.setText("0");
        }else{
            long n1, n2, jumlah;
            String d;
            n1 = Long.valueOf(harga_satuan.getText());
            n2 = Long.valueOf(jumlah_beli.getText());
            jumlah = n1 * n2;
            d = String.valueOf(jumlah);
            total_harga.setText(d);
            
            int a    = Integer.parseInt(jumlah_beli.getText());
            int b    = Integer.parseInt(harga_satuan.getText());
            int stok = Integer.parseInt(String.valueOf(stok_tampung.getText()));
            if (a > stok) {
                JOptionPane.showMessageDialog(null,"Stok Hanya Tersedia " + stok);
            }
            else{
                jumlah = a * b;
                total_harga.setText(String.valueOf(jumlah));
            }
        }
    }//GEN-LAST:event_jumlah_beliKeyReleased

    private void total_keseluruhanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_keseluruhanKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_total_keseluruhanKeyTyped

    private void harga_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harga_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga_satuanActionPerformed

    private void uang_masukKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uang_masukKeyReleased
        // TODO add your handling code here:
        long a = Long.valueOf(total_keseluruhan.getText());
        long b = Long.valueOf(uang_masuk.getText());
        long e;
        String d;
        e = b-a;
        d = Long.toString(e);
        uang_kembali.setText(d);
    }//GEN-LAST:event_uang_masukKeyReleased

    private void total_keseluruhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_total_keseluruhanActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_total_keseluruhanActionPerformed

    private void bstrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bstrukActionPerformed
        // TODO add your handling code here:
        if (Desktop.isDesktopSupported()) {
            try{
                Desktop.getDesktop().browse(new URI("http://localhost:81/laporan/struk.php"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_bstrukActionPerformed

    private void bselesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bselesaiActionPerformed
        // TODO add your handling code here:
        long a = 0;
        long b = 0;
        if (nama_pembeli.getText().equals("") || uang_masuk.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"Isi Data Terlebih Dahulu");
        }
        else {
            a = Long.valueOf(total_keseluruhan.getText());
            b = Long.valueOf(uang_masuk.getText());
            if (b < a){
                JOptionPane.showMessageDialog(null, "Uang Anda Kurang");
            }else {
                simpan_selesai();
                total_keseluruhan.setText("");
                nama_pembeli.setText("");
                uang_masuk.setText("");
                uang_kembali.setText("");
            }
        }
    }//GEN-LAST:event_bselesaiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bselesai;
    private javax.swing.JButton bstruk;
    private javax.swing.JButton btambah_barang;
    private javax.swing.JTextField harga_satuan;
    private javax.swing.JButton homepage;
    private javax.swing.JTextField id_beli;
    private javax.swing.JTextField id_hp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jumlah_beli;
    private javax.swing.JTextField ket;
    private javax.swing.JTextField merek;
    private javax.swing.JTextField nama_pembeli;
    private javax.swing.JLabel stok_tampung;
    private javax.swing.JTable tb_inputhp;
    private javax.swing.JTable tb_keranjang;
    private javax.swing.JTextField total_harga;
    private javax.swing.JTextField total_keseluruhan;
    private javax.swing.JTextField uang_kembali;
    private javax.swing.JTextField uang_masuk;
    private javax.swing.JTextField warna;
    // End of variables declaration//GEN-END:variables
}
