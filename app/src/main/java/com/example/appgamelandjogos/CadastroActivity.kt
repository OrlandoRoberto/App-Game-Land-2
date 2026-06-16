package com.example.appgamelandjogos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar Toolbar com botão de voltar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCadastro)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val btnConcluir = findViewById<android.widget.Button>(R.id.btnconcluir)
        val btnVoltarLogin = findViewById<android.widget.Button>(R.id.btnvoltarlogin)
        val campoNome = findViewById<TextInputEditText>(R.id.textInputEditText)
        val campoEmail = findViewById<TextInputEditText>(R.id.textInputEditText2)
        val campoSenha = findViewById<TextInputEditText>(R.id.textInputEditText3)
        val campoTelefone = findViewById<TextInputEditText>(R.id.textInputEditText4)
        val campoNumeroCartao = findViewById<TextInputEditText>(R.id.campoNumeroCartao)
        val campoNomeCartao = findViewById<TextInputEditText>(R.id.campoNomeCartao)
        val campoValidade = findViewById<TextInputEditText>(R.id.campoValidadeCartao)
        val campoCvv = findViewById<TextInputEditText>(R.id.campoCvvCartao)

        btnConcluir.setOnClickListener {
            val nome = campoNome.text.toString().trim()
            val email = campoEmail.text.toString().trim()
            val senha = campoSenha.text.toString().trim()
            val telefone = campoTelefone.text.toString().trim()
            val numeroCartao = campoNumeroCartao.text.toString().trim()
            val nomeCartao = campoNomeCartao.text.toString().trim()
            val validade = campoValidade.text.toString().trim()
            val cvv = campoCvv.text.toString().trim()

            // Validação dados pessoais
            if (nome.isEmpty()) {
                campoNome.error = "Digite seu nome"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                campoEmail.error = "Digite seu e-mail"
                return@setOnClickListener
            }
            if (senha.isEmpty() || senha.length < 6) {
                campoSenha.error = "Senha deve ter pelo menos 6 caracteres"
                return@setOnClickListener
            }
            if (telefone.isEmpty()) {
                campoTelefone.error = "Digite seu telefone"
                return@setOnClickListener
            }

            // Validação cartão (se preencheu algum campo do cartão)
            if (numeroCartao.isNotEmpty() || nomeCartao.isNotEmpty() || validade.isNotEmpty() || cvv.isNotEmpty()) {
                if (numeroCartao.length < 16) {
                    campoNumeroCartao.error = "Digite os 16 dígitos do cartão"
                    return@setOnClickListener
                }
                if (nomeCartao.isEmpty()) {
                    campoNomeCartao.error = "Digite o nome do cartão"
                    return@setOnClickListener
                }
                if (validade.isEmpty()) {
                    campoValidade.error = "Digite a validade"
                    return@setOnClickListener
                }
                if (cvv.length < 3) {
                    campoCvv.error = "CVV inválido"
                    return@setOnClickListener
                }
            }

            // Salvar dados no SharedPreferences
            val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("logado", true)
            editor.putString("email", email)
            editor.putString("nome", nome)

            // Salvar cartão se foi preenchido
            if (numeroCartao.isNotEmpty()) {
                editor.putString("numeroCartao", "**** **** **** ${numeroCartao.takeLast(4)}")
                editor.putString("nomeCartao", nomeCartao)
                editor.putString("validadeCartao", validade)
                editor.putBoolean("temCartao", true)
            }
            editor.apply()

            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, principalActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnVoltarLogin.setOnClickListener {
            finish()
        }
    }
}