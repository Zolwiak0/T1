import baboon.*;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TestFlow1 {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeTest
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterTest
    public void tearDown() {
        System.setOut(standardOut);
       }

    public ProductSelector getProductSelector() {
        Storage storage = getStorage();
        CoinCollector coinCollector = getCoinCollector();
        InputReader mockedScanner = getMockScanner();
        return new ProductSelector(storage, coinCollector, mockedScanner);
    }

    private Storage getStorage() {
        Map<String, Integer> products = getProducts();
        return new Storage(products);
    }

    private CoinCollector getCoinCollector() {
        CoinStorage coinStorage = new CoinStorage(10);
        Releaser releaser = new Releaser(coinStorage);
        InputReader mockedScanner = getMockScanner();

        return new CoinCollector(coinStorage, releaser, mockedScanner );
    }

    private Map<String, Integer> getProducts() {
        Map<String, Integer> productMap = new HashMap<>();
        productMap.put("water", 40);
        productMap.put("crisp", 65);
        productMap.put("chocolate", 75);

        return productMap;
    }

    private InputReader getMockScanner() {
        InputReader scanner = Mockito.mock(InputReader.class);
        Mockito.when(scanner.nextLine()).thenReturn("water", "rock", "1", "2", "3,5", "5", "10", "25" );
        return scanner;
    }

    @Test
    public void flow1(){
        ProductSelector productSelector = getProductSelector();
        productSelector.logicForMainLoop();
//        CoinCollector coinCollector = getCoinCollector();
//        coinCollector.collectorLoop("water", 20);
        Assert.assertEquals(outputStreamCaptor.toString().trim() ,"Select product from list: \n" +
                "{water=40, crisp=65, chocolate=75}\n" +
                "  Selected product\n" +
                "Price: 40 Insert coin \n" +
                " Type: return to abort\n" +
                "Return thing water\n" +
                "Collected coins: []\n" +
                "Return thing rock\n" +
                "Collected coins: []\n" +
                "Collected coins: [1]\n" +
                "Return unacceptable coin2\n" +
                "Collected coins: [1]\n" +
                "Return thing 3,5\n" +
                "Collected coins: [1]\n" +
                "Collected coins: [1, 5]\n" +
                "Collected coins: [1, 5, 10]\n" +
                "Collected coins: [1, 5, 10, 25]\n" +
                "Release:water\n" +
                "Release:1");
    }
}
