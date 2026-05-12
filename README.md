# Siber Tehdit İstihbarat ve Log Analiz Sistemi

Gazi Üniversitesi **BMT210 Veri Yapıları** dersi kapsamında geliştirilen bu proje; bir siber güvenlik operasyon merkezine (SOC) düşen milyonlarca ağ logunu analiz etmek, tehditleri önceliklendirmek ve siber saldırıların ağ içindeki izlerini sürmek amacıyla tasarlanmış bir mühendislik çözümüdür.

---

## Projenin Amacı
Projenin temel amacı, teorik veri yapıları (**Dizi, Bağlı Liste, Yığın, Kuyruk, Ağaç, Graf, Hash** vb.) ve algoritmaların (**Sorting, BFS**), gerçek dünya ölçeğinde bir veri setinde (**1 Milyon+ satır**) performans, bellek yönetimi ve işlem hızı açısından nasıl farklar yarattığını somutlaştırmaktır.

##  Dosya Yapısı ve Görevleri
Sistem, Modüler ve **Nesne Yönelimli Programlama (OOP)** prensiplerine uygun olarak 3 ana sınıftan oluşur:

* **`LogEntry.java` (Model Katmanı):** Her bir siber güvenlik logunu temsil eden nesne kalıbıdır. *Timestamp, SourceIP, DestinationIP, Protocol, Port, ThreatLevel* ve *Action* gibi kritik verileri kapsüller.
* **`VeriMerkezi.java` (Veri Yönetim Katmanı):** Uygulamanın beynidir. Tüm veri yapılarının (`ArrayList`, `LinkedList`, `Stack`, `Queue`, `PriorityQueue`, `HashMap`, `HashSet`, `Graph`, `BST`) yönetildiği merkezdir.
* **`LogAnalyzer.java` (Kontrol ve Arayüz Katmanı):** Kullanıcı etkileşim noktasıdır. **GUI (Swing)** kodlarını, arama/sıralama algoritmalarını ve performans test motorunu içerir.
* **`siber_guvenlik_loglari.csv` (Veri Seti):** Sistemin analiz ettiği, gerçek ağ trafiğini simüle eden ham veridir.

##  Veri Seti (Loglar) Nasıl Oluşturuldu?
Log dosyası, tipik bir **IDS/IPS (Saldırı Tespit Sistemi)** çıktısını simüle edecek şekilde yapılandırılmıştır.

* **Kaynak ve Hedef IP:** Saldırgan ve kurban cihazların adresleri.
* **Port:** Saldırının hedeflediği servis (Örn: 80-HTTP, 443-HTTPS, 22-SSH).
* **Threat Level (Tehdit Seviyesi):** Low, Medium, High, Critical. Bu etiketler, **Priority Queue** yapısının önceliklendirme mantığını belirler.
* **Action:** Sistemin aldığı aksiyon (ALLOW veya DENY).

##  Algoritmalar ve Veri Yapıları (Teknik Detay)

### 1. Zaman Karmaşıklığı (Time Complexity) Analizi
* **Hızlı Erişim ($O(1)$):** IP adreslerine anında ulaşmak için **HashMap** kullanılmıştır.
* **Hiyerarşik Arama ($O(\log N)$):** Port numaralarını analiz etmek için sıfırdan yazılmış bir **İkili Arama Ağacı (BST)** kullanılmıştır.
* **Acil Müdahale (Max-Heap):** Kritik tehditleri yönetmek için **Priority Queue** kullanılmıştır. Tehdit seviyesi en yüksek olan log her zaman en başındadır.
* **Sıralama ($O(N \log N)$):** Verileri IP bazlı dizmek için verimli bir **Merge Sort** algoritması gerçeklenmiştir.

### 2. Alan Karmaşıklığı ve Bellek Yönetimi
Sistem, verileri 8 farklı veri yapısında aynı anda tutar. 1 milyon satırda yaklaşık **1 GB RAM** tüketimi gözlemlenmiştir. Testler sırasında Java **Garbage Collector** mekanizmasının etkileri raporlanmıştır.

##  Nasıl Çalıştırılır?
1.  `siber_guvenlik_loglari.csv` dosyasının projenin kök dizininde olduğundan emin olun.
2.  `LogAnalyzer.java` dosyasını bir IDE (IntelliJ, VS Code vb.) ile açın.
3.  `main` metodunu çalıştırın.
4.  **"Hacker Terminali"** temalı panelden veri yükleme ve performans testlerini başlatın.

---

##  Geliştiriciler
Bu proje, **Gazi Üniversitesi Teknoloji Fakültesi Bilgisayar Mühendisliği** öğrencileri tarafından geliştirilmiştir:

* **Ege Ertekin**
* **Melda Kahraman**
