package blockchain;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Mempool {

    //Future development can call priority queue by fees
    private Queue<Transaction> mempool;

    public Mempool() {
        this.mempool = new ArrayDeque<>();
    }

    public boolean addToMempool(Transaction transaction) {
        return this.mempool.add(transaction);
    }

    public boolean removeFromMempool(Transaction transaction) {
        return this.mempool.remove(transaction);
    }

    public boolean removeAllFromMempool(Set<Transaction> transactions) {
        return this.mempool.removeAll(transactions);
    }

    //To store some transactions in the block
    public Set<Transaction> getUnconfirmedTransactions() {
        Set<Transaction> set = new HashSet<>();
        //Future development can increase the number of iterations due to a large number of transactions
        for (int i = 0; i < 5; i++) {
            if (this.mempool.isEmpty()) {
                break;
            } else {
                set.add(this.mempool.remove());
            }
        }
        return set;
    }

    //Print all unconfirmed transactions
    public void printAllUnconfirmedTransactions(){
        mempool.iterator().toString();
    }
}
