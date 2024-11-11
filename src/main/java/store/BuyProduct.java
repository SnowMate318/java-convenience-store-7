package store;

public class BuyProduct {

    private String productName;
    private int quantity;
    private Integer price;

    BuyProduct(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    BuyProduct(String productName, int quantity, int price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName () {
        return this.productName;
    }

    public int getQuantity () {
        return this.quantity;
    }

    public Integer getPrice () {
        return this.price;
    }

}
