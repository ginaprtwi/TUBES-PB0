/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemmanajemenmieayam;

import javax.swing.*;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author HP
 */
public class frm_utama extends javax.swing.JFrame {

    koneksi dbsetting;
    String driver, database, user, pass;
    Object tabel;
    private final ZoneId WIB_ZONE = ZoneId.of("Asia/Jakarta");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss");

    /**
     * Creates new form frm_utama
     */
    public frm_utama() {
        initComponents();
        dbsetting = new koneksi();
        driver = dbsetting.SettingPanel("DBDriver");
        database = dbsetting.SettingPanel("DBDatabase");
        user = dbsetting.SettingPanel("DBUsername");
        pass = dbsetting.SettingPanel("DBPassword");
        tabel_transaksi_terbaru.setModel(tableModel);
        setMinWidthColumn();

        setTableLoad();

        int currencyColumnIndex = 2;
        tabel_transaksi_terbaru.getColumnModel().getColumn(currencyColumnIndex).setCellRenderer(new CurrencyCellRenderer());

        currencyColumnIndex = 8;
        tabel_transaksi_terbaru.getColumnModel().getColumn(currencyColumnIndex).setCellRenderer(new CurrencyCellRenderer());

        setLocationRelativeTo(null);
        kategori k = new kategori();
        list_menu m = new list_menu();
        list_topping list = new list_topping();
        pelanggan p = new pelanggan();
        list_transaksi t = new list_transaksi();
        transaksi d = new transaksi();

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

        TableColumnModel kolom = tabel_transaksi_terbaru.getColumnModel();

        TableColumn id_transaksi = kolom.getColumn(0);
        id_transaksi.setPreferredWidth(1);
        id_transaksi.setMinWidth(1);

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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_dashboard = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btn_kategori = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        btn_topping = new javax.swing.JButton();
        btn_detail = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_pelanggan = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_transaksi_terbaru = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jpanel20 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));

        jPanel2.setBackground(new java.awt.Color(240, 160, 100));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(230, 230, 230)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(230, 230, 230)));

        btn_dashboard.setBackground(new java.awt.Color(255, 204, 153));
        btn_dashboard.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_dashboard.setForeground(new java.awt.Color(40, 26, 13));
        btn_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled167_20250717015011.png"))); // NOI18N
        btn_dashboard.setText("Dashboard");
        btn_dashboard.setFocusPainted(false);
        btn_dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_dashboard.setIconTextGap(15);
        btn_dashboard.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_dashboard.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled167_20250717015011.png"))); // NOI18N
        btn_dashboard.setRolloverEnabled(false);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 199, 122));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 45, 45));
        jLabel1.setText("Menu");

        btn_kategori.setBackground(new java.awt.Color(255, 204, 153));
        btn_kategori.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_kategori.setForeground(new java.awt.Color(40, 26, 13));
        btn_kategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled165_20250717014416 (2).png"))); // NOI18N
        btn_kategori.setText("Kategori");
        btn_kategori.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_kategori.setIconTextGap(25);
        btn_kategori.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kategoriActionPerformed(evt);
            }
        });

        btn_menu.setBackground(new java.awt.Color(255, 204, 153));
        btn_menu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_menu.setForeground(new java.awt.Color(40, 26, 13));
        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled168_20250717015036.png"))); // NOI18N
        btn_menu.setText("Daftar Menu    ");
        btn_menu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_menu.setIconTextGap(15);
        btn_menu.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menuActionPerformed(evt);
            }
        });

        btn_topping.setBackground(new java.awt.Color(255, 204, 153));
        btn_topping.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_topping.setForeground(new java.awt.Color(40, 26, 13));
        btn_topping.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled169_20250717015111.png"))); // NOI18N
        btn_topping.setText("Daftar Topping");
        btn_topping.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_topping.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_topping.setIconTextGap(10);
        btn_topping.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_topping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_toppingActionPerformed(evt);
            }
        });

        btn_detail.setBackground(new java.awt.Color(255, 204, 153));
        btn_detail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_detail.setForeground(new java.awt.Color(40, 26, 13));
        btn_detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled166_20250717014453.png"))); // NOI18N
        btn_detail.setText("Transaksi");
        btn_detail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_detail.setIconTextGap(15);
        btn_detail.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detailActionPerformed(evt);
            }
        });

        btn_keluar.setBackground(new java.awt.Color(255, 204, 153));
        btn_keluar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_keluar.setForeground(new java.awt.Color(40, 26, 13));
        btn_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled171_20250717155425.png"))); // NOI18N
        btn_keluar.setText("Keluar");
        btn_keluar.setIconTextGap(5);
        btn_keluar.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 199, 122));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(45, 45, 45));
        jLabel3.setText("Transaksi");

        btn_pelanggan.setBackground(new java.awt.Color(255, 204, 153));
        btn_pelanggan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_pelanggan.setForeground(new java.awt.Color(40, 26, 13));
        btn_pelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled170_20250717050354.png"))); // NOI18N
        btn_pelanggan.setText("Pelanggan");
        btn_pelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_pelanggan.setIconTextGap(15);
        btn_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pelangganActionPerformed(evt);
            }
        });

        btn_transaksi.setBackground(new java.awt.Color(255, 204, 153));
        btn_transaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btn_transaksi.setForeground(new java.awt.Color(40, 26, 13));
        btn_transaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled169_20250717015111.png"))); // NOI18N
        btn_transaksi.setText("Daftar Transaksi");
        btn_transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_transaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_transaksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_transaksi.setIconTextGap(10);
        btn_transaksi.setPreferredSize(new java.awt.Dimension(145, 30));
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(btn_detail, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_topping, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_kategori, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_pelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(btn_transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_topping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(btn_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(248, 246, 240));

        jTabbedPane2.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0; // Tidak menampilkan area tab
            }
        });
        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(230, 230, 230)));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(1461, 802));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 45, 45));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled167_20250717015011.png"))); // NOI18N
        jLabel2.setText("Dashboard");

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));

        jPanel18.setBackground(new java.awt.Color(248, 246, 240));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(45, 45, 45));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/Untitled169_20250717015111.png"))); // NOI18N
        jLabel6.setText("Transaksi Terbaru");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        tabel_transaksi_terbaru.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabel_transaksi_terbaru.setForeground(new java.awt.Color(45, 45, 45));
        tabel_transaksi_terbaru.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_transaksi_terbaru.setSelectionForeground(new java.awt.Color(45, 45, 45));
        jScrollPane1.setViewportView(tabel_transaksi_terbaru);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel15.setBackground(new java.awt.Color(248, 246, 240));

        jPanel14.setBackground(new java.awt.Color(248, 246, 240));

        jpanel20.setBackground(new java.awt.Color(248, 246, 240));
        jpanel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jpanel20.setAlignmentX(1000.0F);
        jpanel20.setAlignmentY(100.0F);

        javax.swing.GroupLayout jpanel20Layout = new javax.swing.GroupLayout(jpanel20);
        jpanel20.setLayout(jpanel20Layout);
        jpanel20Layout.setHorizontalGroup(
            jpanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );
        jpanel20Layout.setVerticalGroup(
            jpanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        jPanel16.setBackground(new java.awt.Color(248, 246, 240));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(45, 45, 45));
        jLabel5.setText("Jam Buka : 07.00 - 13.00 WIB ");

        jPanel21.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        jPanel22.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(79, 79, 79)
                .addComponent(jLabel5)
                .addGap(30, 30, 30)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(234, 234, 234))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(248, 246, 240));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(45, 45, 45));
        jLabel7.setText("Buka Setiap Hari");

        jPanel23.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(365, 365, 365))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(248, 246, 240));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(45, 45, 45));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistemmanajemenmieayam/icon/mieayam.png"))); // NOI18N
        jLabel4.setAlignmentX(1000.0F);
        jLabel4.setAlignmentY(500.0F);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(109, 109, 109))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Dashboard", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Kategori", jPanel6);
        jTabbedPane2.setComponentAt(1, new kategori());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Daftar Menu", jPanel7);
        jTabbedPane2.setComponentAt(2, new list_menu());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Daftar Topping", jPanel8);
        jTabbedPane2.setComponentAt(3, new list_topping());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Pelanggan", jPanel9);
        jTabbedPane2.setComponentAt(4, new pelanggan());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Transaksi", jPanel10);
        jTabbedPane2.setComponentAt(5, new transaksi());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1452, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Daftar Transaksi", jPanel11);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(0);
        tableModel.setRowCount(0);
        setTableLoad();

    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kategoriActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(1);
        jTabbedPane2.setComponentAt(1, new kategori());

    }//GEN-LAST:event_btn_kategoriActionPerformed

    private void btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menuActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(2);
        jTabbedPane2.setComponentAt(2, new list_menu());

    }//GEN-LAST:event_btn_menuActionPerformed

    private void btn_toppingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_toppingActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(3);
        jTabbedPane2.setComponentAt(3, new list_topping());

    }//GEN-LAST:event_btn_toppingActionPerformed

    private void btn_detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detailActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(5);
        jTabbedPane2.setComponentAt(5, new transaksi());
    }//GEN-LAST:event_btn_detailActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:

        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Apakah kamu yakin ingin keluar?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pelangganActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(4);
        jTabbedPane2.setComponentAt(4, new pelanggan());
    }//GEN-LAST:event_btn_pelangganActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(6);
        jTabbedPane2.setComponentAt(6, new list_transaksi());
    }//GEN-LAST:event_btn_transaksiActionPerformed

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
            java.util.logging.Logger.getLogger(frm_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_utama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_detail;
    private javax.swing.JButton btn_kategori;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_menu;
    private javax.swing.JButton btn_pelanggan;
    private javax.swing.JButton btn_topping;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel jpanel20;
    private javax.swing.JTable tabel_transaksi_terbaru;
    // End of variables declaration//GEN-END:variables
}
