package com.cse431.marsmen;

import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;

/* Note, I removed consideration of maxEnergy */

public class Util {

    private static final int DEFAULT_WEIGHT = 11;
    private MarsAgent agent;
    private ArrayList<LogicBelief> edges;
    private ArrayList<LogicBelief> nodes;
    protected ArrayList<Vertex> vertices;

    /* Initialze array lists */
    public Util(MarsAgent agent) {
        this.agent = agent;
        edges = new ArrayList<LogicBelief>();
        nodes = new ArrayList<LogicBelief>();
        vertices = new ArrayList<Vertex>();
        updateVertices();
    }

    /* Largest Area?? */

    /* Get an ArrayList of nodes to traverse (the path) */
    public ArrayList<String> getDirection(String start, ArrayList<String> goal) {
        System.out.println("Running Dijkstra");
        Dijkstra dija = new Dijkstra();
        System.out.println("We have "+edges.size()+" edges and "+nodes.size()+" nodes ("+vertices.size()+" vertices)");
        return dija.getDirection(vertices, goal, start);
    }

    /* Update internal knowledge base */
    private void updateVertices() {
        /* For all vertices in beliefs */
        for (LogicBelief b2 : agent.getAllBeliefs("vertex")) {
            String thisVertex = (String)b2.getParameters().get(0);
            LogicBelief fu = new LogicBelief("vertex",thisVertex);
            if (nodes.contains((Object)fu)) 
                continue;
            /* Add to our list of known nodes */
            nodes.add(fu);
            /* Add to list of vertices */
            vertices.add(new Vertex(thisVertex));
        }

        /* For every edge */
        for (LogicBelief b2 : agent.getAllBeliefs("edge")) {
            String vertex1 = b2.getParameters().get(0);
            String vertex2 = b2.getParameters().get(1);
            String weightStr = b2.getParameters().get(2);
            int weight = Integer.parseInt(weightStr);

            /* If only the default weight is known but we know the actual weight */
            if (edges.contains(new LogicBelief("edge", vertex1, vertex2,Integer.toString(DEFAULT_WEIGHT))) && weight!=DEFAULT_WEIGHT) {
                /* Update weight */
                int idx = edges.indexOf(new LogicBelief(b2.getPredicate(), vertex1,vertex2,Integer.toString(DEFAULT_WEIGHT)));
                edges.set(idx, b2);
                /* For every known vertex */
                for (Vertex v : vertices) {
                    /* Match vertex 1 */
                    if (v.name.equals(vertex1)) {
                        /* Find the correct edge from v1 to v2 and update its weight */
                        for (int i=0; i<v.adjacencies.size(); i++){
                        	Edge e = v.adjacencies.get(i);
                            if (e.target.name.equals(vertex2)) 
                                v.adjacencies.set(i, new Edge(e.target,weight));
                        }
                    }
                    /* Match vertex 2 */
                    if (v.name.equals(vertex2)){
                        /* Find the correct edge from v2 to v1 and update its weight */
                    	for (int i=0; i<v.adjacencies.size(); i++){
                        	Edge e = v.adjacencies.get(i);
                            if (e.target.name.equals(vertex1)) 
                                v.adjacencies.set(i, new Edge(e.target,weight));
                        }
                    }
                }
            }

            /* We should add it to our edges */
            if (!edges.contains(b2)) {
                edges.add(b2);
                Vertex v1 = null;
                Vertex v2 = null;
                for (Vertex v : vertices) {
                    if (v.name.equals(vertex1))
                        v1 = v;
                    if (v.name.equals(vertex2))
                        v2 = v;
                }
                v1.adjacencies.add(new Edge(v2,weight));
                v2.adjacencies.add(new Edge(v1,weight));
            }
        }
    }

    /* Return array of neighbor vertices */
    public ArrayList<String> getNeighborVertexes(String start) {
        ArrayList<String> vertexes = new ArrayList<String>();
        String position = start;
        vertexes.add(position);/* Include self as neighbor, for convenince sake... */
        for (LogicBelief p : agent.getAllBeliefs("edge")) {
            String vertex1 = p.getParameters().get(0);
            String vertex2 = p.getParameters().get(1);
            if (vertex1.equals(position))
                vertexes.add(vertex2);
            if (vertex2.equals(position)) 
                vertexes.add(vertex1);
        }
        return vertexes;
    }

    /* Return the next node to go to, given the list of goal nodes */
    public String getDir(ArrayList <String> goals){
        String position = agent.getLocation();
        /* Use Djikstra's to get the path */
        ArrayList<String> path = getDirection(position, goals);
        System.out.println("Path from "+position+" to "+goals.get(0));
        System.out.println("\tNote, goal size:"+goals.size()+"Final dest: "+path.get(path.size()-1));
        for (String p : path){
            System.out.print(p+",");
        }System.out.println();
        /* If the path is not empty, and the first node is my current location */
        if (path.size() > 0 && path.get(0).equals(position)) {
            path.remove(0);
        }
        /* If path is not empty, a valid path was found, and the first step is a neighbor */
        if (path.size() > 0 && !path.get(0).equals("novalidpath") && getNeighborVertexes(position).contains(path.get(0))) {
            System.out.println("Valid direction found... moving from "+position+" to "+path.get(0));
            return path.get(0);
        }
        /* Otherwise, go to my first neighbor vertex */
        else {
            System.out.println("Issue in getDir, just going to neighbor");
            return getNeighborVertexes(position).get(0);
        }
    }
}
