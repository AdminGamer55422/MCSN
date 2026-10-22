package net.admingamer.mcsn.util;

import java.util.Scanner;

public class MCSNGen {
    public static void main(String[] args) {
        System.out.println("------------------------\nHello! Welcome to this custom MCSN Data Gen! What do you want to create?\n - Block");
        Scanner scanner = new Scanner(System.in);
        String chosenObject = scanner.nextLine();
        System.out.println(chosenObject);
    }
}

// {
//  "parent": "minecraft:block/cube_all",
//  "textures": {
//    "all": "mcsn:block/asphalt_a"
//  }
//}
