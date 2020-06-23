package com.codeWithArsalon.NonLinearDS;

import java.util.*;

public class WeightedGraph {
    private class Node{
        private String label;
        private List<Edge> edges = new ArrayList<>(); //replace adjacency list, list of edge objects, could use HashMap

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public void addEdge(Node to, int weight){
            edges.add(new Edge(this, to, weight)); //object oriented approach
        }

        public List<Edge> getEdges(){
            return edges;
        }
    }

    private class Edge{
        private Node from;
        private Node to;
        private int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return from + "->" + to;
        }
    }

    private class NodeEntry{
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();

    public void addNode(String label){
        nodes.putIfAbsent(label, new Node(label)); //automatically initializes edges to a new ArrayList
    }

    public void addEdge(String from, String to, int weight){
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException(); //not a valid node

        var toNode = nodes.get(to);
        if(toNode == null)
            throw new IllegalArgumentException();

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

    public Path getShortestPath(String from, String to){
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);

        Map<Node, Integer> distances = new HashMap<>();
        for (var node : nodes.values())
            distances.put(node, Integer.MAX_VALUE); //set all values to max
        distances.replace(fromNode, 0); //returns current node object, sets 0 as distance value for current

        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();


        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(
                Comparator.comparingInt(ne -> ne.priority)); //anon func gets nodeEntry, returns priority, comparator compares priority
        queue.add(new NodeEntry(fromNode, 0));

        while(!queue.isEmpty()){
            var current = queue.remove().node; //returns nodeEntry obj, so access node field (we want node)
            visited.add(current);

            for(var edge : current.getEdges()){ //returns list of nodeEdge objects (neighbors)
                if(visited.contains(edge.to))
                    continue;

                var newDistance = distances.get(current) + edge.weight;
                if(newDistance < distances.get(edge.to)){
                    distances.replace(edge.to, newDistance);
                    previousNodes.put(edge.to, current); //edge.to = neighbor
                    queue.add(new NodeEntry(edge.to, newDistance));
                }
            }
        }

        return buildPath(previousNodes, toNode);
    }

    private Path buildPath( Map<Node, Node> previousNodes, Node toNode) {
        Stack<Node> stack = new Stack<>();
        stack.push(toNode);
        var previous = previousNodes.get(toNode);
        while(previous != null){
            stack.push(previous);
            previous = previousNodes.get(previous);
        }

        var path = new Path();
        while(!stack.isEmpty())
            path.add(stack.pop().label); //returns node obj, access label field

        return path;
    }

    public void print(){
        for(var node : nodes.values()) {//returns all source nodes
            var edges = node.getEdges(); //returns a array list of edges
            if(!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }

    }


}
