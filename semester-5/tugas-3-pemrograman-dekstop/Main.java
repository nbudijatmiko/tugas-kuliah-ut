
import java.io.*;
import java.util.*;

// Kelas utama
public class Main {

    static Scanner scanner = new Scanner(System.in);
    static RentalKendaraan rental = new RentalKendaraan();
    static Penyewaan penyewaan = new Penyewaan();

    public static void main(String[] args) {
        try {
            rental.muatDariFile("kendaraan.txt");
        } catch (IOException e) {
            System.out.println("Data kendaraan tidak ditemukan, mulai dengan daftar kosong.");
        }

        try {
            penyewaan.muatStruk("struk.txt", rental.getDaftarKendaraan());
        } catch (IOException e) {
            System.out.println("Data struk tidak ditemukan, mulai kosong.");
        }

        boolean running = true;
        while (running) {
            try {
                System.out.println("\n=== Sistem Rental Kendaraan ===");
                System.out.println("1. Tambah Kendaraan");
                System.out.println("2. Tampilkan Daftar Kendaraan");
                System.out.println("3. Sewa Kendaraan");
                System.out.println("4. Tampilkan Struk Penyewaan");
                System.out.println("5. Pengembalian Kendaraan");
                System.out.println("6. Simpan Data dan Keluar");
                System.out.print("Pilih menu: ");
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1 ->
                        tambahKendaraanMenu();
                    case 2 ->
                        rental.tampilDaftarKendaraan();
                    case 3 ->
                        sewaKendaraanMenu();
                    case 4 ->
                        penyewaan.tampilStruk();
                    case 5 ->
                        pengembalianMenu();
                    case 6 -> {
                        rental.simpanKeFile("kendaraan.txt");
                        penyewaan.simpanStruk("struk.txt");
                        System.out.println("Data tersimpan. Keluar program.");
                        running = false;
                    }
                    default ->
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void tambahKendaraanMenu() {
        System.out.println("Tambah Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("3. Bus");
        System.out.print("Pilih jenis kendaraan: ");
        int jenis = Integer.parseInt(scanner.nextLine());

        System.out.print("Masukkan nama kendaraan: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan harga sewa per hari: ");
        double harga = Double.parseDouble(scanner.nextLine());

        switch (jenis) {
            case 1 -> {
                System.out.print("Masukkan jumlah kursi: ");
                int kursi = Integer.parseInt(scanner.nextLine());
                rental.tambahKendaraan(new Mobil(nama, harga, kursi));
                System.out.println("Mobil berhasil ditambahkan.");
            }
            case 2 -> {
                System.out.print("Masukkan cc mesin: ");
                int cc = Integer.parseInt(scanner.nextLine());
                rental.tambahKendaraan(new Motor(nama, harga, cc));
                System.out.println("Motor berhasil ditambahkan.");
            }
            case 3 -> {
                System.out.print("Masukkan kapasitas penumpang: ");
                int kapasitas = Integer.parseInt(scanner.nextLine());
                rental.tambahKendaraan(new Bus(nama, harga, kapasitas));
                System.out.println("Bus berhasil ditambahkan.");
            }
            default ->
                System.out.println("Jenis kendaraan tidak valid.");
        }
    }

    private static void sewaKendaraanMenu() throws Exception {
        System.out.println("Daftar kendaraan tersedia:");
        ArrayList< Kendaraan> daftar = rental.getDaftarKendaraan();
        boolean adaTersedia = false;

        for (int i = 0; i < daftar.size(); i++) {
            if (daftar.get(i).isTersedia()) {
                System.out.print(i + ". ");
                daftar.get(i).tampilKendaraan();
                System.out.println("--------------------");
                adaTersedia = true;
            }
        }

        if (!adaTersedia) {
            System.out.println("Tidak ada kendaraan tersedia saat ini.");
            return;
        }

        System.out.print("Pilih index kendaraan yang ingin disewa: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Kendaraan k = rental.getKendaraan(idx);

        if (!k.isTersedia()) {
            throw new Exception("Kendaraan sudah disewa.");
        }

        System.out.print("Masukkan lama sewa (hari): ");
        int hari = Integer.parseInt(scanner.nextLine());

        penyewaan.tambahPenyewaan(k, hari);
        System.out.println("Kendaraan berhasil disewa.");
    }

    private static void pengembalianMenu() {
        ArrayList< Kendaraan> disewa = penyewaan.getKendaraanDisewa();

        if (disewa.isEmpty()) {
            System.out.println("Tidak ada kendaraan yang sedang disewa.");
            return;
        }

        System.out.println("Daftar kendaraan yang sedang disewa:");
        for (int i = 0; i < disewa.size(); i++) {
            System.out.println(i + ". " + disewa.get(i).getNama() + " (" + disewa.get(i).getJenis() + ")");
        }

        System.out.print("Pilih index kendaraan yang ingin dikembalikan: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            penyewaan.kembalikanKendaraan(index);
            System.out.println("Kendaraan berhasil dikembalikan.");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}

// ======================== Kelas Kendaraan ========================
abstract class Kendaraan {

    private String nama;
    private double hargaSewa;
    private String jenis;
    private boolean status;

    public Kendaraan(String nama, double hargaSewa, String jenis) {
        this.nama = nama;
        this.hargaSewa = hargaSewa;
        this.jenis = jenis;
        this.status = true;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public String getJenis() {
        return jenis;
    }

    public boolean isTersedia() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public abstract void tampilKendaraan();

    public String toFileString() {
        return nama + ";" + hargaSewa + ";" + jenis + ";" + status;
    }
}

class Mobil extends Kendaraan {

    private int jumlahKursi;

    public Mobil(String nama, double hargaSewa, int jumlahKursi) {
        super(nama, hargaSewa, "Mobil");
        this.jumlahKursi = jumlahKursi;
    }

    @Override
    public void tampilKendaraan() {
        System.out.println("Jenis: Mobil");
        System.out.println("Nama: " + getNama());
        System.out.println("Harga Sewa: Rp " + getHargaSewa());
        System.out.println("Jumlah Kursi: " + jumlahKursi);
        System.out.println("Status: " + (isTersedia() ? "Tersedia" : "Disewa"));
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + jumlahKursi;
    }

    public static Mobil fromFileString(String line) {
        String[] parts = line.split(";");
        Mobil m = new Mobil(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[4]));
        m.setStatus(Boolean.parseBoolean(parts[3]));
        return m;
    }
}

class Motor extends Kendaraan {

    private int ccMesin;

    public Motor(String nama, double hargaSewa, int ccMesin) {
        super(nama, hargaSewa, "Motor");
        this.ccMesin = ccMesin;
    }

    @Override
    public void tampilKendaraan() {
        System.out.println("Jenis: Motor");
        System.out.println("Nama: " + getNama());
        System.out.println("Harga Sewa: Rp " + getHargaSewa());
        System.out.println("CC Mesin: " + ccMesin);
        System.out.println("Status: " + (isTersedia() ? "Tersedia" : "Disewa"));
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + ccMesin;
    }

    public static Motor fromFileString(String line) {
        String[] parts = line.split(";");
        Motor m = new Motor(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[4]));
        m.setStatus(Boolean.parseBoolean(parts[3]));
        return m;
    }
}

class Bus extends Kendaraan {

    private int kapasitasPenumpang;

    public Bus(String nama, double hargaSewa, int kapasitasPenumpang) {
        super(nama, hargaSewa, "Bus");
        this.kapasitasPenumpang = kapasitasPenumpang;
    }

    @Override
    public void tampilKendaraan() {
        System.out.println("Jenis: Bus");
        System.out.println("Nama: " + getNama());
        System.out.println("Harga Sewa: Rp " + getHargaSewa());
        System.out.println("Kapasitas Penumpang: " + kapasitasPenumpang);
        System.out.println("Status: " + (isTersedia() ? "Tersedia" : "Disewa"));
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + kapasitasPenumpang;
    }

    public static Bus fromFileString(String line) {
        String[] parts = line.split(";");
        Bus b = new Bus(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[4]));
        b.setStatus(Boolean.parseBoolean(parts[3]));
        return b;
    }
}

// ======================== Kelas RentalKendaraan ========================
class RentalKendaraan {

    private ArrayList< Kendaraan> daftarKendaraan = new ArrayList<>();

    public void tambahKendaraan(Kendaraan k) {
        daftarKendaraan.add(k);
    }

    public void tampilDaftarKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan dalam daftar.");
            return;
        }
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            System.out.println("Index: " + i);
            daftarKendaraan.get(i).tampilKendaraan();
            System.out.println("--------------------");
        }
    }

    public Kendaraan getKendaraan(int index) {
        return daftarKendaraan.get(index);
    }

    public ArrayList< Kendaraan> getDaftarKendaraan() {
        return daftarKendaraan;
    }

    public void simpanKeFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Kendaraan k : daftarKendaraan) {
                writer.write(k.toFileString());
                writer.newLine();
            }
        }
    }

    public void muatDariFile(String filename) throws IOException {
        daftarKendaraan.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 5) {
                    continue;
                }
                switch (parts[2]) {
                    case "Mobil" ->
                        daftarKendaraan.add(Mobil.fromFileString(line));
                    case "Motor" ->
                        daftarKendaraan.add(Motor.fromFileString(line));
                    case "Bus" ->
                        daftarKendaraan.add(Bus.fromFileString(line));
                }
            }
        }
    }
}

