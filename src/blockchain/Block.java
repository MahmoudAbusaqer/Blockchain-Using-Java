package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Block {

    private TreeSet<String> merkleTree;

    

    private int index;
    private Header header;
    private int transactionCounter;
    private List<Transaction> transactions;
    private String blockHash;

    public Block(int index, Header header, Set<Transaction> transactions) throws NoSuchAlgorithmException {
        this.index = index;
        this.transactions = new ArrayList<>(transactions);
        this.transactionCounter = this.transactions.size();
//        System.out.println("before the call of the merkle tree");
//        MerkleTree();

        this.header = header;
//        System.out.println("** after the call header object");
//        System.out.println(this.header.toString());
//        this.header.setMerkleRoot(getMerkleRoot());
//        blockHash = calculateHash(this.toString());
    }

    public void MerkleTree() throws NoSuchAlgorithmException {
        merkleTree = new TreeSet<>();
//        System.out.println(merkleTree.toString());
        for (int i = 0; i < transactions.size(); i++) {
            merkleTree.add(transactions.get(i).getTransactionHash());
        }
        LinkedList<String> temp = new LinkedList<>(merkleTree);
        LinkedList<String> temp2 = new LinkedList<>();
//        System.out.println(merkleTree.toString());
        int i = 0;
        while (temp.size() != 1) {
            int tempSize = temp.size();
//            System.out.println("$$tempSize= " + tempSize);
            String newHash = null;

            if (!temp.isEmpty()) {
                if ((i + 1) % 2 != 0 && i + 1 < tempSize) {
                    String newString = temp.get(i) + temp.get(i + 1);
                    newHash = calculateHash(newString);
//                    System.out.println("newString= " + temp.get(i) + " | " + temp.get(i + 1));
                } else {
                    String newString = temp.get(i) + temp.get(i);
                    newHash = calculateHash(newString);
//                    System.out.println("newString= " + temp.get(i) + " | " + temp.get(i));
                }
            } else {
                break;
            }

            merkleTree.add(newHash);
            temp2.add(newHash);
            if (tempSize <= i + 2) {
//                System.out.println("temp = " + temp.toString());
                temp = (LinkedList<String>) temp2.clone();
//                System.out.println("temp = " + temp.toString());
                temp2.clear();
                i = 0;
            } else {
                i += 2;
            }
        }
//        System.out.println(merkleTree.toString());

    }

    public void setMerkleRoot() {
        this.header.setMerkleRoot(merkleTree.last());
//        if (merkleTree.last() != null) {
//            return merkleTree.last();
//        } else {
//            return "";
//        }

    }

    public String calculateHash(String string) throws NoSuchAlgorithmException {
        //Call the SHA-256 Algorithm from MessageDigest
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //Hash the transaction
        byte[] encodedhash = digest.digest(
                string.getBytes(StandardCharsets.UTF_8));
        //Change the array of byte into a string
        String transaction_Hash = DatatypeConverter.printHexBinary(encodedhash);
        return transaction_Hash;
    }

    public Header getHeader() {
        return header;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Block{" + "index=" + index + ", header=" + header + ", transactionCounter=" + transactionCounter + ", transactions=" + transactions + '}';
    }

}
