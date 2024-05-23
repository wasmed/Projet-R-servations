$(document).ready(function () {
    // Initialisation de DataTable avec la réactivité
    var table = $('#shows-table').DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 100, { "label": "Tout", "value": -1 }]
    });

    // Recherche dans le tableau lors de la frappe
    $('#searchInput').on('keyup', function () {
        table.search(this.value).draw();
    });

    // Nombre de spectacles affichés
    $('#showCountSelect').on('change', function () {
        var rowCount = table.rows().count();
        var selectedValue = $(this).val();

        if (selectedValue == 'all') {
            table.page.len(-1).draw();
            $(this).find('option[value="all"]').text('les spectacles (' + rowCount + ')');
        } else {
            table.page.len(selectedValue).draw();
        }
    });
});
