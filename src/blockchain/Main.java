package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
    blockchain.addTransaction(new Transaction("s1"));
    blockchain.addTransaction(new Transaction("s2"));
    blockchain.addTransaction(new Transaction("s3"));
    blockchain.addTransaction(new Transaction("s4"));
    blockchain.addTransaction(new Transaction("s5"));
    blockchain.addTransaction(new Transaction("s6"));
    
    blockchain.mineBlock();
//        System.out.println("mine="+blockchain.mineBlock());
    blockchain.blockExplorer();
//        System.out.println("block 0="+blockchain.getBlock(0));
//        System.out.println("block 1="+blockchain.getBlock(1));
    
//    Queue<String> set = new ArrayDeque<>();
//    set.add("sss");
//        System.out.println(set.remove());
//        
    }
}
