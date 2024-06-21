import java.util.Date;

public class BlockHeader {
    // private final Integer version;
    private final String prevHash;
    private final Block prev;
    private final long timestamp; //must be unsigned
    //private final Integer targetDifficulty; //must be unsigned
    private final Integer nonce; //must be unsigned
    //transaction roothash

    public BlockHeader(Block prev, Integer nonce) {
        this.prev = prev;
        this.prevHash = prev.getHash();
        this.timestamp = new Date().getTime();

        // temp
        // version = 1;
        this.nonce = nonce;
    }

    protected String getPrevHash() { return prevHash; }

    protected Block getPrev() { return prev; }

    protected Integer getNonce() { return nonce; }

    protected String dataString() {
        return prevHash + Long.toString(timestamp);
    }
}
