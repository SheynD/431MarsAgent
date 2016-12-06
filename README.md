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

# Beliefs

### Broadcasted -----------------------------------------------------

*Position of Agent:						Position (node, agentName, role)

*Node/Vertex (-1 designates unknown):	Node (name, value)

*Edge (11 designates unknown cost):		Edge (node1, node2, cost)

*Agent in need of repair:				NeedsRepair (node, agentName)

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

Total number of edges:			NumEdges (number)

Total number of vertices:		NumVertices (number)

Agent's Role:					Role (role)

Agent's Energy:					Energy (energy)

Agent's Max Energy:				MaxEnergy (energy)

Agent's Max Energy Disabled:	MaxEnergyDisabled (energy)

Agent's Health:					Health (health)

Agent's Max Health:				MaxHealth (health)

Agent's Strength:				Strength (strength)

Agent's Visible Range:			VisRange (distance)

Total Money:					Money (money)

Current Score:					Score (score)

Current Step:					Step (stepNum)

Total Number of Steps:			TotalSteps (steps)

Last Action:					LastAction (action)

Last Action Parameters:			LastActionParam (param)

Last Action Result:				LastActionResult (result)

Last Step Score:				LastStepScore (score)

Zone Score:						ZoneScore (score)

ZonesScore ?:					ZonesScore (score)

OLD BELIEF'S --------------------------------------------------------

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


