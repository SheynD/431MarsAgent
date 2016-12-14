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

    /* Update minDistance and previous */
    public static void computePaths(Vertex source, ArrayList<String> goal) {
        System.out.println("Computing paths");
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            /* Take smallest vertex, by distance */
            Vertex evaluateNode = vertexQueue.poll();

            /* Visit each edge exiting evaluateNode */
            for (Edge e : evaluateNode.adjacencies) {
                Vertex neighbor = e.target;
                int weight = e.weight;
                /* Possible new total distance for neighbor */
                int distanceThroughU = evaluateNode.minDistance + weight;
                /* If it is less then current distance, relax the weight */
                if (distanceThroughU < neighbor.minDistance) {
                    vertexQueue.remove(neighbor);
                    neighbor.minDistance = distanceThroughU;
                    neighbor.previous = evaluateNode;
                    vertexQueue.add(neighbor);
                }
            }
            if (goal.contains(evaluateNode.name)) {
                vertexQueue.clear();
                break;
            }
        }
        System.out.println("Done");
    }

    public static ArrayList<String> getShortestPathTo(Vertex target) {
        System.out.println("Getting shortest Path");
        ArrayList<String> path = new ArrayList<String>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex.toString());
        }
        Collections.reverse(path);
        System.out.println("Done");
        return path;
    }

    public ArrayList<String> getDirection(ArrayList<Vertex> vertices, ArrayList<String> goal, String start) {
        System.out.println("Getting direction");
        int i = -1;
        for (Vertex neighbor : vertices) {
            if (neighbor.name.equals(start)) {
                i = vertices.indexOf(neighbor);
            }
        }
        computePaths(vertices.get(i), goal);
        int dist = Integer.MAX_VALUE;
        Vertex ausgabe = new Vertex("novalidpath");
        for (String z : goal) {
            for (Vertex neighbor : vertices) {
                if (neighbor.name.equals(z) && neighbor.minDistance < dist) {
                    dist = neighbor.minDistance;
                    ausgabe = neighbor;
                }
            }
        }
        ArrayList<String> path = getShortestPathTo(ausgabe);
        for (Vertex neighbor : vertices) {
            neighbor.previous = null;
            neighbor.minDistance = 10000;
        }
        System.out.println("Done");
        return path;
    }
}
