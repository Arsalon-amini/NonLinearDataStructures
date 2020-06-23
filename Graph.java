package com.codeWithArsalon.NonLinearDS;

import java.util.*;

public class Graph {
    //Big O analysis
    //removeNode = O(V * k) bcuz iterate all nodes (vertices) & iterate bucket searching by value (k)
    //addEdget = O(1) bcuz addFirst / addLast in LinkedList is O(1) operation
    //removeEdget = O(n) bcuz iterate LinkedList (bucket) to get/remove node.

    private class Node{
        private String label;

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private Map<String, Node> nodes = new HashMap<>(); //HashMap is implementation of Map interface
    private Map<Node, List<Node>> adjacencyList = new HashMap<>(); //each element has a list of neighbors

    public void addNode(String label){
        var node = new Node(label); //wrap label in Node Obj
        nodes.putIfAbsent(label, node);
        adjacencyList.putIfAbsent(node, new ArrayList<>()); //initialize empty arrayList in each node
    }

    public void addEdge(String from, String to){
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException(); //not a valid node

        var toNode = nodes.get(to);
        if(toNode == null)
            throw new IllegalArgumentException();

        adjacencyList.get(fromNode).add(toNode); //O(1) operation addFirst/addLast LinkedList
    }

    public void removeNode(String label){
        var node = nodes.get(label);
        if(node == null)
            return;

        for(var n : adjacencyList.keySet()) //O(v) operation iterate all nodes in hashmap (graph)
            adjacencyList.get(n).remove(node); //O(K) operation remove from LinkedList (bucket)

        adjacencyList.remove(node);
        nodes.remove(node);
    }

    public void removeEdge(String from, String to){
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);

        if(fromNode == null || toNode == null)
            return;

        adjacencyList.get(fromNode).remove(toNode); //O(k) operation iterate bucket to remove node
    }

    public void traverseDepthFirst(String root){
        var node = nodes.get(root);
        if(node == null)
            return;

        Set<Node> visited = new HashSet<>();//keep track of Nodes visited
        Stack<Node> stack = new Stack<>();//push neighbors
        stack.push(node);

        while(!stack.isEmpty()){
            var current = stack.pop();

            if(visited.contains(current)) //pop w/o executing below logic
                continue;

            System.out.println(current); //visit node
            visited.add(current); //add node to visited list (set)

            for(var neighbor : adjacencyList.get(current)) //O(k) operation!
                if (!visited.contains(neighbor))
                    stack.push(neighbor);
        }

    }

    public void traverseDepthFirstRecursive(String root){
        var node = nodes.get(root);
        if (node == null)
            return;

        traverseDepthFirstRecursive(nodes.get(root), new HashSet<>());
    }

    private void traverseDepthFirstRecursive(Node root, Set<Node> visited){
        System.out.println(root);
        visited.add(root); //add root to set (keep track of visited nodes)

        for(var node : adjacencyList.get(root))//O(k)
            if(!visited.contains(node))
                traverseDepthFirstRecursive(node, visited);
    }

    public void traverseBreadthFirst(String root){
        var node = nodes.get(root);
        if(node == null)
            return;

        Set<Node> visited = new HashSet<>();

        Queue<Node> queue = new ArrayDeque<>(); //queue using an array
        queue.add(node);

        while(!queue.isEmpty()){
            var current = queue.remove();

            if(visited.contains(current))
                continue;

            System.out.println(current);
            visited.add(current);

            for (var neighbor : adjacencyList.get(current))//iterate the bucket = look at LinkedList nodes (get) neighbors
                if(!visited.contains(neighbor))
                    queue.add(neighbor);
        }
    }

    public List<String> topologicalSort(){
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for(var node : nodes.values()) // (key = string, value = node), returns nodes
            topologicalSort(node, visited, stack); //call topologicalsort on each node

        List<String> sorted = new ArrayList<>(); //all items are in a stack, reverse order, pop + list
        while (!stack.empty())
            sorted.add(stack.pop().label);//pop label field in each node

        return sorted; //return sorted list
    }

    public void topologicalSort(Node node, Set<Node> visited, Stack<Node> stack){
        if(visited.contains(node))
            return;

        visited.add(node);

        for(var neighbor : adjacencyList.get(node))
            topologicalSort(neighbor, visited, stack); //recursive visit neighbors of node

        stack.push(node); //once visited all children nodes, add to stack first, pop last items in list

    }

    public boolean hasCycle() {
        Set<Node> all = new HashSet<>();
        all.addAll(nodes.values()); // adds all nodes (collection) to a set

        Set<Node> visiting = new HashSet<>();
        Set<Node> visited = new HashSet<>();

        while (!all.isEmpty()) {
            var current = all.iterator().next(); //all collections have method, pick obj from first set
            if (hasCycle(current, all, visiting, visited))
                return true;
        }

        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all, Set<Node> visiting, Set<Node> visited){
        all.remove(node); //move from first set
        visiting.add(node); //add to visiting set

        for(var neighbor : adjacencyList.get(node)){ //visit all neighbors of node to check for cycle
            if(visited.contains(neighbor)) //if visited, not a cycle, continue to look at next neighbor
                continue;

            if(visiting.contains(neighbor))//if it's in visiting, there is a cycle!
                return true;

            if(hasCycle(neighbor, all, visiting, visited)) //recursively call method on neighbors
                return true; //if true, return true
        }

        visiting.remove(node); //haven't found cycle in current node
        visited.add(node);

        return false; //haven't found a cycle
    }

    public void print(){
        for(var source : adjacencyList.keySet()) {//returns all source nodes
            var targets = adjacencyList.get(source); //get adjacency nodes
            if(!targets.isEmpty())
                System.out.println(source + " is connected to " + targets);
        }

    }

}
