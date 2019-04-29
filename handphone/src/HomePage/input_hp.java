package HomePage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author Muhamad Miftah
 */
public class input_hp extends javax.swing.JFrame {
    Connection con = new Login.koneksi().getConnection();
    ResultSet rs;
    Statement st;
    PreparedStatement prst;
    String sql;
    
    public Home hm;
    public DefaultTableModel input_table;
    
    public input_hp() {
        initComponents();
        tampil_table();
        tampildata_hp();
        table();
        autokode();
        id.setEnabled(false);
        bedit.setEnabled(false);
        bhapus.setEnabled(false);
    }
    
    public void table(){
        for(int a=0; a < table_inputhp.getColumnCount(); a++){
            Class <?> b = table_inputhp.getColumnClass(a);
            table_inputhp.setDefaultEditor(b, null);
        }
    }
    
    public void tampil_table(){
        Object[]tampil_table = {"ID HP","Merek HP","Warna HP","Harga HP","Stok HP"};
        input_table = new DefaultTableModel(null, tampil_table);
        table_inputhp.setModel(input_table);
    }
    
    public void tampildata_hp(){
        try{
            st = con.createStatement();
            input_table.getDataVector().removeAllElements();
            input_table.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM input_hp WHERE hapus = '0'");
            while(rs.next()){
                Object[]data={
                    rs.getString("id_hp"),
                    rs.getString("merek_hp"),
                    rs.getString("warna_hp"),
                    rs.getString("harga_hp"),
                    rs.getString("stok_hp"),};
                input_table.addRow(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void autokode(){
        try {
            sql = "SELECT * FROM input_hp ORDER BY id_hp DESC";
            prst = con.prepareStatement(sql);
            rs = prst.executeQuery();
            if (rs.next()) {
                String kode_otomatis = rs.getString("id_hp").substring(1);
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
                id.setText("H"+nol+an);
            }else{
                id.setText("H00001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reset(){
        bsimpan.setEnabled(true);
        merek.setText("");
        harga.setText("");
        warna.setText("");
        stok.setText("");
    }
    
    public void simpan(){
        if (id.getText().equals("") || merek.getText().equals("") || warna.getText().equals("") || harga.getText().equals("") 
                || stok.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Harap Lengkapi Data!");
        }else{
            try {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO input_hp SET id_hp ='" + id.getText() + "', merek_hp='" + merek.getText() + "',"
                        + "harga_hp='" + harga.getText() +"', warna_hp='" + warna.getText() + "', stok_hp='" + stok.getText() + "', hapus='0'");
                JOptionPane.showMessageDialog(null, "Data Tersimpan","Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampildata_hp();
                autokode();
                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void edit(){
        try {
            int bar = table_inputhp.getSelectedRow();
            String a = table_inputhp.getValueAt(bar, 0).toString();
            String b = table_inputhp.getValueAt(bar, 1).toString();
            String c = table_inputhp.getValueAt(bar, 2).toString();
            String d = table_inputhp.getValueAt(bar, 3).toString();
            String e = table_inputhp.getValueAt(bar, 4).toString();

            id.setText(a);
            merek.setText(b);
            warna.setText(c);
            harga.setText(d);
            stok.setText(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void hapus(){
        if (id.getText().equals("") || merek.getText().equals("") || warna.getText().equals("") || harga.getText().equals("") 
                || stok.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Pilih Data Yang Akan Dihapus!");
        }else{
            try {
            sql = "DELETE FROM input_hp WHERE id_hp = '"+id.getText()+"'";
            prst = con.prepareStatement(sql);
            prst.executeUpdate();
            prst.close();
            JOptionPane.showMessageDialog(null, "Data Terhapus","Informasi", JOptionPane.INFORMATION_MESSAGE);
            tampildata_hp();
            reset();
            bedit.setEnabled(true);
            bhapus.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void update(){
         if (id.getText().equals("") || merek.getText().equals("") || warna.getText().equals("") || harga.getText().equals("") 
                || stok.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Harap Lengkapi Data!");
        }else{
            try {
                sql = "UPDATE input_hp SET "
                        + "id_hp = '"+id.getText()+"',"
                        + "merek_hp = '"+merek.getText()+"',"
                        + "harga_hp = '"+harga.getText()+"',"
                        + "warna_hp = '"+warna.getText()+"',"
                        + "stok_hp = '"+stok.getText()+"'"
                        + "WHERE id_hp = '"+id.getText()+"'";
                prst = con.prepareStatement(sql);
                prst.executeUpdate();
                prst.close();
                JOptionPane.showMessageDialog(null, "Data Terupdate","Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampildata_hp();
                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        stok = new javax.swing.JTextField();
        warna = new javax.swing.JTextField();
        merek = new javax.swing.JTextField();
        id = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        harga = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_inputhp = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        breset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        bsimpan = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        bedit = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        bhapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(24, 199, 224));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Smartphones_96px.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 100));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("INPUT HANDPHONE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 370, 60));

        homepage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Menu_48px_1.png"))); // NOI18N
        homepage.setBorder(null);
        homepage.setContentAreaFilled(false);
        homepage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homepageActionPerformed(evt);
            }
        });
        jPanel1.add(homepage, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, -1, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 100));

        jPanel2.setBackground(new java.awt.Color(216, 216, 251));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Harga Satuan");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Warna");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Merk");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("ID Handphone");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        stok.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        stok.setBorder(null);
        stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stokKeyTyped(evt);
            }
        });
        jPanel2.add(stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 160, 30));

        warna.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        warna.setBorder(null);
        jPanel2.add(warna, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 160, 30));

        merek.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        merek.setBorder(null);
        jPanel2.add(merek, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 160, 30));

        id.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        id.setBorder(null);
        id.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        jPanel2.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 160, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        harga.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        harga.setBorder(null);
        harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaActionPerformed(evt);
            }
        });
        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hargaKeyTyped(evt);
            }
        });
        jPanel3.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 120, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Rp.");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 160, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Stok");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

