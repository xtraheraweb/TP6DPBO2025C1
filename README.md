# JANJI
Saya Safira Aliyah Azmi dengan NIM 2309209 mengerjakan Tugas Praktikum 6 pada Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

##  Deskripsi 

Program ini merupakan implementasi permainan **Flappy Bird**. Pemain mengendalikan burung yang harus tetap terbang sambil menghindari rintangan berupa pipa yang terus bergerak dari kanan ke kiri. Program menampilkan grafik permainan, mencakup latar belakang, sprite pemain, pipa, skor, dan kontrol permainan.

##  Struktur Program

Program terdiri dari 5 kelas utama:

### 1. `App.java`
- **Fungsi:** Titik awal (*entry point*) program.
- **Tugas:**
  - Membuat dan menampilkan window (`JFrame`).
  - Menambahkan panel permainan (`FlappyBird`).
  - Mengatur ukuran, lokasi, dan visibilitas window.

### 2. `FlappyBird.java`
- **Turunan:** `JPanel`
- **Implementasi:** `ActionListener`, `KeyListener`, `MouseListener`
- **Fungsi:** Menangani keseluruhan logika permainan dan rendering elemen visual.
- **Fitur:**
  - Menggambar background, pemain, pipa, dan skor.
  - Mengatur gravitasi dan lompatan pemain.
  - Deteksi tabrakan dan kondisi game over.
  - Loop permainan menggunakan `Timer`.
  - Menangani input keyboard dan mouse (mulai, jeda, ulang).

### 3. `Player.java`
- **Fungsi:** Mewakili karakter burung yang dikendalikan pemain.
- **Atribut:**
  - `posX`, `posY`: posisi
  - `width`, `height`: ukuran
  - `image`: sprite/gambar pemain
  - `velocityY`: kecepatan vertikal
- **Metode:** Konstruktor dan getter/setter

### 4. `Pipe.java`
- **Fungsi:** Mewakili rintangan berupa pipa yang bergerak.
- **Atribut:**
  - `posX`, `posY`: posisi
  - `width`, `height`: ukuran
  - `image`: sprite pipa
  - `velocityX`: kecepatan horizontal
  - `passed`: status apakah sudah dilewati pemain
- **Metode:** Konstruktor dan getter/setter

### 5. `Score.java`
- **Fungsi:** Menyimpan dan mengelola skor selama permainan berlangsung.
- **Atribut:**
  - `score`: jumlah skor yang didapat pemain

---

##  Alur Permainan

###  Menu Awal
- Menampilkan judul permainan dan tombol **Start**.
- Pemain memulai permainan dengan klik tombol.

###  Saat Bermain
- Tekan tombol **Spasi** untuk terbang.
- Burung akan jatuh karena gravitasi.
- Pipa bergerak dari kanan ke kiri.
- Skor bertambah setiap melewati pipa.
- Tekan **ESC** untuk jeda dan pilih:
  - **Resume**: lanjut permainan.
  - **Restart**: ulang dari awal.
  - **Main Menu**: kembali ke tampilan awal.

###  Game Over
- Terjadi jika:
  - Menabrak pipa
  - Terlalu tinggi atau jatuh ke tanah
- Tampilan "Game Over" muncul dengan skor akhir.
- Tekan **R** untuk memulai ulang permainan.

---

##  Fitur Utama

- ✅ Kontrol sederhana (Spasi, ESC, klik, R)
- ✅ Skor otomatis terhitung setiap lewat pipa
- ✅ Menu jeda dan restart interaktif
- ✅ Grafik custom menggunakan `paintComponent`
- ✅ Deteksi tabrakan pipa dan tanah

---

##  Tampilan Permainan

*(Tambahkan screenshot jika ada)*

---

##  Cara Menjalankan Program

1. Pastikan Java sudah terinstal.
2. Compile semua file Java:
   ```bash
   javac App.java
