package blockchain;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain();
        blockchain.addTransaction(new Transaction("Transaction 1"));
        blockchain.addTransaction(new Transaction("Transaction 2"));
        blockchain.addTransaction(new Transaction("Transaction 3"));
        blockchain.addTransaction(new Transaction("Transaction 4"));
        blockchain.addTransaction(new Transaction("Transaction 5"));
        blockchain.addTransaction(new Transaction("Transaction 6"));
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.blockExplorer();
    }
}
