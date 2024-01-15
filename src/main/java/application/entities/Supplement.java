package application.entities;

/**
 * Entity class representing a supplement product being scraped from a website.
 * This class includes a parameterized constructor to initialize the supplement attributes.
 * Also contains Getter, Setter and toString methods
 */

public class Supplement {
    private String imageUrl;
    private String supplementName;
    private String price;
    private String supplementUrl;

    /**
     * Parameterized constructor for the Supplement class.
     *
     * @param imageUrl       The URL of the supplement's image.
     * @param supplementName The name of the supplement.
     * @param price          The price of the supplement.
     * @param supplementUrl  The URL of the supplement's details.
     */
    public Supplement(String imageUrl, String supplementName, String price, String supplementUrl) {
        this.imageUrl = imageUrl;
        this.supplementName = supplementName;
        this.price = price;
        this.supplementUrl = supplementUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public void setSupplementName(String supplementName) {
        this.supplementName = supplementName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupplementUrl() {
        return supplementUrl;
    }

    public void setSupplementUrl(String supplementUrl) {
        this.supplementUrl = supplementUrl;
    }

    @Override
    public String toString() {
        return "Supplement{" +
                "imageUrl='" + imageUrl + '\'' +
                ", supplementName='" + supplementName + '\'' +
                ", price='" + price + '\'' +
                ", supplementUrl='" + supplementUrl + '\'' +
                '}';
    }
}
