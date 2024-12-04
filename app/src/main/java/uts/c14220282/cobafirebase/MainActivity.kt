package uts.c14220282.cobafirebase

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.sql.Array

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    var DataProvinsi = ArrayList<daftarProvinsi>()
    lateinit var lvAdapter: ArrayAdapter<daftarProvinsi>
    lateinit var _etProvinsi : EditText
    lateinit var _etIbukota : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _etProvinsi = findViewById<EditText>(R.id.etProvinsi)
        _etIbukota = findViewById<EditText>(R.id.etIbukota)
        val _btSimpan = findViewById<Button>(R.id.btSimpan)
        val _lvData = findViewById<ListView>(R.id.lvData)


        lvAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataProvinsi
        )
        _lvData.adapter = lvAdapter



        fun tambahData(db: FirebaseFirestore, Provinsi: String, Ibukota : String) {
            val dataBaru = daftarProvinsi(Provinsi, Ibukota)
            db.collection("tbProvinsi")
                .add(dataBaru)
                .addOnSuccessListener {
                    _etProvinsi.setText("")
                    _etIbukota.setText("")
                    Log.d("Firebase", "Data berhasil disimpan")
                }
                .addOnFailureListener{
                    Log.d("Firebase",it.message.toString())
                }
        }
        // stuck di point 23 disini


        fun readData(db: FirebaseFirestore) {
            db.collection("tbProvinsi").get()
                .addOnSuccessListener{
                    result ->
                    DataProvinsi.clear()
                    for (document in result) {
                        val readData = daftarProvinsi(
                            document.data.get("provinsi").toString(),
                            document.data.get("ibukota").toString()
                        )
                        DataProvinsi.add(readData)
                    }
                    lvAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener{
                    Log.d("Firebase",it.message.toString())
                }
        }
        // tes
    }
}