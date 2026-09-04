const setaDropdown = document.getElementById("seta-dropdown");
const dropdownMenu = document.getElementById("dropdown-menu");

setaDropdown.addEventListener("click", function () {
    dropdownMenu.classList.toggle("aberto");
});