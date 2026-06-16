package com.example.appgamelandjogos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class JogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jogo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar com botão voltar
        val toolbar = findViewById<Toolbar>(R.id.toolbarJogo)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Receber dados do jogo
        val nome = intent.getStringExtra("nome") ?: "Jogo"
        val genero = intent.getStringExtra("genero") ?: ""
        val descricao = intent.getStringExtra("descricao") ?: ""
        val precoCompra = intent.getStringExtra("precoCompra") ?: ""
        val precoAluguel = intent.getStringExtra("precoAluguel") ?: ""
        val imagemUrl = intent.getStringExtra("imagemUrl") ?: ""
        val tipoOperacao = intent.getStringExtra("tipoOperacao") ?: "compra"

        // Preencher os campos
        supportActionBar?.title = nome
        findViewById<TextView>(R.id.tvNomeJogo).text = nome
        findViewById<TextView>(R.id.tvGeneroJogo).text = genero
        findViewById<TextView>(R.id.tvDescricaoJogo).text = descricao
        findViewById<TextView>(R.id.tvPrecoCompra).text = precoCompra
        findViewById<TextView>(R.id.tvPrecoAluguel).text = precoAluguel

        // Carregar imagem com Glide
        Glide.with(this)
            .load(imagemUrl)
            .placeholder(android.R.color.darker_gray)
            .into(findViewById<ImageView>(R.id.imgJogo))

        // Botão Comprar
        findViewById<Button>(R.id.btnComprar).setOnClickListener {
            val intent = Intent(this, PagamentoActivity::class.java)
            intent.putExtra("nomeJogo", nome)
            intent.putExtra("valor", precoCompra)
            intent.putExtra("tipo", "Compra")
            startActivity(intent)
        }

        // Botão Alugar
        findViewById<Button>(R.id.btnAlugar).setOnClickListener {
            val intent = Intent(this, PagamentoActivity::class.java)
            intent.putExtra("nomeJogo", nome)
            intent.putExtra("valor", precoAluguel)
            intent.putExtra("tipo", "Aluguel")
            startActivity(intent)
        }
    }
}