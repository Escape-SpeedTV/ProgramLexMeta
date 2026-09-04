let etapaAtual = 1;
const totalEtapas = 5;

function mostrarEtapa(etapa) {
    document.querySelectorAll('.etapa-form').forEach(div => {
        div.style.display = 'none';
    });

    document.getElementById(`etapa-${etapa}`).style.display = 'block';

    document.querySelectorAll('.stepper-item').forEach(item => {
        item.classList.remove('ativo');
        if (parseInt(item.dataset.etapa) === etapa) {
            item.classList.add('ativo');
        }
    });

    document.getElementById('numero-etapa').textContent = `Etapa ${etapa} de ${totalEtapas}`;

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