        table_inputhp.setModel(new javax.swing.table.DefaultTableModel(
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
        table_inputhp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                table_inputhpMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_inputhp);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 510, 230));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        breset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        breset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Reset_30px.png"))); // NOI18N
        breset.setText("Reset");
        breset.setBorder(null);
        breset.setContentAreaFilled(false);
        breset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bresetActionPerformed(evt);
            }
        });
        jPanel4.add(breset, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 300, 120, 40));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bsimpan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Plus_30px.png"))); // NOI18N
        bsimpan.setText("Simpan");
        bsimpan.setBorder(null);
        bsimpan.setContentAreaFilled(false);
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });
        jPanel5.add(bsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 120, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bedit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Edit_Column_30px.png"))); // NOI18N
        bedit.setText("Edit");
        bedit.setBorder(null);
        bedit.setContentAreaFilled(false);
        bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditActionPerformed(evt);
            }
        });
        jPanel6.add(bedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 300, 120, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bhapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_Trash_30px.png"))); // NOI18N
        bhapus.setText("Hapus");
        bhapus.setBorder(null);
        bhapus.setContentAreaFilled(false);
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });
        jPanel7.add(bhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 120, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 940, 400));

        setSize(new java.awt.Dimension(950, 540));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homepageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homepageActionPerformed
        // TODO add your handling code here:
        hm = new Home();
        hm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homepageActionPerformed

    private void table_inputhpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_inputhpMousePressed
        // TODO add your handling code here:
        edit();
        bedit.setEnabled(true);
        bhapus.setEnabled(true);
        bsimpan.setEnabled(false);
    }//GEN-LAST:event_table_inputhpMousePressed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_bsimpanActionPerformed

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_beditActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_bhapusActionPerformed

    private void bresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bresetActionPerformed
        // TODO add your handling code here:
        reset();
        bsimpan.setEnabled(true);
    }//GEN-LAST:event_bresetActionPerformed

    private void hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyTyped
        // TODO add your handling code here:
        char a = evt.getKeyChar();
        if (!(Character.isDigit(a))) {
            evt.consume();
        }
        if (harga.getText().length() >=8) {
            evt.consume();
        }
    }//GEN-LAST:event_hargaKeyTyped

    private void stokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokKeyTyped
        // TODO add your handling code here:
        char a = evt.getKeyChar();
        if (!(Character.isDigit(a))) {
            evt.consume();
        }
        if (stok.getText().length() >=8) {
            evt.consume();
        }
    }//GEN-LAST:event_stokKeyTyped

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(input_hp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(input_hp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(input_hp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(input_hp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new input_hp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bedit;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton breset;
    private javax.swing.JButton bsimpan;
    private javax.swing.JTextField harga;
    private javax.swing.JButton homepage;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField merek;
    private javax.swing.JTextField stok;
    private javax.swing.JTable table_inputhp;
    private javax.swing.JTextField warna;
    // End of variables declaration//GEN-END:variables
}
