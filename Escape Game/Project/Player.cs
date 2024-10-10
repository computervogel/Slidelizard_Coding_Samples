using System;
using System.Collections.Generic;

namespace Project
{
    public class Player
    {
        private readonly List<string> _inventory = new List<string>();

        public void AddItem(string item)
        {
            _inventory.Add(item);
            Console.WriteLine($"{item} has been added to your inventory.");
        }

        public void RemoveItem(string item)
        {
            _inventory.Remove(item);
            Console.WriteLine($"{item} has been removed from your inventory");
        }

        public bool HasItem(string item)
        {
            return _inventory.Contains(item);
        }

        public void DisplayInventory()
        {
            Console.WriteLine("\nYour inventory:");
            
            if (_inventory.Count == 0)
            {
                Console.WriteLine("- There is only dust and breadcrumbs here. Move to a different room to find useful objects!");
            }
            foreach (string item in _inventory)
            {
                Console.WriteLine($"- {item}");
            }
        }
    }
}