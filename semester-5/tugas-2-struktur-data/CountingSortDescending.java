public class CountingSortDescending {

    public static void main(String[] args) {
        // Tentukan nilai-nilai awal
        int[] data = {55, 23, 78, 99, 12, 67};

        System.out.println("Data sebelum diurutkan:");
        printArray(data);
        System.out.println("----------------------");

        // Panggil fungsi countingSort
        int[] sorted = countingSortDescending(data);

        System.out.println("\nData setelah diurutkan (dari terbesar ke terkecil):");
        printArray(sorted);
    }

    // Fungsi Counting Sort untuk urutan descending
    public static int[] countingSortDescending(int[] arr) {
        int max = findMax(arr);

        // Buat array hitung
        int[] count = new int[max + 1];

        // Hitung frekuensi setiap elemen
        for (int num : arr) {
            count[num]++;
        }
        
        System.out.println("count");
        printArray(count);

        // Susun ulang data dari nilai tertinggi ke terendah
        int index = 0;
        int[] output = new int[arr.length];
        for (int i = max; i >= 0; i--) {
            while (count[i] > 0) {
                output[index++] = i;
                count[i]--;
            }
        }
        

        return output;
    }

    // Fungsi untuk mencari nilai maksimum
    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max)
                max = num;

            System.out.println("Proses pengurutan:");
            System.out.println(max);
        }
        
        return max;
    }

    // Fungsi untuk menampilkan array
    public static void printArray(int[] arr) {
        for (int value : arr)
            System.out.print(value + " ");
        System.out.println();
    }
}
