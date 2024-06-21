import java.math.BigInteger;
// import java.security.NoSuchAlgorithmException;

public class BlockChain {
    private Block current;
    private BigInteger size;

    public BlockChain() throws Exception {
        current = Block.GenisisBlock();
        size = BigInteger.ONE;
    }

    public boolean addBlock(Block newBlock) {
        current = newBlock;
        size = size.add(BigInteger.ONE);
        return true;
    }

    public Block getLastBlock() { return current; }

    public String size() { return size.toString(); }

    public String getHeight() { return new BigInteger(size.toString()).subtract(BigInteger.ONE).toString(); }

    public String getNextHeight() { return new BigInteger(size.toString()).toString(); }

    public String list() {
        String str = current.getHeight() + "\t" + current.getHash();
        Block prev = current.getPrev();

        while(prev != null) {
            // str =  prev.getHeight() + "\t" + prev.getHash() + "\n^\n" + str;
            str =  prev.getHeight() + "\t" + prev.getHash() + "\n" + str;
            prev = prev.getPrev();
        }

        return str+"\n";
    }

    @Override
    public String toString() {
        String str = current.getHash();
        final String point = "  <--  ";
        Block prev = current.getPrev();

        while(prev != null) {
            str = prev.getHash() + point + str;
            prev= prev.getPrev();
        }

        return str+"\n";
    }

    public static void main(String[] args) throws Exception {
        BlockChain BC = new BlockChain();
        System.out.println(BC);

        Block b1 = new Block(BC.getLastBlock(), BC.getNextHeight());
        BC.addBlock(b1);

        //add 10 more blocks
        for(int i = 0; i < 10; i++) {
            BC.addBlock(new Block(BC.getLastBlock(), BC.getNextHeight()));
        }

        // System.out.println(BC);
        System.out.println(BC.list());
    }
}
