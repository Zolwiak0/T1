package baboon;

public class mainClass {

    public static void main(String[] args)  {
        ProductSelector productSelector = new Initializer().getProductSelector();
        productSelector.mainLoop();



    }

}

