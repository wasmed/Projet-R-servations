 document.addEventListener('DOMContentLoaded', function () {
        const representationsContainer = document.getElementById('representationsContainer');
        const addRepresentationBtn = document.getElementById('addRepresentationBtn');
        const representationTemplate = representationsContainer.querySelector('.representation-template');

        let representationCount = 0; // Counter for representation index

        function addRepresentation() {
            const newRepresentation = representationTemplate.cloneNode(true);
            newRepresentation.style.display = 'block';

            const inputs = newRepresentation.querySelectorAll('input, select');
            inputs.forEach(input => {
                input.name = input.name.replace('[0]', '[' + representationCount + ']');
                input.id = input.name; // Update ID to match name
            });

            newRepresentation.querySelector('.remove-representation').addEventListener('click', function() {
                this.parentNode.remove();
                representationCount--; // Decrement the counter when a representation is removed
            });

            representationsContainer.appendChild(newRepresentation);
            representationCount++;
        }
