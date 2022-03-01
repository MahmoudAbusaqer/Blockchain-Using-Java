package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Blockchain {

//    private static Blockchain blockchain;
    private Mempool mempool = Mempool.getInstance();
    private List<Block> chain = new LinkedList<>();
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
        z("powHash= " + powHash);
        z("added to chain:::::" + this.chain.add(block));
        return true;
    }

//    public void x(Block block) {
//        System.out.println("block.getHeader().getNonce()" + block.getHeader().getNonce());
//    }
    //The algorithm of finding the hash taha match with the difficulty target
    public String proofOfWork(Block block) throws NoSuchAlgorithmException {
//        x(block);
//y(block);
        int nonce = block.getHeader().getNonce();
        String computBlockHash = block.calculateHash(block.toString());
        while (true) {
            if (computBlockHash.substring(0, 2).equals("00")) {
                break;
            }
//            while (!computBlockHash.startsWith("0", block.getHeader().getDifficulty())) {
            block.getHeader().setNonce(++nonce);
            computBlockHash = block.calculateHash(block.toString());

        }
        return computBlockHash;
    }

    public void y(Block block) {
        System.out.println("block= " + block.toString());
    }

    public void z(String s) {
        System.out.println(s);
    }

    //Mining a new block
    public boolean mineBlock() throws NoSuchAlgorithmException {
        if (mempool.getUnconfirmedTransactions().isEmpty()) {
            z("1");
            return false;
        }
        z("**1");
        Block block = null;
        if (chain.isEmpty()) {
            z("2");
            Header header = new Header(1, "0", this.difficulty);
//            try {
//                header = block.new Header(1, "0", this.difficulty);
////                y(block);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
            block.MerkleTree();

        } else {
            z("3");
            Block lastBlock = getLastBlockInChain();
            Header header = new Header(1, lastBlock.getBlockHash(), this.difficulty);
            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
            block.MerkleTree();
//            y(block);
        }
        z("4");
        String pow = proofOfWork(block);
        z("addToChain " + addToChain(block, pow));
        z("pow" + pow);
        mempool.removeAllFromMempool(block.getTransactions());
        y(block);
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
    public boolean addTransaction(Transaction transaction) {
        return mempool.addToMempool(transaction);
    }

    //Create the first block in the blockchain
    private void createGenesisBlock() throws NoSuchAlgorithmException {
        System.out.println(addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer")));
//        addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer"));
//        System.out.println("mempool: " + mempool.getUnconfirmedTransactions().toString());
        System.out.println(mineBlock());
//        mineBlock();
        System.out.println("inside the createGenesisBlock");
        System.out.println("mempool: " + mempool.getUnconfirmedTransactions().toString());
//        Block block = null;
//        Block.Header header = block.new Header(0, "0", 0);
//        block = new Block(header, mempool.getUnconfirmedTransactions());
    }
}
