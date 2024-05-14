package be.iccbxl.pid.reservationsSpringboot.service;

import be.iccbxl.pid.reservationsSpringboot.model.Role;
import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }


    public User getUser(long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public void updateUser(long id, User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    public User findByLogin(String login) {
        return userRepository.findFirstByLogin(login);
    }

    public User findByEmail(String email) {
       return userRepository.findByEmail(email);
    }

    public void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirmation de votre inscription");
        message.setText("Bonjour " + user.getFirstname() + ",\n\n"
                + "Merci pour votre inscription sur notre site. Veuillez cliquer sur le lien suivant pour activer votre compte :http://localhost:8080/login" + "\n\n"
                + "your Login: " + user.getLogin() + "\n"

                + "Cordialement,\nVotre équipe de support");
        javaMailSender.send(message);
    }
}