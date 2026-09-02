const percentualConcluido = window.percentualConcluido || 68;

const ctx = document.getElementById("graficoPercentual").getContext("2d");

const graficoPercentual = new Chart(ctx, {
    type: "doughnut",
    data: {
        labels: ["Concluído", "Restante"],
        datasets: [{
            data: [percentualConcluido, 100 - percentualConcluido],
            backgroundColor: ["#005b63", "#e0e0e0"],
            borderWidth: 0
        }]
    },
    options: {
        cutout: "70%",
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { display: false }
        }
    }
});

const ctxLinhas = document.getElementById("graficoLinhas").getContext("2d");

new Chart(ctxLinhas, {
    type: "line",
    data: {
        labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        datasets: [
            {
                label: "Alcançado",
                data: [20, 25, 40, 55, 70, 65, 80, 85, 90, 75, 95, 100],
                borderColor: "#005b63",
                backgroundColor: "rgba(0, 91, 99, 0.05)",
                fill: true,
                tension: 0.4,
                pointRadius: 3
            },
            {
                label: "Meta",
                data: [40, 40, 50, 60, 70, 70, 85, 90, 95, 95, 100, 105],
                borderColor: "#e0e0e0",
                backgroundColor: "transparent",
                borderDash: [5, 5],
                tension: 0.4,
                pointRadius: 3
            }
        ]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                max: 125,
                ticks: { stepSize: 25 },
                grid: { display: true }
            },
            x: {
                grid: { display: false }
            }
        },

        plugins: {
            legend: {
                position: "top",
                align: "start",
                labels: {
                    usePointStyle: true,
                    boxWidth: 8,
                    boxHeight: 8,
                    color: "#666"
                }
            }
        }
    }
});

const setaDropdown = document.getElementById("seta-dropdown");
const dropdownMenu = document.getElementById("dropdown-menu");

setaDropdown.addEventListener("click", function () {
    dropdownMenu.classList.toggle("aberto");
});