package pt.ipt.dam.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch
import pt.ipt.dam.movies.utils.SupabaseClient

/**
 * Activity responsável por controlar o perfil do utilizador
 * Permite visualizar informações do perfil e fazer logout
 */
class ProfileActivity : AppCompatActivity() {
    /**
     * Inicializa a Activity e configura o botão de logout
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnLogout: Button = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout()
        }
    }

    /**
     * Realiza o logout do utilizador usando o Supabase
     * Após o logout bem-sucedido, redireciona para a página de login
     * e limpa a pilha das Activities
     */
    private fun logout() {
        lifecycleScope.launch {
            try {
                // Realizar logout
                SupabaseClient.client.gotrue.logout()
                // Volta para a página de login
                val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                // Tratar do erro se for necessário
            }
        }
    }
}
