package be.iccbxl.pid.reservationsSpringboot.model;

import lombok.Data;

@Data
public class RoleUser {

    private Long id;
    private Long userId;
    private Long roleId;

    public RoleUser(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
