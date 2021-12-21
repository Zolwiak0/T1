package baboon;

public class Releaser {
    //For task purpose entire release process will be only on console
    CoinStorage coinStorage;

    public Releaser(CoinStorage coinStorage){
        this.coinStorage = coinStorage;
    }

    public void release(String productName){
        //TODO subtract from quantinty
        System.out.println("Release: " + productName);
    }

    public void release(String productName, Integer change){
        //TODO subtract from quantinty
        coinStorage.subtractFromCoinsForChange(change);
        System.out.println("Release:" + productName);
        System.out.println("Release:" + change);
    }
}
