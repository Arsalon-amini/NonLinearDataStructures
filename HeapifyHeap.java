package com.codeWithArsalon.NonLinearDS;

public class HeapifyHeap {
    public static void heapify(int [] array) {
        var lastParentIndex = array.length / 2 - 1; //lastParentIndex in Heap (last parent layer) = (n / 2) - 1
        for (var i = lastParentIndex; i >= 0; i--)
            heapify(array, i);
    }

    private static void heapify(int [] array, int index){
        var largerIndex = index; //initial assumption (current node is largest)

        var leftChildIndex = index * 2 + 1;
        if(leftChildIndex < array.length &&
                array[leftChildIndex] > array[largerIndex]) //validating assumption
            largerIndex = leftChildIndex; //reset largerIndex

        var rightChildIndex = index * 2 + 2;
        if(rightChildIndex < array.length &&
                array[rightChildIndex] > array[largerIndex]) //validating assumption
            largerIndex = rightChildIndex; //reset largerIndex

        if(index == largerIndex) //item is in the right position //recursion terminates
            return;

        swap(array, index, largerIndex);
        heapify(array, largerIndex); //recursively go down tree

        }

    private static void swap(int [] array, int largerChildIndex, int parentIndex){
        var largerChild = array[largerChildIndex];
        array[largerChildIndex] = array[parentIndex]; //copy parent node into child node
        array[parentIndex] = largerChild; //swap child into parent

    }

    public static int getKthLargest(int [] array, int k){
        if(k < 1 || k > array.length)
            throw new IllegalArgumentException();

        var heap = new Heap();
        for (var number : array) //insert values into a heap
            heap.insert(number);
        for (var i = 0; i < k - 1; i++)
            heap.remove();

        return heap.max();

    }
}
