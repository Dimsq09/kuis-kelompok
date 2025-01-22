package com.mycompany.uasgui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataSewaPancing {

    private static final String DATA_FOLDER = "data";
    private static final String SEWA_FILE = DATA_FOLDER + "/data_sewa_pancing.csv";

    public DataSewaPancing() {
        // Membuat folder data jika belum ada
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Membuat file data_sewa_pancing.csv jika belum ada
        File file = new File(SEWA_FILE);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("PancingKatrol,PancingTajur,PancingTegek,Umpan"); // Header CSV
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk menyimpan data ke file
    public void simpanData(String pancingKatrol, String pancingTajur, String pancingTegek, String umpan) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEWA_FILE, true))) {
            writer.write(pancingKatrol + "," + pancingTajur + "," + pancingTegek + "," + umpan);
            writer.newLine();
            System.out.println("Data sewa berhasil disimpan ke " + SEWA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghapus semua data
    public boolean hapusSemuaData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEWA_FILE))) {
            writer.write("PancingKatrol,PancingTajur,PancingTegek,Umpan"); // Tulis ulang header
            writer.newLine();
            System.out.println("Semua data sewa telah dihapus.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk mengambil semua data dari file
    public List<String[]> ambilData() {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SEWA_FILE))) {
            String line;
            reader.readLine(); // Lewati header
            while ((line = reader.readLine()) != null) {
                dataList.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
