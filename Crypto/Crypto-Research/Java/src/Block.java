import java.math.BigInteger;
// import java.security.NoSuchAlgorithmException;
import crypto.SHA256;

public class Block {
    /**
     * result of SHA256.hash("Genisis Hash");
     */
    // private static final String GenisisHash = "A8380A63AD8C6D0783F65BD68D66E4C08564620924570606019997BE610EBBF3";

    private BlockHeader header;
    private final String hash;
    private final String height;
    //Transaction merkle tree

    /**
     * Constructor for the Genisis Block ONLY!!
     * @throws Exception
     */
    private Block() throws Exception {
        this.header = null;
        // this.hash = GenisisHash;
        this.hash = "A8380A63AD8C6D0783F65BD68D66E4C08564620924570606019997BE610EBBF3";
        this.height = BigInteger.ZERO.toString();
    }

    /**
     * Constructor of Block, to be used from blockchain only
     * @param prev the previous block
     * @throws Exception
     */
    protected Block(Block prev, String height) throws Exception {
        this.header = new BlockHeader(prev, 0); //! change zero to a nonce value when implementing mining
        this.height = height;
        this.hash = this.hash();
    }

    protected String hash() throws Exception {
        return SHA256.hash(header.dataString());
    }

    protected String getHash() {
        return hash;
    }

    public String getHeight() {
        return height;
    }

    protected Block getPrev() {
        if(header==null)
            return null;

        return header.getPrev();
    }

    @Override
    public String toString() {
        return hash;
    }

    protected static Block GenisisBlock() throws Exception {
        return new Block();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Block.GenisisBlock());
    }
}
