
import java.util.*;

class GraphDFS {

    private int V; // Jumlah simpul
    private LinkedList<Integer>[] adj; // Array of adjacency list

    // Konstruktor
    GraphDFS(int v) {
        V = v;

        // Gunakan @SuppressWarnings untuk menghindari unchecked warning
        @SuppressWarnings("unchecked")
        LinkedList<Integer>[] temp = (LinkedList<Integer>[]) new LinkedList[v];
        adj = temp;

        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    // Tambahkan edge
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // Fungsi util DFS
    void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int n : adj[v]) {
            if (!visited[n]) {
                DFSUtil(n, visited);
            }
        }
    }

    // DFS mulai dari simpul v
    void DFS(int v) {
        boolean[] visited = new boolean[V];
        DFSUtil(v, visited);
    }

    // Main method
    public static void main(String[] args) {
        GraphDFS g = new GraphDFS(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("DFS (vertex awal 2):");
        g.DFS(2);
    }
}
