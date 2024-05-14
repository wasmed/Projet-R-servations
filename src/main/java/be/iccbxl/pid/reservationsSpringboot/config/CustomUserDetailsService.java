package be.iccbxl.pid.reservationsSpringboot.config;

import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.repository.UserRepository;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username :"+username);
        User user = userRepository.findByLogin(username);
        System.out.println("ROLE :"+user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                getGrantedAuthorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
