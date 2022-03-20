package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Block {

    //Create the merkle tree to store the hash transaction on it and then retrieve the merkle root
    private ArrayList<String> merkleTree;

    //The Block variables
    private int index;
    private Header header;
    private int transactionCounter;
    private List<Transaction> transactions;
    private String blockHash;

    public Block(int index, Header header, Set<Transaction> transactions) throws NoSuchAlgorithmException {
        this.index = index;
        this.transactions = new ArrayList<>(transactions);
        this.transactionCounter = this.transactions.size();
        this.header = header;
        setMerkleRoot();
    }

    public void MerkleTree() throws NoSuchAlgorithmException {
        merkleTree = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            merkleTree.add(transactions.get(i).getTransactionHash());
        }
        LinkedList<String> temp = new LinkedList<>(merkleTree);
        LinkedList<String> temp2 = new LinkedList<>();
        int i = 0;
        while (temp.size() != 1) {
            int tempSize = temp.size();
            String newHash = null;

            if (!temp.isEmpty()) {
                if ((i + 1) % 2 != 0 && i + 1 < tempSize) {
                    String newString = temp.get(i) + temp.get(i + 1);
                    newHash = calculateHash(newString);
                } else {
                    String newString = temp.get(i) + temp.get(i);
                    newHash = calculateHash(newString);
                }
            } else {
                break;
            }

            merkleTree.add(newHash);
            temp2.add(newHash);
            if (tempSize <= i + 2) {
                temp = (LinkedList<String>) temp2.clone();
                temp2.clear();
                i = 0;
            } else {
                i += 2;
            }
        }
    }

    public void setMerkleRoot() throws NoSuchAlgorithmException {
        MerkleTree();
        this.header.setMerkleRoot(merkleTree.get(merkleTree.size() - 1));
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
        return "\nBlock{\n" + "Index = " + index + ",\n" + header + ",\n Transaction Counter = " + transactionCounter + ",\n " + transactions + "}";
    }

}
