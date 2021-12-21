package baboon;

import java.util.HashMap;
import java.util.Map;

public class Initializer {

    public ProductSelector getProductSelector() {
        Storage storage = getStorage();
        CoinCollector coinCollector = getCoinCollector();
        InputReader scanner = new InputReader();


        return new ProductSelector(storage, coinCollector, scanner);
    }

    private Storage getStorage() {
        Map<String, Integer> products = getProducts();
        return new Storage(products);
    }

    private CoinCollector getCoinCollector() {
        CoinStorage coinStorage = new CoinStorage(10);
        Releaser releaser = new Releaser(coinStorage);
        InputReader scanner = new InputReader();


        return new CoinCollector(coinStorage, releaser, scanner);
    }

    private Map<String, Integer> getProducts() {
        Map<String, Integer> productMap = new HashMap<>();
        productMap.put("water", 40);
        productMap.put("crisp", 65);
        productMap.put("chocolate", 75);

        return productMap;
    }

}
