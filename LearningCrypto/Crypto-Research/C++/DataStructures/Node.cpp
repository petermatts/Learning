#include <iostream>
using namespace std;

typedef int T;
class Node {
    public:
        Node(T x) : val(x), Next(0) {}
        T value() const { return val; }
        Node* next() { return Next; }
        void link(Node *p) { Next = p; }
    private:
        T val;
        Node* Next;
};
