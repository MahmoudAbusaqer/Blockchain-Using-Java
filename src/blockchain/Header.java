package blockchain;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Header {

    //Block Header
    private int version;
    private String previousHash;
    private String merkleRoot = null;
    private Long timestamp;
    private int difficulty;
    private int nonce = 0;

    public Header(int version, String previousHash, int difficulty) {
        this.version = version;
        this.previousHash = previousHash;//need to be the last block hash
        this.timestamp = System.currentTimeMillis();
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Header{\n\t" + " Version = " + version + ",\n\t Previous Hash = " + previousHash + ",\n\t Merkle Root = " + merkleRoot + ",\n\t Timestamp = " + timestamp + ",\n\t Difficulty = " + difficulty + ",\n\t Nonce = " + nonce + "\n}";
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

}
