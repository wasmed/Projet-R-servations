 $(document).ready(function () {
     // Initialisation de DataTable avec la réactivité
     $('#shows-table').DataTable({
         responsive: true
     });

     // Recherche dans le tableau lors de la frappe
     $('#searchInput').on('keyup', function() {

         var searchTerm = $(this).val();
         console.log("searchTerm : " + searchTerm);
         $('#shows-table').DataTable().search(searchTerm).draw();
     });

     // Nombre de spectacles affichés
     $('#showCountSelect').on('change', function() {

         var rowCount = $('#shows-table').DataTable().rows().count();
         var selectedValue = $(this).val();

         console.log("rowCount: " + rowCount);
         console.log("selectedValue: " + selectedValue);

         if (selectedValue == 'all') {
             $('#shows-table').DataTable().page.len(-1).draw();
             $('#showCountSelect option[value="all"]').text('les spectacles (' + rowCount + ')');
         } else {
             $('#shows-table').DataTable().page.len(selectedValue).draw();
         }
     });
 });
