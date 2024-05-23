package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.ArtisteType;
import be.iccbxl.pid.reservationsSpringboot.model.Show;
import be.iccbxl.pid.reservationsSpringboot.service.ShowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ShowController {

    private List<String> cart = new ArrayList<>();

    @Autowired
    private HttpSession session;
    @Autowired
    ShowService service;

    @GetMapping("/shows")
    public String index(Model model) {

        List<Show> shows = service.getAll();

        model.addAttribute("shows", shows);
        model.addAttribute("title", "Liste des spectacles");

        return "show/index";
    }

    @GetMapping("/shows/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Show show = service.get(id);

        //Récupérer les artistes du spectacle et les grouper par type
        Map<String, ArrayList<Artist>> collaborateurs = new TreeMap<>();

        for(ArtisteType at : show.getArtistTypes()) {
            if(collaborateurs.get(at.getType().getType()) == null) {
                collaborateurs.put(at.getType().getType(), new ArrayList<>());
            }

            collaborateurs.get(at.getType().getType()).add(at.getArtist());
        }

        model.addAttribute("collaborateurs", collaborateurs);
        model.addAttribute("show", show);
        model.addAttribute("title", "Fiche d'un spectacle");

        return "show/show";
    }

    @PostMapping("/cart/{id}")
    public String addToCart(@PathVariable("id") String id) {
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart"; // Redirigez pour éviter les problèmes de rafraichissement
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        List<Show> cartShows = new ArrayList<>();
        double totalPrice = 0.0; // Initialiser le prix total
        for (String showId : cart) {
            Show show = service.get(showId);
            cartShows.add(show);
            totalPrice += show.getPrice(); // Ajouter le prix au total
        }
        if (session.isNew()) { // Initialiser la session si elle est nouvelle
            session.setAttribute("paidShows", new ArrayList<String>());
        }
        List<String> paidShows = (List<String>) session.getAttribute("paidShows");
        if (paidShows == null) {
            paidShows = new ArrayList<>();
        }
        model.addAttribute("paidShows", paidShows); // Ajouter paidShows au modèle

        model.addAttribute("cartShows", cartShows);
        model.addAttribute("totalPrice", totalPrice); // Passer le prix total à la vue
        return "show/cart";
    }

    @ModelAttribute("cartItemCount")
    public int getCartItemCount() {
        List<String> cart = (List<String>) session.getAttribute("cart");
        return (cart != null) ? cart.size() : 0;
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") String id) {
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

}
