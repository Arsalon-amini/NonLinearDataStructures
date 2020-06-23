package com.codeWithArsalon.NonLinearDS;

//insert - n log n because O(n) resizeIfrequired() * Log n bubble up (divide and conquer algorithm)

public class Heap {

    private int [] items = new int[10];
    private int size;

    public void insert(int value){
        if (isFull())
            throw new IllegalStateException();
        items[size++] = value; //store in next available slot
        bubbleUp();
    }

    public int remove (){
        if (isEmpty())
            throw new IllegalStateException();
        var root = items[0];
        items[0] = items[--size]; //swap last value w/ root (1st decrement size field, use as index of last item)

        bubbleDown();

        return root;
    }

    private void bubbleUp (){
        var index = size - 1; //index of last item
        while(index > 0 && items[index] > items[parentIndex(index)]) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    private void bubbleDown(){
        var index = 0;
        while (index <= size && !isValidParent(index)){
            var largerChildIndex = largerChildIndex(index);
            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private boolean isValidParent(int index) {
        if(!hasLeftChild(index)) //no leftChild, valid parent (greater than children)
            return true;

        var isValid = items[index] >= leftChild(index); // have left child, ensure meets heap property (root >= leftChild))

        if(hasRightChild(index))
            isValid &= items[index] >= rightChild(index); //have right child, ensure both meet heap (root >= rightChild)


        return isValid;
    }

    private int largerChildIndex(int index){
        if(!hasLeftChild(index)) //it doesn't have children
            return index;
        if(!hasRightChild(index)) //if no rightChild, return left index
            return leftChildIndex(index);

        return (leftChild(index) > rightChild(index)) //compare R & L children, return greatest
                    ? leftChildIndex(index) : rightChildIndex(index);
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    private int leftChildIndex(int index){
        return index * 2 + 1;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    private int rightChildIndex(int index){
        return index * 2 + 2;
    }

    private int rightChild(int index){
        return items[rightChildIndex(index)]; //right child of root index
    }

    private int leftChild(int index){
        return items[leftChildIndex(index)];//left child of index
    }

    private int parentIndex (int index){
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        var temp = items[first]; //copy first item into temp variable
        items[first] = items[second]; //copy second item into first item
        items[second] = temp; //copy temp into second position
    }

    public boolean isFull() {
        return size == items.length;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int max (){
        if(isEmpty())
            throw new IllegalStateException();
        return items[0];
    }

}
