package com.codeWithArsalon.NonLinearDS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    public static int ALPHABET_SIZE = 26;

    private class TrieNode {
        private char value;
        private HashMap<Character, TrieNode> children = new HashMap<>(); //keys chars, values TrieNodes
        private boolean isEndOfWord;

        private TrieNode(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Value=" + value;
        }
        public boolean hasChild(char ch){
            return children.containsKey(ch);
        }
        public void addChild(char ch){
            children.put(ch, new TrieNode(ch));
        }
        public TrieNode getChild (char ch){
            return children.get(ch);
        }
        public TrieNode[] getChildren(){
            return children.values().toArray(new TrieNode[0]);//returns array [] of children TrieNodes
        }
        public boolean hasChildren(){
            return !children.isEmpty();
        }
        public void removeChild(char ch){
            children.remove(ch);
        }
    }

    private TrieNode root = new TrieNode(' '); //blank root

    public void insert(String word){
        var current = root;

        for(var ch : word.toCharArray()){
            if(!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }

    public boolean contains(String word){
        if (word == null) //defensive programming, nullPointerException guarding
            return false;

        var current = root;
        for(var ch: word.toCharArray()) {
            if (!current.hasChild(ch))//check if node has child, if not return false immediately
                return false;
            current = current.getChild(ch);
        }
        return current.isEndOfWord; //points to last character of word
        }

    public void traverse(){
        traverse(root);
    }

    private void traverse(TrieNode root){
        for(var child : root.getChildren())
            traverse(child);

        System.out.println(root.value); //post order traversal
    }

    public void remove(String word){
        if (word == null)
            return;

        remove(root, word, 0);
    }

    private void remove(TrieNode root, String word, int index){
        if(index == word.length()) { //base condition
            root.isEndOfWord = false; //remove endOfWord marker
            return;
        }

        var ch = word.charAt(index); //returns char in word at index
        var child = root.getChild(ch); //get children
        if(child == null) //defensive programming
            return;

        remove(child, word, index + 1);

        if(!child.hasChildren() && !child.isEndOfWord)
            root.removeChild(ch);
    }

    public List<String> findWords(String prefix){
        List<String> words = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);

        findWords(lastNode, prefix, words);

        return words;
    }

    private void findWords(TrieNode root, String prefix, List<String> words){
        if(root == null)
            return;

        if(root.isEndOfWord)
            words.add(prefix);

        for(var child : root.getChildren())
            findWords(child, prefix + child.value, words);
    }

    private TrieNode findLastNodeOf(String prefix){
        if (prefix == null)
            return null;

        var current = root;
        for(var ch: prefix.toCharArray()){
            var child = current.getChild(ch);
            if(child == null)
                return null;
            current = child;
        }
        return current;
    }
}



