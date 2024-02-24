App url : https://swag-farrelsheva.koyeb.app

### Refleksi 1 

Dalam mengerjakan Exercise 1, saya berpikir ada kepentinganya dalam cara kita bisa membuat proses untuk membangun aplikasi dan menambahkan fitur
dengan meminimalisasi masalah yang mungkin terjadi. Workflow yang baik dan code yang rapih (clean code) dapat membantu kita dalam meminimalisasi masalah dan memudahkan kita dalam mengisolasi
masalah/error yang terjadi. 

Dalam source Code yang diberikan, saya menemukan beberapa masalah yang mungkin tidak diinginkan, seperti:
- Dalam model 'Product', ID merupakan string dan bisa dijadikan seperti primary key dalam action edit dan delete, namun
  Id in tidak digenerate oleh sistem, sehingga saya tambahkan id generation pada repository, namun setelah itu saya sadar bahwa untuk unit testing, diperlukanya agar kita bisa mengunit test dengan ID yang dibuat sendiri, Karena ini 
  Id generation saya pindahkan ke layer service.
- Saat membuat edit page, saya menemukan roadblock saat merouting form ke aksi edit, ternyata setelah dilihat, aksi merouting ke route yang salah

### Refleksi 2.1
1. ***Perasaan saya*** dalam mengerjakan exercise unit test adalah kegelisihan untuk memastikan bahwa setiap aspek yang bisa dilakukan oleh user dapat saya test dengan baik.
Karena ini Unit Test mencakupi beberapa case yang mungkin terjadi. Menurut saya sebuah function dari aplikasi yang dibuat *Minimal* memiliki 1 unit test (jika sangat sederhana)
Namun ini bukan berarti kita harus membuat 1 unit test yang mempunyai kompleksitas tinggi, sebuah testing yang bagus haruslah simple, cepat, dan mudah dipahami oleh programmer lain.
Hal ini memastikan kita bisa mengisolasi error yang terjadi dan mendebug dengan cepat. Perasaan yang paling buruk adalah waktu dimana kita merasa sudah mendebug suatu error yang terjadi namun memunculkan error lain yang tidak kita sadari.

2. Dengan menduplikasi Setup dan Prosedur yang kita sudah pernah buat, kita akan melawan prinsip DRY (*Don't Repeat Yourself*) yang membuat codebase susah untuk dimaintain, dan dimodifikasi dikarenakan modularitas yang berantakan.
Menduplikasi setup juga merugikan aspek *Readibliity*, bayangkan jika sebuah programmer yang ingin memodifikasi codebase harus membaca dua class yang sangat mirip dengan perbedaan yang minim, ini pasti akan menghabiskan banyak waktu dan energi.
Salah satu solusi yang saya bisa pikirkan merupakan pengidentifikasian setup yang sama di semua test suites yang kita buat, dan lalu membuat sebuah base class yang bisa dibagikan, ini akan mengurangi duplikasi dan membuat codebase lebih consistent.

### Refleksi 2.2
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

### Refleksi 3
1. Prinsip SOLID yang saya aplikasikan:
- Single Responsiility; Controller `Car` dan `Product` saya pisahkan menjadi dua class yang berbeda, ini dikarenakan kedua class mempunya tanggung jawab yang berbeda dengan fungsionalitasnya, jadi `CarController` tidak lagi mengextend `ProductController`, dikarenakan kedua class ini mempunyai tanggung jawab yang berbeda ini akan membantu saya dalam memodifikasi kedua class ini tanpa mempengaruhi class lain.
- Open/Closed; Memodifikasi `CarRepository` untuk mengset semua properti dari car dibandingkan dengan mengubahnya satu-satu, ini memugkinkan perubaha atau penambahan atribute pada class `Car` tanpa memerlukan perubahan langsung kepada method `update` pada repository layer
- Depenedency Inversion; Dalam CarController saya mengubah depenency dari `CarServiceImpl`, menjadi interfacenya `CarService`, Decoupling kedua class ini memungkinkan untuk melakukan modifikasi pada `CarServiceImpl` akan mempunyai efek yang minimal pada `CarController` (Selama interface tidak diubah)
- Interface Segregation; Walaupun interface `CarSerice` dan `ProductService` mempunyai semua method yang sama, kedua interface ini tetap dipisah karena kita tahu bahwa mereka mempunyai tanngung jawab yang berbeda, dengan memisah dan mengspelialisasikan kedua interface ini, kita bisa memastikan bahwa class yang mengimplementasikan interface ini mempunyai method yang sesuai dengan kebutuhan class tersebut.

2. Advantage dari menggunakan prinsip SOLID:
- Meningkatkan *Readibility* dan *Maintainability* dari codebase, dengan memisahkan tanggung jawab dari class yang berbeda, kita bisa memastikan bahwa class tersebut mempunyai fungsionalitas yang jelas dan mudah dipahami oleh programmer lain. Sebuah class atau method panjang akan sangat susah dibaca apalagi untuk dimaitain jika kita tidak memisahkan tanggung jawab dari class tersebut.

- Meningkatkan *Testability*, codebase dengan banyak dependensi akan sangat sulit untuk membuat unit test yang pendek dan readable, dengan menggunakan prinsip SOLID, kita bisa memastikan bahwa class yang kita buat mempunyai dependensi yang minimal dan mudah untuk diisolasi (membantu debugging dengan efisien).

- Meningkatkan *Scalability*, dalam aspek ini open/closed principle sangat membantu kita dalam memastikan bahwa codebase kita bisa diextend dengan mudah tanpa mempengaruhi class yang sudah ada, ini memungkinkan kita untuk membuat fitur baru yang berinteraksi secara bagus dengan fitur lama dengan cepat dan efisien.

3. Disadvantage dari tidak menggunakan prinsip SOLID:
- Codebase yang sulit untuk dimaintain
- Code yang sulit untuk dibaca dan dipahami
- Code yang sulit untuk ditest,  
- Code yang sulit untuk diextend