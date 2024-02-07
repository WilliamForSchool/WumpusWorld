## TODO: 
1. Modify score: 
- Reward getting gold: +50
- Reward killing wumpus (stretch goal): +100

2. **Kill Wumpus (stretch goal)**
- Add the ability to kill wumpus
- Player gets one "shot" total
- Shot goes through entire row/col and kills wumpus

3. **Implement Stack**
- Create a stack and push every move, this allows you to backtrack if you are in danger
- You can also use this to backtrack all the way to the beginning if you find the gold

4. **Make gold getable**
- Add some logic that if the player is on the gold: + 50
- Switch grpahic to empty chest

## **AI**
- Start simple: just getting it to work and return is goal 1
- Create new ai methods, do not destroy a working solution even if bad
- Create new ai's and attempt bettwe scoring solutions
  - Use heuristics to grade possible moves
  - One Idea: create a list of all possible moves, score all possible moves (heuristic) 
    - Choose the best move, create a method that can get you to any best move
  - Stretch algorithms (make your own first) - A* pathfinding
    - Dykstra's Algorithm