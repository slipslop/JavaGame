# JavaGame
First larger game made with Java.

Controls:
- WASD movement
- Mouse aim and click to shoot.
- P to pause, U to unpause, L for godmode, G to reset ammos (obviously some of these has to be removed from final version)


Functionalities:

  - Notch's game loop (creator of Minecraft) ticks every game obect 60 times/s
  - Player following camera
  - 2 levels
  
  
  
New ideas learned from this project
  - Every object is loaded to a LinkedList that accepts <GameObject> type of data.
    -  By using LinkedList, I can easily add or remove objects from the LinkedList (https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html)
  - Camera that follows the player
    - First time I looked this up it felt quite a fancy and hard-to-develop system.
    - But after research and guides it was very simple to implement.
    - Pseudo code as follows:
      - Determine what object you want the camera to follow
      - Pass the wanted object's X and Y coordinate to the Camera.
      - Update the camera (give new X and Y coordinates as the object moves)
  - All the objects in the game are created from an abstract GameObject class.
    - This class provides the 'bones' of every game object.
  - Every game object has ID which is an enum class. 
  
  
  
  Things to improve
    - Better sprite animation.
    - Put classes to packages.
    
