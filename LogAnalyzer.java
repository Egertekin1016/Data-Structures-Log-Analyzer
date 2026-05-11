import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class LogAnalyzer {

    static VeriMerkezi merkez = new VeriMerkezi();

    // Tasarım için bileşenler
    private static JFrame frame;
    private static JTextArea consoleArea;

    // Merge Sort Algoritması
    public static void mergeSort(List<LogEntry> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private static void merge(List<LogEntry> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        List<LogEntry> L = new ArrayList<>(n1);
        List<LogEntry> R = new ArrayList<>(n2);
        for (int i = 0; i < n1; ++i) L.add(list.get(left + i));
        for (int j = 0; j < n2; ++j) R.add(list.get(mid + 1 + j));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L.get(i).getSourceIp().compareTo(R.get(j).getSourceIp()) <= 0) { list.set(k, L.get(i)); i++; }
            else { list.set(k, R.get(j)); j++; }
            k++;
        }
        while (i < n1) { list.set(k, L.get(i)); i++; k++; }
        while (j < n2) { list.set(k, R.get(j)); j++; k++; }
    }

    // Arayüz Başlatıcı
    public static void main(String[] args) {
        // Arayüzün modern görünmesi için sistem temasını kullan (forumlardan baktım)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(LogAnalyzer::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame(" Siber Tehdit İstihbarat Sistemi ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        // Üst Başlık
        JLabel headerLabel = new JLabel(" SİBER TEHDİT İSTİHBARAT YÖNETİM PANELİ", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Consolas", Font.BOLD, 22));
        headerLabel.setForeground(new Color(0, 255, 128)); // Hacker Yeşili
        headerLabel.setBorder(new EmptyBorder(15, 10, 10, 10));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Konsol Alanı
        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(15, 15, 15));
        consoleArea.setForeground(new Color(0, 255, 65));
        consoleArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(consoleArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 128), 1));
        frame.add(scrollPane, BorderLayout.CENTER);

        // System.out yönlendirmesi
        redirectSystemStreams();

        // Butonlar Menüsü
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(12, 1, 5, 5));
        menuPanel.setBackground(new Color(30, 30, 30));
        menuPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        String[] menuOptions = {
                "-> 1. Log Yükle (Veri Boyutu Seç)",
                "-> 2. Performans Testleri (CSV)",
                "-> 3. Manuel IP Ara (Hash Testi)",
                "-> 4. Acil Tehdit İşle (Max-Heap)",
                "-> 5. Ağ Analizi (Graph - BFS)",
                "-> 6. Log Güncelle & Geri Al",
                "-> 7. Belirli Bir IP'yi Sil",
                "-> 8. Analitik Raporlama",
                "-> 9. Normal Logları İşle (FIFO)",
                "-> 10. Port ile Log Ara (BST)",
                "-> 11. IP'ye Göre Sırala (Merge Sort)",
                "-> Konsolu Temizle"
        };

        for (int i = 0; i < menuOptions.length; i++) {
            JButton button = createStyledButton(menuOptions[i]);
            int actionIndex = i + 1;
            button.addActionListener(e -> processMenuAction(actionIndex));
            menuPanel.add(button);
        }

        frame.add(menuPanel, BorderLayout.WEST);

        System.out.println("Sistem başarıyla başlatıldı. İşlem yapmak için soldaki menüyü kullanın.\n");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(20, 20, 20)); // Siyah arka plan
        button.setForeground(new Color(0, 255, 128)); // Fosforlu yeşil yazılar
        button.setFocusPainted(false);
        button.setFont(new Font("Consolas", Font.BOLD, 13)); // Fontu terminaldeki fontun aynısı yapıyorum
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Windows'un bizim renklerimizi değiştirmesini engelliyoruz
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Butonların etrafına ince yeşil çizgi
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 128), 1));

        return button;
    }

    // Tasarımsal Butonlara Akdiyon Atama
    // Ağır işlemleri yeni Thread'e alıyoruz (arayüzün donöasını ve takılmasını engellemek için)
    private static void processMenuAction(int actionIndex) {
        new Thread(() -> {
            try {
                switch (actionIndex) {
                    case 1:
                        String limitStr = JOptionPane.showInputDialog(frame, "Yüklenecek satır sayısını giriniz (Örn: 100000):", "Veri Yükleme", JOptionPane.QUESTION_MESSAGE);
                        if (limitStr != null && !limitStr.isEmpty()) veriYukle(Integer.parseInt(limitStr));
                        break;
                    case 2: performansDongusu(); break;
                    case 3:
                        if (veriKontrol()) {
                            String ip = JOptionPane.showInputDialog(frame, "Aranacak IP:", "IP Arama", JOptionPane.QUESTION_MESSAGE);
                            if (ip != null) manuelIpArama(ip);
                        } break;
                    case 4: if (veriKontrol()) manuelAcilKuyrukIsle(); break;
                    case 5:
                        if (veriKontrol()) {
                            String k = JOptionPane.showInputDialog(frame, "Kaynak IP:", "Ağ Analizi", JOptionPane.QUESTION_MESSAGE);
                            if(k == null) break;
                            String h = JOptionPane.showInputDialog(frame, "Hedef IP:", "Ağ Analizi", JOptionPane.QUESTION_MESSAGE);
                            if(h != null) grafBfsAnalizi(k, h);
                        } break;
                    case 6:
                        if (veriKontrol()) {
                            String ip = JOptionPane.showInputDialog(frame, "Güncellenecek IP:", "Log Güncelle", JOptionPane.WARNING_MESSAGE);
                            if (ip != null) logGuncelleVeGeriAl(ip);
                        } break;
                    case 7:
                        if (veriKontrol()) {
                            String ip = JOptionPane.showInputDialog(frame, "Silinecek IP:", "Tehlikeli İşlem", JOptionPane.ERROR_MESSAGE);
                            if (ip != null) manuelIpSil(ip);
                        } break;
                    case 8: if (veriKontrol()) analitikRaporUret(); break;
                    case 9: if (veriKontrol()) standartKuyrukIsle(); break;
                    case 10:
                        if (veriKontrol()) {
                            String portStr = JOptionPane.showInputDialog(frame, "Aranacak Port Numarası:", "BST Port Arama", JOptionPane.QUESTION_MESSAGE);
                            if (portStr != null && !portStr.isEmpty()) bstPortArama(Integer.parseInt(portStr));
                        } break;
                    case 11: if (veriKontrol()) manuelSiralamaGoster(); break;
                    case 12: consoleArea.setText(""); break; // Temizle Butonu
                }
            } catch (Exception ex) {
                System.out.println(" Hata oluştu: Lütfen geçerli bir değer giriniz.");
            }
        }).start();
    }

    // System.out'u GUI'deki Konsola Aktarır
    private static void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) { updateTextArea(String.valueOf((char) b)); }
            @Override
            public void write(byte[] b, int off, int len) { updateTextArea(new String(b, off, len)); }
        };
        System.setOut(new PrintStream(out, true));
    }

    private static void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(text);
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        });
    }

    // Arka Plan Algoritmaları
    public static long veriYukle(int satirLimiti) {
        System.out.println("\n[...] Veri yükleniyor... Lütfen bekleyiniz.");
        if (satirLimiti >= 1000000) System.out.println(" ");

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        merkez.temizle();
        try (BufferedReader br = new BufferedReader(new FileReader("siber_guvenlik_loglari.csv"))) {
            br.readLine(); // Başlık satırını atla
            int okunan = 0;
            while (okunan < satirLimiti && br.ready()) {
                String satir = br.readLine();

                // Kod bu satır sayesinde hem virgülle ayrılmış içerikleri hem de noktalı virgülle ayrılmış içerikleri okuyabil,r
                String[] v = satir.split("[,;]");

                // .trim() Excel'in bırakabileceği gereksiz boşlukları temizleyip, programın çökmesini engeller.
                LogEntry log = new LogEntry(
                        v[0].trim(),
                        v[1].trim(),
                        v[2].trim(),
                        v[3].trim(),
                        Integer.parseInt(v[4].trim()),
                        v[5].trim(),
                        v[6].trim()
                );

                merkez.getArrayListesi().add(log);
                merkez.getLinkedListesi().add(log);
                merkez.getStandartKuyruk().offer(log);
                merkez.getIpLogHaritasi().computeIfAbsent(log.getSourceIp(), k -> new ArrayList<>()).add(log);
                merkez.getPortAgaci().insert(log);
                merkez.getEssizIpler().add(log.getSourceIp());
                merkez.getAgGrafi().computeIfAbsent(log.getSourceIp(), k -> new HashSet<>()).add(log.getDestinationIp());

                if (log.getThreatLevel().matches("Critical|High")) merkez.getAcilDurumKuyrugu().offer(log);
                okunan++;
            }
            merkez.setVeriYuklendiMi(true);
            long memoryUsedMB = ((runtime.totalMemory() - runtime.freeMemory()) - memoryBefore) / (1024 * 1024);
            System.out.println("[+] " + okunan + " satır yüklendi. (RAM Tüketimi: " + memoryUsedMB + " MB)");
            return memoryUsedMB;
        } catch (Exception e) {
            // Hatanın sebebini ekrana yazdırıyoruz bu sayede neyden kaynaklandığını direkt panel üzerinden görebiliriz
            System.out.println("[-] Hata: Dosya okunamadı veya format bozuk! Detay: " + e.getMessage());
            return 0;
        }
    }

    public static void performansDongusu() {
        System.out.println("\n Performans testleri başlatıldı...");
        try (FileWriter fw = new FileWriter("performans_raporu.csv")) {
            fw.write("Veri_Boyutu,RAM_MB,Array_Ara_ms,Hash_Ara_ns,Arr_Sil_ms,Link_Sil_ms,Queue_Poll_ns,PQ_Poll_ns,MergeSort_ms\n");
            int[] boyutlar = {10000, 100000, 1000000};
            for (int b : boyutlar) {
                System.out.println("\n>>> TEST BOYUTU: " + b + " SATIR");
                long ramMB = veriYukle(b);
                performansTestleriniKostur(b, ramMB, fw);
            }
            System.out.println("\n EXCEL İÇİN HAZIR! 'performans_raporu.csv' dosyasına kaydedildi.");
        } catch (Exception e) { System.out.println("CSV yazma hatası."); }
    }

    public static void performansTestleriniKostur(int boyut, long ramMB, FileWriter fw) throws Exception {
        if(merkez.getArrayListesi().isEmpty()) return;
        String testIp = merkez.getArrayListesi().get(merkez.getArrayListesi().size()/2).getSourceIp();

        long t = System.nanoTime();
        for(LogEntry l : merkez.getArrayListesi()) if(l.getSourceIp().equals(testIp));
        long arrAraMs = (System.nanoTime()-t)/1000000;
        System.out.println("[Arama] ArrayList (O(N)): " + arrAraMs + " ms");

        t = System.nanoTime();
        merkez.getIpLogHaritasi().get(testIp);
        long hashAraNs = (System.nanoTime()-t);
        System.out.println("[Arama] HashMap (O(1)): " + hashAraNs + " ns");

        List<LogEntry> kopyaArr = new ArrayList<>(merkez.getArrayListesi());
        List<LogEntry> kopyaLink = new LinkedList<>(merkez.getLinkedListesi());

        t = System.nanoTime();
        for(int i=0; i<100; i++) kopyaArr.remove(0);
        long arrDelMs = (System.nanoTime()-t)/1000000;
        System.out.println("[Silme] ArrayList Baştan Silme (O(N) Kaydırma): " + arrDelMs + " ms");

        t = System.nanoTime();
        for(int i=0; i<100; i++) kopyaLink.remove(0);
        long linDelMs = (System.nanoTime()-t)/1000000;
        System.out.println("[Silme] LinkedList Baştan Silme (O(1) Koparma): " + linDelMs + " ms");

        Queue<LogEntry> kopyaStdKuyruk = new LinkedList<>(merkez.getStandartKuyruk());
        Queue<LogEntry> kopyaPqKuyruk = new PriorityQueue<>(merkez.getAcilDurumKuyrugu());

        t = System.nanoTime();
        for(int i=0; i<1000 && !kopyaStdKuyruk.isEmpty(); i++) kopyaStdKuyruk.poll();
        long stdKuyrukNs = (System.nanoTime()-t);
        System.out.println("[Kuyruk] Standart FIFO Çekme (O(1) Maliyeti): " + stdKuyrukNs + " ns");

        t = System.nanoTime();
        for(int i=0; i<1000 && !kopyaPqKuyruk.isEmpty(); i++) kopyaPqKuyruk.poll();
        long pqKuyrukNs = (System.nanoTime()-t);
        System.out.println("[Kuyruk] Priority Queue Max-Heap Çekme (O(log N)): " + pqKuyrukNs + " ns");

        List<LogEntry> kopyaMerge = new ArrayList<>(merkez.getArrayListesi());
        t = System.currentTimeMillis();
        mergeSort(kopyaMerge, 0, kopyaMerge.size() - 1);
        long mergeMs = (System.currentTimeMillis()-t);
        System.out.println("[Sıralama] Kapsamlı Merge Sort (O(N log N)): " + mergeMs + " ms");

        fw.write(boyut + "," + ramMB + "," + arrAraMs + "," + hashAraNs + "," + arrDelMs + "," + linDelMs + "," + stdKuyrukNs + "," + pqKuyrukNs + "," + mergeMs + "\n");
    }

    public static void bstPortArama(int port) {
        long t = System.nanoTime();
        List<LogEntry> sonuc = merkez.getPortAgaci().search(port);
        long sureNs = System.nanoTime() - t;
        if (sonuc.isEmpty()) System.out.println("\n Ağaçta bu porta ait kayıt bulunamadı.");
        else {
            System.out.println("\n Port " + port + " için O(log N) sürede " + sonuc.size() + " log bulundu! (Süre: " + sureNs + " ns)");
            for (int i = 0; i < Math.min(3, sonuc.size()); i++) System.out.println(" -> " + sonuc.get(i));
        }
    }

    public static void manuelSiralamaGoster() {
        System.out.println("\n Loglar IP adreslerine göre sıralanıyor (Merge Sort)...");
        List<LogEntry> kopyaListe = new ArrayList<>(merkez.getArrayListesi());
        long t = System.currentTimeMillis();
        mergeSort(kopyaListe, 0, kopyaListe.size() - 1);
        System.out.println(" Sıralama " + (System.currentTimeMillis() - t) + " ms sürede tamamlandı!");
        for (int i = 0; i < Math.min(5, kopyaListe.size()); i++) System.out.println(" -> " + kopyaListe.get(i));
    }

    public static void logGuncelleVeGeriAl(String ip) {
        if (!merkez.getIpLogHaritasi().containsKey(ip)) { System.out.println("\n IP bulunamadı."); return; }
        LogEntry log = merkez.getIpLogHaritasi().get(ip).get(0);
        System.out.println("\nEski Durum: " + log.getAction());
        merkez.getIslemGecmisi().push(new VeriMerkezi.GecmisKaydi(log, log.getAction()));
        log.setAction("BLOCKED_BY_ADMIN");
        System.out.println("Güncellendi: " + log.getAction());
        System.out.println("Geri Alınıyor (Stack Pop)...");
        VeriMerkezi.GecmisKaydi sonIslem = merkez.getIslemGecmisi().pop();
        sonIslem.degisenLog.setAction(sonIslem.eskiDurum);
        System.out.println("Eski Duruma Dönüldü: " + sonIslem.degisenLog.getAction());
    }

    public static void standartKuyrukIsle() {
        System.out.println("\n[ FIFO ] Standart Log Kuyruğu İşleniyor...");
        for(int i=0; i<5 && !merkez.getStandartKuyruk().isEmpty(); i++) {
            System.out.println(" -> İşlendi (O(1)): " + merkez.getStandartKuyruk().poll());
        }
        System.out.println("Kuyrukta bekleyen: " + merkez.getStandartKuyruk().size());
    }

    public static void analitikRaporUret() {
        System.out.println("\n---  SİBER İSTİHBARAT ANALİTİK RAPORU ---");
        System.out.println("\n[1] EN ÇOK TEHDİT ÜRETEN İLK 5 IP ADRESİ");
        List<Map.Entry<String, List<LogEntry>>> list = new ArrayList<>(merkez.getIpLogHaritasi().entrySet());
        list.sort((a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()));
        for (int i = 0; i < Math.min(5, list.size()); i++) {
            System.out.println((i + 1) + ". IP: " + list.get(i).getKey() + " -> " + list.get(i).getValue().size() + " Log");
        }
        System.out.println("\n[2] PORTLARA GÖRE SALDIRI DAĞILIMI");
        merkez.getPortAgaci().inorderReport(merkez.getPortAgaci().getRoot());
    }

    public static void manuelIpSil(String ip) {
        if (merkez.getIpLogHaritasi().containsKey(ip)) {
            merkez.getIpLogHaritasi().remove(ip);
            merkez.getEssizIpler().remove(ip);
            merkez.getAgGrafi().remove(ip);
            merkez.getArrayListesi().removeIf(l -> l.getSourceIp().equals(ip));
            merkez.getLinkedListesi().removeIf(l -> l.getSourceIp().equals(ip));
            System.out.println("\n IP tüm veri yapılarından kazındı.");
        } else System.out.println("\n IP bulunamadı.");
    }

    public static void grafBfsAnalizi(String start, String end) {
        Queue<String> q = new LinkedList<>(); Map<String, Integer> dist = new HashMap<>();
        q.offer(start); dist.put(start, 0);
        while (!q.isEmpty()) {
            String curr = q.poll();
            if (curr.equals(end)) { System.out.println("\n Lateral Movement Tespit Edildi! Mesafe: " + dist.get(curr) + " hop."); return; }
            for (String neighbor : merkez.getAgGrafi().getOrDefault(curr, new HashSet<>())) {
                if (!dist.containsKey(neighbor)) { dist.put(neighbor, dist.get(curr) + 1); q.offer(neighbor); }
            }
        }
        System.out.println("\n İki cihaz arasında ağ bağlantısı yok.");
    }

    public static void manuelIpArama(String ip) {
        if(merkez.getIpLogHaritasi().containsKey(ip)) System.out.println("\n Kayıt: Bu IP'den " + merkez.getIpLogHaritasi().get(ip).size() + " bağlantı saptandı.");
        else System.out.println("\n Yok: Bu IP adresi sistemde bulunamadı.");
    }

    public static void manuelAcilKuyrukIsle() {
        System.out.println("\n Öncelikli Tehditler İşleniyor...");
        for(int i=0; i<3 && !merkez.getAcilDurumKuyrugu().isEmpty(); i++)
            System.out.println("Max-Heap'ten Çekildi: " + merkez.getAcilDurumKuyrugu().poll());
    }

    public static boolean veriKontrol() {
        if (!merkez.isVeriYuklendiMi()) {
            JOptionPane.showMessageDialog(frame, " Lütfen önce Menü 1'den veri yükleyin!", "Veri Yok", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}