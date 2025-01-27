package pt.ipt.dam.movies

import android.app.Dialog
import android.widget.TextView
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btGetStarted: Button = findViewById(R.id.BT_GetStarted)
        btGetStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Configurar o botão Sobre
        val btnSobre: Button = findViewById(R.id.btnSobre)
        btnSobre.setOnClickListener {
            mostrarDialogoSobre()
        }
    }

    private fun mostrarDialogoSobre() {
        val dialogo = Dialog(this)
        dialogo.setContentView(R.layout.dialog_sobre)

        // Configurar o botão de fechar
        val btnFechar: Button = dialogo.findViewById(R.id.btnFechar)
        btnFechar.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }
}