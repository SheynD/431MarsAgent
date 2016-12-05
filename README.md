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

# BeliefBase
- visibleAgent
    0. name of agent that reported it
    1. vehicle name
    2. vertex
    3. team
    4. disabled or normal
- energy
- max energy
- health
- maxHealth
- probedVertex
- position
- role
- surveyedEdge
    0. vertex1
    1. vertex2
    2. weight
- visibleEdge
    0. vertex1
    1. vertex2
    2. weight = "11"
- visibleVertex
