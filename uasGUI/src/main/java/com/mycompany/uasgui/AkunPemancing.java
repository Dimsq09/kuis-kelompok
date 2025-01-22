package com.mycompany.uasgui;

import java.io.*;
import java.util.*;

public class AkunPemancing {

    private static final String DATA_FOLDER = "data";
    private static final String AKUN_FILE = DATA_FOLDER + "/data_akun.csv";

    public AkunPemancing() {
        // Buat folder data jika belum ada
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Buat file data_akun.csv jika belum ada
        File akunFile = new File(AKUN_FILE);
        if (!akunFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(akunFile))) {
                writer.write("username,fullname,password"); // Header CSV
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean registerAkun(String fullname, String username, String password) {
        // Periksa apakah username sudah ada
        if (isUsernameExist(username)) {
            return false;
        }

        // Tambahkan akun ke data_akun.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AKUN_FILE, true))) {
            writer.write(username + "," + fullname + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean verifikasiLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(AKUN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isUsernameExist(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(AKUN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hapusAkun(String username) {
        boolean berhasil = false;

        try {
            // Baca semua data akun
            File akunFile = new File(DATA_FOLDER + "/data_akun.csv");
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(akunFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith(username + ",")) {
                        lines.add(line); // Simpan semua kecuali akun yang akan dihapus
                    }
                }
            }

            // Tulis ulang data akun tanpa username tersebut
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(akunFile))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            berhasil = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return berhasil;
    }

    public String getFullNameByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(AKUN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    return data[1]; // Fullname berada di index 1
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Jika tidak ditemukan
}
}