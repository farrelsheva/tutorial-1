### Refleksi 1 

Dalam mengerjakan Exercise 1, saya berpikir ada kepentinganya dalam cara kita bisa membuat proses untuk membangun aplikasi dan menambahkan fitur
dengan meminimalisasi masalah yang mungkin terjadi. Workflow yang baik dan code yang rapih (clean code) dapat membantu kita dalam meminimalisasi masalah dan memudahkan kita dalam mengisolasi
masalah/error yang terjadi. 

Dalam source Code yang diberikan, saya menemukan beberapa masalah yang mungkin tidak diinginkan, seperti:
- Dalam model 'Product', ID merupakan string dan bisa dijadikan seperti primary key dalam action edit dan delete, namun
  Id in tidak digenerate oleh sistem, sehingga saya tambahkan id generation pada repository, namun setelah itu saya sadar bahwa untuk unit testing, diperlukanya agar kita bisa mengunit test dengan ID yang dibuat sendiri, Karena ini 
  Id generation saya pindahkan ke layer service.
- Saat membuat edit page, saya menemukan roadblock saat merouting form ke aksi edit, ternyata setelah dilihat, aksi merouting ke route yang salah

### Refleksi 2
1. ***Perasaan saya*** dalam mengerjakan exercise unit test adalah kegelisihan untuk memastikan bahwa setiap aspek yang bisa dilakukan oleh user dapat saya test dengan baik.
Karena ini Unit Test mencakupi beberapa case yang mungkin terjadi. Menurut saya sebuah function dari aplikasi yang dibuat *Minimal* memiliki 1 unit test (jika sangat sederhana)
Namun ini bukan berarti kita harus membuat 1 unit test yang mempunyai kompleksitas tinggi, sebuah testing yang bagus haruslah simple, cepat, dan mudah dipahami oleh programmer lain.
Hal ini memastikan kita bisa mengisolasi error yang terjadi dan mendebug dengan cepat. Perasaan yang paling buruk adalah waktu dimana kita merasa sudah mendebug suatu error yang terjadi namun memunculkan error lain yang tidak kita sadari.

2. Dengan menduplikasi Setup dan Prosedur yang kita sudah pernah buat, kita akan melawan prinsip DRY (*Don't Repeat Yourself*) yang membuat codebase susah untuk dimaintain, dan dimodifikasi dikarenakan modularitas yang berantakan.
Menduplikasi setup juga merugikan aspek *Readibliity*, bayangkan jika sebuah programmer yang ingin memodifikasi codebase harus membaca dua class yang sangat mirip dengan perbedaan yang minim, ini pasti akan menghabiskan banyak waktu dan energi.
Salah satu solusi yang saya bisa pikirkan merupakan pengidentifikasian setup yang sama di semua test suites yang kita buat, dan lalu membuat sebuah base class yang bisa dibagikan, ini akan mengurangi duplikasi dan membuat codebase lebih consistent.

### Refleksi 3
1. Setelah menggunakan PMD sebagai code analysis tool, ada beberapa quality issue yang dideteksi:
- **Unused Imports**: ruleset PMD tidak menyukai imports dimana * digunakan jika hanya satu atau dua class dari module yang digunakan, ini bisa dihilangkan dengan mudah
- **Unnecessary modifier 'public'**: Pada interface service layer, saya memakai public modifier untuk semua method, ini bisa dihilangkan karena semua method pada interface secara default public

2. Secara teknis sudah, namun merupakan sebuah bentuk yang sangat primitif. Aplikasi yang dibuild tidak mempunyai dokumentasi yang cukup ataupun version control yang merupakan hal penting dalam operasi Devops.
Tetapi semua hal esential dalam CI/CD sudah terpenuhi, seperti:
- Source code dalam single repository
- Code Main branch yang digunakan untuk deployment
- Unit Test yang dijalankan sebelum deployment secara otomatis
- Deployment yang otomatis
- Monitoring yang otomatis