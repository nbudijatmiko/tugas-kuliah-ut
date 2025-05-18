
import java.util.ArrayList;
import java.util.Scanner;

class Buku {
    String judul;
    String penulis;
    String kategori;
    boolean tersedia;
    int hariKeterlambatan;

    public Buku(String judul, String penulis, String kategori) {
        this.judul = judul;
        this.penulis = penulis;
        this.kategori = kategori;
        this.tersedia = true;
        this.hariKeterlambatan = 0;
    }

    public void setTersedia(boolean status) {
        this.tersedia = status;
    }

    public String getStatus() {
        return tersedia ? "Tersedia" : "Dipinjam";
    }

    public int hitungDenda() {
        if (hariKeterlambatan > 7) {
            return (hariKeterlambatan - 7) * 5000;
        }
        return 0;
    }

    
    public String toString() {
        return "Judul: " + judul + ", Penulis: " + penulis + ", Kategori: " + kategori + ", Status: " + getStatus();
    }
}

public class Main {
    static ArrayList<Buku> daftarBuku = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        inisialisasiBuku();
        menuUtama();
    }

    static void inisialisasiBuku() {
        daftarBuku.add(new Buku("Laskar Pelangi", "Andrea Hirata", "Fiksi"));
        daftarBuku.add(new Buku("Bumi", "Tere Liye", "Fiksi"));
        daftarBuku.add(new Buku("Sapiens", "Yuval Harari", "Non-Fiksi"));
        daftarBuku.add(new Buku("Sejarah Dunia", "E.H. Carr", "Sejarah"));
        daftarBuku.add(new Buku("Clean Code", "Robert C. Martin", "Teknologi"));
        daftarBuku.add(new Buku("Atomic Habits", "James Clear", "Non-Fiksi"));
        daftarBuku.add(new Buku("Negeri 5 Menara", "Ahmad Fuadi", "Fiksi"));
        daftarBuku.add(new Buku("Internet of Things", "John Doe", "Teknologi"));
        daftarBuku.add(new Buku("Sejarah Indonesia", "M. Yamin", "Sejarah"));
        daftarBuku.add(new Buku("AI for Beginners", "Jane Smith", "Teknologi"));
    }

    static void menuUtama() {
        int pilihan;
        do {
            System.out.println("\n=== MENU UTAMA PERPUSTAKAAN ===");
            System.out.println("1. Tampilkan Daftar Buku");
            System.out.println("2. Peminjaman Buku");
            System.out.println("3. Pengembalian Buku");
            System.out.println("4. Manajemen Data Buku (Admin)");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine(); // buang newline

            switch (pilihan) {
                case 1 -> tampilkanBuku();
                case 2 -> peminjamanBuku();
                case 3 -> pengembalianBuku();
                case 4 -> manajemenBuku();
                case 5 -> System.out.println("Terima kasih telah menggunakan sistem.");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
    }

    static void tampilkanBuku() {
        System.out.println("\n== DAFTAR BUKU ==");
        for (Buku b : daftarBuku) {
            System.out.println(b);
        }
    }

    static void peminjamanBuku() {
        ArrayList<Buku> bukuDipinjam = new ArrayList<>();
        while (true) {
            System.out.print("\nJudul buku yang ingin dipinjam ('selesai' untuk selesai): ");
            String judul = input.nextLine();
            if (judul.equalsIgnoreCase("selesai")) break;

            Buku buku = cariBuku(judul);
            if (buku != null && buku.tersedia) {
                buku.setTersedia(false);
                bukuDipinjam.add(buku);
                System.out.println("Buku berhasil dipinjam.");
            } else {
                System.out.println("Buku tidak tersedia atau tidak ditemukan.");
            }
        }

        cetakStruk("PEMINJAMAN", bukuDipinjam, 0);
    }

    static void pengembalianBuku() {
        ArrayList<Buku> bukuDikembalikan = new ArrayList<>();
        int totalDenda = 0;

        while (true) {
            System.out.print("\nJudul buku yang ingin dikembalikan ('selesai' untuk selesai): ");
            String judul = input.nextLine();
            if (judul.equalsIgnoreCase("selesai")) break;

            Buku buku = cariBuku(judul);
            if (buku != null && !buku.tersedia) {
                System.out.print("Hari keterlambatan (jika ada): ");
                int terlambat = input.nextInt(); input.nextLine();

                buku.hariKeterlambatan = terlambat;
                buku.setTersedia(true);
                bukuDikembalikan.add(buku);
                totalDenda += buku.hitungDenda();
                System.out.println("Buku berhasil dikembalikan.");
            } else {
                System.out.println("Buku tidak ditemukan atau tidak sedang dipinjam.");
            }
        }

        cetakStruk("PENGEMBALIAN", bukuDikembalikan, totalDenda);
    }

    static Buku cariBuku(String judul) {
        for (Buku b : daftarBuku) {
            if (b.judul.equalsIgnoreCase(judul)) {
                return b;
            }
        }
        return null;
    }

    static void cetakStruk(String jenis, ArrayList<Buku> listBuku, int denda) {
        System.out.println("\n=== STRUK " + jenis + " ===");
        for (Buku b : listBuku) {
            System.out.println(b.judul + " - Status: " + b.getStatus());
        }
        if (denda > 0) {
            System.out.println("Total Denda: Rp. " + denda);
        }
    }

    static void manajemenBuku() {
        int pilihan;
        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Ubah Buku");
            System.out.println("3. Hapus Buku");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt(); input.nextLine();

            switch (pilihan) {
                case 1 -> tambahBuku();
                case 2 -> ubahBuku();
                case 3 -> hapusBuku();
                case 4 -> {}
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 4);
    }

    static void tambahBuku() {
        System.out.print("Judul: ");
        String judul = input.nextLine();
        System.out.print("Penulis: ");
        String penulis = input.nextLine();
        System.out.print("Kategori: ");
        String kategori = input.nextLine();

        daftarBuku.add(new Buku(judul, penulis, kategori));
        System.out.println("Buku berhasil ditambahkan.");
    }

    static void ubahBuku() {
        System.out.print("Judul buku yang ingin diubah: ");
        String judul = input.nextLine();
        Buku buku = cariBuku(judul);

        if (buku != null) {
            System.out.print("Ubah Judul: ");
            buku.judul = input.nextLine();
            System.out.print("Ubah Penulis: ");
            buku.penulis = input.nextLine();
            System.out.print("Ubah Kategori: ");
            buku.kategori = input.nextLine();
            System.out.println("Data buku berhasil diubah.");
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    static void hapusBuku() {
        System.out.print("Judul buku yang ingin dihapus: ");
        String judul = input.nextLine();
        Buku buku = cariBuku(judul);

        if (buku != null) {
            System.out.print("Yakin ingin menghapus buku ini? (y/n): ");
            String konfirmasi = input.nextLine();
            if (konfirmasi.equalsIgnoreCase("y")) {
                daftarBuku.remove(buku);
                System.out.println("Buku berhasil dihapus.");
            }
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }
}
