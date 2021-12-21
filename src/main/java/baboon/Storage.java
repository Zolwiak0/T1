package baboon;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map<String, Integer> productsList;

    public Storage(Map<String, Integer> productsList){
        this.productsList = productsList;
    }

    public boolean hasProduct(String product){
        return productsList.containsKey(product.toLowerCase());
    }

    public Integer getProductPrice(String product){
        return productsList.get(product.toLowerCase());
    }

    public String getAvailableProducts() {
        return productsList.toString();
    }
}
