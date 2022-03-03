package blockchain;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Mempool {

    private static Mempool mempool;
    //Future development can call priority queue by fees
    private static ArrayDeque<Transaction> mempoolStorage;

    private Mempool() {

    }

    public static Mempool getInstance() {
        if (mempool == null) {
            mempool = new Mempool();
            mempoolStorage = new ArrayDeque<>();
        }
        return mempool;
    }

    public boolean addToMempool(Transaction transaction) {
        return this.mempoolStorage.add(transaction);
    }

    public boolean removeFromMempool(Transaction transaction) {
        return this.mempoolStorage.remove(transaction);
    }

    public boolean removeAllFromMempool(List<Transaction> transactions) {
        return this.mempoolStorage.removeAll(transactions);
    }

    //To store some transactions in the block
    public Set<Transaction> getUnconfirmedTransactions() {
        Queue<Transaction> tempMempoolStorage = null ;
            tempMempoolStorage = mempoolStorage.clone();
        
        Set<Transaction> set = new HashSet<>();
        //Future development can increase the number of iterations due to a large number of transactions
        for (int i = 0; i < 5; i++) {
            if (tempMempoolStorage.isEmpty()) {
                break;
            } else {
                set.add(tempMempoolStorage.remove());
            }
        }
        return set;
    }

    //Print all unconfirmed transactions
    public void printAllUnconfirmedTransactions() {
        this.mempoolStorage.iterator().toString();
    }
    
    public int getSize(){
        return this.mempoolStorage.size();
    }
}
