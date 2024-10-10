using System;
using System.Collections.Generic;

namespace Project
{
    public class Game
    {
        private readonly Player _player;
        private readonly Dictionary<string, Room> _rooms;

        public Game()
        {
            _player = new Player();
            _rooms = new Dictionary<string, Room>();

            InitializeRooms();
        }

        public void Start()
        {
            Room currentRoom = _rooms["Foyer"]; //starting room

            while (true)
            {
                Console.WriteLine($"\nYou are in the {currentRoom.Name}");
                Console.WriteLine(currentRoom.Description);

                if (currentRoom.HasChallenge)
                {
                    HandleChallenge(currentRoom);
                }

                if (currentRoom.Name == "Attic" && _player.HasItem("Escape Map"))
                {
                    Console.WriteLine("\nCongratulations! You escaped the haunted house through the attic!");
                    break;
                }

                Console.WriteLine("\nWhat would you like to do?");
                Console.WriteLine("1. Move to another room");
                Console.WriteLine("2. Check inventory");
                Console.WriteLine("3. Quit game");

                string choice = Console.ReadLine();

                switch (choice)
                {
                    case "1":
                        currentRoom = MoveToAnotherRoom(currentRoom);
                        break;
                    case "2":
                        _player.DisplayInventory();
                        break;
                    case "3":
                        Console.WriteLine("\nThanks for playing!");
                        return;
                    default:
                        Console.WriteLine("\nInvalid choice. Please try again.");
                        break;
                }
            }
        }

        private void InitializeRooms()
        {
            Room foyer = new Room("Foyer", "A small, dimly lit entrance with a dusty chandelier.");
            Room livingRoom = new Room("Living Room", "A large room with old furniture covered in white sheets. There is a strange painting on the wall.");
            Room kitchen = new Room("Kitchen", "A cold, dark kitchen with rusty utensils scattered around. There's a locked cabinet.");
            Room library = new Room("Library", "A room filled with ancient books and a mysterious aura. There's a locked safe.");
            Room basement = new Room("Basement", "A dark, damp basement with a creaky wooden staircase. There's something glowing in the corner.");
            Room garden = new Room("Garden", "An overgrown garden with a mysterious statue.");
            Room study = new Room("Study", "A quiet study with a large wooden desk. There's a locked drawer.");
            Room attic = new Room("Attic", "A dusty attic filled with old furniture and boxes. There's a locked door that might lead to an escape route.");

            foyer.AddExit("north", livingRoom);
            livingRoom.AddExit("south", foyer);
            livingRoom.AddExit("east", kitchen);
            kitchen.AddExit("west", livingRoom);
            livingRoom.AddExit("west", library);
            library.AddExit("east", livingRoom);
            library.AddExit("down", basement);
            basement.AddExit("up", library);
            library.AddExit("north", garden);
            garden.AddExit("south", library);
            livingRoom.AddExit("north", study);
            study.AddExit("south", livingRoom);
            library.AddExit("up", attic);
            attic.AddExit("down", library);

            foyer.HasChallenge = false;
            livingRoom.HasChallenge = true;
            kitchen.HasChallenge = true;
            library.HasChallenge = true;
            basement.HasChallenge = true;
            garden.HasChallenge = true;
            study.HasChallenge = true;
            attic.HasChallenge = false;

            _rooms.Add("Foyer", foyer);
            _rooms.Add("Living Room", livingRoom);
            _rooms.Add("Kitchen", kitchen);
            _rooms.Add("Library", library);
            _rooms.Add("Basement", basement);
            _rooms.Add("Garden", garden);
            _rooms.Add("Study", study);
            _rooms.Add("Attic", attic);
        }

        private Room MoveToAnotherRoom(Room currentRoom)
        {
            Console.WriteLine("\nWhere would you like to go?");
            currentRoom.DisplayExits();

            string direction = Console.ReadLine()?.ToLower();

            if (currentRoom.Exits.ContainsKey(direction))
            {
                Room nextRoom = currentRoom.Exits[direction];
                
                //special room conditions
                if (nextRoom.Name == "Study" && (!_player.HasItem("Ancient Scroll") || !_player.HasItem("Mysterious Artifact")))
                {
                    Console.WriteLine("\nThe door to the Study is locked. You need both the Ancient Scroll and the Mysterious Artifact to enter.");
                    return currentRoom;
                } 
                
                if (nextRoom.Name == "Attic" && (!_player.HasItem("Escape Map") || !_player.HasItem("Key")))
                {
                    Console.WriteLine("\nThe door to the Attic is locked. You need the escape map and a key to enter.");
                    return currentRoom;
                }
                
                return nextRoom;
            }
            
            Console.WriteLine("\nYou can't go that way. Please try again.");
            return currentRoom;
        }

