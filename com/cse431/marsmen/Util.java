package com.cse431.marsmen;

import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;

public class Util {
	
	private MarsAgent agent;
	private ArrayList<LogicBelief> edges = new ArrayList<LogicBelief>();
	private ArrayList<LogicBelief> nodes = new ArrayList<LogicBelief>();
	protected ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	public Util(MarsAgent agent) {
        this.agent = agent;
    }
	
	public ArrayList<String> getDirection(String start, ArrayList<String> goal) {
        Dijkstra dija = new Dijkstra();
        this.updateVertices();
        return dija.getDirection(this.vertices, goal, start);
    }

    private void updateVertices() {
        for (LogicBelief b2 : this.agent.getAllBeliefs("vertex")) {
            LogicBelief fu = new LogicBelief("vertex", new String[]{(String)b2.getParameters().get(0)});
            if (this.nodes.contains((Object)fu)) continue;
            this.nodes.add(fu);
            this.vertices.add(new Vertex((String)b2.getParameters().get(0)));
            this.vertices.get((int)(this.vertices.size() - 1)).adjacencies = new ArrayList<>();
        }
        for (LogicBelief b2 : this.agent.getAllBeliefs("edge")) {
            if (this.edges.contains((Object)new LogicBelief("edge", new String[]{(String)b2.getParameters().get(0), (String)b2.getParameters().get(1), "11"})) && !((String)b2.getParameters().get(2)).equals("11")) {
                this.edges.set(this.edges.indexOf((Object)new LogicBelief(b2.getPredicate(), new String[]{(String)b2.getParameters().get(0), (String)b2.getParameters().get(1), "11"})), b2);
                for (Vertex v : this.vertices) {
                    Vertex watt;
                    double i;
                    if (v.name.equals(b2.getParameters().get(0))) {
                        for (Edge e : v.adjacencies) {
                            if (!e.target.name.equals(b2.getParameters().get(1))) continue;
                            watt = e.target;
                            i = Integer.parseInt((String)b2.getParameters().get(2));
                            i *= 2.0;
                            i /= (double)Integer.parseInt((String)this.agent.getAllBeliefs("maxEnergy").get(0).getParameters().get(0));
                            v.adjacencies.set(v.adjacencies.indexOf(e), new Edge(watt, i += 1.0));
                        }
                    }
                    if (!v.name.equals(b2.getParameters().get(1))) continue;
                    for (Edge e : v.adjacencies) {
                        if (!e.target.name.equals(b2.getParameters().get(0))) continue;
                        watt = e.target;
                        i = Integer.parseInt((String)b2.getParameters().get(2));
                        i *= 2.0;
                        i /= (double)Integer.parseInt((String)this.agent.getAllBeliefs("maxEnergy").get(0).getParameters().get(0));
                        v.adjacencies.set(v.adjacencies.indexOf(e), new Edge(watt, i += 1.0));
                    }
                }
            }
            if (this.edges.contains((Object)b2)) continue;
            this.edges.add(b2);
            Vertex a = new Vertex("");
            Vertex c = new Vertex("");
            for (Vertex v : this.vertices) {
                if (v.name.equals(b2.getParameters().get(0))) {
                    a = v;
                }
                if (!v.name.equals(b2.getParameters().get(1))) continue;
                c = v;
            }
            double i = Integer.parseInt((String)b2.getParameters().get(2));
            i *= 2.0;
            i /= (double)Integer.parseInt((String)this.agent.getAllBeliefs("maxEnergy").get(0).getParameters().get(0));
            this.vertices.get((int)this.vertices.indexOf((Object)a)).adjacencies.add(new Edge(c, i += 1.0));
            this.vertices.get((int)this.vertices.indexOf((Object)c)).adjacencies.add(new Edge(a, i));
        }
    }
    
    public ArrayList<String> getNeighborVertexes(String start) {
        ArrayList<String> vertexes = new ArrayList<String>();
        String position = start;
        for (LogicBelief p : this.agent.getAllBeliefs("edge")) {
            if (((String)p.getParameters().get(0)).toString().equals(position)) {
                vertexes.add(((String)p.getParameters().get(1)).toString());
                continue;
            }
            if (!((String)p.getParameters().get(1)).toString().equals(position)) continue;
            vertexes.add(((String)p.getParameters().get(0)).toString());
        }
        return vertexes;
    }
}
