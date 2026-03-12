# Refleksi Modul 4

## Refleksi 1 TDD Flow

Menurut saya, TDD flow yang diikuti di tutorial ini cukup berguna untuk memahami cara kerja pengembangan berbasis test. Dengan melihat bagaimana test seperti `OrderTest`, `OrderRepositoryTest`, dan `OrderServiceImplTest` ditulis terlebih dahulu sebelum implementasinya ada, saya jadi lebih paham kenapa urutan itu penting. Dengan nulis test duluan, kita dipaksa mikirin dulu "fitur ini seharusnya ngapain?" sebelum langsung loncat ke implementasi.

Dari mengikuti tutorial ini, ada beberapa hal yang saya catat sebagai pelajaran untuk diterapkan saat membuat test sendiri ke depannya:

1. Siklus Red Green Refactor harus diikuti secara disiplin. Tutorial ini menunjukkan bahwa menulis test yang gagal dulu (Red) itu punya tujuan, yaitu memastikan test benar benar menguji sesuatu, bukan sekadar ikut ikutan pass karena implementasinya sudah ada duluan.

2. Fase Refactor tidak boleh dilewati. Setelah test berhasil Green, kode perlu dirapikan tanpa mengubah perilakunya. Ini yang sering terlewat kalau tidak terbiasa dengan TDD.

3. Edge case perlu dipikirkan sejak awal. Melihat test seperti `testCreateOrderEmptyProduct` dan `testCreateOrderInvalidStatus`, saya belajar bahwa test yang baik tidak hanya menguji happy path, tapi juga kondisi kondisi yang tidak valid.

## Refleksi 2 F.I.R.S.T. Principle

1. F (Fast)
Semua unit test menggunakan `@ExtendWith(MockitoExtension.class)` dengan dependency yang dimock, sehingga tidak ada akses ke database atau network. Setiap test berjalan dengan cepat.

2. I (Isolated/Independent)
Setiap test punya `@BeforeEach` yang mereset state dari awal (membuat ulang `products` dan `orders`), sehingga antar test tidak saling mempengaruhi. Penggunaan mock juga memastikan tidak ada shared state yang bocor antar test.

3. R (Repeatable)
Karena tidak bergantung pada database, waktu sistem, atau resource eksternal, test bisa dijalankan berulang kali di environment manapun dengan hasil yang konsisten.

4. S (Self Validating)
Semua test menggunakan assertion yang eksplisit seperti `assertEquals`, `assertNull`, `assertThrows`, dan `assertTrue`, sehingga bisa menentukan sendiri apakah pass atau fail tanpa perlu dicek manual.

5. T (Timely)
Test test di tutorial ini memang dirancang untuk ditulis sebelum implementasinya ada, sesuai prinsip TDD. Hal ini terlihat dari bagaimana `OrderServiceImplTest` sudah lengkap sementara `OrderServiceImpl` belum dibuat, yang memang merupakan titik awal yang benar dalam siklus TDD.