# JANJI
Saya Safira Aliyah Azmi dengan NIM 2309209 mengerjakan Tugas Praktikum 6 pada Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

##  Deskripsi 

Program ini merupakan implementasi permainan **Flappy Bird**. Pemain mengendalikan burung yang harus tetap terbang sambil menghindari rintangan berupa pipa yang terus bergerak dari kanan ke kiri. Program menampilkan grafik permainan, mencakup latar belakang, sprite pemain, pipa, skor, dan kontrol permainan.

##  Struktur Program

Program terdiri dari 4 kelas utama:

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
  - `score`: jumlah skor yang didapat pemain

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

- Kontrol sederhana (Spasi, ESC, klik, R)
- Skor otomatis terhitung setiap lewat pipa
- Deteksi tabrakan pipa dan tanah

---

##  Tampilan Permainan

![Image](https://github.com/user-attachments/assets/b4768d28-f245-40e6-a55a-e3b4d1bd5187)
![Image](https://github.com/user-attachments/assets/40a528b6-1992-4e73-bc51-3f6c5fee02d7)
![Image](https://github.com/user-attachments/assets/35981119-b960-4444-b1cf-4829290b491b)
![Image](https://github.com/user-attachments/assets/870bd093-d340-46e0-a654-85d73b7b9d57)

