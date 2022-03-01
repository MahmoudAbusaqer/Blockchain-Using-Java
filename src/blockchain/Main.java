package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        List<String> chain = new LinkedList<>();
//        chain.add("a");
//        chain.add("d");
//        chain.add("v");
//        chain.add("e");
//        chain.add("1");
//        System.out.println(chain.get(0));
//        int i = 0;
//        System.out.println("i++ = " + i++);
//        System.out.println("i = " + i);
//        System.out.println("++i = " + ++i);
//        System.out.println("i = " + i);

    Blockchain blockchain = new Blockchain();
//    blockchain.getInstance();
    blockchain.addTransaction(new Transaction("s"));
    blockchain.blockExplorer();
        
    }
}
