#include <iostream>
#include "Node.cpp"

using namespace std;

class Stack {
    public:
        Stack() : head(0), sp(false) {}
        void push(int x) {
            Node* p = new Node(x);
            if(head != 0)
                p->link(head);
            head = p;
        }
        int pop() {
            int r = head->value();
            Node *p = head;
            head = head->next();
            delete p;
            return r;
        }
        void printStack() {
            if(head == 0)
                cout << "Stack empty" << endl;
            else {
                cout << "(HEAD) " << head->value() << " ";
                Node* next = head->next();
                while(next != NULL) {
                    cout << next->value() << " ";
                    next = next->next();
                }
                cout << "(TAIL)";
            }
        }

    private:
        Node* head;
        int sp;
};

int main() {
    Stack s;
    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);
    cout << s.pop() << endl;
    s.printStack();
}
