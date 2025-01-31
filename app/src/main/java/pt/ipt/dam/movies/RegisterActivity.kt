package pt.ipt.dam.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch
import pt.ipt.dam.movies.utils.SupabaseClient
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo

/**
 * Activity responsável pelo registo de novos utilizadores
 * Permite que novos utilizadores criem uma conta na aplicação
 */
class RegisterActivity : AppCompatActivity() {
    /**
     * Campos de entrada para os dados do registo
     */
    private lateinit var nameEdt: EditText
    private lateinit var emailEdt: EditText
    private lateinit var passwordEdt: EditText
    private lateinit var confirmPasswordEdt: EditText
    private lateinit var registerBtn: Button

    /**
     * Inicializa a Activity e configura a interface
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()

        val textViewLoginNow: TextView = findViewById(R.id.textViewLoginNow)
        textViewLoginNow.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    /**
     * Inicializa e configura todos os componentes da interface
     * Define os listeners para a validação e envio do formulário
     */
    private fun initView() {
        nameEdt = findViewById(R.id.editTextName)
        emailEdt = findViewById(R.id.editTextEmail)
        passwordEdt = findViewById(R.id.editTextRegisterPassword)
        confirmPasswordEdt = findViewById(R.id.editTextConfirmPassword)
        registerBtn = findViewById(R.id.BT_Register)

        registerBtn.setOnClickListener {
            if (validateInputs()) {
                registerWithSupabase(
                    emailEdt.text.toString(),
                    passwordEdt.text.toString()
                )
            }
        }
    }

    /**
     * Valida os campos do formulário antes do registo
     * @return true se todos os campos estiverem preenchidos corretamente
     */
    private fun validateInputs(): Boolean {
        if (nameEdt.text.toString().isEmpty() ||
            emailEdt.text.toString().isEmpty() ||
            passwordEdt.text.toString().isEmpty() ||
            confirmPasswordEdt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (passwordEdt.text.toString() != confirmPasswordEdt.text.toString()) {
            Toast.makeText(this, "As passwords não coincidem", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    /**
     * Realiza o registo do utilizador usando o Supabase
     * Em caso de sucesso, redireciona para a MainActivity
     * Em caso de erro, mostra uma mensagem ao utilizador
     */
    private fun registerWithSupabase(email: String, password: String) {
        lifecycleScope.launch {
            try {
                // Criar novo utilizador
                SupabaseClient.client.gotrue.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                // Registo bem sucedido
                Toast.makeText(
                    this@RegisterActivity,
                    "Registo realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                // Redirecionar para login
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Erro no registo: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
