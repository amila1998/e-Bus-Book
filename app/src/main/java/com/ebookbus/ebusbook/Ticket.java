package com.ebookbus.ebusbook;
import com.google.firebase.firestore.Exclude;
import java.util.Map;
public class Ticket {
    private String documentId;
    private String noPlate;
    private String currLocatioin;
    private String endLocation;
    private String fullqty;
    private String Halfqty;
    private float price;

    public Ticket() {
            //public no-arg constructor needed
    }
    public Ticket(String noPlate, String currLocatioin,String endLocation,String fullqty,String Halfqty,float price) {
            this.noPlate = noPlate;
            this.currLocatioin = currLocatioin;
            this.endLocation = endLocation;
            this.fullqty = fullqty;
            this.Halfqty = Halfqty;
            this.price = price;

    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public String getNoPlate() {
        return noPlate;
    }

    public void setNoPlate(String noPlate) {
        this.noPlate = noPlate;
    }

    public String getCurrLocatioin() {
        return currLocatioin;
    }

    public void setCurrLocatioin(String currLocatioin) {
        this.currLocatioin = currLocatioin;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getFullqty() {
        return fullqty;
    }

    public void setFullqty(String fullqty) {
        this.fullqty = fullqty;
    }

    public String getHalfqty() {
        return Halfqty;
    }

    public void setHalfqty(String halfqty) {
        Halfqty = halfqty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

