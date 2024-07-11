#include <iostream>
#include "Block.cpp"
// #include "GenisisBlock.cpp"

using namespace std;

class Blockchain {
    public:
        Blockchain() : size(0), current(new GenisisBlock()) {}
        long int getSize() { return size; }
        void printHash() { cout << current->getHash() << endl; }
        string getCurrentHash() { return current->getHash(); }
        bool addBlock(Block* candidate);

    private:
        Block* current;
        long int size;
};

bool Blockchain::addBlock(Block* candidate) {
    // const long int i_size = size;

    //if candidate does not have a next assign it to the current current block

    //make candidate the new current block
    current = candidate;
    size++;
    return true;
}

//! for some reason this constructor type was not working for me, could be a compiler issue
// Blockchain::Blockchain() {
//     size = 0;
//     Block c("genisis");
//     current = &c;
// }

int main() {
    Blockchain bc;
    bc.printHash();
    // cout << bc.getSize() << endl;
}
