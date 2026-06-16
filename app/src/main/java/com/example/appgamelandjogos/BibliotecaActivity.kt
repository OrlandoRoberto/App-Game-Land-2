package com.example.appgamelandjogos

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BibliotecaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biblioteca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar com botão voltar
        val toolbar = findViewById<Toolbar>(R.id.toolbarBiblioteca)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Buscar biblioteca salva
        val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
        val biblioteca = prefs.getString("biblioteca", "") ?: ""
        val listaJogos = findViewById<LinearLayout>(R.id.listaJogos)

        if (biblioteca.isEmpty()) {
            // Nenhum jogo comprado ainda
            val tv = TextView(this)
            tv.text = "Você ainda não tem jogos na biblioteca.\nCompre ou alugue um jogo para começar! 🎮"
            tv.textSize = 15f
            tv.setTextColor(Color.parseColor("#888888"))
            tv.gravity = Gravity.CENTER
            tv.setPadding(0, 60, 0, 0)
            listaJogos.addView(tv)
        } else {
            // Mostrar jogos comprados
            val jogos = biblioteca.split(";;")
            jogos.forEach { item ->
                val partes = item.split("|")
                if (partes.size == 3) {
                    val tipo = partes[0]
                    val nome = partes[1]
                    val valor = partes[2]

                    // Criar card do jogo
                    val card = CardView(this)
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 0, 16)
                    card.layoutParams = params
                    card.radius = 16f
                    card.cardElevation = 4f

                    val layout = LinearLayout(this)
                    layout.orientation = LinearLayout.VERTICAL
                    layout.setPadding(40, 32, 40, 32)

                    // Badge tipo (Compra/Aluguel)
                    val tvTipo = TextView(this)
                    tvTipo.text = tipo
                    tvTipo.textSize = 11f
                    tvTipo.setTextColor(Color.WHITE)
                    tvTipo.setBackgroundColor(Color.parseColor("#00AD89"))
                    tvTipo.setPadding(16, 6, 16, 6)

                    // Nome do jogo
                    val tvNome = TextView(this)
                    tvNome.text = nome
                    tvNome.textSize = 16f
                    tvNome.setTextColor(Color.parseColor("#1A1A2E"))
                    tvNome.setPadding(0, 12, 0, 4)
                    tvNome.paint.isFakeBoldText = true

                    // Valor pago
                    val tvValor = TextView(this)
                    tvValor.text = "Valor pago: $valor"
                    tvValor.textSize = 13f
                    tvValor.setTextColor(Color.parseColor("#888888"))

                    // Status download
                    val tvStatus = TextView(this)
                    tvStatus.text = "✅ Disponível para download"
                    tvStatus.textSize = 13f
                    tvStatus.setTextColor(Color.parseColor("#00AD89"))
                    tvStatus.setPadding(0, 8, 0, 0)

                    layout.addView(tvTipo)
                    layout.addView(tvNome)
                    layout.addView(tvValor)
                    layout.addView(tvStatus)
                    card.addView(layout)
                    listaJogos.addView(card)
                }
            }
        }
    }
}