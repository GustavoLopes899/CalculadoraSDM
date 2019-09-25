package br.edu.ifsp.scl.calculadorasdmkt.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.view.MainActivity.Constantes.CONFIGURACOES_REQUEST_CODE
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        // Fragment padrão
        supportFragmentManager.beginTransaction()
            .replace(R.id.calculadoraFl, CalculadoraBasicaFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    object Constantes {
        const val CONFIGURACOES_REQUEST_CODE = 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno = false
        when (item.itemId) {
            R.id.sairMenuItem -> {
                retorno = true
                finish()
            }
            R.id.configuracoesMenuItem -> {
                retorno = true
                val configuracaoIntent = Intent(this, ConfiguracaoActivity::class.java)
                startActivityForResult(configuracaoIntent, CONFIGURACOES_REQUEST_CODE)
            }
        }
        return retorno
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CONFIGURACOES_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // Pegar configuração retornada
            val configuracao =
                data?.getParcelableExtra<Configuracao>(ConfiguracaoActivity.Constantes.CONFIGURACAO)

            if (configuracao!!.layoutAvancado) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.calculadoraFl, CalculadoraAvancadaFragment()).commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.calculadoraFl, CalculadoraBasicaFragment()).commit()
            }
        }
    }

}
