package pt.ipt.dam.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import pt.ipt.dam.movies.utils.SupabaseClient

/**
 * Activity responsável pela página de login do utilizador
 */
class LoginActivity : AppCompatActivity() {
    // Declaração das variáveis para os elementos da UI
    private lateinit var userEdt: EditText
    private lateinit var passEdt: EditText
    private lateinit var loginBtn: Button

    /**
     * Método chamado quando a Activity é criada
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa os elementos da UI
        initView()

        // Configura o botão de registo
        val textViewRegisterNow: TextView = findViewById(R.id.textView10)
        textViewRegisterNow.setOnClickListener {
            // Navega para a página de registo
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * Inicializa e configura os elementos da interface do utilizador
     */
    private fun initView() {
        // Encontra os elementos da UI pelo ID
        userEdt = findViewById(R.id.editTextText)
        passEdt = findViewById(R.id.editTextPassword)
        loginBtn = findViewById(R.id.BT_Login)

        // Configura o listener do botão de login
        loginBtn.setOnClickListener {
            // Verifica se os campos estão preenchidos
            if (userEdt.text.toString().isEmpty() || passEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                // Tenta fazer login com as informações fornecidas
                loginWithSupabase(userEdt.text.toString(), passEdt.text.toString())
            }
        }
    }

    /**
     * Realiza a autenticação do utilizador usando o Supabase
     * @param email Email do utilizador
     * @param password Password do utilizador
     */
    private fun loginWithSupabase(email: String, password: String) {
        lifecycleScope.launch {
            try {
                // Tenta fazer login no Supabase
                SupabaseClient.client.gotrue.loginWith(Email) {
                    this.email = email
                    this.password = password
                }
                // Se o login for bem sucedido, vai para a MainActivity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                // Em caso de erro, mostra uma mensagem para o utilizador
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Erro no login: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}