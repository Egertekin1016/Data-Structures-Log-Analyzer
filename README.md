🛡️ Siber Tehdit İstihbarat ve Log Analiz Sistemi
Gazi Üniversitesi BMT210 Veri Yapıları dersi kapsamında geliştirilen bu proje; bir siber güvenlik operasyon merkezine (SOC) düşen milyonlarca ağ logunu analiz etmek, tehditleri önceliklendirmek ve siber saldırıların ağ içindeki izlerini sürmek amacıyla tasarlanmış bir mühendislik çözümüdür.

📋 Projenin Amacı
Projenin temel amacı, teorik veri yapıları (Dizi, Bağlı Liste, Yığın, Kuyruk, Ağaç, Graf, Hash vb.) ve algoritmaların (Sorting, BFS), gerçek dünya ölçeğinde bir veri setinde (1 Milyon+ satır) performans, bellek yönetimi ve işlem hızı açısından nasıl farklar yarattığını somutlaştırmaktır.

📁 Dosya Yapısı ve Görevleri
Sistem, Modüler ve Nesne Yönelimli Programlama (OOP) prensiplerine uygun olarak 3 ana sınıftan oluşur:

LogEntry.java (Model Katmanı): Her bir siber güvenlik logunu temsil eden nesne kalıbıdır. Timestamp, SourceIP, DestinationIP, Protocol, Port, ThreatLevel ve Action gibi kritik siber istihbarat verilerini kapsüller.

VeriMerkezi.java (Veri Yönetim Katmanı): Uygulamanın beynidir. Tüm veri yapılarının (ArrayList, LinkedList, Stack, Queue, PriorityQueue, HashMap, HashSet, Graph, BST) tanımlandığı ve yönetildiği merkezdir. Verilerin bellekte (RAM) nasıl organize edileceğine dair kuralları barındırır.

LogAnalyzer.java (Kontrol ve Arayüz Katmanı): Kullanıcı ile sistemin etkileşime girdiği sınıftır. GUI (Swing) kodlarını, arama/sıralama algoritmalarını (Merge Sort, BFS) ve performans test motorunu içerir.

siber_guvenlik_loglari.csv (Veri Seti): Sistemin analiz ettiği ham veridir. Gerçek bir ağ trafiğini simüle edecek şekilde; milyonlarca satırlık, virgülle veya noktalı virgülle ayrılmış siber güvenlik verilerini içerir.

📊 Veri Seti (Loglar) Nasıl Oluşturuldu?
Log dosyası, tipik bir IDS/IPS (Saldırı Tespit Sistemi) çıktısını simüle edecek şekilde yapılandırılmıştır. Her satır bir ağ hareketini temsil eder:

Kaynak ve Hedef IP: Saldırgan ve kurban cihazların adresleri.

Port: Saldırının hedeflediği servis (Örn: 80-HTTP, 443-HTTPS, 22-SSH).

Threat Level (Tehdit Seviyesi): Low, Medium, High, Critical olarak etiketlenmiştir. Bu etiketler, Priority Queue yapısının veriyi nasıl önceliklendireceğini belirlemek için kullanılır.

Action: Sistemin aldığı aksiyon (ALLOW veya DENY).

⚙️ Algoritmalar ve Veri Yapıları (Teknik Detay)
Projenin başarısı, her işlemin doğasına en uygun veri yapısının seçilmesinden gelir:

1. Zaman Karmaşıklığı (Time Complexity) Analizi

Shutterstock
Keşfet
Hızlı Erişim (O(1)): IP adreslerine anında ulaşmak için HashMap kullanılmıştır. Milyonlarca kayıt arasında arama süresi veri boyutundan bağımsızdır.

Hiyerarşik Arama (O(logN)): Port numaralarını analiz etmek için sıfırdan yazılmış bir İkili Arama Ağacı (BST) kullanılmıştır.

Acil Müdahale (Max-Heap): Kritik tehditleri yönetmek için Priority Queue kullanılmıştır. Tehdit seviyesi en yüksek olan log, her zaman kuyruğun en başındadır.

Sıralama (O(NlogN)): Verileri IP bazlı dizmek için verimli bir Merge Sort (Böl ve Fethet) algoritması gerçeklenmiştir.

2. Alan Karmaşıklığı (Space Complexity) ve Bellek Yönetimi
Sistem, verileri 8 farklı veri yapısında aynı anda tutar. Bu durum, 1 milyon satırda yaklaşık 1 GB RAM tüketimine yol açar. Bu testler sırasında, Java'nın Garbage Collector mekanizmasının dinamik bellek üzerindeki etkileri gözlemlenmiş ve raporlanmıştır.

🛠️ Nasıl Çalıştırılır?
GitHub reposunu bilgisayarınıza klonlayın: git clone https://github.com/kullaniciadi/proje-adi.git

siber_guvenlik_loglari.csv dosyasının projenin kök dizininde olduğundan emin olun.

LogAnalyzer.java dosyasını favori IDE'nizde (IntelliJ, Eclipse, VS Code) açın.

main metodunu çalıştırın.

Açılan "Hacker Terminali" temalı panelden:

Menü 1: Veri yükleme (10K, 100K veya 1M seçebilirsiniz).

Menü 2: Performans testlerini koşturup CSV raporu oluşturma.

Diğer Menüler: Manuel IP arama, Graph analizi ve Stack tabanlı geri alma işlemlerini yapma.

👨‍💻 Geliştiriciler
Bu proje, Gazi Üniversitesi Teknoloji Fakültesi Bilgisayar Mühendisliği öğrencilerinden oluşan 2 kişilik ekip tarafından geliştirilmiştir:

Ege Ertekin

Melda Kahraman

Bu çalışma, Bilgisayar Mühendisliği eğitiminin "Veri Yapıları" dersi final projesi olarak sunulmuştur.
