# A simple implementation of blockchain

A decentralized blockchain build by using a p2p network and by using multithreading to demonstrate a client and server connection.


The blockchain consists of a chain of blocks, each block contains a header and data (transactions), a long side of a mempool to store all the unverified transactions.

## Header

The structure of the header contiains: 

* Version

* Previous Block Hash

* Merkle Tree Root 

* Timestamp

* Difficulty

* Nonce 

## Block

The main structure of the block contiains: 

* Index

* Header

* Number of Transactions

* Transactions 

Other important variables to construct the block:
 
*  Merkle Tree

* The Block Hash

## Transaction 

The structure of a transaction contiains of:

* Data

* The Transaction Hash

## Mempool

Mempool is constructed of an ArrayDeque to store all the unverified transactions in the mempool to be stored in the blocks.

## Blockchain 

The blockchain consest of:

* A mempool to store the unverified transactions.

* A Chain to store the blocks.
