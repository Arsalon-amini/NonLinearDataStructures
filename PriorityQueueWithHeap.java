package com.codeWithArsalon.NonLinearDS;

public class PriorityQueueWithHeap {
    private Heap heap = new Heap();

    public void enqueue(int item){
        heap.insert(item); //O (log n) using bubbleUp () to satisfy heap property
    }

    public int dequeue(){
        return heap.remove(); //O (log n) using bubbleDown () to satisfy heap property
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }
}
