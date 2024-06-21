package MerkleTree;

import java.security.NoSuchAlgorithmException;

/**
 * Representation of a Merkle Tree (Hash Tree)
 * Works by...
 */
public abstract class Merkle<T> {
    /**
     * Gets the roothash of the tree
     * @return String roothash
     */
    abstract public String getRootHash();

    /**
     * @return size of the tree
     */
    abstract public int size();

    /**
     * @return true if the tree is empty, otherwise false
     */
    abstract public boolean isEmpty();

    /**
     * @return true if the tree is full, otherwise false
     */
    abstract public boolean isFull();

    /**
     * Adds an element to the tree and then recalculates all hash data
     * !WARNING no remove method exists
     * @return true iff node was successfully added to the tree, otherwise false
     * @throws Exception
     * @throws NoSuchAlgorithmException
     */
    abstract public boolean add(MerkleNode node) throws NoSuchAlgorithmException, Exception;

    /**
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * 
     */
    abstract protected void calculate() throws NoSuchAlgorithmException, Exception;
}
