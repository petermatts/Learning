package MerkleTree;

public abstract class MerkleNode {
    protected String hash;

    /**
     * @return a SHA256 hash of the string representation of the data of the node
     */
    abstract public String hash();
}
