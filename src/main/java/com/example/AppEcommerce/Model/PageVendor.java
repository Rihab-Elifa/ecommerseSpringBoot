package com.example.AppEcommerce.Model;

import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Enum.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PageVendor {
    @Id
    private String id;
    private String title;
    private String address;
    private String email;
    private long phone ;
    //private String city;
    private long postalCode;
    @Enumerated(EnumType.STRING)
    private Activity activity;
    @Enumerated(EnumType.STRING)
    private Region region;

    private double longitude;

    private double latitude;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="p_id")
    private List<Produit> p=new ArrayList<>();*/
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_profil_id")
    private File imageProfile;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_couverture_id")
    private File imageCouverture;

    public PageVendor(String title, String address, File imageProfile,File imageCouverture,String email,Long phone,Long postalCode,Activity activity,Region region, double longitude, double latitude){
        this.title = title;
        this.address = address;
        this.email = email;
        this.phone = phone;

        this.postalCode = postalCode;


        this.imageProfile = imageProfile;
        this.imageCouverture = imageCouverture;
        this.activity=activity;
        this.region=region;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public File getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(File imageProfile) {
        this.imageProfile = imageProfile;
    }

    public File getImageCouverture() {
        return imageCouverture;
    }

    public void setImageCouverture(File imageCouverture) {
        this.imageCouverture = imageCouverture;
    }
}
