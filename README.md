# Refleksi Modul 2

## Refleksi 1 – Code Quality Issues

Pas jalanin SonarCloud scan lewat GitHub Actions, hasilnya aman nggak ada issue yang ketangkep. Tapi ada beberapa hal yang saya benerin sendiri pas review kode manual

1. Redundant `public` di interface. Di Java, method dalam interface itu udah otomatis `public`, jadi nulis `public` lagi itu sebenernya nggak perlu. Saya hapus aja biar kodenya lebih bersih dan nggak bikin bingung.

2. Double semicolon (`;;`). Di `ProductServiceImpl.java` bagian `findAll()` ada typo kecil, dua titik koma sekaligus. Java sih masih bisa jalan, tapi tetep saya benerin biar kode nya rapi.

## Refleksi 2 – CI/CD Implementation

Menurut saya, implementasi CI/CD yang udah dibuat ini udah memenuhi definisi CI dan CD. Di sisi CI, setiap ada push atau pull request langsung otomatis ngejalanin unit test, ngukur code coverage pakai JaCoCo, scan kualitas kode pakai SonarCloud, dan cek keamanan dependency lewat OSSF Scorecard, semua tanpa harus ngapa-ngapain manual. Di sisi CD, setiap perubahan yang masuk ke branch `main` langsung otomatis kedeploy ke Railway pakai Dockerfile, jadi versi terbaru aplikasi langsung live tanpa perlu deploy sendiri. Menurut saya ini bagus, karena kode yang naik ke production udah pasti udah lewat serangkaian pengecekan otomatis duluan

## Refleksi 3 - Maintainability & OO Principles

1. Prinsip SOLID yang saya terapkan

- SRP (Single Responsibility Principle): `CarController` dipisahkan ke file sendiri. Sebelumnya dia digabung bareng `ProductController` dalam satu file, padahal dua controller ini punya tanggung jawab yang berbeda.

- OCP (Open/Closed Principle): `CarController` sekarang bergantung ke interface `CarService`, bukan langsung ke `CarServiceImpl`. Kalau mau ganti implementasi service-nya, nggak perlu ubah controller sama sekali.

- LSP (Liskov Substitution Principle): Dihapusnya `CarController extends ProductController`. Relasi warisan itu nggak masuk akal karena `CarController` bukan "versi khusus" dari `ProductController`, jadi subclass nggak bisa menggantikan parent-nya dengan benar.

- ISP (Interface Segregation Principle): Keyword `public` yang redundant di interface `CarService` dihapus. Interface tetap bersih dan hanya berisi kontrak yang relevan.

- DIP (Dependency Inversion Principle): `CarServiceImpl` dan `ProductServiceImpl` diubah dari field injection (`@Autowired` langsung ke field) ke constructor injection. Dengan begitu, dependency-nya eksplisit dan high-level module nggak langsung bergantung ke low-level module.

2. Keuntungan pakai SOLID

- Kode lebih gampang dibaca dan dimaintain. Misalnya, karena `CarController` sudah pisah file, kalau ada bug di fitur Car tinggal buka `CarController.java` aja, nggak perlu scroll di file yang isinya campur-campur.

- Lebih mudah di-test. Karena `CarController` pakai interface `CarService` bukan langsung `CarServiceImpl`, di unit test bisa di-mock dengan mudah pakai `@MockitoBean CarService`.

- Lebih fleksibel untuk berkembang. Kalau suatu saat mau ganti implementasi `CarService` (misalnya dari in-memory ke database), tinggal buat class baru yang implement `CarService`, tanpa perlu ubah `CarController` sama sekali.

3. Kekurangan kalau SOLID nggak diterapkan

- Sebelum SRP diterapkan, `CarController` dan `ProductController` ada di satu file. Akibatnya, test `@WebMvcTest(ProductController.class)` ikut load `CarController` yang butuh bean `CarServiceImpl` — padahal bean itu nggak di-mock, sehingga CI langsung gagal.

- Kalau DIP nggak diterapkan dan tetap pakai field injection, dependency jadi tersembunyi dan susah dideteksi. Testing juga jadi lebih rumit karena Spring harus fully inject semua bean.

- Kalau LSP diabaikan dan `CarController extends ProductController` tetap ada, perilaku aplikasi jadi nggak predictable karena subclass mewarisi method yang nggak relevan, dan bisa override sesuatu yang nggak seharusnya.