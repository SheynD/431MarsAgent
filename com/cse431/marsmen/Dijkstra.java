package com.cse431.marsmen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

class Vertex implements Comparable<Vertex> {
    public final String name;
    public ArrayList<Edge> adjacencies;
    public int minDistance;
    public Vertex previous;

    public Vertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
        minDistance = Integer.MAX_VALUE;
        previous = null;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Integer.compare(minDistance, other.minDistance);
    }
}

class Edge {
    public final Vertex target;
    public final int weight;

    public Edge(Vertex argTarget, Integer argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

public class Dijkstra {

    public static void computePaths(Vertex source, ArrayList<String> goal) {
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                int weight = e.weight;
                int distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
            if (goal.contains(u.name)) {
                vertexQueue.clear();
                break;
            }
        }
    }

    public static ArrayList<String> getShortestPathTo(Vertex target) {
        ArrayList<String> path = new ArrayList<String>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex.toString());
        }
        Collections.reverse(path);
        return path;
    }

    public ArrayList<String> getDirection(ArrayList<Vertex> vertices, ArrayList<String> goal, String start) {
        int i = -1;
        for (Vertex v : vertices) {
            if (v.name.equals(start)) {
                i = vertices.indexOf(v);
            }
        }
        computePaths(vertices.get(i), goal);
        int dist = Integer.MAX_VALUE;
        Vertex ausgabe = new Vertex("test");
        for (String z : goal) {
            for (Vertex v : vertices) {
                if (v.name.equals(z) && v.minDistance < dist) {
                    dist = v.minDistance;
                    ausgabe = v;
                }
            }
        }
        ArrayList<String> path = getShortestPathTo(ausgabe);
        for (Vertex v : vertices) {
            v.previous = null;
            v.minDistance = 10000;
        }
        return path;
    }
}
