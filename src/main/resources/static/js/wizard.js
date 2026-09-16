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

function validarEtapa(etapa){
    if(etapa === 1){
        const numero = document.querySelector('input[name="numeroProcesso"]').value.trim();
        const cliente = document.querySelector('input[name="cliente"]').value.trim();
        const status = document.querySelector('select[name="status"]').value;
        const responsavel = document.querySelector('select[name="responsavel"]').value;

        if(!numero || !cliente || !status ||!responsavel){
            alert("Preencha todos os campos obrigatórios da Etapa 1 (Número do Processo, Cliente, Status e Responsável).");
        return false;
        }
    }

    if(etapa === 2){

    }
    return true;
}

function proximaEtapa() {
    if(validarEtapa(etapaAtual)){
        if (etapaAtual < totalEtapas) {
            mostrarEtapa(etapaAtual + 1);
        }
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

document.addEventListener('DOMContentLoaded', () =>{
    const campoValor = document.getElementById('valorCausa');

    if (campoValor){
        campoValor.addEventListener('input', function (e){
            let valor = e.target.value;
            valor = valor.replace(/\D/g, '');

            if (valor === ''){
                e.target.value = '';
                return;
            }

            valor = (parseInt(valor, 10) / 100).toFixed(2);
            valor = valor.replace('.', ',');
            valor = valor.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
            e.target.value = valor;
        })
    }
})