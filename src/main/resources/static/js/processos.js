document.addEventListener('DOMContentLoaded', function() { //Esse trecho, eu estou garantindo que os elementos existam antes de manipulá-los com o java script! E um adendo, quando adicionamos um evento de lista, sempre vamos precisar uma function, como segundo parametro.
    console.log('processos.js carregado!'); // Para debug

    const setaDropdown = document.getElementById("seta-dropdown"); //Nesse trecho, estamos pegando um elemento pelo o ID.
    const dropdownMenu = document.getElementById("dropdown-menu");
    if (setaDropdown && dropdownMenu) { //Aqui, é uma validação, como estou usando um E comercial, todas as alternativas precisam ser verdadeiras, se apenas uma for verdadeira, logo, essa validação toda, ela é falsa.
        setaDropdown.addEventListener("click", function (event) { //Esse event, é so o nome de uma variável, posso chamar ela de qualquer coisa.
            event.stopPropagation(); //Nesse trecho, eu estou garantindo que a pagina inteira seja aberta, quando o usuário clicar em algo. Nesse caso, nos temos uma seta, se eu apertar nessa seta, sem esse comando, ela nem chega abrir, pois a pagina inteira é recarregada.
            dropdownMenu.classList.toggle("aberto"); //Aqui, o toggle, ele está trabalhando como se fosse um interruptor, se uma classe não existe, ele adiciona, se ela existe, ele tira. É como se fosse um liga e desliga. Nesse caso, eu estou abrindo uma seta.
        });
        document.addEventListener("click", function () {
            dropdownMenu.classList.remove("aberto"); //Nesse trecho, em vez de abrir a seta, eu estou fechando ela.
        });
    }


    //Esse trecho do código, eu estou simplesmente definindo a quantidade  itens por pagina.
    const selectItens = document.getElementById("select-itens-por-pagina");
    if (selectItens) {
        selectItens.addEventListener("change", function() { //O change nesse trecho, ele está como um evento que só muda, se o usuário escolher a quantidade de itens por página.
            const url = new URL(window.location.href); //Esse trecho, eu estou pegando a url da pagina completa. Ou melhor dizendo, ele esta devolvendo a URL como string.
            url.searchParams.set('itensPorPagina', this.value); //Nesse trecho,eu estou escolhendo quantos itens eu quero por pagina como foi definido no html.
            url.searchParams.set('pagina', '1'); //E nesse trecho eu estou definindo um padrão, que caso o usuário coloque 20 paginas, algumas paginas podem nem existir mais.
            window.location.href = url.toString(); //Aqui, eu estou recarregando a pagina, caso o usuário troque algum parametro.
        });
    }

    const campoBusca = document.getElementById("campoBusca");
    if(campoBusca){
        let timerBusca = null;

        campoBusca.addEventListener("input", function (){
            clearTimeout(timerBusca);

            timerBusca = setTimeout(() => {
                const url = new URL(window.location.href);
                const valor = this.value.trim();

                if(valor){
                    url.searchParams.set("busca", valor);
                }else{
                    url.searchParams.delete("busca");
                }

                url.searchParams.set("pagina", "1");
                window.location.href = url.toString();
            }, 500);
        });
    }

    function fecharTodosMenus(){
        document.querySelectorAll(".dropdown-acoes").forEach(menu =>{
            menu.classList.remove('aberto');
        });
    }

    window.toggleMenuAcoes = function (elemento){
        const menu = elemento.nextElementSibling;
        const estaAberto = menu.classList.contains("aberto");
        fecharTodosMenus();

        if(!estaAberto){
            menu.classList.add("aberto");
        }

    };


    document.addEventListener("click", function (event){
        if(!event.target.closest(".menu-acoes")){
            fecharTodosMenus();
        }
    })
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