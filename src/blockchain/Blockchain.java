package blockchain;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class Blockchain {

    private Mempool mempool = Mempool.getInstance();
    private static List<Block> chain = new LinkedList<>();
    private int difficulty = 2;

    public Blockchain() throws NoSuchAlgorithmException, IOException {
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
        print("Block added to chain = " + this.chain.add(block));
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

    public void print(String string) {
        System.out.println(string);
    }

    //Mining a new block
    public boolean mineBlock() throws NoSuchAlgorithmException, IOException {
        if ((mempool.getUnconfirmedTransactions().isEmpty() && chain.isEmpty()) || mempool.getUnconfirmedTransactions().isEmpty()) {
            return false;
        }
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
        writeToFile(block);
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
    private void createGenesisBlock() throws NoSuchAlgorithmException, IOException {
        addTransaction(new Transaction("Send 100 MAS coin to Mahmoud Abusaqer"));
        mineBlock();
    }
    
    //Write the new generated block in a file for Central Blockchain Development
    private void writeToFile(Block block) throws FileNotFoundException, IOException{
        File file = new File("Blockchain.txt");
        FileOutputStream fos = new FileOutputStream(file, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        PrintWriter pr = new PrintWriter(osw);
        pr.println(block);
        osw.close();
        pr.flush();
        pr.close();
    }
    
    public static void connectToClient() {
        String msg;
        Scanner inputStream = null;
        PrintWriter outputStream = null;
        ServerSocket serverSocket = null;
        try {
            // Wait for connection on port 6789
            System.out.println("Server: Waiting for a connection.");
            serverSocket = new ServerSocket(6789);
            if (serverSocket.isBound()) {
                System.out.println("Server: *Server is up*");
            }
            Socket socket = serverSocket.accept();

            while (true) {
                // Connection made, set up streams
                inputStream = new Scanner(new InputStreamReader(socket.getInputStream()));
                outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
                // Read a line from the client
                msg = inputStream.next();
//                System.out.println("Server: msg = " + msg);
                // Output text to the client
                switch (msg) {
                    case "version":
                        // send a verack msg to the client
                        System.out.println("version");
                        break;
                    case "getaddr":
                        // send all the nodes addresses to the client
                        System.out.println("getaddr");
                        break;
                    case "addr":
                        // store the address of the client to the nodes list
                        System.out.println("addr");
                        System.out.println("Client Address: " + socket.getLocalSocketAddress());
                        break;
                    case "getblocks":
                        // send all the blocks to the client
                        System.out.println("getblocks");
                        outputStream.println(chain);
                        break;
                    case "getheders":
                        // send all the headers to the client
                        System.out.println("getheders");
                        break;
                    case "exit":
                        // send all the headers to the client
                        System.out.println("exit");
                        break;
                    default:
                        // code block
                        System.out.println("default");
                }
                outputStream.println("end");
                outputStream.flush();
            }

        } catch (Exception e) {
            // If any exception occurs, display it
            System.out.println("Server: Error" + e);

        }
        inputStream.close();
        outputStream.close();
    }

    public static void connectToServer() {
        String msg;
        Scanner inputStream = null;
        PrintWriter outputStream = null;
        try {
            Socket clientSocket = new Socket("localhost", 6789);
            if (clientSocket.isBound()) {
                System.out.println("Client: *Connected succeed*");
            }
            Scanner userInput = new Scanner(System.in);
            String input;
            while (true) {

                // Set up streams to send/receive data
                inputStream = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                outputStream = new PrintWriter(new DataOutputStream(clientSocket.getOutputStream()));

                // Send message to the server
                System.out.print("Client: ");
                input = userInput.nextLine();
                if (input.equals("exit")) {
                    System.out.println("Client: **scoket closed**");
                    clientSocket.close();
                    userInput.close();
                }
//                System.out.println("Client: input = " + input);
                outputStream.println(input);
                outputStream.flush(); // Sends data to the stream
                // Read everything from the server until no
                // more lines and output it to the screen
                while (inputStream.hasNextLine()) {
                    msg = inputStream.nextLine();
                    if (msg.equals("end")) {
                        break;
                    }

                    System.out.println(msg);
                }
            }

        } catch (Exception e) {
            // If any exception occurs, display it
            System.out.println("Client: Error " + e);
        }
        inputStream.close();
        outputStream.close();
    }
}
