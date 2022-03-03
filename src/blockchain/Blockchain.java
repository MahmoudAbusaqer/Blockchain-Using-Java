package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Blockchain {

    private Mempool mempool = Mempool.getInstance();
    private List<Block> chain = new LinkedList<>();
    private int difficulty = 2;

    public Blockchain() throws NoSuchAlgorithmException {
        System.out.println("mempool size = " + mempool.getSize());
        createGenesisBlock();
    }

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
        z("added to chain:::::" + this.chain.add(block));
        return true;
    }

    //The algorithm of finding the hash taha match with the difficulty target
    public String proofOfWork(Block block) throws NoSuchAlgorithmException {
        int nonce = block.getHeader().getNonce();
        String computBlockHash = block.calculateHash(block.toString());
        while (true) {
            if (computBlockHash.substring(0, 2).equals("00")) {
                break;
            }
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
        if (mempool.getUnconfirmedTransactions().isEmpty() && chain.isEmpty()) {
            return false;
        }
//        System.out.println("mempool size = " + mempool.getSize());
//        System.out.println("mempool: " + mempool.getUnconfirmedTransactions().toString());
        Block block = null;
        if (chain.isEmpty()) {
            Header header = new Header(1, "0", this.difficulty);
            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
        } else {
            Block lastBlock = getLastBlockInChain();
            Header header = new Header(1, lastBlock.getBlockHash(), this.difficulty);
            block = new Block(this.chain.size(), header, mempool.getUnconfirmedTransactions());
            block.MerkleTree();
        }
        String pow = proofOfWork(block);
        block.setBlockHash(pow);
        addToChain(block, pow);
        mempool.removeAllFromMempool(block.getTransactions());
        return true;
    }

    //Get the last Blcok in the BlockChain
    public Block getLastBlockInChain() {
        return chain.get(chain.size() - 1);
    }

    //Check if the hash of the block is valid
    public boolean isValid(Block block, String blockHash) {
        return blockHash.substring(0, 2).equals("00") && blockHash.equals(block.getBlockHash());
    }

    //Get a specific block from the blockchain
    public Block getBlock(Integer index) {
        return chain.get(index);
    }

    //Prit all the blocks in the blockchain
    public void blockExplorer() {
        System.out.println(chain.toString());
    }

    //Add a new transaction
    public boolean addTransaction(Transaction transaction) {
        return mempool.addToMempool(transaction);
    }

    //Create the first block in the blockchain
    private void createGenesisBlock() throws NoSuchAlgorithmException {
        addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer"));
        mineBlock();
    }
}
