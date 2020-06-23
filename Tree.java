package com.codeWithArsalon.NonLinearDS;

import java.util.ArrayList;

public class Tree {

    private class Node{
        private int value;
        private Node leftChild;
        private Node rightChild;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node=" + value;
        }
    }

    private Node root;

    public void addItem(int value){
        var node = new Node(value);

        if(root == null){
            root = node;
            return;
        }

        var current = root;
        while(true){
            if(value < current.value){
                if(current.leftChild == null){ //leaf node
                    current.leftChild = node;
                    break;
                }
                current = current.leftChild; //else, continue to next left child
            }
            else{
                if(current.rightChild == null){ //leaf node
                    current.rightChild = node;
                    break;
                }
                current = current.rightChild; //else, continue to next right child
            }
        }
    }

    public boolean lookup(int value){
        var current = root;
        while (current != null){
            if(value < current.value) //left subtree
                current = current.leftChild;
            else if (value > current.value) //right tree
                current = current.rightChild;
            else
                return true;
        }
        return false; //if node isn't in tree, current will become null eventually and break out of loop

    }

    public void traversePreOrder(){
        traversePreOrder(root);// kick off recursion
    }

    private void traversePreOrder(Node root){
        if(root == null) //base condition
            return;
        System.out.println(root.value);
        traversePreOrder(root.leftChild);
        traversePreOrder(root.rightChild);

    }

    public void traversePostOrder(){
        traversePostOrder(root); //kick off recursion
    }

    private void traversePostOrder(Node root){
        if(root == null) //base condition
            return;
        traversePostOrder(root.leftChild);
        traversePostOrder(root.rightChild);
        System.out.println(root.value);
    }

    public void traverseInOrder(){
        traverseInOrder(root); //kick off recursion
    }

    private void traverseInOrder(Node root){
        if(root == null) //base condition
            return;
        traversePreOrder(root.leftChild);
        System.out.println(root.value);
        traversePreOrder(root.rightChild);
    }

    public void traverseLevelOrder(){
        for(var i = 0; i <= height(); i++) { //longest path to root node
            for (var value : getNodesAtDistance(i)) //returns an ArrayList of nodes at index i
                System.out.println(value);
        }
    }

    public ArrayList<Integer> getNodesAtDistance(int distance){
        var list = new ArrayList<Integer>(); //stores values in array list
        getNodesAtDistance(root, distance, list);
        return list;
    }
    private void getNodesAtDistance(Node root, int distance, ArrayList list){
        if (root == null) //if tree is empty, return
            return;

        if(distance == 0) { //base condition, terminate recursion, print value
            list.add(root.value); //add value to list
            return;
        }
        getNodesAtDistance(root.leftChild, distance - 1, list); //look at right & left children, decrement distance by 1
        getNodesAtDistance(root.rightChild, distance - 1, list);
    }

    public int height(){
        return height(root);
    }

    private int height(Node root){
        if(root == null)
            return -1;
        if(isLeafNode(root)) //base condition
            return 0; //height of leaf nodes is zero

        var count = 1 + Math.max(
                height(root.leftChild), //breaking problem into smaller parts
                height(root.rightChild));
        return count;
    }

    public boolean isBinarySearchTree(){
        return isBinarySearchTree(root,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
    }

    private boolean isBinarySearchTree(Node root, int min, int max){
        if (root == null) //empty tree is a binary tree
            return true;

        if(isLeafNode(root))
            return true;

        if(root.value < min || root.value > max) //means node is out of range
            return false;

        return
                isBinarySearchTree(root.leftChild, min, root.value - 1) //tells is left child is in right range
                && isBinarySearchTree(root.rightChild, root.value + 1, max); //tells is right child is in right range
    }

    public boolean equals(Tree other){

        return equals(root, other.root); //pass root of current, root of other tree
    }

    private boolean equals(Node first, Node second){
        if(first == null && second == null) //both nodes are null (scenario #1)
            return true;

        if(first != null && second != null) //both nodes are not null (scenario #2)
            return (first.value == second.value) //check root for equality
                    && equals(first.leftChild, second.leftChild) //check left subtree for equality
                    && equals(first.rightChild, second.rightChild); //check right subtree for equality

        return false; //one node is null, other is not (scenario #3)
    }

    public int minBinaryTree(){
        //O (log n) time complexity
        if(root == null)
            throw new IllegalStateException(); //empty tree

        var current = root;
        var last = current;
        while(current != null){
            last = current; //stores last value
            current = current.leftChild;
        }
        return last.value; //references leaf-most node
    }

    public int min(){
        return min(root); //non binary search
    }

    private int min(Node root){
        //O(n) complexity
        if(isLeafNode(root)) //base condition
            return root.value;

        var left = min(root.leftChild); //min in left tree
        var right = min(root.rightChild); //min in right

        return Math.min(Math.min(left, right), root.value); //find min of three values (left, right, root)
    }

    private boolean isLeafNode(Node root) {
        return root.leftChild == null & root.rightChild == null;
    }

}
