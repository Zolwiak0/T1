package baboon;

import java.util.LinkedList;
import java.util.List;

public class CoinStorage {
    // Due to lack of requirements, let assume machine stored only coins with value 1 for purpose of change
    private Integer coinsForChange;
    private List< List<Integer>> storedCoins = new LinkedList<>();

    public CoinStorage(int initialCoins){
        this.coinsForChange = initialCoins;
    }

    public void storeCoins(List<Integer> collectedCoins){
        storedCoins.add(collectedCoins);
    }

    public String storedCoinsStatus(){
        return storedCoins.toString();
    }

    public Integer getCoinsForChange(){
        return coinsForChange;
    }

    public void subtractFromCoinsForChange(Integer releasedChange){
        coinsForChange = coinsForChange - releasedChange;
    }

}
