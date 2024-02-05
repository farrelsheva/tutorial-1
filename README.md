### Refleksi 1 

Dalam mengerjakan Exercise 1, saya berpikir ada kepentinganya dalam cara kita bisa membuat proses untuk membangun aplikasi dan menambahkan fitur
dengan meminimalisasi masalah yang mungkin terjadi. Workflow yang baik dan code yang rapih (clean code) dapat membantu kita dalam meminimalisasi masalah dan memudahkan kita dalam mengisolasi
masalah/error yang terjadi. 

Dalam source Code yang diberikan, saya menemukan beberapa masalah yang mungkin tidak diinginkan, seperti:
- Dalam model 'Product', ID merupakan string dan bisa dijadikan seperti primary key dalam action edit dan delete, namun
  Id in tidak digenerate oleh sistem, sehingga saya tambahkan id generation pada repository.
- Saat membuat edit page, saya menemukan roadblock saat merouting form ke aksi edit, ternyata setelah dilihat, aksi merouting ke route yang salah