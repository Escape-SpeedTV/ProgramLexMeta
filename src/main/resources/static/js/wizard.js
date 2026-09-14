let etapaAtual = 1;
const totalEtapas = 4;

function mostrarEtapa(etapa) {
    for (let i = 1; i <= totalEtapas; i++) {
        const divEtapa = document.getElementById(`etapa-${i}`);
        if (divEtapa) {
            divEtapa.style.display = 'none';
        }
    }

    const divAtual = document.getElementById(`etapa-${etapa}`);
    if (divAtual) {
        divAtual.style.display = 'block';
    }

    document.querySelectorAll('.stepper-item').forEach(item => {
        item.classList.remove('ativo');
        if (parseInt(item.dataset.etapa) === etapa) {
            item.classList.add('ativo');
        }
    });

    document.getElementById('numero-etapa').textContent = `Etapa ${etapa} de ${totalEtapas}`;

    const botaoSalvar = document.querySelector('.botao-salvar');
    if (etapa === totalEtapas) {
        botaoSalvar.textContent = 'Finalizar e Salvar';
        botaoSalvar.setAttribute('type', 'submit'); // <- Isso faz o formulário enviar!
        botaoSalvar.setAttribute('onclick', ''); // Remove o onclick (proximaEtapa)
    } else {
        botaoSalvar.textContent = 'Salvar e continuar →';
        botaoSalvar.setAttribute('type', 'button'); // Volta a ser botão
        botaoSalvar.setAttribute('onclick', 'proximaEtapa()'); // Volta a avançar
    }

    etapaAtual = etapa;
}

function proximaEtapa() {
    if (etapaAtual < totalEtapas) {
        mostrarEtapa(etapaAtual + 1);
    }
}

function voltarEtapa() {
    if (etapaAtual > 1) {
        mostrarEtapa(etapaAtual - 1);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    mostrarEtapa(1);
});