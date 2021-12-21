package baboon;

public class ProductSelector {

    private Storage storage;
    private CoinCollector coinCollector;

    private boolean runLoop = true;
    private InputReader input;



    public ProductSelector(Storage storage, CoinCollector coinCollector, InputReader scanner){
        this.storage = storage;
        this.coinCollector = coinCollector;
        this.input = scanner;

    }

    public void mainLoop() {
        while(runLoop) {
                logicForMainLoop();
        }

    }
    //public only for tests purpose
    public void logicForMainLoop(){
        System.out.println("Select product from list: ");
        System.out.println(storage.getAvailableProducts());

        String incomingInput = input.nextLine();

        if(storage.hasProduct(incomingInput)) {
            System.out.println("  Selected product");
            Integer totalPrice = storage.getProductPrice(incomingInput);
            coinCollector.collectorLoop(incomingInput, totalPrice);
        } else {
            System.out.println("Select right product");
        }
    }
}

