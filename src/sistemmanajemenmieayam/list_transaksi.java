/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemmanajemenmieayam;

import javax.swing.*;
import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author HP
 */
public class list_transaksi extends javax.swing.JPanel {

    koneksi dbsetting;
    String driver, database, user, pass;
    Object tabel;
    private final ZoneId WIB_ZONE = ZoneId.of("Asia/Jakarta");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss");

    public list_transaksi() {
        initComponents();
        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        tabel_detail_transaksi.setModel(tableModel);
        setMinWidthColumn();

        setTableLoad();

        int currencyColumnIndex = 2;
        tabel_detail_transaksi.getColumnModel().getColumn(currencyColumnIndex).setCellRenderer(new CurrencyCellRenderer());

        currencyColumnIndex = 8;
        tabel_detail_transaksi.getColumnModel().getColumn(currencyColumnIndex).setCellRenderer(new CurrencyCellRenderer());

    }

    private javax.swing.table.DefaultTableModel tableModel = getDefaultTabel();

    private javax.swing.table.DefaultTableModel getDefaultTabel() {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Transaksi", "Tanggal Transaksi", "Total Bayar", "Nama Pelanggan",
                    "Menu", "QTY Menu", "Topping", "QTY Topping", "Subtotal"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int ColumnIndex) {
                return canEdit[ColumnIndex];
            }
        };

    }
    ;
    
    Object data[] = new Object[tableModel.getColumnCount()];

    private void setTableLoad() {

        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = "SELECT "
                    + "    t_transaksi.*, "
                    + "    t_detail_transaksi.*, "
                    + "    COALESCE(t_pelanggan.nama, 'Tidak Pakai') AS nama_pelanggan, "
                    + "    t_menu.nama_menu, "
                    + "    COALESCE(t_topping.nama_topping, 'Tidak  Pakai') AS nama_topping "
                    + "FROM "
                    + "    t_transaksi "
                    + "INNER JOIN "
                    + "    t_detail_transaksi ON t_transaksi.id_transaksi = t_detail_transaksi.id_transaksi "
                    + "LEFT JOIN "
                    + "    t_pelanggan ON t_transaksi.id_pelanggan = t_pelanggan.id_pelanggan "
                    + "INNER JOIN "
                    + "    t_menu ON t_detail_transaksi.id_menu = t_menu.id_menu "
                    + "LEFT JOIN "
                    + "    t_topping ON t_detail_transaksi.id_topping = t_topping.id_topping "
                    + "ORDER BY"
                    + "    t_transaksi.id_transaksi ASC, "
                    + "    t_detail_transaksi.id_detail_transaksi ASC";

            ResultSet res = stt.executeQuery(sql);

            String currentTransaksiId = null;
            while (res.next()) {

                String resIdTransaksi = res.getString("id_transaksi");

                if (currentTransaksiId == null || !currentTransaksiId.equals(resIdTransaksi)) {
                    // First time seeing this transaction ID or new transaction
                    data[0] = resIdTransaksi;

                    data[1] = formatter.format(res.getTimestamp("tgl_transaksi").toInstant().atZone(WIB_ZONE)); // Use getTimestamp for DATETIME

                    data[2] = res.getDouble("total_bayar");
                    data[3] = res.getString("nama_pelanggan");
                    currentTransaksiId = resIdTransaksi; // Update current transaction ID
                } else {
                    // Same transaction ID as previous row, leave these cells empty
                    data[0] = ""; // Or null, depending on your table's renderer
                    data[1] = "";
                    data[2] = "";
                    data[3] = "";
                }
                data[4] = res.getString("nama_menu");
                data[5] = res.getString("jumlah_item_menu");
                data[6] = res.getString("nama_topping");
                data[7] = res.getString("jumlah_item_topping");
                data[8] = res.getDouble("subtotal");
                tableModel.addRow(data);
            }

            res.close();
            stt.close();
            kon.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public void setMinWidthColumn() {

        TableColumnModel kolom = tabel_detail_transaksi.getColumnModel();

        TableColumn id_transaksi = kolom.getColumn(0);
        id_transaksi.setPreferredWidth(1);
        id_transaksi.setMinWidth(1);

    }

    public void searchData() {
        tableModel.setRowCount(0);
        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();

            String selectedColumn = (String) cari_combo_box.getSelectedItem();
            String searchTerm = cari_txt_field.getText().trim();

            String sql = "SELECT "
                    + "    t_transaksi.*, "
                    + "    t_detail_transaksi.*, "
                    + "    COALESCE(t_pelanggan.nama, 'Tidak Pakai') AS nama_pelanggan, "
                    + "    t_menu.nama_menu, "
                    + "    COALESCE(t_topping.nama_topping, 'Tidak  Pakai') AS nama_topping "
                    + "FROM "
                    + "    t_transaksi "
                    + "INNER JOIN "
                    + "    t_detail_transaksi ON t_transaksi.id_transaksi = t_detail_transaksi.id_transaksi "
                    + "LEFT JOIN "
                    + "    t_pelanggan ON t_transaksi.id_pelanggan = t_pelanggan.id_pelanggan "
                    + "INNER JOIN "
                    + "    t_menu ON t_detail_transaksi.id_menu = t_menu.id_menu "
                    + "LEFT JOIN "
                    + "    t_topping ON t_detail_transaksi.id_topping = t_topping.id_topping ";

            if (!searchTerm.isEmpty()) {
                sql += " WHERE ";
                switch (selectedColumn) {
                    case "ID Transaksi":
                        sql += "t_transaksi.id_transaksi LIKE '%" + searchTerm + "%'";
                        break;
                    case "Nama Pelanggan":
                        sql += "t_pelanggan.nama LIKE '%" + searchTerm + "%'";
                        break;
                    case "Menu":
                        sql += "t_menu.nama_menu LIKE '%" + searchTerm + "%'";
                        break;
                    case "Topping":
                        sql += "t_topping.nama_topping LIKE '%" + searchTerm + "%'";
                        break;
                    case "QTY Menu":
                        sql += "t_detail_transaksi.jumlah_item_menu LIKE '%" + searchTerm + "%'";
                        break;
                    case "QTY Topping":
                        sql += "t_detail_transaksi.jumlah_item_topping LIKE '%" + searchTerm + "%'";
                        break;
                    case "Subtotal":
                        sql += "t_detail_transaksi.subtotal LIKE '%" + searchTerm + "%'";
                        break;
                    case "Total Bayar":
                        sql += "t_transaksi.total_bayar LIKE '%" + searchTerm + "%'";
                        break;
                    default:
                        sql += "t_transaksi.id_transaksi LIKE '%" + searchTerm + "%'";
                        break;
                }
            }

            sql += " ORDER BY"
                    + "    t_transaksi.id_transaksi ASC, "
                    + "    t_detail_transaksi.id_detail_transaksi ASC";

            ResultSet res = stt.executeQuery(sql);

            String currentTransaksiId = null;
            while (res.next()) {

                String resIdTransaksi = res.getString("id_transaksi");

                if (currentTransaksiId == null || !currentTransaksiId.equals(resIdTransaksi)) {
                    // First time seeing this transaction ID or new transaction
                    data[0] = resIdTransaksi;

                    data[1] = formatter.format(res.getTimestamp("tgl_transaksi").toInstant().atZone(WIB_ZONE)); // Use getTimestamp for DATETIME

                    data[2] = res.getDouble("total_bayar");
                    data[3] = res.getString("nama_pelanggan");
                    currentTransaksiId = resIdTransaksi; // Update current transaction ID
                } else {
                    // Same transaction ID as previous row, leave these cells empty
                    data[0] = ""; // Or null, depending on your table's renderer
                    data[1] = "";
                    data[2] = "";
                    data[3] = "";
                }
                data[4] = res.getString("nama_menu");
                data[5] = res.getString("jumlah_item_menu");
                data[6] = res.getString("nama_topping");
                data[7] = res.getString("jumlah_item_topping");
                data[8] = res.getDouble("subtotal");
                tableModel.addRow(data);
            }

            res.close();
            stt.close();
            kon.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
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

        jPanel12 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cari_txt_field = new javax.swing.JTextField();
        cari_combo_box = new javax.swing.JComboBox();
        cariBtn = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_detail_transaksi = new javax.swing.JTable();
        tampilBtn = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(45, 45, 45));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled169_20250717015111.png"))); // NOI18N
        jLabel3.setText("Daftar Transaksi");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(567, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(45, 45, 45));
        jLabel4.setText("Cari Berdasarkan");

        cari_txt_field.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cari_txt_field.setForeground(new java.awt.Color(45, 45, 45));

        cari_combo_box.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cari_combo_box.setForeground(new java.awt.Color(45, 45, 45));
        cari_combo_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID Transaksi", "Total Bayar", "Nama Pelanggan", "Menu", "QTY Menu", "Topping", "QTY Topping", "Subtotal", " ", " " }));

        cariBtn.setBackground(new java.awt.Color(255, 204, 153));
        cariBtn.setForeground(new java.awt.Color(40, 26, 13));
        cariBtn.setText("Cari");
        cariBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariBtnActionPerformed(evt);
            }
        });

        tabel_detail_transaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabel_detail_transaksi.setForeground(new java.awt.Color(45, 45, 45));
        tabel_detail_transaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_detail_transaksi.setSelectionForeground(new java.awt.Color(45, 45, 45));
        jScrollPane1.setViewportView(tabel_detail_transaksi);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        tampilBtn.setBackground(new java.awt.Color(255, 204, 153));
        tampilBtn.setForeground(new java.awt.Color(40, 26, 13));
        tampilBtn.setText("Tampilkan Semua List Transaksi");
        tampilBtn.setPreferredSize(new java.awt.Dimension(90, 30));
        tampilBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cari_combo_box, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cari_txt_field, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cariBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tampilBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cari_combo_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cari_txt_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cariBtn)
                    .addComponent(tampilBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel12.add(jPanel1, "card2");

        add(jPanel12, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cariBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariBtnActionPerformed
        // TODO add your handling code here:
        searchData();
    }//GEN-LAST:event_cariBtnActionPerformed

    private void tampilBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilBtnActionPerformed
        // TODO add your handling code here:
        tableModel.setRowCount(0);
        setTableLoad();
    }//GEN-LAST:event_tampilBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cariBtn;
    private javax.swing.JComboBox cari_combo_box;
    private javax.swing.JTextField cari_txt_field;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_detail_transaksi;
    private javax.swing.JButton tampilBtn;
    // End of variables declaration//GEN-END:variables
}
