package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Transaction {

    private String transaction;
    private String transactionHash;
    Mempool mempool = Mempool.getInstance();

    public Transaction(String transaction) throws NoSuchAlgorithmException {
        this.transaction = transaction;
        this.transactionHash = calculateHash(transaction);
        this.mempool.addToMempool(this);
    }

    public String getTransaction() {
        return transaction;
    }

    private String calculateHash(String transaction) throws NoSuchAlgorithmException {
        //Call the SHA-256 Algorithm from MessageDigest
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //Hash the transaction
        byte[] encodedhash = digest.digest(
                transaction.getBytes(StandardCharsets.UTF_8));
        //Change the array of byte into a string
        String transaction_Hash = DatatypeConverter.printHexBinary(encodedhash);
        return transaction_Hash;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transaction=" + transaction + ", transactionHash=" + transactionHash + '}';
    }

}
