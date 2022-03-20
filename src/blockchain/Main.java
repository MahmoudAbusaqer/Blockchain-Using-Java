package blockchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        Blockchain blockchain = new Blockchain();
        blockchain.addTransaction(new Transaction("The 1st transaction"));
        blockchain.addTransaction(new Transaction("The 2nd transaction"));
        blockchain.addTransaction(new Transaction("The 3rd transaction"));
        blockchain.addTransaction(new Transaction("The 4th transaction"));
        blockchain.addTransaction(new Transaction("The 5th transaction"));
        blockchain.addTransaction(new Transaction("The 6th transaction"));
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.mineBlock();
        blockchain.blockExplorer();
    }
}
