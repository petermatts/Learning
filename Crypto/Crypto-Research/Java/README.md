# My Exploration in Cryptography and Cryptocurrencies

This project/repository is the result of my research into Cryptography and Cryptocurrencies during the summer of 2021. As well as, my attempt to implment a basic form of how the work to help myself better understand the subject.

This includes but is not limited to Blocks and Blockchains, Merkle Trees (a.k.a Hash Trees), and cryptographic algorithms.java`

Which are very exciting and intfeature of the computer science world.

## Folder Structure

- `lib`: the folder to maintain dependencies (test resources)
- `src`: the folder to maintain sources
  - `crypto`: the folder to manage cryptographic algorithms (more on the way hopefully)
    - SHA256
  - `Merkle Tree`: my implementation of a merkle tree based on the properties and descriptions of such a tree that I have found onlined
    - `Merkle.java`: abstract class for `MerkleTree.java`
    - `MerkleNode.java`: abstract class for `MerkleTreeNode.java` to represent the data that the tree will use generally
    - `MerkleTree.java`: implementation of a merkle tree in java
  - `App.java`: default file that came with the package
  - `Block.java`: implementation of a blockchain block in java
  - `BlockChain.java`: implementation of a blockchain in java
  - `BlockHeader.java`: container for some instance data of a block from `Block.java`

For more information see the `README.md` files in each folder for more detail specific to the contents of the files with each folders. (These README files are coming soon).

### More information

Project by Matthew Peters an undergrad student @ UMASS Amherst

More detail and information in README files on the way
