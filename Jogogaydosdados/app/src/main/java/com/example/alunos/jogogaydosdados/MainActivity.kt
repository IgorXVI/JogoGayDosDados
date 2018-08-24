package com.example.alunos.jogogaydosdados

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    var numeroPontosVoce: Int = 0
    var numeroPontosComputador: Int = 0
    var dado: ImageView? = null
    var pontosVoce: TextView? = null
    var pontosComputador: TextView? = null
    var finalizarTurno: Button? = null
    var rolarDado: Button? = null
    var reiniciar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dado = findViewById(R.id.dado)
        pontosVoce = findViewById(R.id.pontosVoce)
        pontosComputador = findViewById(R.id.pontosComputador)
        finalizarTurno = findViewById(R.id.btn_finalizarTurno)
        rolarDado = findViewById(R.id.btn_rolarDado)
        reiniciar = findViewById(R.id.btn_reiniciarJogo)
        btn_rolarDado.setOnClickListener {
            var valorDado = mudarDado()
            var valorAntigo: Int = pontosVoce?.text.toString().toInt()
            if(valorDado != 1){
                numeroPontosVoce += valorDado
                valorAntigo += valorDado
            }
            else{
                valorAntigo -= numeroPontosVoce
                fazerToast("Tirou 1! Você perdeu!")
                btn_finalizarTurno.performClick()
            }
            mudarTextView(valorAntigo.toString(), pontosVoce)
        }

        btn_reiniciarJogo.setOnClickListener{
            mudarTextView("0", pontosVoce)
            mudarTextView("0", pontosComputador)
            vezJogador()
        }

        btn_finalizarTurno.setOnClickListener {
            numeroPontosComputador = 0
            finalizarTurno?.setEnabled(false)
            rolarDado?.setEnabled(false)
            reiniciar?.setEnabled(false)
            mudarImagemDado(1)
            jogadaComputadorComDelay()
        }

    }

    fun mudarDado(): Int{
        var numero = Random().nextInt(6) + 1
        mudarImagemDado(numero)
        return numero
    }

    fun mudarImagemDado(numero: Int){
        runOnUiThread {
            when(numero) {
                1 -> dado?.setImageResource(R.mipmap.dice1)
                2 -> dado?.setImageResource(R.mipmap.dice2)
                3 -> dado?.setImageResource(R.mipmap.dice3)
                4 -> dado?.setImageResource(R.mipmap.dice4)
                5 -> dado?.setImageResource(R.mipmap.dice5)
                6 -> dado?.setImageResource(R.mipmap.dice6)
            }
        }
    }

    fun mudarTextView(str: String, campo: TextView?){
        runOnUiThread {
            campo?.setText(str)
        }
    }

    fun jogadaComputador(){
        mudarImagemDado(1)
        var valorDado = mudarDado()
        var valorAntigo: Int = pontosComputador?.text.toString().toInt()
        if(valorDado != 1){
            numeroPontosComputador += valorDado
            valorAntigo += valorDado
            var joga = Random().nextBoolean()
            if(joga){
                jogadaComputadorComDelay()
            }
            else{
                vezJogadorComDelay()
            }
        }
        else{
            valorAntigo -= numeroPontosComputador
            fazerToast("Tirou 1! Computador perdeu!")
            vezJogadorComDelay()
        }
        mudarTextView(valorAntigo.toString(), pontosComputador)

    }

    fun vezJogador(){
        mudarImagemDado(1)
        numeroPontosVoce = 0
        runOnUiThread {
            finalizarTurno?.setEnabled(true)
            rolarDado?.setEnabled(true)
            reiniciar?.setEnabled(true)
        }
    }

    fun fazerToast(str: String){
        runOnUiThread {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }

    fun jogadaComputadorComDelay(){
        Timer().schedule(2000){
         
            jogadaComputador()
           
        }
    }

    fun vezJogadorComDelay(){
        Timer().schedule(2000){
            vezJogador()
        }
    }
}
