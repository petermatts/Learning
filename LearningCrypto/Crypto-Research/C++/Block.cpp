#include <iostream>
#include <time.h>
#include <string>

// #include "crypto/SHA256.cpp"

using namespace std;

class Block {
    public:
        Block(string hash);
        // void printHash() { cout << HASH << endl; }
        string getHash() const { return HASH; }
        time_t getTimestamp() const { return timestamp; }

    private:
        string HASH;
        time_t timestamp;
};

Block::Block(string hash) {
    HASH = hash;
    timestamp = time(&timestamp);
}

// int main() {
//     Block b("abcd");
//     // b.printHash();
//     // cout << b.getHash();
//     cout << b.getTimestamp() << endl;
// }

class GenisisBlock : public Block {
    public:
        explicit GenisisBlock() : Block("A8380A63AD8C6D0783F65BD68D66E4C08564620924570606019997BE610EBBF3") {}
};
