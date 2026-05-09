# Ice Sliding Puzzle Solver
Tugas Kecil 3 IF2211 Strategi Algoritma (Algoritma Pathfinding)

## Deskripsi Program
Ice Sliding Puzzle Solver adalah program untuk menyelesaikan permainan Ice Sliding Puzzle menggunakan algoritma pathfinding. Pada permainan ini, pemain harus menggerakkan karakter dari posisi awal menuju tujuan pada papan es yang licin. Karakter hanya dapat bergerak ke arah atas, bawah, kiri, atau kanan, dan akan terus meluncur hingga menabrak dinding atau rintangan.

Program ini mendukung beberapa algoritma pencarian jalur, yaitu:
- Uniform Cost Search (UCS)
- Greedy Best First Search (GBFS)
- A Search*
- Breadth First Search (BFS) (algoritma bonus)

Untuk algoritma berbasis heuristic (GBFS dan A*), program menyediakan tiga heuristic:
- H1 (Manhattan Heuristic)
- H2 (Checkpoint Heuristic)
- H3 (Advanced Heuristic)

Program menyediakan dua mode penggunaan:
- CLI (Command Line Interface) → Interaksi melalui terminal
- GUI (Graphical User Interface) → Visualisasi interaktif menggunakan JavaFX

Fitur utama program:
- Membaca konfigurasi papan dari file .txt
- Menemukan jalur menuju goal
- Menampilkan total cost solusi
- Menampilkan jumlah iterasi dan waktu eksekusi
- Playback langkah solusi (pada mode CLI)
- Menyimpan hasil pencarian ke file output
- Visualisasi langkah solusi melalui GUI

## Requirement Program
Program ini dibuat dengan menggunakan bahasa pemrograman Java dengan GUI menggunakan framework JavaFx.

### Software yang Dibutuhkan
Pastikan sistem telah terinstall:
#### 1. Java Development Kit (JDK)
JDK versi minimum Java 17 atau lebih baru.
Cek instalasi:
```java --version```

#### 2. Apache Maven
Digunakan untuk dependency management dan build project.
Cek instalasi:
```mvn --version```

#### 3. JavaFX
Dependency JavaFX sudah dikelola menggunakan Maven (pom.xml), sehingga tidak perlu instalasi manual.

## Struktur Folder
```bash
Tucil3_13524073_13524109
├── src/            # source kode program
├── test/           # file input testcase
├── output/         # hasil output solusi
├── pom.xml         # dependency Maven
├── Makefile        # shortcut command build/run
└── README.md       
```

## Cara Kompilasi dan Menjalankan Program
### 1. Menggunakan Mode CLI
Jalankan command:
```bash
make clean
make all
make run
```

Program akan meminta input:
- Nama file testcase (dari folder test)
- Pilihan algoritma
- Pilihan heuristic (untuk algoritma GBFS dan A*)

### Menggunakan Mode GUI
Jalankan command:
```bash
mvn javafx:run
```
Pada GUI, user dapat:
- Memilih file input .txt dari 
- Memilih algoritma
- Memilih heuristic (jika diperlukan)
- Menjalankan solver
- Melihat visualisasi solusi
- Menyimpan hasil solusi

## Format File Input
Input konfigurasi papan menggunakan file dengan format teks (.txt)
Contoh :
```bash
7 7
XXXXXXX
X0****X
X**X**X
X****OX
X1***LX
XZ**X*X
XXXXXXX
999 999 999 999 999 999 999
999 3   5   2   8   1   999
999 7   4   999 6   9   999
999 2   8   3   5   4   999
999 6   1   7   2   999 999
999 9   3   4   999 8   999
999 999 999 999 999 999 999  
```

Keterangan simbol: 
* = path yang bisa dilewati   
X = Rintangan/Batu. Aktor akan berhenti tepat sebelum batu.  
L = Lava. Melewati lava akan mengalami game over (Meskipun tidak berhenti tepat di lava).  
Z = Aktor/Pengguna  
O = Titik tujuan   
<i> = Angka checkpoint  

## Author
Anggota 1
Nama : Keisha Rizka Syofyani
NIM : 13524073

Anggota 2
Nama : Helena Kristela Sarhawa
NIM : 13524109

Program Studi Teknik Informatika
Sekolah Teknik Elektro dan Informatika
Institut Teknologi Bandung
2026





