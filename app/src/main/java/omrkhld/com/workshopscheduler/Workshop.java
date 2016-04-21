package omrkhld.com.workshopscheduler;

/**
 * Created by Omar on 21/4/2016.
 */
public class Workshop {
    private String id, name, coords, phone;
    private boolean tyre_change, oil_change, battery_change;
    private int rating;

    public Workshop() {
    }

    public Workshop(String id, String name, String coords, String phone, boolean t, boolean o, boolean b, int rating) {
        this.id = id;
        this.name = name;
        this.coords = coords;
        this.phone = phone;
        tyre_change = t;
        oil_change = o;
        battery_change = b;
        this.rating = rating;
    }

    public String getID() { return id; }
    public String getName() { return name; }
    public String getCoords() { return coords; }
    public String getPhone() { return phone; }
    public boolean getTyreChange() { return tyre_change; }
    public boolean getOilChange() { return oil_change; }
    public boolean getBattChange() { return battery_change; }
    public int getRating() { return rating; }

    public void setID(String i) { id = i; }
    public void setName(String n) { name = n; }
    public void setCoords(String c) { coords = c; }
    public void setPhone(String p) { phone = p; }
    public void setTyreChange(boolean t) { tyre_change = t; }
    public void setOilChange(boolean o) { oil_change = o; }
    public void setBattChange(boolean b) { battery_change = b; }
    public void setRating(int r) { rating = r; }
}
