package pt.ipt.dam.movies

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        // Adicionar listener para o TextView de login
        val textViewLoginNow: TextView = findViewById(R.id.textViewLoginNow)
        textViewLoginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Fecha a activity atual para não acumular na pilha
        }
    }
}
