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

public class LoginActivity : AppCompatActivity() {
    private lateinit var userEdt: EditText
    private lateinit var passEdt: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView();
    }

    private fun initView() {
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.BT_Login);

        loginBtn.setOnClickListener {
            if (userEdt.text.toString().isEmpty() || passEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Please fill your user and password", Toast.LENGTH_SHORT).show()
            } else if (userEdt.text.toString() == "test" && passEdt.text.toString() == "test") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Your user and password is not correct", Toast.LENGTH_SHORT).show()
            }
        }
    }
}