

   document.addEventListener("DOMContentLoaded", async () => {
     const stripe = Stripe('pk_test_51PIioGLRZ67u1OVroQzRgKVWBgZZTfPk4WYL9EeIo9RqK5gtxCmzbU2Zts0vpIjuthiXa2y7n96V9PSKDfYHPoHT00eRDOAz7Y');
       const elements = stripe.elements();
       const cardElement = elements.create('card');
       cardElement.mount('#card-element');

       const form = document.getElementById('payment-form');
       const amount = form.getAttribute('data-amount');

       form.addEventListener('submit', async (event) => {
           event.preventDefault();

           const {error, paymentMethod} = await stripe.createPaymentMethod({
               type: 'card',
               card: cardElement,
           });

           if (error) {
               document.getElementById('card-errors').textContent = error.message;
           } else {
               const response = await fetch('/payment', {
                   method: 'POST',
                   headers: {
                       'Content-Type': 'application/json',
                   },
                   body: JSON.stringify({
                       paymentMethodId: paymentMethod.id,
                       amount: amount // Inclure le montant dans le corps de la requête
                   }),
               });

               const paymentResult = await response.json();
               if (paymentResult.error) {
                   document.getElementById('card-errors').textContent = paymentResult.error;
               } else {
                   window.location.href = "/payment/success";
               }
           }
       });
   });
