#include <iostream>
#include <string>
#include <list>
#include <bitset>
#include "SHA256.h"
#include "BaseConversion.cpp"
using namespace std;

class SHA256 {
	public:
		string hash(string data); //change to string return type
};

string SHA256::hash(string data) {
	int len = data.size();
	list<uint> chars = {}; //? not entirely necessary

	for(int i = 0; i < len; i++)
		chars.push_back((uint) data[i]);

	list<uint> :: iterator thing;
	list<string> messageBlocks = {};

	string block = "";
	for(thing = chars.begin(); thing != chars.end(); thing++) {
		block += bitset<8>(*thing).to_string();
	}

	if(block.size() == 512) {
		messageBlocks.push_back(block);
		block = "";
	}

	if(!block.empty())
		messageBlocks.push_back(block);

	string last = "";
	if(!messageBlocks.empty()) {
		last = messageBlocks.back();
		messageBlocks.pop_back();
	}
	last += "1";
	//pad with trailing zeroes until last is of length divisble by 512, but stop 64 spots shy to insert strBE (which always has length 64)
	while((last.size()+64)%512 != 0)
		last += "0";

	string BE = bitset<64>(data.size()*8).to_string();
	last += BE;

	//reinsert into messageBlocks
	if(last.size() <= 512) {
		messageBlocks.push_back(last);
	} else {
		for(int lower = 0; lower < last.size(); lower+=512) {
			int upper = lower + 512;
			if(upper < last.size()) {
				messageBlocks.push_back(last.substr(lower));
			} else {
				messageBlocks.push_back(last.substr(lower, upper));
			}
		}
	}

	// cout << messageBlocks.back();

	/* setup complete */

	unsigned long K[k_size] = {};
	for(int i = 0; i < k_size; i++) {
		K[i] = (unsigned long) k[i];
		// cout << K[i] << " ";
	}

	unsigned long H[h_size] = {};
	for(int i = 0; i < h_size; i++) {
		H[i] = (unsigned long) h[i];
	}

	string w[64] = {};
	while(!messageBlocks.empty()) {
		string word = messageBlocks.front();
		messageBlocks.pop_front();

		w[0] = word.substr(0, 32);
		w[1] = word.substr(32, 32);
		w[2] = word.substr(64, 32);
		w[3] = word.substr(96, 32);
		w[4] = word.substr(128, 32);
		w[5] = word.substr(160, 32);
		w[6] = word.substr(192, 32);
		w[7] = word.substr(224, 32);
		w[8] = word.substr(256, 32);
		w[9] = word.substr(288, 32);
		w[10] = word.substr(320, 32);
		w[11] = word.substr(352, 32);
		w[12] = word.substr(384, 32);
		w[13] = word.substr(416, 32);
		w[14] = word.substr(448, 32);
		w[15] = word.substr(480, 32); // word.substring(480); works too
	
		unsigned long long mod = (unsigned long long)1 << 32; 

		// For w[16 ... 63]
		// w[i] = w[i-16] + sigma0(w[i-15]) + w[i-7] + sigma1(w[i-2])
		for(int i = 16; i < 64; i++) {		
			unsigned long l1 = std::stoul(w[i-16], nullptr, 2);
			unsigned long l2 = std::stoul(w[i-15], nullptr, 2);
			unsigned long l3 = std::stoul(w[i-7], nullptr, 2);
			unsigned long l4 = std::stoul(w[i-2], nullptr, 2);

			unsigned long long sum = (l1 + sigma0(l2) + l3 + sigma1(l4)) % mod;

			w[i] = bitset<32>(sum).to_string();
		}

		// print out message schedule
		// for(int i = 0; i < 64; i++) {
		// 	cout << w[i] << endl;
		// }
		// cout << endl;

		unsigned long a = H[0];
		unsigned long b = H[1];
		unsigned long c = H[2];
		unsigned long d = H[3];
		unsigned long e = H[4];
		unsigned long f = H[5];
		unsigned long g = H[6];
		unsigned long h = H[7];

		// cout << a << endl;
		// cout << b << endl;
		// cout << c << endl;
		// cout << d << endl;
		// cout << e << endl;
		// cout << f << endl;
		// cout << g << endl;
		// cout << h << endl;

		/* Compression Loop */
		for(int i = 0; i < 64; i++) {
			unsigned long sum1 = (h + SIGMA1(e) + CHOICE(e, f, g) + K[i] + std::stoul(w[i], nullptr, 2)) % mod;
			unsigned long sum2 = (SIGMA0(a) + MAJORITY(a, b, c)) % mod;
			
			string temp1 = bitset<32>(sum1).to_string();
			string temp2 = bitset<32>(sum2).to_string();

			// if(i==0) {
			// 	cout << "temp1:" << endl << "01011011110111010101100111010100\tExpected" << endl << temp1 + "\tActual" << endl << endl;
			// 	cout << "temp2:" << endl << "00001000100100001001101011100101\tExpected" << endl << temp2 + "\tActual" << endl << endl;
			// }

			//nitty gritty, shifting variables
			h = g;
			g = f;
			f = e;
			e = (d + std::stoul(temp1, nullptr, 2)) % mod;
			d = c;
			c = b;
			b = a;
			a = (std::stoul(temp1, nullptr, 2) + std::stoul(temp2, nullptr, 2)) % mod;

			// if(i==0){
			// 	cout << "a " << bitset<32>(a).to_string() << endl;
			// 	cout << "b " << bitset<32>(b).to_string() << endl;
			// 	cout << "c " << bitset<32>(c).to_string() << endl;
			// 	cout << "d " << bitset<32>(d).to_string() << endl;
			// 	cout << "e " << bitset<32>(e).to_string() << endl;
			// 	cout << "f " << bitset<32>(f).to_string() << endl;
			// 	cout << "g " << bitset<32>(g).to_string() << endl;
			// 	cout << "h " << bitset<32>(h).to_string() << endl;
			// }
		}

		H[0] = (H[0] + a) % mod;
		H[1] = (H[1] + b) % mod;
		H[2] = (H[2] + c) % mod;
		H[3] = (H[3] + d) % mod;
		H[4] = (H[4] + e) % mod;
		H[5] = (H[5] + f) % mod;
		H[6] = (H[6] + g) % mod;
		H[7] = (H[7] + h) % mod;
	}

	string b1 = bitset<32>(H[0]).to_string();
	string b2 = bitset<32>(H[1]).to_string();
	string b3 = bitset<32>(H[2]).to_string();
	string b4 = bitset<32>(H[3]).to_string();
	string b5 = bitset<32>(H[4]).to_string();
	string b6 = bitset<32>(H[5]).to_string();
	string b7 = bitset<32>(H[6]).to_string();
	string b8 = bitset<32>(H[7]).to_string();

	string fin_bin = b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8;
	string hex = binToHex(fin_bin);

	//the following couts are equal if data = "hello world"
	// cout << hex + "\tActual" << endl << "B94D27B9934D3E08A52E52D7DA7DABFAC484EFE37A5380EE9088F7ACE2EFCDE9\tExpected" << endl;
	// cout << hex.size() << endl;
	return hex;
}

int main() {
	SHA256 hasher;
	string toBeHashed = "";
	string hash = hasher.hash(toBeHashed);
	cout << hash << endl;

	return 0;
}
