/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemmanajemenmieayam;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class transaksi extends javax.swing.JPanel {

    koneksi dbsetting;
    String driver, database, user, pass;
    Object tabel;

    public transaksi() {
        initComponents();

        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        tabelDaftarPesanan.setModel(tableModel);

        btnStatusInit();

        populateDataMenuToCombobox();
        initToppingCombobox();
        initPelangganCombobox();
        populateDataToppingToCombobox();
        populateDataPelangganToCombobox();

        int currencyColumnIndex = 4; // Assuming the 3rd column (index 2) contains currency
        tabelDaftarPesanan.getColumnModel().getColumn(currencyColumnIndex).setCellRenderer(new CurrencyCellRenderer());

    }

    private javax.swing.table.DefaultTableModel tableModel = getDefaultTabel();

    private javax.swing.table.DefaultTableModel getDefaultTabel() {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Nama Menu", "Jumlah Menu Dibeli", "Nama Topping", "Jumlah Topping Dibeli", "Total"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int ColumnIndex) {
                return canEdit[ColumnIndex];
            }
        };

    }
    ;

    DefaultComboBoxModel<KategoriCombo> model_menu = new DefaultComboBoxModel<>();

    public void populateDataMenuToCombobox() {
        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = "SELECT id_menu, nama_menu FROM t_menu";
            ResultSet res = stt.executeQuery(sql);
            boolean flag = false;

            while (res.next()) {
                int id_kategori = Integer.parseInt(res.getString("id_menu"));
                String namaKategori = res.getString("nama_menu");
                model_menu.addElement(new KategoriCombo(id_kategori, namaKategori));
                flag = true;
            }
            
            checkMenuAda(flag);
            menuCombobox.setModel(model_menu);
            res.close();
            stt.close();
            kon.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
     public void checkMenuAda(boolean status) {
        if (!status) {
            menuCombobox.setEnabled(false);
            pesanKosong.setText("Menu Kosong, silahkan isi menu telebih dahulu");
            pesanKosong.setMaximumSize(new Dimension(20, 32000));
            pesanKosong.setForeground(Color.RED);
            jumlahItemMenu.setEnabled(false);
        }
    }

    DefaultComboBoxModel<KategoriCombo> model_topping = new DefaultComboBoxModel<>();

    public void populateDataToppingToCombobox() {
        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = "SELECT id_topping, nama_topping FROM t_topping";
            ResultSet res = stt.executeQuery(sql);
            boolean flag = false;

            while (res.next()) {
                int id_kategori = Integer.parseInt(res.getString("id_topping"));
                String namaKategori = res.getString("nama_topping");
                model_topping.addElement(new KategoriCombo(id_kategori, namaKategori));
                flag = true;
            }

            toppingCombobox.setModel(model_topping);
            res.close();
            stt.close();
            kon.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public void initToppingCombobox() {
        model_topping.addElement(new KategoriCombo(-1, "Tidak Pakai"));
        toppingCombobox.setModel(model_topping);

    }

    DefaultComboBoxModel<KategoriCombo> model_pelanggan = new DefaultComboBoxModel<>();

    public void populateDataPelangganToCombobox() {
        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = "SELECT id_pelanggan, nama FROM t_pelanggan";
            ResultSet res = stt.executeQuery(sql);
            boolean flag = false;

            while (res.next()) {
                int id_kategori = Integer.parseInt(res.getString("id_pelanggan"));
                String namaKategori = res.getString("nama");
                model_pelanggan.addElement(new KategoriCombo(id_kategori, namaKategori));
                flag = true;
            }

            pelangganCombobox.setModel(model_pelanggan);
            res.close();
            stt.close();
            kon.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public void initPelangganCombobox() {
        model_pelanggan.addElement(new KategoriCombo(-1, "Tidak Pakai"));
        pelangganCombobox.setModel(model_pelanggan);
    }

    public void btnStatusInit() {
        field_total_pesanan.setEditable(false);
        jumlahItemTopping.setEnabled(false);
        pesanKosong.setText("");
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
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jumlahItemMenu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        toppingCombobox = new javax.swing.JComboBox();
        menuCombobox = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jumlahItemTopping = new javax.swing.JTextField();
        pesanKosong = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tambahPesanan = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDaftarPesanan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        pelangganCombobox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        field_total_pesanan = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(45, 45, 45));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled166_20250717014453.png"))); // NOI18N
        jLabel3.setText("Transaksi");

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
                .addContainerGap(760, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(45, 45, 45));
        jLabel5.setText("Menu");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(45, 45, 45));
        jLabel6.setText("Jumlah Item Menu");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(45, 45, 45));
        jLabel7.setText("Topping");

        toppingCombobox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        toppingCombobox.setForeground(new java.awt.Color(45, 45, 45));
        toppingCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        toppingCombobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                toppingComboboxItemStateChanged(evt);
            }
        });

        menuCombobox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        menuCombobox.setForeground(new java.awt.Color(45, 45, 45));
        menuCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(45, 45, 45));
        jLabel8.setText("Jumlah Item Topping");

        jumlahItemTopping.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jumlahItemTopping.setForeground(new java.awt.Color(45, 45, 45));

        pesanKosong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pesanKosong.setForeground(new java.awt.Color(45, 45, 45));
        pesanKosong.setText("test");
        pesanKosong.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pesanKosong)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jumlahItemTopping, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(toppingCombobox, javax.swing.GroupLayout.Alignment.LEADING, 0, 203, Short.MAX_VALUE)
                        .addComponent(jumlahItemMenu, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menuCombobox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(34, 34, 34)
                .addComponent(menuCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pesanKosong)
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jumlahItemMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(toppingCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jumlahItemTopping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        tambahPesanan.setBackground(new java.awt.Color(255, 204, 153));
        tambahPesanan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tambahPesanan.setForeground(new java.awt.Color(40, 26, 13));
        tambahPesanan.setText("Tambah Pesanan");
        tambahPesanan.setPreferredSize(new java.awt.Dimension(90, 30));
        tambahPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahPesananActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(tambahPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(tambahPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 45, 45));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled169_20250717015111.png"))); // NOI18N
        jLabel1.setText("Daftar Pesanan");

        btn_simpan.setBackground(new java.awt.Color(255, 204, 153));
        btn_simpan.setForeground(new java.awt.Color(40, 26, 13));
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tabelDaftarPesanan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabelDaftarPesanan.setForeground(new java.awt.Color(45, 45, 45));
        tabelDaftarPesanan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelDaftarPesanan);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 45, 45));
        jLabel2.setText("Pilih Pelanggan");

        pelangganCombobox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pelangganCombobox.setForeground(new java.awt.Color(45, 45, 45));
        pelangganCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Total Pesanan");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(60, 60, 60)
                                    .addComponent(pelangganCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(56, 56, 56)
                                    .addComponent(field_total_pesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pelangganCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(field_total_pesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jPanel12.add(jPanel1, "card2");

        add(jPanel12, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tambahPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahPesananActionPerformed
        // TODO add your handling code here:
        pesananToTablePesanan();

    }//GEN-LAST:event_tambahPesananActionPerformed

    private void toppingComboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_toppingComboboxItemStateChanged
        // TODO add your handling code here:
        KategoriCombo itemTopping = (KategoriCombo) model_topping.getSelectedItem();
        if (itemTopping.getId() == -1) {
            jumlahItemTopping.setText("");
            jumlahItemTopping.setEnabled(false);
        } else {
            jumlahItemTopping.setEnabled(true);
        }
    }//GEN-LAST:event_toppingComboboxItemStateChanged

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        invokeDataToTableDB();
    }//GEN-LAST:event_btn_simpanActionPerformed

    double subtotal = 0;
    String[] dbColumnNames = {
        "id_menu", "jumlah_item_menu", "id_topping", "jumlah_item_topping", "subtotal"
    };
    private javax.swing.table.DefaultTableModel tableInsertToDB = new DefaultTableModel(dbColumnNames, 0);

    public void invokeDataToTableDB() {
        PreparedStatement pstmtTransaksi = null;
        PreparedStatement pstmtDetail = null;
        Connection kon = null;

        int rowCount = tableInsertToDB.getRowCount();

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada item pesanan dalam daftar untuk disimpan.");
            return;
        }

        int idPelanggan = -1;
        if (model_pelanggan.getSize() > 0) {
            KategoriCombo itemPelanggan = (KategoriCombo) model_pelanggan.getSelectedItem();
            if (itemPelanggan.getId() != -1) {
                idPelanggan = itemPelanggan.getId();
            }
        }

        try {
            Class.forName(driver);
            kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();

            kon.setAutoCommit(false);

            String sqlInsertTransaksi = "INSERT INTO t_transaksi (tgl_transaksi, id_pelanggan, total_bayar) VALUES (?, ?, ?)";
            pstmtTransaksi = kon.prepareStatement(sqlInsertTransaksi, Statement.RETURN_GENERATED_KEYS);

            Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));

            pstmtTransaksi.setTimestamp(1, ts);

            // Set id_pelanggan (bisa NULL)
            if (idPelanggan != -1) {
                pstmtTransaksi.setInt(2, idPelanggan);
            } else {
                pstmtTransaksi.setNull(2, java.sql.Types.INTEGER);
            }

            pstmtTransaksi.setDouble(3, 0.0);

            pstmtTransaksi.executeUpdate();

            int id_transaksi_baru = -1;
            try (ResultSet rs = pstmtTransaksi.getGeneratedKeys()) {
                if (rs.next()) {
                    id_transaksi_baru = rs.getInt(1);
                }
            }

            if (id_transaksi_baru == -1) {
                throw new SQLException("Gagal mendapatkan ID transaksi yang baru dibuat.");
            }

            String sqlInsertDetail = "INSERT INTO t_detail_transaksi (id_transaksi, id_menu, jumlah_item_menu, id_topping, jumlah_item_topping, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            pstmtDetail = kon.prepareStatement(sqlInsertDetail);

            double totalBayarKeseluruhan = 0;

            for (int i = 0; i < rowCount; i++) {
                // Kolom dbInsertTableModel:
                // 0: id_menu (Integer)
                // 1: jumlah_item_menu (Integer)
                // 2: id_topping (Integer, bisa null)
                // 3: jumlah_item_topping (Integer)
                // 4: subtotal (Double)

                int id_menu = (int) tableInsertToDB.getValueAt(i, 0);
                int jumlah_item_menu = (int) tableInsertToDB.getValueAt(i, 1);
                // Penting: Baca sebagai Integer agar bisa menangani null dari model
                int id_topping_from_model = (int) tableInsertToDB.getValueAt(i, 2);
                int jumlah_item_topping = (int) tabelDaftarPesanan.getValueAt(i, 3);
                double subtotal_item = (double) tableInsertToDB.getValueAt(i, 4);

                pstmtDetail.setInt(1, id_transaksi_baru);
                pstmtDetail.setInt(2, id_menu);
                pstmtDetail.setInt(3, jumlah_item_menu);

                // Set id_topping: cek apakah null
                if (id_topping_from_model != -1) {
                    pstmtDetail.setInt(4, id_topping_from_model);
                } else {
                    pstmtDetail.setNull(4, java.sql.Types.INTEGER);
                }
                pstmtDetail.setInt(5, jumlah_item_topping);
                pstmtDetail.setDouble(6, subtotal_item);

                pstmtDetail.addBatch(); // Tambahkan pernyataan ke batch
                totalBayarKeseluruhan += subtotal_item;
            }

            pstmtDetail.executeBatch();

            String sqlUpdateTotal = "UPDATE t_transaksi SET total_bayar = ? WHERE id_transaksi = ?";
            try (PreparedStatement pstmtUpdateTotal = kon.prepareStatement(sqlUpdateTotal)) {
                pstmtUpdateTotal.setDouble(1, totalBayarKeseluruhan);
                pstmtUpdateTotal.setInt(2, id_transaksi_baru);
                pstmtUpdateTotal.executeUpdate();
            }

            kon.commit();
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan! ID Transaksi: " + id_transaksi_baru);

            // --- 6. Pembersihan UI dan Model ---
            tableModel.setRowCount(0); // Bersihkan JTable tampilan
            tableInsertToDB.setRowCount(0);

        } catch (SQLException e) {

            try {
                if (kon != null) {
                    kon.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error saat rollback: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error saat rollback transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "Error saat menyimpan transaksi: " + e.getMessage(), "Error Transaksi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Penting untuk debugging
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (pstmtDetail != null) {
                    pstmtDetail.close();
                }
                if (pstmtTransaksi != null) {
                    pstmtTransaksi.close();
                }
                if (kon != null) {
                    kon.close(); // Pastikan koneksi ditutup
                }
            } catch (SQLException e) {
                System.err.println("Error menutup sumber daya database: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "Error menutup sumber daya database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void pesananToTablePesanan() {
        try {
            KategoriCombo itemMenu = (KategoriCombo) model_menu.getSelectedItem();
            String menu = itemMenu.getNama();

            int jumlahQtyMenu = Integer.parseInt(jumlahItemMenu.getText());

            if (jumlahQtyMenu <= 0) {
                JOptionPane.showMessageDialog(null, "Jumlah Item tidak boleh minus atau 0!", "Error", JOptionPane.INFORMATION_MESSAGE);
                jumlahItemMenu.setText("");
                jumlahItemMenu.requestFocus();
                return;
            }

            String topping = "Tidak Ada";
            int toppingID = -1;
            if (model_topping.getSize() > 0) {
                KategoriCombo itemTopping = (KategoriCombo) model_topping.getSelectedItem();
                if (itemTopping.getId() != -1) {
                    topping = itemTopping.getNama();
                    toppingID = itemTopping.getId();
                }
            }

            int jumlahQtyTopping = 0;

            String jumlahQtyToppingString = jumlahItemTopping.getText();

            if (!"".equals(jumlahQtyToppingString)) {
                jumlahQtyTopping = Integer.parseInt(jumlahQtyToppingString);
            }

            if (jumlahQtyTopping < 0) {
                JOptionPane.showMessageDialog(null, "Jumlah Item tidak boleh minus atau 0!", "Error", JOptionPane.INFORMATION_MESSAGE);
                jumlahItemTopping.setText("");
                jumlahItemTopping.requestFocus();
                return;
            }

            double subtotalMenu = calSubTotalMenu(itemMenu.getId(), jumlahQtyMenu);
            double subtotalTopping = calSubTotalTopping(toppingID, jumlahQtyMenu);

            double subtotalPerRow = subtotalMenu + subtotalTopping;
            subtotal += subtotalPerRow;

            Locale indonesia = new Locale("in", "ID");
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(indonesia);

            String formattedRupiah = rupiahFormat.format(subtotal);

            field_total_pesanan.setText(formattedRupiah);

            Object dataBuatDisplay[] = new Object[5];
            dataBuatDisplay[0] = menu;
            dataBuatDisplay[1] = jumlahQtyMenu;
            dataBuatDisplay[2] = topping;
            dataBuatDisplay[3] = jumlahQtyTopping;
            dataBuatDisplay[4] = subtotalPerRow;

            Object dataBuatDB[] = new Object[5];
            dataBuatDB[0] = itemMenu.getId();
            dataBuatDB[1] = jumlahQtyMenu;
            dataBuatDB[2] = toppingID;
            dataBuatDB[3] = jumlahQtyTopping;
            dataBuatDB[4] = subtotalPerRow;

            tableModel.addRow(dataBuatDisplay);

            tableInsertToDB.addRow(dataBuatDB);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Jumlah item menu atau topping harus berupa angka!", "Error", JOptionPane.INFORMATION_MESSAGE);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Menu tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public double calSubTotalMenu(int itemId, int jumlahQTY) {
        if (itemId == -1) {
            return 0;
        }

        double subTotal = 0;

        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = String.format("SELECT harga FROM t_menu WHERE id_menu = %s", itemId);
            ResultSet res = stt.executeQuery(sql);
            double hargaMenu = 0;

            while (res.next()) {
                hargaMenu = res.getDouble("harga");
            }

            res.close();
            stt.close();
            kon.close();

            subTotal = hargaMenu * jumlahQTY;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        return subTotal;
    }

    public double calSubTotalTopping(int itemId, int jumlahQTY) {
        if (itemId == -1) {
            return 0;
        }

        double subTotal = 0;

        try {
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String sql = String.format("SELECT harga FROM t_topping WHERE id_topping = %s", itemId);
            ResultSet res = stt.executeQuery(sql);
            double hargaTopping = 0;

            while (res.next()) {
                hargaTopping = res.getDouble("harga");
            }

            res.close();
            stt.close();
            kon.close();

            subTotal = hargaTopping * jumlahQTY;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        return subTotal;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_simpan;
    private javax.swing.JTextField field_total_pesanan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jumlahItemMenu;
    private javax.swing.JTextField jumlahItemTopping;
    private javax.swing.JComboBox menuCombobox;
    private javax.swing.JComboBox pelangganCombobox;
    private javax.swing.JLabel pesanKosong;
    private javax.swing.JTable tabelDaftarPesanan;
    private javax.swing.JButton tambahPesanan;
    private javax.swing.JComboBox toppingCombobox;
    // End of variables declaration//GEN-END:variables
}
