package MerkleTree;

import crypto.SHA256;

// import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree<T> extends Merkle<T>{
    private String roothash;
    // private MerkleTreeNode<T> root;
    private List<MerkleNode> nodes;
    private int size;
    public static final int MAXSIZE = 64; //subject to change
    
    /**
     * No args constructor for the Merkle Tree
     */
    public MerkleTree() {
        this.nodes = new ArrayList<MerkleNode>();
    }

    /**
     * Constructor for merkle tree with initial value
     * @param nodes list of initial merkle node values
     * @throws Exception if nodes.size() exceeds MAXSIZE
     */
    public MerkleTree(List<MerkleNode> nodes) throws Exception {
        if(nodes.size() > MAXSIZE)
            throw new IllegalArgumentException("List too big");

        this.nodes = new ArrayList<MerkleNode>(nodes);
        size = nodes.size();
        calculate();
    }

    @Override
    protected void calculate() throws Exception {
        List<String> hashes = new ArrayList<String>();
        for(int i = 0; i < nodes.size(); i++)
            hashes.add(nodes.get(i).hash());

        while(hashes.size() > 1) {
            List<String> reduced = new ArrayList<String>();
            for(int i = 0; i < hashes.size(); i+=2) {
                if(i+1 < hashes.size()) {
                    String h1 = hashes.get(i);
                    String h2 = hashes.get(i+1);
                    reduced.add(SHA256.hash(h1 + h2));
                } else {
                    reduced.add(hashes.get(i));
                }
            }

            hashes = reduced;
        }

        roothash = hashes.remove(0);
    }

    @Override
    public boolean add(MerkleNode node) throws Exception {
        if(isFull())
            return false;

        nodes.add(node);
        size = nodes.size(); // should be same as size++
        calculate();
        return true;
    }

    @Override
    public String getRootHash() { return roothash; }

	@Override
	public int size() { return size; }

    @Override
    public boolean isEmpty() { return size==0; }

    @Override
    public boolean isFull() { return size>=MAXSIZE; }
}
