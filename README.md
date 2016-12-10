# 431MarsAgent
Colonizing Mars so we can send Razavi there

# To get it up and running 
1. Download the zip and extract it
2. CD into that directory (massim-2013-1.4)
3. Glone this repo
4. Follow [Heflin's instructions](http://www.cse.lehigh.edu/~heflin/courses/agents-2016/mars-faq.html)
    - Note the type in 6.1. (javaagents)
    - In 6.4, point it to the marsmen directory
5. Follow Heflin's instructions to run the agent!

As of now, all agents recharge at every time step. See section 5 of EISMASSim description for more details

The agent config files (in marsmen) are confifured to work with Heflin's tournament configuration, and plays against the dummy/simple agents. See Heflin's instructions for download of server-config. No need to utilize agent-configs, dummy10b is incorporated into our config.

# Get it run from the command line / JAR
1. Follow instructions from heflin's website, saving the JAR to the marsmen directory
    - Exlipse threw me an error about not having a main class, but it still builds the JAR
2. CD into the marsmen directiory
3. Execute this command
```
java -ea -classpath ../../javaagents/target/javaagents-2.1.jar:marsmen.jar massim.javaagents.App 
```

# Beliefs

### Broadcasted -----------------------------------------------------

*Position of Agent:						Position (node, agentName, role)

*Node/Vertex (-1 designates unknown):	Node (name, value)

*Edge (11 designates unknown cost):		Edge (node1, node2, cost)

*Agent in need of repair:				needRepair (node, agentName) , repairComing(node, disabledAgentName, RepairAgentName)

*Enemy Agent:							Enemy (node)

*Visible Agent (opponent or teammate):	VisibleAgent (reporterName, vehicleName, vertex, team, isDisabled)

### Internals (derived from percepts) -----------------------------------

Percept info not stored
// achievement
// bye
// deadline
// id
// ranking
// requestAction
// simEnd
// simStart
// timestamp
// lastActionParams

Total number of edges:  
numEdges (number)  

Total number of vertices:  
numVertices (number)  

Agent's Role:  
role (role)  

Agent's Energy:  
energy (energy)  

Agent's Max Energy:  
maxEnergy (energy)  

Agent's Max Energy Disabled:  
maxEnergyDisabled (energy)  

Agent's Health:  
health (health)  

Agent's Max Health:  
maxHealth (health)  

Agent's Strength:  
strength (strength)  

Agent's Visible Range:  
visRange (distance)  

Total Money:  
money (money)  

Current Score:  
score (score)  

Current Step:  
step (stepNum)  

Total Number of Steps:  
totalSteps (steps)  

Last Action:  
lastAction (action)  

Last Action Result:  
lastActionResult (result)  

Last Step Score:  
lastStepScore (score)  

Zone Score:  
zoneScore (score)  

ZonesScore ?:  
zonesScore (score)  

### OLD BELIEFS --------------------------------------------------------

visibleAgent  
1. name of agent that reported it  
2. vehicle name  
3. vertex  
4. team  
5. disabled or normal  

energy

max energy

health

maxHealth

probedVertex

position

role

surveyedEdge  
1. vertex1  
2. vertex2  
3. weight  

visibleEdge  
1. vertex1  
2. vertex2  
3. weight = "11"  

visibleVertex