// ======================== Kelas Penyewaan ========================
class Penyewaan {

    private ArrayList< Kendaraan> kendaraanDisewa = new ArrayList<>();
    private ArrayList< Integer> lamaSewa = new ArrayList<>();

    public void muatStruk(String filename, ArrayList< Kendaraan> daftarKendaraan) throws IOException {
        kendaraanDisewa.clear();
        lamaSewa.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Total")) {
                    continue; // lewati baris total

                }
                String[] parts = line.split(";");
                String nama = parts[0];
                String jenis = parts[1];
                double harga = Double.parseDouble(parts[2]);
                int hari = Integer.parseInt(parts[3]);

                // cari kendaraan yang cocok dari daftar
                for (Kendaraan k : daftarKendaraan) {
                    if (k.getNama().equals(nama) && k.getJenis().equals(jenis)) {
                        kendaraanDisewa.add(k);
                        lamaSewa.add(hari);
                        k.setStatus(false); // tandai disewa
                        break;
                    }
                }
            }
        }
    }

    public void tambahPenyewaan(Kendaraan k, int hari) throws Exception {
        if (!k.isTersedia()) {
            throw new Exception("Kendaraan tidak tersedia.");
        }
        kendaraanDisewa.add(k);
        lamaSewa.add(hari);
        k.setStatus(false);
    }

    public void tampilStruk() {
        if (kendaraanDisewa.isEmpty()) {
            System.out.println("Belum ada kendaraan yang disewa.");
            return;
        }
        System.out.println("=== Struk Penyewaan ===");
        double total = 0;
        for (int i = 0; i < kendaraanDisewa.size(); i++) {
            Kendaraan k = kendaraanDisewa.get(i);
            int hari = lamaSewa.get(i);
            double biaya = k.getHargaSewa() * hari;
            System.out.println((i + 1) + ". " + k.getNama() + " (" + k.getJenis() + ") - " + hari + " hari x Rp " + k.getHargaSewa() + " = Rp " + biaya);
            total += biaya;
        }
        System.out.println("Total Biaya: Rp " + total);
    }

    public ArrayList< Kendaraan> getKendaraanDisewa() {
        return kendaraanDisewa;
    }

    public void kembalikanKendaraan(int index) throws Exception {
        if (index < 0 || index >= kendaraanDisewa.size()) {
            throw new Exception("Index tidak valid.");
        }
        kendaraanDisewa.get(index).setStatus(true);
        kendaraanDisewa.remove(index);
        lamaSewa.remove(index);
    }

    public void simpanStruk(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < kendaraanDisewa.size(); i++) {
                Kendaraan k = kendaraanDisewa.get(i);
                writer.write(k.getNama() + ";" + k.getJenis() + ";" + k.getHargaSewa() + ";" + lamaSewa.get(i));
                writer.newLine();
            }
            writer.write("Total;" + hitungTotalBiaya());
            writer.newLine();
        }
    }

    public double hitungTotalBiaya() {
        double total = 0;
        for (int i = 0; i < kendaraanDisewa.size(); i++) {
            total += kendaraanDisewa.get(i).getHargaSewa() * lamaSewa.get(i);
        }
        return total;
    }
}
