/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uasgui;

/**
 *
 * @author abdulloh
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private static final String DATA_FOLDER = "data";

    // Menyimpan data ke file CSV dengan header jika belum ada
    public void simpanData(String username, String jenis, String ukuran, String jumlah, String lokasi, String pemancing) {
        String filePath = DATA_FOLDER + "/data_" + username + ".csv";
        File file = new File(filePath);

        boolean fileBaru = !file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Jika file baru, tulis header
            if (fileBaru) {
                writer.write("nama,harga,jumlah,tanggal");
                writer.newLine();
            }
            // Tulis data baru
            writer.write(jenis + "," + ukuran + "," + jumlah + "," + lokasi + "," + pemancing);
            writer.newLine();
            System.out.println("Data berhasil disimpan ke " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Menghapus data dari file CSV berdasarkan nama barang
    public boolean hapusData(String username, String nama) {
        String filePath = DATA_FOLDER + "/data_" + username + ".csv";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File tidak ditemukan: " + filePath);
            return false;
        }

        List<String> semuaData = new ArrayList<>();
        boolean dataDihapus = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;

            // Baca semua baris dan simpan kecuali baris yang akan dihapus
            while ((baris = reader.readLine()) != null) {
                if (!baris.split(",")[0].equalsIgnoreCase(nama)) {
                    semuaData.add(baris);
                } else {
                    dataDihapus = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Tulis ulang data tanpa data yang dihapus
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String baris : semuaData) {
                writer.write(baris);
                writer.newLine();
            }
            System.out.println("Data berhasil dihapus: " + nama);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return dataDihapus;
    }

    // Contoh: Mengambil semua data dari file untuk ditampilkan
    public List<String[]> ambilData(String username) {
        String filePath = DATA_FOLDER + "/data_" + username + ".csv";
        File file = new File(filePath);

        List<String[]> dataList = new ArrayList<>();

        if (!file.exists()) {
            System.out.println("File tidak ditemukan: " + filePath);
            return dataList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;

            // Lewati header
            reader.readLine();

            while ((baris = reader.readLine()) != null) {
                dataList.add(baris.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
