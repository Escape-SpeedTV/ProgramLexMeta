document.addEventListener('DOMContentLoaded', function (){
    var abas = document.querySelectorAll('.aba');
    var paineis = document.querySelectorAll('.aba-painel');
    if(!abas.length) return;

    function abrir(id){
        var existe = false;
        paineis.forEach(function (p){
            var ativo = p.id === id;
            p.classList.toggle('ativo', ativo);
            if(ativo) existe = true;
        });
        if(!existe) return false;
        abas.forEach(function(a){
            a.classList.toggle('ativa', a.dataset.aba === id);
            a.setAttribute('aria-selected', a.dataset.aba === id);
        });
        return true;
    }
    abas.forEach(function(aba){
        aba.addEventListener('click', function(){
            abrir(aba.dataset.aba);
            history.replaceState(null, '', '#' + aba.dataset.aba);
        });
    });

    if(location.hash) abrir(location.hash.substring(1));
})