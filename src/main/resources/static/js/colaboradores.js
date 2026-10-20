function abrirModalNovo(){
    document.getElementById('modal-novo').classList.add('aberto');
}

function fecharModalNovo(){
    document.getElementById('modal-novo').classList.remove('aberto');
}

function toggleMenuColab(elemento){
    const menu = elemento.nextElementSibling;
    const estaAberto = menu.classList.contains('aberto');

    document.querySelectorAll('.dropdown-acoes').forEach(m => m.classList.remove('aberto'));

    if(!estaAberto){
        menu.classList.add('aberto');
    }
}

document.addEventListener('click', function (event){
    if(!event.target.closest('.menu-acoes')){
        document.querySelectorAll('.dropdown-acoes').forEach(m => m.classList.remove('aberto'));
    }
});

function filtrarColaboradores(){
    const busca = document.getElementById('campoBusca').value.toLowerCase();
    const cargo = document.getElementById('filtroCargo').value;

    document.querySelectorAll('#corpoTabela tr').forEach(linha => {
        const nome = linha.getAttribute('data-nome').toLowerCase();
        const cargoLinha = linha.getAttribute('data-cargo');

        const matchBusca = nome.includes(busca);
        const matchCargo = !cargo || cargoLinha === cargo;

        linha.style.display = (matchBusca && matchCargo) ? "" : "none";
    });
}

function alterarCargo(select){
    const id = select.getAttribute('data-id');
    const novoCargo = select.value;

    fetch('/configuracoes/colaboradores/alterar-cargo', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}&cargo=${encodeURIComponent(novoCargo)}`
    }).then(() => {
        alert('Cargo alterado com sucesso!');
        location.reload();
    });
}

function toggleAtivo(id){
    if(confirm('Deseja Alterar o status deste colaborador?')){
        fetch('/configuracoes/colaboradores/toggle-ativo', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `id=${id}`
        }).then(() => location.reload());
    }
}

function excluirColaborador(id){
    if (confirm('Tem certeza que deseja excluir este colaborador?')) {
        fetch('/configuracoes/colaboradores/excluir', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `id=${id}`
        }).then(() => location.reload());
    }
}

function abrirModalEditar(){
    alert('Editar colaborador: ' + id + ' (em breve');
}