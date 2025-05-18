public class MergeSortDescending {

    // Fungsi utama untuk menjalankan program
    public static void main(String[] args) {
        // Tentukan nilai-nilai awal
        int[] data = {85, 67, 90, 45, 78, 92};

        System.out.println("Data sebelum diurutkan:");
        printArray(data);
        System.out.println("----------------------");

        // Panggil fungsi mergeSort
        mergeSort(data, 0, data.length - 1);

        System.out.println("\nData setelah diurutkan (dari terbesar ke terkecil):");
        printArray(data);
    }

    // Fungsi mergeSort untuk membagi dan mengurutkan
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            // Bagi dua dan urutkan masing-masing bagian
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            // Gabungkan hasilnya
            merge(arr, left, middle, right);
            
            System.out.println("Proses pengurutan:");
            printArray(arr);
        }
    }

    // Fungsi merge untuk menggabungkan dua bagian terurut
    public static void merge(int[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Buat array sementara
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Salin data ke array sementara
        for (int i = 0; i < n1; ++i)
            leftArray[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArray[j] = arr[middle + 1 + j];

        // Gabungkan kembali kedua array ke dalam arr[]
        int i = 0, j = 0;
        int k = left;

        // Ubah perbandingan untuk urutan menurun
        while (i < n1 && j < n2) {
            if (leftArray[i] >= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Salin sisa elemen jika ada
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Fungsi untuk menampilkan array
    public static void printArray(int[] arr) {
        for (int value : arr)
            System.out.print(value + " ");
        System.out.println();
    }
}
