using System;

namespace Project
{
    internal static class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Welcome to the Haunted House Adventure!");
            Console.WriteLine("Explore the haunted house, collect items, and solve puzzles to escape!");

            Game game = new Game();
            game.Start();
        }
    }
}
