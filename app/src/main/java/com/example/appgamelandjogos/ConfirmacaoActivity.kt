package com.example.appgamelandjogos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfirmacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmacao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Receber dados
        val nomeJogo = intent.getStringExtra("nomeJogo") ?: ""
        val valor = intent.getStringExtra("valor") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""
        val formaPagamento = intent.getStringExtra("formaPagamento") ?: ""

        // Preencher informações
        findViewById<TextView>(R.id.tvConfirmacaoJogo).text = "$tipo: $nomeJogo"
        findViewById<TextView>(R.id.tvConfirmacaoValor).text = valor

        // Salvar jogo na biblioteca
        val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
        val bibliotecaAtual = prefs.getString("biblioteca", "") ?: ""
        val novoItem = "$tipo|$nomeJogo|$valor"

        // Evitar duplicatas
        if (!bibliotecaAtual.contains(nomeJogo)) {
            val novaBiblioteca = if (bibliotecaAtual.isEmpty()) novoItem
            else "$bibliotecaAtual;;$novoItem"
            prefs.edit().putString("biblioteca", novaBiblioteca).apply()
        }

        // Botão voltar ao início
        findViewById<Button>(R.id.btnVoltarInicio).setOnClickListener {
            val intent = Intent(this, principalActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}