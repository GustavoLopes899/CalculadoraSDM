package br.edu.ifsp.scl.calculadorasdmkt.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.controller.ConfiguracaoController
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.Separador
import kotlinx.android.synthetic.main.activity_configuracao.*
import kotlinx.android.synthetic.main.toolbar.*

class ConfiguracaoActivity : AppCompatActivity() {
    object Constantes {
        // Chave de retorno da MainActivity
        val CONFIGURACAO = "CONFIGURACAO"
    }

    // Referência para Controller
    lateinit var configuracaoController: ConfiguracaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)

        // Toolbar
        toolbar.title = "Configuração"
        setSupportActionBar(toolbar)

        // Chama controller e atualiza view
        configuracaoController = ConfiguracaoController(this)
        configuracaoController.buscaConfiguracao()
    }

    // Funçao chamada pelo Controller depois de acessar o Model
    fun AtualizaView(configuracao: Configuracao) {
        // Ajusta o layout conforme a configuração
        layoutSpn.setSelection(if (configuracao.layoutAvancado) 1 else 0)
        separadorRg.check(if (configuracao.separador == Separador.PONTO) R.id.pontoRb else R.id.virgulaRb)

        // SETAR O RESULTADO PARA MAIN ACTIVITY
        setResult(
            AppCompatActivity.RESULT_OK,
            Intent().putExtra(Constantes.CONFIGURACAO, configuracao)
        )
    }

    fun onClickSalvaConfiguracao(v: View) {
        // Pega os dados da tela
        val layoutAvancado = layoutSpn.selectedItemPosition == 1
        var separador = if (pontoRb.isChecked) Separador.PONTO else Separador.VIRGULA
        // Cria o objeto Configuracao
        val novaConfiguracao = Configuracao(layoutAvancado, separador)
        // Chama o Controller para salvar
        configuracaoController.salvaConfiguracao(novaConfiguracao)
    }
}