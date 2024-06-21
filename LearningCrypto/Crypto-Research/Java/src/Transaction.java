import java.util.ArrayList;
import java.util.List;
import java.util.Date;
// import java.security.NoSuchAlgorithmException;

import crypto.SHA256;
import MerkleTree.MerkleNode;
import MerkleTree.MerkleTree;

public class Transaction extends MerkleNode {
    private double amount;
    private final long timestamp;
    // private String sender;
    // private String[] reciever;

    public Transaction(double amount) throws Exception {
        this.amount = amount;
        this.timestamp = new Date().getTime();
        this.hash = SHA256.hash(amount + Long.toString(timestamp));
    }

    public double getAmount() { return amount; }

    @Override
    public String hash() { return hash; }

    public String toString() { return Double.toString(amount); }
    
    public static void main(String[] args) throws Exception {
        List<MerkleNode> TXS = new ArrayList<MerkleNode>();
        Transaction tx = new Transaction(100.0);
        TXS.add(tx);

        //add 31 random transactions
        for(int i = 0; i < 31; i++)
            TXS.add(new Transaction(Math.random()*(101)));

        System.out.println(TXS.size()); //should be 32

        // for(Transaction t : TXS)
        //     System.out.println(t);

        MerkleTree<Transaction> mt = new MerkleTree<Transaction>(TXS);
        System.out.println(mt.getRootHash());
        System.out.println(mt.isFull());

        // System.out.println(tx.hash());
    }
}
