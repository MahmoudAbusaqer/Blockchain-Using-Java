package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Blockchain {

//    private static Blockchain blockchain;
    private Mempool mempool = new Mempool();
    private final List<Block> chain = new LinkedList<>();
    private int difficulty = 2;

    public Blockchain() throws NoSuchAlgorithmException {
        createGenesisBlock();
    }

//    public Blockchain getInstance() throws NoSuchAlgorithmException {
//        if (blockchain == null) {
//            blockchain = new Blockchain();
//            createGenesisBlock();
//        }
//        return blockchain;
//    }
//    public void createNewBlock() throws NoSuchAlgorithmException {
//        Block block = null;
//        Block.Header header = block.new Header(0, getLastBlockInChain().getBlockHash(), difficulty);
//        block = new Block(header, mempool.getUnconfirmedTransactions());
//        mineBlock();
//        chain.add(block);
//    }
    //Add a new block to the Blockchain after being mind
    public boolean addToChain(Block block, String powHash) {
        String PreviousHash;
        if (chain.isEmpty()) {
            PreviousHash = "0";
        } else {
            PreviousHash = getLastBlockInChain().getBlockHash();
        }

        if (!PreviousHash.equals(block.getHeader().getPreviousHash())) {
            return false;
        }

        if (!isValid(block, powHash)) {
            return false;
        }
        block.setBlockHash(powHash);
        this.chain.add(block);
        return true;
    }

    //The algorithm of finding the hash taha match with the difficulty target
    public String proofOfWork(Block block) throws NoSuchAlgorithmException {
        int nonce = block.getHeader().getNonce();
        String computBlockHash = block.calculateHash(block.toString());
        while (!computBlockHash.startsWith("0", block.getHeader().getDifficulty())) {
            block.getHeader().setNonce(++nonce);
            computBlockHash = block.calculateHash(block.toString());
        }
        return computBlockHash;
    }

    //Mining a new block
    public boolean mineBlock() throws NoSuchAlgorithmException {
        if (mempool.getUnconfirmedTransactions().isEmpty()) {
            return false;
        }

        Block block = null;
        if (chain.isEmpty()) {
            Block.Header header = null;
            try {
                header = block.new Header(1, "0", this.difficulty);
            } catch (Exception e) {
                e.printStackTrace();
            }

            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
        } else {
            Block lastBlock = getLastBlockInChain();
            Block.Header header = block.new Header(1, lastBlock.getBlockHash(), this.difficulty);
            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
        }

        String pow = proofOfWork(block);
        addToChain(block, pow);
        return true;
    }

    //Get the last Blcok in the BlockChain
    public Block getLastBlockInChain() {
        return chain.get(chain.size() - 1);
    }

    //Check if the hash of the block is valid
    public boolean isValid(Block block, String blockHash) {
        return blockHash.startsWith("0", 2) && blockHash.equals(block.getBlockHash());
    }

//    public boolean setBlock(Block block) {
//        return chain.add(block);
//    }
    //Get a specific block from the blockchain
    public Block getBlock(Integer index) {
        return chain.get(index);
    }

    //Prit all the blocks in the blockchain
    public void blockExplorer() {
//        for (int i = 0; i < chain.size(); i++) {
        System.out.println(chain.toString());
//        }

    }

    //Add a new transaction
    public void addTransaction(Transaction transaction) {
        mempool.addToMempool(transaction);
    }

    //Create the first block in the blockchain
    private void createGenesisBlock() throws NoSuchAlgorithmException {
        addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer"));
        mineBlock();
//        Block block = null;
//        Block.Header header = block.new Header(0, "0", 0);
//        block = new Block(header, mempool.getUnconfirmedTransactions());
    }
}
