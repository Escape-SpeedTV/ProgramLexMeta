const ctx = document.getElementById("graficoPercentual").getContext("2d");

const graficoPercentual = new Chart(ctx,{
    type: "doughnut",
    data: {
        labels: [`Concluido`, `Restante`],
        datasets: [{
            data: [percentualConcluido, 100 - percentualConcluido],
            backgroundColor: [`#005b63`, `#e0e0e0`],
            borderWidth: 0
    }]
},
options: {
    cutout: `70%`,
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
        legend: {display: false}
    }
}
});