import java.util.LinkedList;

public class main {
    public static void main(String[] args) {

        // Deklarasi variabel integer
        int jumlahBaris;

        // Deklarasi variabel string
        String kalimatBaru = "Deklarasi tipe data String";

        // Deklarasi array satu dimensi
        int[] empatAngka = {7, 10, 20, 23};

        // Deklarasi array dua dimensi (3x3)
        String[][] alfabet = {
            {"P", "S", "N"},
            {"W", "L", "B"},
            {"F", "R", "E"}
        };

        // Deklarasi linked list
        LinkedList<Integer> listAngka = new LinkedList<>();
        listAngka.add(26);
        listAngka.add(8);
        listAngka.add(23);
        listAngka.add(24);
        listAngka.add(16);

        // Menampilkan semua nilai sebagai output (opsional)
        System.out.println("kalimatBaru: " + kalimatBaru);
        System.out.print("empatAngka: ");
        for (int angka : empatAngka) {
            System.out.print(angka + " ");
        }
        System.out.println("\nMatriks alfabet:");
        for (int i = 0; i < alfabet.length; i++) {
            for (int j = 0; j < alfabet[i].length; j++) {
                System.out.print(alfabet[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("listAngka: " + listAngka);
    }
}
