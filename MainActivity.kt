package com.nisaardiyanti.catatan
//nama package
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nisaardiyanti.catatan.model.Note
import com.nisaardiyanti.catatan.model.NoteRepository
import com.nisaardiyanti.catatan.model.InternalFileRepository
import com.nisaardiyanti.catatan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //kelas MainActiviity

    private val repo: NoteRepository by lazy { InternalFileRepository(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //untuk menyambungkan ke halaman ActivityMainBinding
        setContentView(binding.root)

        binding.btnWrite.setOnClickListener {
            //menghubungkan  btnWrite ke halaman selanjutnya dengan btnWrite akan menjadi tombok aktif(dapat terkoneksi)
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    repo.addNote(
                        Note(
                            binding.editFileName.text.toString(),
                            //menyambungkan halaman editFileName
                            binding.editTeksCatatan.text.toString()
                            //menyambungkan editTextCatatan kehalaman selanjutnya
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "File Write Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRead.setOnClickListener {
            //Tombol baca aktif(dapat digunakan/terkoneksi)
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    val note = repo.getNote(binding.editFileName.text.toString())
                    binding.editTeksCatatan.setText(note.noteText)
                } catch (e: Exception) {
                    Toast.makeText(this, "File Read Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                    //Akan tampil komentar File Read Failed jika nama file yang dimasukkan
                    // tidak tersedia pada data yang telah ditambahkan
                }
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
                //akan tampil komentar Please Provide a Filenamejika tidak ada text yang dibuat
            }
        }

        binding.btnDelete.setOnClickListener {
            //tombol hapus dapat digunakan atau aktif
            if (binding.editFileName.text.isNotEmpty()) {
                //jika text tidak ada maka akan tampil komentar seperti:
                try {
                    if (repo.deleteNote(binding.editFileName.text.toString())) {
                        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show()
                        //jika file telah sesuai ketentuan maka file dapat dihapus dan jika berhasil akan
                        // muncul komentar File Deleted
                    } else {
                        Toast.makeText(this, "File Could Not Be Deleted", Toast.LENGTH_LONG).show()
                        //Akan tampil komentar File Could Not Be Deleted jika namadile tidak ada/tidak sesuai dengan nama file yang
                        //telah ditambahkan
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "File Delete Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
                //akan tampil komentar Please provide a Filename jika namafile tidak dimasukkan
            }
        }
    }
}