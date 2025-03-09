#!/bin/bash

encode() {
	echo "compressing $1"
	java MoveToFront - < "$1" | java BurrowsWheeler - | java Huffman - > "$1.enc"
	echo "finished"
}

encode_algs4() {
	echo "compressing $1"
	java-algs4 MoveToFront - < "$1" | java-algs4 BurrowsWheeler - | java-algs4 Huffman - > "$1.enc"
	echo "finished"
}
decode() {
	echo "decompressing $1"
	java Huffman +  < "$1" | java BurrowsWheeler + | java MoveToFront + > "$1.dec"
	echo "finished"
}
decode_algs4() {
	echo "decompressing $1"
	java-algs4 Huffman +  < "$1" | java-algs4 BurrowsWheeler + | java-algs4 MoveToFront + > "$1.dec"
	echo "finished"
}
