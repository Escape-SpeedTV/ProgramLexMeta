document.addEventListener('DOMContentLoaded', function() {
    console.log('processos.js carregado!'); // Para debug

    const setaDropdown = document.getElementById("seta-dropdown");
    const dropdownMenu = document.getElementById("dropdown-menu");
    if (setaDropdown && dropdownMenu) {
        setaDropdown.addEventListener("click", function (event) {
            event.stopPropagation();
            dropdownMenu.classList.toggle("aberto");
        });
        document.addEventListener("click", function () {
            dropdownMenu.classList.remove("aberto");
        });
    }

    const selectItens = document.getElementById("select-itens-por-pagina");
    console.log('Select encontrado:', selectItens); // Para debug

    if (selectItens) {
        selectItens.addEventListener("change", function() {
            console.log('Mudou para:', this.value); // Para debug
            window.location.href = '/processos?pagina=1&itensPorPagina=' + this.value;
        });
    }
});

const filtroStatus = document.getElementById("filtro-status");
if(filtroStatus){
    filtroStatus.addEventListener("change", function (){
        const status = this.value;
        const url= new URL(window.location.href);

        if (status){
            url.searchParams.set("status", status);
        } else{
            url.searchParams.delete("status");
        }

        url.searchParams.set("pagina", "1");

        window.location.href = url.toString();
    })
}