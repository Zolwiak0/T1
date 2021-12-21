package baboon;

import java.util.*;

public class CoinCollector {

    private CoinStorage coinStorage;
    private Releaser releaser;

    private List<Integer> acceptedCoinValues = new ArrayList<>(Arrays.asList(1, 5, 10, 25));
    private List<Integer> collectedCoins = new LinkedList<>();

    private boolean runLoop = true;
    private String returnSignal = "return";

    private InputReader input;



    public CoinCollector(CoinStorage coinStorage, Releaser releaser, InputReader scanner){
        this.coinStorage = coinStorage;
        this.releaser = releaser;
        this.input = scanner;

    }

    public void collectorLoop(String productName, Integer totalPrice) {
        System.out.println("Price: " + totalPrice + " Insert coin \n Type: " + returnSignal + " to abort");
        runLoop = true;

        while (runLoop) {
            String incomingInput = input.nextLine();
            // to be sure it is a coin
            try {
                Integer coin = Integer.valueOf(incomingInput);
                //check if acceptable coin
                if (acceptedCoinValues.contains(coin)) {
                    collectedCoins.add(coin);
                    System.out.println("Collected coins: " + collectedCoins);
                    int sum = collectedCoins.stream().mapToInt(Integer::intValue).sum();
                    if (sum >= totalPrice) {
                        Integer change = sum - totalPrice;
                        this.checkForChangeAndRelese(change, productName);
                        this.clearCollectedCoins();
                    }
                } else {
                    System.out.println("Return unacceptable coin" + coin);
                    System.out.println("Collected coins: " + collectedCoins);
                }
            } catch (Exception e) {
                if (incomingInput.equalsIgnoreCase(returnSignal)) {
                    System.out.println("Return to main loop, return money" + collectedCoins);
                    this.clearCollectedCoins();
                    runLoop = false;
                } else {
                    System.out.println("Return thing " + incomingInput);
                    System.out.println("Collected coins: " + collectedCoins);
                }
            }

        }
    }

    public void clearCollectedCoins(){
        this.collectedCoins.clear();
    }

    // public only for test reasons, I know I could use reflection and .setAccessible
    public void checkForChangeAndRelese(Integer change, String productName ){
        if (change == 0) {
            coinStorage.storeCoins(this.collectedCoins);
            releaser.release(productName);
            runLoop = false;
        } else {
            Integer enoughForChange = coinStorage.getCoinsForChange() - change;
            if (enoughForChange >= 0 ){
                coinStorage.storeCoins(this.collectedCoins);
                releaser.release(productName, change);
                runLoop = false;
            } else {
                System.out.println("Not enough coins for change, try again or return");
                this.returnCoins(collectedCoins);
            }
        }

    }
    private void returnCoins(List<Integer> collectedCoins){
        System.out.println("Coins returned" + collectedCoins);
        clearCollectedCoins();

    }

}
