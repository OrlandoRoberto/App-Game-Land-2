package com.example.appgamelandjogos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class principalActivity : AppCompatActivity() {

    data class Jogo(
        val nome: String,
        val genero: String,
        val descricao: String,
        val precoCompra: String,
        val precoAluguel: String,
        val imagemUrl: String
    )

    val jogos = listOf(
        Jogo("God of War", "Ação • Aventura", "Embarque numa jornada épica com Kratos e seu filho Atreus pelos reinos nórdicos. Um jogo emocionante cheio de batalhas, mistérios e uma história poderosa sobre paternidade e redenção.", "R$ 59,90", "R$ 9,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/1593500/header.jpg"),
        Jogo("The Witcher 3", "RPG • Fantasia", "Como Geralt de Rívia, um caçador de monstros profissional, explore um mundo aberto repleto de escolhas morais, criaturas perigosas e uma narrativa rica e envolvente.", "R$ 79,90", "R$ 12,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/292030/header.jpg"),
        Jogo("FIFA 24", "Esportes • Futebol", "O simulador de futebol mais popular do mundo com times, jogadores e estádios reais. Dispute campeonatos, monte seu time e jogue online com amigos.", "R$ 49,90", "R$ 7,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/2195250/header.jpg"),
        Jogo("Red Dead Redemption 2", "Ação • Mundo Aberto", "Uma épica história de honra e lealdade no coração da América em 1899. Explore um vasto mundo aberto cheio de vida, missões e decisões morais.", "R$ 69,90", "R$ 11,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/1174180/header.jpg"),
        Jogo("Cyberpunk 2077", "RPG • Ficção Científica", "Em Night City, uma megalópole obcecada por poder e modificações corporais, você é V, um mercenário em busca de um implante único que garante imortalidade.", "R$ 59,90", "R$ 9,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg"),
        Jogo("Elden Ring", "RPG • Ação", "Explore as Terras Intermédias em um RPG de ação desafiador criado por FromSoftware e George R.R. Martin. Enfrente inimigos épicos e descubra segredos ancestrais.", "R$ 74,90", "R$ 12,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/header.jpg"),
        Jogo("GTA V", "Ação • Mundo Aberto", "Viva três histórias entrelaçadas em Los Santos. Com modo história épico e GTA Online com centenas de atividades, é diversão sem fim para todos os tipos de jogadores.", "R$ 44,90", "R$ 6,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg"),
        Jogo("Minecraft", "Aventura • Criativo", "Construa, explore e sobreviva em um mundo gerado proceduralmente. Com modo criativo e sobrevivência, Minecraft é um dos jogos mais vendidos de todos os tempos.", "R$ 39,90", "R$ 5,90", "https://cdn.cloudflare.steamstatic.com/steam/apps/1672970/header.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Mostrar nome do usuário
        val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
        val nome = prefs.getString("nome", "jogador")
        findViewById<TextView>(R.id.tvBoasVindas).text = "Olá, $nome! 👋"

        // IDs das imagens
        val imgIds = listOf(R.id.imgJogo1, R.id.imgJogo2, R.id.imgJogo3, R.id.imgJogo4, R.id.imgJogo5, R.id.imgJogo6, R.id.imgJogo7, R.id.imgJogo8)

        // IDs dos textos nome
        val nomeIds = listOf(R.id.tvNomeJogo1, R.id.tvNomeJogo2, R.id.tvNomeJogo3, R.id.tvNomeJogo4, R.id.tvNomeJogo5, R.id.tvNomeJogo6, R.id.tvNomeJogo7, R.id.tvNomeJogo8)

        // IDs dos textos gênero
        val generoIds = listOf(R.id.tvGeneroJogo1, R.id.tvGeneroJogo2, R.id.tvGeneroJogo3, R.id.tvGeneroJogo4, R.id.tvGeneroJogo5, R.id.tvGeneroJogo6, R.id.tvGeneroJogo7, R.id.tvGeneroJogo8)

        // IDs dos textos preço
        val precoIds = listOf(R.id.tvPrecoJogo1, R.id.tvPrecoJogo2, R.id.tvPrecoJogo3, R.id.tvPrecoJogo4, R.id.tvPrecoJogo5, R.id.tvPrecoJogo6, R.id.tvPrecoJogo7, R.id.tvPrecoJogo8)

        // IDs dos botões
        val btnIds = listOf(R.id.btnVerJogo1, R.id.btnVerJogo2, R.id.btnVerJogo3, R.id.btnVerJogo4, R.id.btnVerJogo5, R.id.btnVerJogo6, R.id.btnVerJogo7, R.id.btnVerJogo8)

        // Preencher cards dinamicamente
        jogos.forEachIndexed { index, jogo ->
            Glide.with(this).load(jogo.imagemUrl).into(findViewById<ImageView>(imgIds[index]))
            findViewById<TextView>(nomeIds[index]).text = jogo.nome
            findViewById<TextView>(generoIds[index]).text = jogo.genero
            findViewById<TextView>(precoIds[index]).text = jogo.precoCompra

            findViewById<Button>(btnIds[index]).setOnClickListener {
                val intent = Intent(this, JogoActivity::class.java)
                intent.putExtra("nome", jogo.nome)
                intent.putExtra("genero", jogo.genero)
                intent.putExtra("descricao", jogo.descricao)
                intent.putExtra("precoCompra", jogo.precoCompra)
                intent.putExtra("precoAluguel", jogo.precoAluguel)
                intent.putExtra("imagemUrl", jogo.imagemUrl)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_biblioteca -> {
                val intent = Intent(this, BibliotecaActivity::class.java)
                startActivity(intent)
                true
            }


            R.id.menu_perfil -> {
                Toast.makeText(this, "Em breve: Meu Perfil!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_logout -> {
                val prefs = getSharedPreferences("GameLandPrefs", Context.MODE_PRIVATE)
                val nomeSalvo = prefs.getString("nome", "jogador")
                val editor = prefs.edit()
                editor.clear()
                editor.putString("nome", nomeSalvo)
                editor.apply()
                Toast.makeText(this, "Até logo!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}