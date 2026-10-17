function abrirModalMeta() {
    const modal = document.getElementById('modal-meta');
    if (modal) modal.style.display = 'flex';
}

function fecharModalMeta() {
    const modal = document.getElementById('modal-meta');
    if (modal) modal.style.display = 'none';
}

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('modal-meta');
    if (modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                fecharModalMeta();
            }
        });
    }
});