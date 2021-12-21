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

public class TestsSingleModules {


    private PrintStream standardOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

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

        return new CoinCollector(coinStorage, releaser, mockedScanner);
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
        Mockito.when(scanner.nextLine()).thenReturn("10");
        return scanner;
    }


    @Test
    public void test_releaser_release() {

        String testedString = "water";
        CoinStorage coinStorage = new CoinStorage(10);
        Releaser releaser = new Releaser(coinStorage);
        releaser.release(testedString);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Release: water");
    }

    @Test
    public void test_releaser_release_product_with_change() {
        String testedString = "water";
        CoinStorage coinStorage = new CoinStorage(10);
        Releaser releaser = new Releaser(coinStorage);
        releaser.release("water", 10);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Release:water\n" + "Release:10");
    }

    @Test
    public void test_coinCollector_release_nochange() {
        String testedString = "someProduct";
        CoinStorage coinStorage = new CoinStorage(10);
        Releaser releaser = new Releaser(coinStorage);
        InputReader mockedScanner = getMockScanner();

        CoinCollector coinCollector = new CoinCollector(coinStorage, releaser, mockedScanner);

        coinCollector.checkForChangeAndRelese(0, testedString);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Release: " + testedString);
    }

    @Test
    public void test_coinCollector_release_not_enought_for_change() {
        String testedString = "someProduct";
        CoinStorage coinStorage = new CoinStorage(0);
        Releaser releaser = new Releaser(coinStorage);
        InputReader mockedScanner = getMockScanner();

        CoinCollector coinCollector = new CoinCollector(coinStorage, releaser, mockedScanner);

        coinCollector.checkForChangeAndRelese(10, testedString);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Not enough coins for change, try again or return\n" +
                "Coins returned[]");
    }

    @Test
    public void test_coinCollector_with_change() {
        String testedString = "someProduct";
        CoinStorage coinStorage = new CoinStorage(5);
        Releaser releaser = new Releaser(coinStorage);
        InputReader mockedScanner = getMockScanner();

        CoinCollector coinCollector = new CoinCollector(coinStorage, releaser, mockedScanner);

        coinCollector.checkForChangeAndRelese(5, testedString);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Release:" + testedString + "\n" +
                "Release:5");
    }

    @Test
    public void test_coinCollector_sum_coins() {
//        ProductSelector productSelector = getProductSelector();
//        productSelector.mainLoop();
        CoinCollector coinCollector = getCoinCollector();
        coinCollector.collectorLoop("water", 20);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Price: 20 Insert coin \n" +
                " Type: return to abort\n" +
                "Collected coins: [10]\n" +
                "Collected coins: [10, 10]\n" +
                "Release: water");
    }

    @Test
    public void test_productSelector_not_product() {
        ProductSelector productSelector = getProductSelector();
        productSelector.logicForMainLoop();
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "Select product from list: \n" +
                "{water=40, crisp=65, chocolate=75}\n" +
                "Select right product");
    }


}


