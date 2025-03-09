#include<iostream>
#include<vector>

using std::string;

template<typename Value>
class TST {
    public:
        /* REQURIED API*/
        /* create a symbol table */
        TST();
        ~TST();

        /* put key-value pair into the table
        (remove key if value is null )*/
        void put(string key, Value val);

        /* value paird with key
        (null if key is absent) */
        Value get(string key);

        /* remove key (and its value) */
        void del(string key);

        /*is there a value paird with key? */
        bool contains(string key);

        /* is the table empty */
        bool isEmpty();

        /* the longest key that is a prefix of s */
        string longestPrefixOf(string s);

        /* all the keys having s as a prefix*/ 
        std::vector<string> keysWithPrefix(string s);

        /* all the keys that match s
        (where . matches any character)*/
        std::vecotr<string> keysThatMatch(string s);

        /* number of key-value pairs*/
        int size();

        /* all the keys in the table*/
        std::vector<string> keys();

        // OTHER FOR TEST
        class Node {
            public: 
                Value val;
                Node* next;
                Node():next(new Node[R]){}
        }
    private:
        const int R = 256;
        Node* root;
        int sz;
        
};

#include "TST.cpp