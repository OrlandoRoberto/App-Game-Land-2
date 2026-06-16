package com.example.appgamelandjogos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Verificar se já está logado
        val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
        val estaLogado = prefs.getBoolean("logado", false)

        if (estaLogado) {
            // Já está logado, vai direto para tela principal
            val intent = Intent(this, principalActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val campoEmail = findViewById<TextInputEditText>(R.id.emaillogin)
        val campoSenha = findViewById<TextInputEditText>(R.id.senhalogin)
        val btnLogin = findViewById<android.widget.Button>(R.id.buttonlogin)
        val btnCadastro = findViewById<android.widget.Button>(R.id.buttoncadastrarlogin)

        // Botão Entrar
        btnLogin.setOnClickListener {
            val email = campoEmail.text.toString().trim()
            val senha = campoSenha.text.toString().trim()

            if (senha.isEmpty()) {
                campoSenha.error = "Digite sua senha"
                return@setOnClickListener
            }
            if (senha.length < 6) {
                campoSenha.error = "Senha deve ter pelo menos 6 caracteres"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                campoEmail.error = "Digite um e-mail válido"
                return@setOnClickListener
            }

            // Salvar login mantendo o nome já cadastrado
            val nomeSalvo = prefs.getString("nome", "jogador")
            val editor = prefs.edit()
            editor.putBoolean("logado", true)
            editor.putString("email", email)
            editor.putString("nome", nomeSalvo)
            editor.apply()

            Toast.makeText(this, "Bem-vindo!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, principalActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botão Criar conta
        btnCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}