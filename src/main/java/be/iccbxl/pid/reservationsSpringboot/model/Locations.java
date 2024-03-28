package be.iccbxl.pid.reservationsSpringboot.model;

import lombok.Data;

@Data
public class Locations {

    private Long id;
    private String slug;
    private String designation;
    private String address;
    private Long localityId;
    private String website;
    private String phone;

    // Constructeurs, getters et setters

    // Constructeur par défaut
    public Locations() {
    }

    // Constructeur avec tous les champs
    public Locations(Long id, String slug, String designation, String address, Long localityId, String website, String phone) {
        this.id = id;
        this.slug = slug;
        this.designation = designation;
        this.address = address;
        this.localityId = localityId;
        this.website = website;
        this.phone = phone;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Long localityId) {
        this.localityId = localityId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
