package com.example.appgamelandjogos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PagamentoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagamento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar com botão voltar
        val toolbar = findViewById<Toolbar>(R.id.toolbarPagamento)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Receber dados
        val nomeJogo = intent.getStringExtra("nomeJogo") ?: ""
        val valor = intent.getStringExtra("valor") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""

        // Preencher resumo
        findViewById<TextView>(R.id.tvNomeJogoPagamento).text = nomeJogo
        findViewById<TextView>(R.id.tvTipoPagamento).text = tipo
        findViewById<TextView>(R.id.tvValorPagamento).text = valor

        // Verificar se tem cartão cadastrado
        val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
        val temCartao = prefs.getBoolean("temCartao", false)
        val numeroCartao = prefs.getString("numeroCartao", "")
        val nomeCartao = prefs.getString("nomeCartao", "")

        // Atualizar texto do botão cartão
        val btnCartao = findViewById<Button>(R.id.btnCartao)
        if (temCartao) {
            btnCartao.text = "Pagar com Cartão $numeroCartao"
        }

        // Função para ir para confirmação
        fun irParaConfirmacao(formaPagamento: String) {
            val intent = Intent(this, ConfirmacaoActivity::class.java)
            intent.putExtra("nomeJogo", nomeJogo)
            intent.putExtra("valor", valor)
            intent.putExtra("tipo", tipo)
            intent.putExtra("formaPagamento", formaPagamento)
            startActivity(intent)
            finish()
        }

        // Botão Pix
        findViewById<Button>(R.id.btnPix).setOnClickListener {
            irParaConfirmacao("Pix")
        }

        // Botão Cartão
        btnCartao.setOnClickListener {
            if (temCartao) {
                irParaConfirmacao("Cartão $numeroCartao - $nomeCartao")
            } else {
                irParaConfirmacao("Cartão")
            }
        }
    }
}