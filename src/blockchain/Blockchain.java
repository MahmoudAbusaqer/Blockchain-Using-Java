package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Blockchain {

    private static Blockchain blockchain;
    private Mempool mempool = new Mempool();
    private final List<Block> chain = new LinkedList<>();
    private int difficulty = 2;

    private Blockchain() {
    }

    public Blockchain getInstance() throws NoSuchAlgorithmException {
        if (blockchain == null) {
            blockchain = new Blockchain();
            chain.add(createGenesisBlock());
        }
        return blockchain;
    }
    
    public void createNewBlock() throws NoSuchAlgorithmException{
        Block block = null;
        Block.Header header = block.new Header(0, getBlock(chain.size()).getBlockHash(), difficulty);
        block = new Block(header, mempool.getUnconfirmedTransactions());
        mineBlock();
        chain.add(block);
    }
    
//    public voi

    public boolean setBlock(Block block) {
        return chain.add(block);
    }

    public Block getBlock(Integer index) {
        return chain.get(index);
    }

    public void blockExplorer() {
//        for (int i = 0; i < chain.size(); i++) {
        System.out.println(chain.toString());
//        }

    }

    public void mineBlock() {

    }

    public void addTransaction(Transaction transaction) {
        mempool.addToMempool(transaction);
    }

    private Block createGenesisBlock() throws NoSuchAlgorithmException {
        addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer"));
        Block block = null;
        Block.Header header = block.new Header(0, "0", 0);
        block = new Block(header, mempool.getUnconfirmedTransactions());
        return block;
    }
}
