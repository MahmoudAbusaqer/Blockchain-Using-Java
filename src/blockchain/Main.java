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
        blockchain.addTransaction(new Transaction("s1"));
        blockchain.addTransaction(new Transaction("s2"));
        blockchain.addTransaction(new Transaction("s3"));
        blockchain.addTransaction(new Transaction("s4"));
        blockchain.addTransaction(new Transaction("s5"));
        blockchain.addTransaction(new Transaction("s6"));
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.blockExplorer();
    }
}
