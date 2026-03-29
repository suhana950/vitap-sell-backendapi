package com.vitapsell.project.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double listingId;
    @Column(nullable = false)
    private String Title;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String condition;
    @Column(nullable = false)
    private LocalDateTime datetime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String listingDescription;


    public double getListingId() {
        return listingId;
    }
    public void setListingId(double listingId) {
        this.listingId = listingId;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getConditon() {
        return condition;
    }
    public void setConditon(String conditon) {
        this.condition = conditon;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getListingDescription() {
        return listingDescription;
    }
    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }


    

    
}
