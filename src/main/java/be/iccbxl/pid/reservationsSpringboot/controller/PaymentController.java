package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.Show;
import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.service.PaymentService;
import be.iccbxl.pid.reservationsSpringboot.service.ShowService;
import be.iccbxl.pid.reservationsSpringboot.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payment")

public class PaymentController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Autowired
    ShowService service;

    @Autowired
    PaymentService paymentService;
    @Autowired
    UserService userService;

    @GetMapping
    public String getPaymentPage(Model model, @RequestParam double amount) {
        model.addAttribute("amount", amount);
        return "payment";
    }

    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> paymentData) {
        String paymentMethodId = (String) paymentData.get("paymentMethodId");

        Stripe.apiKey = stripeApiKey;

        try {
            // Créez une intention de paiement avec Stripe
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(1500L) // Le montant en centimes (1500 = 15.00 EUR)
                    .setCurrency("eur")
                    .setPaymentMethod(paymentMethodId)
                    .setConfirm(true)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            return ResponseEntity.ok(Map.of("clientSecret", paymentIntent.getClientSecret()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(HttpSession session, Principal principal, Model model) {

        String username = principal.getName();
        User user = userService.findByLogin(username);
        String userEmail = user.getEmail();
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart != null) {
            List<String> paidShows = (List<String>) session.getAttribute("paidShows");
            if (paidShows == null) {
                paidShows = new ArrayList<>();
            }
            paidShows.addAll(cart);

            // Récupérer les détails de la commande

            List<Show> cartShows = cart.stream().map(service::get).collect(Collectors.toList());
            double totalPrice = cartShows.stream().mapToDouble(Show::getPrice).sum();
            // Construire le corps de l'e-mail
            String emailBody = "Merci pour votre achat !\n\nDétails de votre commande :\n\n";
            for (Show show : cartShows) {
                emailBody += "- " + show.getTitle() + " : " + show.getPrice() + " €\n";
            }
            emailBody += "\nPrix total : " + totalPrice + " €";


            session.setAttribute("paidShows", paidShows);
            session.removeAttribute("cart"); // Vider le panier
            model.addAttribute("emailSent", true);
            // Envoyer l'e-mail
            paymentService.sendConfirmationEmail(userEmail, "Confirmation d'achat", emailBody);
        }

        return "succes";
    }


}
