package com.example.calculadoradeinvestimentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val seekbar = findViewById<SeekBar>(R.id.seekBar)
    val textValorExibir = findViewById<TextView>(R.id.exibi_valor)
    val textResultadoInvestimento = findViewById<TextView>(R.id.valorExibido)
    val botaoMenos = findViewById<Button>(R.id.botaonMenosAnos)
    val botaoMais = findViewById<Button>(R.id.botaoMaisAnos)
    val anosExibidos = findViewById<TextView>(R.id.anos)


    botaoMenos.setOnClickListener {
        val anos = anosExibidos.text.toString()
        var anosInt = Integer.parseInt(anos)
        if( anosInt > 0 ) {
            anosInt--
            anosExibidos.text = anosInt.toString()

            val valorInvestido = textValorExibir.text.toString()
            val result = calculaValor(valorInvestido, anosInt)
            textResultadoInvestimento.text = "$result"
        }
    }

    botaoMais.setOnClickListener {
        val anos = anosExibidos.text.toString()
        var anosInt = Integer.parseInt(anos)
        anosInt++
        anosExibidos.text = anosInt.toString()
        val valorInvestido  = textValorExibir.text.toString()
        val result = calculaValor(valorInvestido, anosInt)
        textResultadoInvestimento.text = "$result"

    }

    seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val valor = progress.toString()
            textValorExibir.text = valor
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            textResultadoInvestimento.text = "0"
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            val anosInt: Int  = Integer.parseInt(anosExibidos.text.toString())
            val valorInvestido : Int? = seekBar?.progress
            valorInvestido?.let{
                val result = calculaValor(it.toString(), anosInt)
                textResultadoInvestimento.text = "$result"
            }


        }
    })

}

fun calculaValor(valor: String, anos: Int): BigDecimal {
    var valorBigDecimal = BigDecimal.valueOf(valor.toDouble())
    for (i in 1..anos) {
        valorBigDecimal = valorBigDecimal.
        add(valorBigDecimal.
        divide(BigDecimal.valueOf(100)).
        multiply(BigDecimal.valueOf(6)))
    }
    return valorBigDecimal.setScale(2, RoundingMode.HALF_EVEN)
}
}