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

The agent config files (in marsmen) are confifured to work with Heflin's tournament configuration. See Heflin's instructions for download of server-config. Also download agent-configs. Extract it to the conf directory. 

# RECENT UPDATE:
The only way to avoid the dummy agent's output from clogging our output and making debugging impossible, is to run the teams seperately. So, I have modified our configuration to only control OUR team. In order to have opponents, in a seperate terminal shell, execute startAgents.sh (choosing one of the configs from agent-configs. So, as of now, execution looks something like:

1. Open up a new shell, and cd into 'massim-2013-1.4/massim/scripts'
2. Execute ```./startServer.sh``` and choose 'conf//2013-marsmenVdummy.xml'
3. Open up a new shell, and cd into 'massim-2013-1.4/massim/scripts'
4. Execute ```./startMarsMonitor.sh``` (Note that you can leave this running for multiple runs, you don't need to close and reopen every time)
5. Open up a new shell, and cd into 'massim-2013-1.4/javaagents/scripts'
6. Execute ```./startAgents.sh``` and choose 'dummy10a' (or any 10 agent config). This starts our opponent team.
7. Choose how to run OUR agents:
    - Run in eclipse, same way we have been
    - Run with build and execute script: cd into our Git Repo, then execute ```./build.sh```
8. Go back to the server (startServer) terminal window. You should see the 20 agents (10 from each team) have connected. Hit enter to start the game. 
9. Sit back and enjoy our agents killin it.



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

*Position of Agent:                                             Position (node, agentName, role)

*Node/Vertex (-1 designates unknown):   Node (name, value)

*Edge (11 designates unknown cost):     Edge (node1, node2, cost)

*Agent in need of repair:               needRepair (node, agentName), 
                                        repairComing(node, disabledAgentName, RepairAgentName)

*Enemy Agent:                           Enemy (node)

*Visible Entity (opponent or teammate):  VisibleEntity (reporterName, vehicleName, vertex, team, isDisabled)

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

Enemy Sabateur
enemySaboteur (name)

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


