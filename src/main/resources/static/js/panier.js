document.addEventListener('DOMContentLoaded', function () {
  const tableBody = document.querySelector('.table tbody');
  const totalPriceElement = document.querySelector('.total-price');


  tableBody.addEventListener('click', function (event) {
    if (event.target.classList.contains('btn-danger')) {
      const row = event.target.closest('tr');
      const priceToRemove = parseFloat(row.querySelector('td:nth-child(2)').textContent);
      const currentTotalPrice = parseFloat(totalPriceElement.textContent.replace(' €', '').replace(',', '.'));
      const newTotalPrice = currentTotalPrice - priceToRemove;

      totalPriceElement.textContent = newTotalPrice.toFixed(2) + ' €';
    }
  });
});