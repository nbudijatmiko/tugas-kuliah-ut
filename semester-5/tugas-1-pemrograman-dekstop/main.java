import java.util.*;

class Kamar {
    int nomor;
    String tipe;
    double hargaPerMalam;
    boolean tersedia;

    public Kamar(int nomor, String tipe, double hargaPerMalam) {
        this.nomor = nomor;
        this.tipe = tipe;
        this.hargaPerMalam = hargaPerMalam;
        this.tersedia = true;
    }
}

class Reservasi {
    Kamar kamar;
    int lamaMenginap;

    public Reservasi(Kamar kamar, int lamaMenginap) {
        this.kamar = kamar;
        this.lamaMenginap = lamaMenginap;
        kamar.tersedia = false;
    }

    public double hitungSubtotal() {
        return kamar.hargaPerMalam * lamaMenginap;
    }
}

public class Main {
    static Kamar[] kamarList = {
        new Kamar(101, "Standar", 150000),
        new Kamar(102, "Superior", 200000),
        new Kamar(103, "Deluxe", 250000),
        new Kamar(104, "Suite", 300000),
        new Kamar(105, "Standar", 150000),
        new Kamar(106, "Superior", 200000)
    };

    static Scanner scanner = new Scanner(System.in);
    static List<Reservasi> reservasiList = new ArrayList<>();

    public static void main(String[] args) {
        tampilkanKamar();
        inputReservasi();
        cetakStruk();
    }

    static void tampilkanKamar() {
        System.out.println("Daftar Kamar Tersedia:");
        for (Kamar k : kamarList) {
            if (k.tersedia) {
                System.out.printf("Nomor: %d | Tipe: %s | Harga: Rp. %.0f\n", k.nomor, k.tipe, k.hargaPerMalam);
            }
        }
    }

    static void inputReservasi() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Masukkan Nomor Kamar (atau 0 untuk selesai): ");
            int nomor = scanner.nextInt();
            if (nomor == 0) break;

            Kamar kamarDipilih = null;
            for (Kamar k : kamarList) {
                if (k.nomor == nomor && k.tersedia) {
                    kamarDipilih = k;
                    break;
                }
            }

            if (kamarDipilih == null) {
                System.out.println("Kamar tidak tersedia atau tidak ditemukan.");
                i--;
                continue;
            }

            System.out.print("Masukkan Lama Menginap (malam): ");
            int malam = scanner.nextInt();
            reservasiList.add(new Reservasi(kamarDipilih, malam));
        }
    }

    static void cetakStruk() {
        double subtotal = 0;
        int totalKamar = reservasiList.size();
        System.out.println("\n===== STRUK RESERVASI =====");
        for (Reservasi r : reservasiList) {
            double harga = r.hitungSubtotal();
            subtotal += harga;
            System.out.printf("Kamar %d | %s | %d malam x Rp. %.0f = Rp. %.0f\n",
                    r.kamar.nomor, r.kamar.tipe, r.lamaMenginap, r.kamar.hargaPerMalam, harga);
        }

        double pajak = 0.10 * subtotal;
        double biayaLayanan = 50000 * totalKamar;
        double totalAwal = subtotal + pajak + biayaLayanan;
        double diskon = 0;
        boolean gratisSarapan = false;

        if (subtotal > 500000) {
            diskon = 0.15 * totalAwal;
        } else if (subtotal > 300000) {
            gratisSarapan = true;
        }

        double totalAkhir = totalAwal - diskon;

        System.out.printf("Subtotal: Rp. %.0f\n", subtotal);
        System.out.printf("Pajak (10%%): Rp. %.0f\n", pajak);
        System.out.printf("Biaya Layanan: Rp. %.0f\n", biayaLayanan);
        if (diskon > 0) {
            System.out.printf("Diskon 15%%: -Rp. %.0f\n", diskon);
        }
        if (gratisSarapan) {
            System.out.println("Bonus: Gratis Sarapan");
        }
        System.out.printf("Total Pembayaran: Rp. %.0f\n", totalAkhir);
    }
}
