package konfigurasi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Kelas untuk konfigurasi koneksi dan operasi database
 */
public class koneksi {

    // Metode untuk mendapatkan koneksi ke database
    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/db_laporan_hasil_pemancingan"; // Ganti nama database sesuai kebutuhan
        String user = "root"; // Sesuaikan user database Anda
        String password = ""; // Sesuaikan password database Anda

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }

    // Metode untuk menampilkan data dari tabel ke JTable
    public static DefaultTableModel tampilkanData(String query) {
        DefaultTableModel model = new DefaultTableModel();

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            // Mendapatkan metadata kolom
            int columnCount = rs.getMetaData().getColumnCount();

            // Menambahkan nama kolom ke model tabel
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Menambahkan data baris ke model tabel
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println("Gagal menampilkan data: " + e.getMessage());
        }

        return model;
    }

    // Metode utama untuk menguji koneksi dan menampilkan data di JTable
    public static void main(String[] args) {
        try {
            // Uji koneksi
            Connection conn = koneksi.getConnection();
            if (conn != null) {
                System.out.println("Koneksi berhasil ke database: " + conn.getCatalog());

                // Query untuk menampilkan data gabungan dari tabel-tabel
                String query = "SELECT akun_pemancing.username, akun_pemancing.fullname, "
                        + "data_pemancingan.jenis_ikan, data_pemancingan.ukuran_ikan, data_pemancingan.jumlah_ikan, "
                        + "data_pemancingan.lokasi, data_pemancingan.nama_pemancing, "
                        + "sewa_pancing.pancing_katrol, sewa_pancing.pancing_tajur, sewa_pancing.pancing_tegek, sewa_pancing.umpan "
                        + "FROM akun_pemancing "
                        + "LEFT JOIN data_pemancingan ON akun_pemancing.username = data_pemancingan.username "
                        + "LEFT JOIN sewa_pancing ON akun_pemancing.username = sewa_pancing.username";

                // Menampilkan data di JTable
                DefaultTableModel model = tampilkanData(query);

                // Membuat JTable untuk menampilkan data
                JTable table = new JTable(model);

                // Menampilkan nama kolom (opsional)
                for (int i = 0; i < model.getColumnCount(); i++) {
                    System.out.print(model.getColumnName(i) + "\t");
                }
                System.out.println();

                // Menampilkan data baris di konsol (opsional)
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        System.out.print(model.getValueAt(i, j) + "\t");
                    }
                    System.out.println();
                }

            } else {
                System.out.println("Koneksi gagal.");
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
