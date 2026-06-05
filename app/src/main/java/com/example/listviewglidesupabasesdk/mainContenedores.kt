package com.example.listviewglidesupabasesdk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.listviewglidesupabasesdk.Adapters.AlumnoAdapter
import com.example.listviewglidesupabasesdk.Models.Alumno
import com.example.listviewglidesupabasesdk.Models.Materia
import com.example.listviewglidesupabasesdk.Services.SupabaseManager
import com.example.listviewglidesupabasesdk.Utils.SupabaseErrorHandler
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class mainContenedores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contenedores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val actvMaterias = findViewById<AutoCompleteTextView>(R.id.actvListaMaterias)
        val actvListaNiveles =   findViewById<AutoCompleteTextView>(R.id.actvListaNiveles)
        val lvAlumnos = findViewById<ListView>(R.id.lvAlumnos)



        actvListaNiveles.setOnItemClickListener {
                parent, view, position, id ->


            actvMaterias.setText("")
            val lstMaterias = ArrayList<String>()
            lifecycleScope.launch {
                try {
                    val listaMaterias = ArrayList(
                        SupabaseManager.client
                            .from("materias")
                            .select {
                                filter {
                                    eq("nivel", position+1)
                                }
                                order("nombre", Order.ASCENDING)
                            }
                            .decodeList<Materia>()
                    )


                    for (materia in listaMaterias) {
                        lstMaterias.add(materia.nombre ?: "")
                    }

                } catch (e: RestException) {
                    SupabaseErrorHandler.show(this@mainContenedores, e)
                    lstMaterias.clear()
                } finally {
                    val adapter = ArrayAdapter(
                        this@mainContenedores,
                        android.R.layout.simple_spinner_dropdown_item,
                        lstMaterias
                    )

                    actvMaterias.setAdapter(adapter)

                }
            }

        }

        actvMaterias.setOnItemClickListener {
                parent, view, position, id ->

            var lstAlumnos = ArrayList<Alumno>()
            lifecycleScope.launch {
                try {
                    lstAlumnos = ArrayList(
                        SupabaseManager.client
                            .from("alumnos")
                            .select {
                                order("nombres", Order.ASCENDING)
                            }
                            .decodeList<Alumno>()
                    )

                } catch (e: RestException) {
                    SupabaseErrorHandler.show(this@mainContenedores, e)
                }finally {
                    val adapter = AlumnoAdapter(this@mainContenedores, lstAlumnos)
                    lvAlumnos.adapter = adapter
                }
            }

        }
    }
}