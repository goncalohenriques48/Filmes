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

/**
 * Activity inicial da aplicação que serve como página de introdução
 * Apresenta opções para iniciar a app ou ver informações sobre a mesma
 */
class IntroActivity : AppCompatActivity() {
    /**
     * Inicializa a Activity e configura os componentes da interface
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Configura o botão de início que leva à página de login
         */
        val btGetStarted: Button = findViewById(R.id.BT_GetStarted)
        btGetStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        /**
         * Configura o botão "SOBRE" que mostra informações sobre a app
         */
        val btnSobre: Button = findViewById(R.id.btnSobre)
        btnSobre.setOnClickListener {
            mostrarDialogoSobre()
        }
    }

    /**
     * Mostra um diálogo com informações sobre a app
     * Cria e configura um Dialog personalizado com o botão de fechar
     */
    private fun mostrarDialogoSobre() {
        val dialogo = Dialog(this)
        dialogo.setContentView(R.layout.dialog_sobre)

        /**
         * Configuração do botão "fechar"
         */
        val btnFechar: Button = dialogo.findViewById(R.id.btnFechar)
        btnFechar.setOnClickListener {
            dialogo.dismiss()
        }

        dialogo.show()
    }
}