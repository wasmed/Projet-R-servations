package be.iccbxl.pid.reservationsSpringboot.model;

public class UserNotFoundException  extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }

}
