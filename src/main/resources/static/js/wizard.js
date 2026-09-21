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
        const autores = Array.from(document.querySelectorAll('#lista-autores .autor-item'));
        let autorValido = false;
        let erroAutor = '';

        autores.forEach((item, index) => {
            const inputs = item.querySelectorAll('input[type="text"]');
            const nome = inputs[0] ? inputs[0].value.trim() : '';
            const cpf = inputs[1] ? inputs[1].value.trim() : '';

            if (!nome) {
                erroAutor = `Preencha o nome do autor ${index + 1}.`;
            } else if (!cpf) {
                erroAutor = `Preencha o CPF do autor ${index + 1}.`;
            } else {
                autorValido = true;
            }
        });

        if (!autorValido) {
            alert(erroAutor || 'Preencha pelo menos um autor.');
            return false;
        }

        const reus = Array.from(document.querySelectorAll('#lista-reus .reu-item'));
        let reuValido = false;
        let erroReu = '';

        reus.forEach((item, index) => {
            const inputs = item.querySelectorAll('input[type="text"]');
            const nome = inputs[0] ? inputs[0].value.trim() : '';
            const cnpj = inputs[1] ? inputs[1].value.trim() : '';

            if (!nome) {
                erroReu = `Preencha o nome/razão social do réu ${index + 1}.`;
            } else if (!cnpj) {
                erroReu = `Preencha o CNPJ do réu ${index + 1}.`;
            } else {
                reuValido = true;
            }
        });

        if (!reuValido) {
            alert(erroReu || 'Preencha pelo menos um réu.');
            return false;
        }

        const advogados = Array.from(document.querySelectorAll('#lista-advogados .advogado-item'));
        let advogadoValido = false;
        let erroAdvogado = '';

        advogados.forEach((item, index) => {
            const inputs = item.querySelectorAll('input[type="text"]');
            const nome = inputs[0] ? inputs[0].value.trim() : '';
            const oab = inputs[1] ? inputs[1].value.trim() : '';

            if (!nome) {
                erroAdvogado = `Preencha o nome do advogado ${index + 1}.`;
            } else if (!oab) {
                erroAdvogado = `Preencha a OAB/UF do advogado ${index + 1}.`;
            } else {
                advogadoValido = true;
            }
        });

        if (!advogadoValido) {
            alert(erroAdvogado || 'Preencha pelo menos um advogado.');
            return false;
        }
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

function adicionarItem(containerId, itemClass, limite, templateHTML){
    const container = document.getElementById(containerId);
    if(!container) return;

    const item = container .querySelectorAll("." + itemClass);
    if(item.length >= limite){
        alert(`Você já atingiu o limite máximo de ${limite} de pessoas`);
        return;
    }

    const novoItem = document.createElement("div");
    novoItem.classList.add("linha-campos");
    novoItem.classList.add(itemClass);
    novoItem.innerHTML = templateHTML;

    container.appendChild(novoItem);

    const lixeira = novoItem.querySelector(".icone-lixeira");
    if(lixeira){
        lixeira.style.cursor = "pointer";
        lixeira.addEventListener("click", () =>{
            novoItem.remove();
        });
    }
}

const templateAutor = `
    <div class="campo">
            <label>Tipo de Pessoa</label>
            <select>
                <option value="Física">Física</option>
                <option value="Jurídica">Jurídica</option>
            </select>
        </div>
        <div class="campo">
            <label>Nome Completo <span style="color: red;">*</span></label>
            <input type="text" placeholder="Ex: João da Silva">
        </div>
        <div class="campo">
            <label>CPF</label>
            <input type="text" placeholder="Ex: 123.456.789.00">
        </div>
        <div class="icone-lixeira">
            <img src="/imagens/lixeira.webp" alt="Lixeira" style="width: 20px; height: 20px; object-fit: contain;">
        </div>
    `;

const btnAddAutor = document.getElementById("btn-add-autor");
if(btnAddAutor){
    btnAddAutor.addEventListener("click", () => {
        adicionarItem("lista-autores", "autor-item", 5, templateAutor);
    });
}

const templateReu = `
    <div class="campo">
        <label>Tipo de Pessoa</label>
        <select>
            <option value="Jurídica">Jurídica</option>
            <option value="Física">Física</option>
        </select>
    </div>
    <div class="campo">
        <label>Nome / Razão Social <span style="color: red;">*</span></label>
        <input type="text" placeholder="Ex: Banco do Brasil S.A.">
    </div>
    <div class="campo">
        <label>CNPJ</label>
        <input type="text" placeholder="Ex: 00.000.000/0001-91">
    </div>
    <div class="icone-lixeira">
        <img src="/imagens/lixeira.webp" alt="Lixeira" style="width: 20px; height: 20px; object-fit: contain;">
    </div>
`;

const btnAddReu = document.getElementById("btn-add-reu");
if(btnAddReu){
    btnAddReu.addEventListener("click", () =>{
        adicionarItem("lista-reus", "reu-item", 5, templateReu);
    })
}

