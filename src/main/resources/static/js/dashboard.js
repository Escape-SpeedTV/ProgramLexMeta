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

const ctxBarras = document.getElementById("graficoBarras").getContext("2d");

const graficoBarras = new Chart(ctxBarras, {
    type: "bar",
    data: {
        labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        datasets: [
            {
                label: "Concluídos",
                data: [30, 45, 40, 60, 35, 70, 68, 50, 60, 40, 75, 55],
                backgroundColor: "#005b63",
                borderRadius: 4
            },
            {
                label: "Meta",
                data: [25, 35, 50, 45, 55, 50, 65, 70, 40, 60, 50, 45],
                backgroundColor: "#e0e0e0",
                borderRadius: 4
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
                ticks: { stepSize: 25 }
            }
        }
    }
});