        private void HandleChallenge(Room room)
        {
            if (!room.HasChallenge) return;

            switch (room.Name)
            {
                case "Living Room":
                    if (SolvePaintingPuzzle())
                    {
                        room.HasChallenge = false;
                    }
                    break;
                case "Kitchen":
                    if (OpenLockedCabinet())
                    {
                        room.HasChallenge = false;
                    }
                    break;
                case "Library":
                    if (OpenLockedSafe())
                    {
                        room.HasChallenge = false;
                    }
                    break;
                case "Basement":
                    if (RetrieveGlowingObject())
                    {
                        room.HasChallenge = false;
                    }
                    break;
                case "Garden":
                    if (SolveStatuePuzzle())
                    {
                        room.HasChallenge = false;
                    }
                    break;
                case "Study":
                    if (OpenLockedDrawer())
                    {
                        room.HasChallenge = false;
                    }
                    break;
            }
        }

        private bool SolvePaintingPuzzle()
        {
            Console.WriteLine("\nYou notice something odd about the painting on the wall.");
            Console.WriteLine("There's a riddle inscribed: 'I speak without a mouth and hear without ears. I have no body, but I come alive with wind.'");
            Console.Write("Enter your answer: ");

            string answer = Console.ReadLine()?.ToLower();
            if (answer == "echo")
            {
                Console.WriteLine("\nCorrect! You solved the riddle and found a key behind the painting.");
                _player.AddItem("Key"); 
                return true;
            }
            Console.WriteLine("\nIncorrect. Try again next time.");
            return false;
        }

        private bool OpenLockedCabinet()
        {
            Console.WriteLine("\nTo open the cabinet you need a key.");
            if (_player.HasItem("Key"))
            {
                Console.WriteLine("\nDo you want to use the key? (yes/no)");
                string choice = Console.ReadLine()?.ToLower();
                if (choice == "yes")
                {
                    Console.WriteLine("\nYou used the key to open the cabinet and found a flashlight.");
                    _player.AddItem("Flashlight");
                    return true; 
                }
                Console.WriteLine("\nYou chose not to use the key.");
                return false;
            }
            Console.WriteLine("\nYou don't have the key to open the cabinet."); 
            return false;
        }

        private bool OpenLockedSafe()
        {
            Console.WriteLine("\nThe safe is locked with a combination lock. You need to solve the puzzle to open it.");
            Console.WriteLine("Combination Lock Puzzle: Enter the sum of the first five prime numbers:");

            string answer = Console.ReadLine();
            if (answer == "28")
            {
                Console.WriteLine("\nCorrect! You opened the safe and found a glowing skull.");
                _player.AddItem("Glowing Skull");
                return true;
            }
            Console.WriteLine("\nIncorrect. Try again.");
            return false;
        }

        private bool RetrieveGlowingObject()
        {
            Console.WriteLine("\nYou see a glowing object in the corner, but it's too dark to see what it is.");
            if (_player.HasItem("Flashlight"))
            {
                Console.WriteLine("\nDo you want to use the flashlight? (yes/no)");
                string choice = Console.ReadLine()?.ToLower();
                if (choice == "yes")
                { 
                    Console.WriteLine("\nYou used the flashlight to illuminate the corner and found a mysterious artifact.");
                    _player.RemoveItem("Flashlight");
                    _player.AddItem("Mysterious Artifact");
                    return true;
                }
                Console.WriteLine("\nYou chose not to use the flashlight.");
                return false;
            }
            Console.WriteLine("\nYou need a flashlight to see what the object is.");
            return false;
        }
        
        private bool SolveStatuePuzzle()
        {
            Console.WriteLine("\nYou enter the garden and find yourself in a maze. Navigate through the maze to find an ancient scroll.");

            string[] correctPath = { "north", "east", "north", "west", "north" };
            int step = 0;

            while (step < correctPath.Length)
            {
                Console.WriteLine("\nWhich direction do you want to go? (north, south, east, west)");
                string direction = Console.ReadLine()?.ToLower();

                if (direction == correctPath[step])
                {
                    step++;
                    Console.WriteLine("\nYou move further into the maze.");
                }
                else
                {
                    Console.WriteLine("\nYou hit a dead end and have to start over.");
                    step = 0;
                }
            }

            Console.WriteLine("\nCongratulations! You have navigated through the maze and found the scroll.");
            _player.AddItem("Ancient Scroll");
            return true;
        }

        private bool OpenLockedDrawer()
        {
            Console.WriteLine("\nThe drawer is locked. You need a key to open it.");
            if (_player.HasItem("Key"))
            {
                Console.WriteLine("Do you want to use the key? (yes/no)");
                string choice = Console.ReadLine()?.ToLower();
                if (choice == "yes")
                {
                    Console.WriteLine("\nYou used the key to open the drawer and found an escape map.");
                    _player.AddItem("Escape Map");
                    return true;
                }
                
                Console.WriteLine("\nYou chose not to use the key.");
                return false;
            }
            
            Console.WriteLine("\nYou don't have the key to open the drawer.");
            return false;
        }
    }
}