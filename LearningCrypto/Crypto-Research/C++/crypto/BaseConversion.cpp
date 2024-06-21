#include <string>

using namespace std;

string binToHex(string bin) {
    const int len = bin.size();
    string code = "";

    if(len%4 == 1) {
        code = "000" + bin;
    } else if (len%4 == 2) {
        code = "00" + bin;
    } else if (len%4 == 3) {
        code = "0" + bin;
    } else {
        code = bin;
    }

    string hex = "";
    for(int i = 0; i < code.size(); i+=4) {
        int sub = std::stoi(code.substr(i, 4));
        switch(sub) {
            case 0:
                hex += "0";
                break;
            case 1:
                hex += "1";
                break;
            case 10:
                hex += "2";
                break;
            case 11:
                hex += "3";
                break;
            case 100:
                hex += "4";
                break;
            case 101:
                hex += "5";
                break;
            case 110:
                hex += "6";
                break;
            case 111:
                hex += "7";
                break;
            case 1000:
                hex += "8";
                break;
            case 1001:
                hex += "9";
                break;
            case 1010:
                hex += "A";
                break;
            case 1011:
                hex += "B";
                break;
            case 1100:
                hex += "C";
                break;
            case 1101:
                hex += "D";
                break;
            case 1110:
                hex += "E";
                break;
            case 1111:
                hex += "F";
                break;
        }
    }
    return hex;
}