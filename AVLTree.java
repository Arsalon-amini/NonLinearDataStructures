package com.codeWithArsalon.NonLinearDS;

public class AVLTree {

    private class AVLNode {
        private int height;
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        public AVLNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value = " + value;
        }
    }

        AVLNode root;

        public void insert(int item){
            root = insert(root, item); //set root field from object returned
        }

        private AVLNode insert(AVLNode root, int value){
            if (root == null) //base condition
                return new AVLNode(value);

            if(value < root.value)
                root.leftChild = insert(root.leftChild, value);
            else
                root.rightChild = insert(root.rightChild, value);

            setHeight(root);

            return balance(root); //returns + sets new root
        }

        private AVLNode balance (AVLNode root){
            if(isLeftHeavy(root)) {
                if(balanceFactor(root.leftChild) < 0){
                   root.leftChild = rotateLeft(root.leftChild); //returns new leftChild node
                    return rotateRight(root); //return new root
                }
            }
            else if(isRightHeavy(root)) {
                if (balanceFactor(root.rightChild) > 0) {
                   root.rightChild = rotateRight(root.rightChild); //returns new rightChild node
                   return rotateLeft(root); //return new root
                }
            }
            return root; //tree is balanced
        }

        private AVLNode rotateRight(AVLNode root){
            var newRoot = root.leftChild;

            root.leftChild = newRoot.rightChild; // (left rotate)
            newRoot.rightChild = root; //(right rotate)

            setHeight(root); //update heights
            setHeight(newRoot);

            return newRoot;
        }
        private AVLNode rotateLeft(AVLNode root){
            var newRoot = root.rightChild;

            root.rightChild = newRoot.leftChild; //(right rotate)
            newRoot.leftChild = root; //(left rotate)

            setHeight(root); //update heights
            setHeight(newRoot);

            return newRoot;
        }

        private boolean isLeftHeavy(AVLNode node){
            return balanceFactor(node) > 1;
        }
        private boolean isRightHeavy(AVLNode node){
            return balanceFactor(node) < -1;
        }
        private int balanceFactor(AVLNode node){
            return (node == null) ? 0 : getHeight(node.leftChild) - getHeight(node.rightChild);
        }

        private void setHeight(AVLNode node){
            node.height = Math.max(
                    getHeight(root.leftChild),
                    getHeight(root.rightChild) + 1);

        }
        private int getHeight(AVLNode node){
            return (node == null) ? -1 : node.height;
        }

    }
