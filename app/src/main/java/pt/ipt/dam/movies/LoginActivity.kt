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


class LoginActivity : AppCompatActivity() {
    private lateinit var userEdt: EditText
    private lateinit var passEdt: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        val textViewRegisterNow: TextView = findViewById(R.id.textView10)
        textViewRegisterNow.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initView() {
        userEdt = findViewById(R.id.editTextText)
        passEdt = findViewById(R.id.editTextPassword)
        loginBtn = findViewById(R.id.BT_Login)

        loginBtn.setOnClickListener {
            if (userEdt.text.toString().isEmpty() || passEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                loginWithSupabase(userEdt.text.toString(), passEdt.text.toString())
            }
        }
    }

    private fun loginWithSupabase(email: String, password: String) {
        lifecycleScope.launch {
            try {
                SupabaseClient.client.gotrue.loginWith(Email) {
                    this.email = email
                    this.password = password
                }
                // Login bem sucedido
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
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