import java.util.*;

public class VeriMerkezi {

    public static class GecmisKaydi {
        public LogEntry degisenLog;
        public String eskiDurum;
        public GecmisKaydi(LogEntry log, String eskiDurum) {
            this.degisenLog = log;
            this.eskiDurum = eskiDurum;
        }
    }

    public static class CustomBST {
        class Node {
            int port;
            List<LogEntry> loglar;
            Node left, right;
            public Node(int port, LogEntry log) {
                this.port = port;
                this.loglar = new ArrayList<>();
                this.loglar.add(log);
                left = right = null;
            }
        }
        private Node root;

        public void insert(LogEntry log) { root = insertRec(root, log.getPort(), log); }
        private Node insertRec(Node root, int port, LogEntry log) {
            if (root == null) return new Node(port, log);
            if (port < root.port) root.left = insertRec(root.left, port, log);
            else if (port > root.port) root.right = insertRec(root.right, port, log);
            else root.loglar.add(log);
            return root;
        }

        // İki Çocuklu Düğüm Silme
        public void delete(int port) { root = deleteRec(root, port); }
        private Node deleteRec(Node root, int port) {
            if (root == null) return root;
            if (port < root.port) root.left = deleteRec(root.left, port);
            else if (port > root.port) root.right = deleteRec(root.right, port);
            else {
                // Tek veya sıfır çocuk durumu
                if (root.left == null) return root.right;
                else if (root.right == null) return root.left;
                // İki çocuk durumu: Sağ alt ağacın en küçük değerini bul
                Node temp = minValueNode(root.right);
                root.port = temp.port;
                root.loglar = temp.loglar;
                root.right = deleteRec(root.right, temp.port);
            }
            return root;
        }
        private Node minValueNode(Node root) {
            Node current = root;
            while (current.left != null) current = current.left;
            return current;
        }

        public List<LogEntry> search(int port) { return searchRec(root, port); }
        private List<LogEntry> searchRec(Node root, int port) {
            if (root == null) return new ArrayList<>();
            if (root.port == port) return root.loglar;
            if (port < root.port) return searchRec(root.left, port);
            return searchRec(root.right, port);
        }

        public void inorderReport(Node root) {
            if (root != null) {
                inorderReport(root.left);
                System.out.println(" ↳ Port " + root.port + " üzerinden: " + root.loglar.size() + " işlem tespit edildi.");
                inorderReport(root.right);
            }
        }

        public Node getRoot() { return root; }
        public void clearRoot() { root = null; }
    }

    // Encapsulation
    private List<LogEntry> arrayListesi = new ArrayList<>();
    private List<LogEntry> linkedListesi = new LinkedList<>();
    private Map<String, List<LogEntry>> ipLogHaritasi = new HashMap<>();
    private Queue<LogEntry> acilDurumKuyrugu = new PriorityQueue<>(); // Max-Heap
    private Queue<LogEntry> standartKuyruk = new LinkedList<>(); // FIFO
    private Set<String> essizIpler = new HashSet<>();
    private CustomBST portAgaci = new CustomBST();
    private Stack<GecmisKaydi> islemGecmisi = new Stack<>();
    private Map<String, Set<String>> agGrafi = new HashMap<>();
    private boolean veriYuklendiMi = false;

    // Getter Metotları
    public List<LogEntry> getArrayListesi() { return arrayListesi; }
    public List<LogEntry> getLinkedListesi() { return linkedListesi; }
    public Map<String, List<LogEntry>> getIpLogHaritasi() { return ipLogHaritasi; }
    public Queue<LogEntry> getAcilDurumKuyrugu() { return acilDurumKuyrugu; }
    public Queue<LogEntry> getStandartKuyruk() { return standartKuyruk; }
    public Set<String> getEssizIpler() { return essizIpler; }
    public CustomBST getPortAgaci() { return portAgaci; }
    public Stack<GecmisKaydi> getIslemGecmisi() { return islemGecmisi; }
    public Map<String, Set<String>> getAgGrafi() { return agGrafi; }
    public boolean isVeriYuklendiMi() { return veriYuklendiMi; }
    public void setVeriYuklendiMi(boolean durum) { this.veriYuklendiMi = durum; }

    public void temizle() {
        arrayListesi.clear(); linkedListesi.clear(); ipLogHaritasi.clear();
        acilDurumKuyrugu.clear(); standartKuyruk.clear(); essizIpler.clear();
        portAgaci.clearRoot(); islemGecmisi.clear(); agGrafi.clear();
        System.gc();
    }
}