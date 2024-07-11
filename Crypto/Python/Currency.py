#example

import json
import hashlib
import time #?
from PySupport.Crypto.PublicKey import RSA
from PySupport.Crypto.Signature import *
from urllib.parse import urlparse
from PySupport.requests import requests

class Blockchain(object):
    def __init__(self):
        self.chain = [] #Blockchain list of blocks
        self.pendingTransactions = []
        self.difficulty = 4
        self.minerRewards = 50 #oooo
        self.blockSize = 10 #???

    def getLastBlock(self):
        return self.chain[-1]

    def addBlock(self, newBlock):
        if(len(self.chain) > 0):
            newBlock.prev = self.getLastBlock().hash
        else:
            newBlock.prev = 'none'
        self.chain.append(newBlock)

    def addTransaction(self, sender, reciever, amount, keyString, senderKey):
        keyByte = keyString.encode("ASCII")
        senderKeyByte = senderKey.encode("ASCII")
        #print(type(keyByte), keyByte)
        key = RSA.import_key(keyByte)
        senderKey = RSA.import_key(senderKeyByte)

        if not sender or not reciever or not amount:
            print('transaction error')
            return False

        transaction = Transaction(sender, reciever, amount)
        transaction.signTransaction(key, senderKey)

        if not transaction.isValidTransaction():
            print('transaction error')
            return False

        self.pendingTransactions.append(transaction)
        return len(self.chain) + 1

    def generateKeys(self):
        key = RSA.generate(2048)
        private_key = key.export_key()
        file_out = open("private.pem", "wb")
        file_out.write(private_key)
        public_key = key.publickey().export_key()
        file_out = open("reciever.pem", "wb")
        file_out.write(public_key)
        return key.publickey().export_key().decode('ASCII')

    def register_node(self, address):
        parsedUrl = urlparse(address)
        self.nodes.add(parsedUrl.netloc)

    def resolveConflicts(self):
        neighbors = self.nodes;
        newChain = None;
        maxLength = len(self.chain);
        for node in neighbors:
            response = requests.get(f'http://{node}/chain');
            if response.status_code == 200:
                length = response.json()['length'];
                chain = response.json()['chain'];
            if length > maxLength and self.isValidChain():
                    maxLength = length;
                    newChain = chain;
        if newChain:
            self.chain = self.chainJSONdecode(newChain);
            print(self.chain);
            return True;
        return False;
        


class Block(object):
    def __init__(self, transactions, time, index):
        self.index = index
        self.transactions = transactions
        self.time = time
        self.prev = ''
        self.hash = self.hash(); #Hash of this block

    def hash(self):
        hashTransactions = ''
        for transaction in self.transactions:
            hashTransactions += transaction.hash()

        hashString = str(self.time) + hashTransactions + self.prev + str(self.index) + str(self.nonse)
        hashEncoded = json.dumps(hashString, sort_keys=True).encode()
        return hashlib.sha256(hashEncoded).hexdigest()

    def mineBlock(self, difficulty):
        arr = []
        for i in range(0, difficulty):
            arr.append(i+1)
        #compute until the beginning of the hash = 0,1,2,3..difficulty

        arrStr = map(str, arr)
        hashPuzzle = ''.join(arrStr)
        #print(len(hashPuzzle))
        while self.hash[0:difficulty] != hashPuzzle:
            self.nonse += 1
            self.hash = self.hash()            
        return True

class Transaction(object):
    def __init__(self, sender, reciever, amount):
        self.sender = sender
        self.reciever = reciever
        self.amount = amount
        self.time = time()
        self.hash = self.hash()

    def hash(self):
        hashString = self.sender + self.reciever +str(self.amount) + str(self.time)
        hashEncoded = json.dumps(hashString, sort_keys=True)
        return hashlib.sha256(hashEncoded).hexdigest()

    def signTransaction(self, key, senderKey):
        if(self.hash != self.hash()):
            print("transaction tampered error");
            return False;
        if(str(key.publickey().export_key()) != str(senderKey.publickey().export_key())):
            print("Transaction attempt to be signed from another wallet");
            return False;
        self.signature = "made";
        #print(key.sign(self.hash, ""));
        print("Made Signature!");
        return True;

    def isValidTransaction(self):
        if(self.hash != self.hash()):
            return False;
        if(self.sender == self.reciever):
            return False;
        if(self.sender == "Miner Rewards"):
            return True;
        if not self.signature or len(self.signature) == 0:
            print("No Signature!")
            return False;
        return True;