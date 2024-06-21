// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "./ConvertLib.sol";

contract PyramidCoin {
    string public name = "Pyramid Coin";
    string public symbol = "PYRA";
    string public standard = "Pyramid Coin v1.0"; 
    uint public immutable totalSupply = 1000000;

    event Transfer(address from, address to, uint amount);
    event Approval(address tokenOwner, address spender, uint tokens);

    mapping (address => uint) balances;
    mapping (address => mapping(address => uint)) public allowance;

    constructor() {
        balances[tx.origin] = 10000;
    }

    function transfer(address reciever, uint amount) public returns(bool success) {
        if(balances[msg.sender] < amount) return false;
        balances[msg.sender] -= amount;
        balances[reciever] += amount;
        emit Transfer(msg.sender, reciever, amount);
        return true;
    }

    function transferFrom(address from, address to, uint amount) public returns(bool success) {
        require(amount <= balances[from]);
        require(amount <= allowance[from][msg.sender]);

        balances[from] -= amount;
        balances[to] += amount;

        allowance[from][msg.sender] -= amount;

        emit Transfer(from, to, amount);

        return true;
    }

    function approve(address spender, uint value) public returns (bool success){
        allowance[msg.sender][spender] = value;
        emit Approval(msg.sender, spender, value);
        return true;
    }

    // function allowance(address owner, address spender) public {
    //      //! needs completion
    // }

    function getBalanceInEth(address addr) public view returns(uint) {
        return ConvertLib.convert(balanceOf(addr),2);
    }

    function balanceOf(address addr) public view returns(uint) {
        return balances[addr];
    }
}