const templateAdvogado = `
    <div class="campo">
            <label>Nome do Advogado <span style="color: red;">*</span></label>
            <input type="text" placeholder="Ex: Maria Oliveira Santos">
        </div>
        <div class="campo">
            <label>OAB / UF <span style="color: red;">*</span></label>
            <input type="text" placeholder="Ex: 12345/SP">
        </div>
        <div class="campo">
            <label>Função</label>
            <select>
                <option value="">Selecione a função</option>
                <option value="Procurador">Procurador</option>
                <option value="Assistente">Assistente</option>
                <option value="Estagiário">Estagiário</option>
            </select>
        </div>
        <div class="icone-lixeira">
            <img src="/imagens/lixeira.webp" alt="Lixeira" style="width: 20px; height: 20px; object-fit: contain;">
        </div>
`;

const btnAddAdvogado = document.getElementById("btn-add-advogado");
if(btnAddAdvogado){
    btnAddAdvogado.addEventListener("click", () =>{
        adicionarItem("lista-advogados", "advogado-item", 5, templateAdvogado);
    });
}

document.addEventListener("DOMContentLoaded", () =>{
    const areaUpload = document.getElementById("area-upload");
    const inputArquivo = document.getElementById("input-arquivo");
    const btnSelecionar = document.getElementById("btn-selecionar");
    const listaArquivos = document.getElementById("lista-arquivos");
    const tituloArquivos = document.getElementById("titulo-arquivos");
    const resumoContador = document.getElementById("resumo-contador");

    let arquivos = [];

    if(areaUpload){
        areaUpload.addEventListener("click", () => inputArquivo.click());
    }
    if(btnSelecionar){
        btnSelecionar.addEventListener("click", (e) => {
            e.stopPropagation();
            inputArquivo.click();
        });
    }

    if(inputArquivo){
        inputArquivo.addEventListener("change", (e) =>{
            const novosArquivos = Array.from(e.target.files);

            novosArquivos.forEach(arquivo =>{
                if(arquivo.size > 10 * 1024 * 1024){
                    alert(`O arquivo "${arquivo.name}" excede o tamanho máximo de 10MB.`);
                    return;
                }

                arquivos.push(arquivo);
                adicionarArquivoNaLista(arquivo);
            });

            atualizarContadores();
            inputArquivo.value = "";
        });
    }

    function adicionarArquivoNaLista(arquivo){
        const extensao= arquivo.name.split(".").pop().toLocaleLowerCase();
        const tipo = detectarTipo(arquivo.name, extensao);

        const item = document.createElement("div");
        item.className = "arquivo-item";

        let icone = "/imagens/pdf-icon.png";
        if(extensao === "doc" || extensao === "docx") icone = "/imagens/pendentes.png";
        if(extensao === "jpg" || extensao === "jpeg" || extensao === "png") icone = "/imagens/imagem.png";

        const tamanhoFormatado = formatarTamanho(arquivo.size);

        const dataAtual = new Date().toLocaleString("pt-br", {
            day: "2-digit", month: "2-digit", year: "numeric", hour: "2-digit", minute: "2-digit"
        }).replace(",", "");

        item.innerHTML = `
            <img src="${icone}" alt="Arquivo" class="icone-arquivo">
            <span class="nome-arquivo">${arquivo.name}</span>
            <span class="tipo-arquivo"><span class="badge-tipo badge-${tipo.classe}">${tipo.nome}</span></span>
            <span class="tamanho-arquivo">${tamanhoFormatado}</span>
            <span class="data-arquivo">${dataAtual}</span>
            <span class="acoes-arquivo">
                <span title="Visualizar">👁</span>
                <span title="Baixar">⬇</span>
                <span title="Remover" class="remover-arquivo" style="color: #d32f2f; cursor: pointer;">✖</span>
            </span>
        `;

        item.querySelector(".remover-arquivo").addEventListener("click", () =>{
            item.remove();
            arquivos = arquivos.filter(a => a !== arquivo);
            atualizarContadores();
        });
        listaArquivos.appendChild(item);
    }

    function detectarTipo(nome, extensao) {
        const nomeLower = nome.toLowerCase();
        if(nomeLower.includes("peti")) return { nome: "Petição", classe: "peticao"};
        if(nomeLower.includes("comprovante")) return {nome: "Comprovante", classe: "comprovante"};
        if(nomeLower.includes("procura")) return {nome: "Procuração", classe: "procura"};
        if(nomeLower.includes("contrato")) return {nome: "Contrato", classe: "contrato"};
        if(nomeLower.includes("termo")) return {nome: "Termo", classe: "termo"};
        return {nome: "Outro", classe: "outro"};
    }

    function formatarTamanho(bytes){
        if(bytes <1024) return bytes + " B";
        if(bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + " KB";
        return (bytes / 1024 * 1024).toFixed(1) + " MB";
    }

    function atualizarContadores(){
        const total = arquivos.length;
        if(tituloArquivos) tituloArquivos.textContent = `Arquivos Anexados (${total})`;
        if (resumoContador) resumoContador.textContent = `${total} arquivo${total !== 1 ? "s" : ""} anexado${total !== 1 ? "s" : ""}`;
    }
});