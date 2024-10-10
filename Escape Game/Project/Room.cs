using System;
using System.Collections.Generic;

namespace Project
{
    public class Room
    {
        public string Name { get; }
        public string Description { get; }
        public Dictionary<string, Room> Exits { get; }
        public bool HasChallenge { get; set; }

        public Room(string name, string description)
        {
            Name = name;
            Description = description;
            Exits = new Dictionary<string, Room>();
            HasChallenge = false;
        }

        public void AddExit(string direction, Room room)
        {
            Exits[direction] = room;
        }

        public void DisplayExits()
        {
            Console.WriteLine("Exits:");
            foreach (var exit in Exits)
            {
                Console.WriteLine($"- {exit.Key}");
            }
        }
    }
}