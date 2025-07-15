/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemmanajemenmieayam;

/**
 *
 * @author Malik
 */
public class KategoriCombo {
    private int id;
    private String nama;

    public KategoriCombo(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    // PENTING: Override toString() method ini
    // JComboBox akan menggunakan metode ini untuk menampilkan teks di dalam drop-down
    @Override
    public String toString() {
        return nama;
    }
}
