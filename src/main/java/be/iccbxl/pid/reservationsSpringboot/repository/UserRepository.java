package be.iccbxl.pid.reservationsSpringboot.repository;

import be.iccbxl.pid.reservationsSpringboot.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long > {
    User findFirstByLogin(String login);
    User findByLogin(String login);
    User findByEmail(String email);
    //List<User> findByLastname(String lastname);
    User findById(long id);


}
