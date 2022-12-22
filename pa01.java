/*=============================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Nikole Solano
| Language: Java
|
| To Compile: javac pa01.java
|
|
| To Execute: java -> java pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2022
| Instructor: McAlpin
| Due Date: October 16th
|
+=============================================================================*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class pa01{
    public static void main(String[]args) throws IOException{

        int size = 0;
        Path keyText = Path.of(args[0]);
        String key = Files.readString(keyText);
        
        //make key an array
        char [] keyArray = new char[512];
        int flag2 = 0;
        while(size < 512)
        {
            for(char c: key.toCharArray())
            {
                if(Character.isLetter(c) && flag2 < 512)
                {
                    keyArray[flag2] = Character.toLowerCase(c);
                    size++;
                    flag2++;
                }
            }
        }
        int length = key.length();
        char [] keyArrayOut = new char[length];
        int flag3 = 0;
        for(char c : key.toCharArray())
        {
            if(Character.isLetter(c) && flag3 < 512)
            {
                keyArrayOut[flag3] = Character.toLowerCase(c);
                flag3++;
            }
        }

        Path file = Path.of(args[1]);
        String input = Files.readString(file);
        char [] charArray = new char[512];
        int flag = 0;
        char [] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        
        for(char c : input.toCharArray())
        {
            if(Character.isLetter(c) && flag < 512)
            {
                charArray[flag] = Character.toLowerCase(c);
                flag++;
            }
        }
        while(flag < 512)
        {
            charArray[flag] = 'x';
            flag++;
        }
        //convert both to ASCII
        char [] solution = new char [512];
        for(int i = 0; i < keyArray.length; i++)
        {
            //do cipher
            int ascii1 = (int)charArray[i];
            int ascii2 = (int)keyArray[i];
            int solution1 = ((ascii1 - 97) + (ascii2 - 97)) % 26;
            //convert to letters
            for(int k = 0; k < charArray.length; k ++)
            {
                solution[i] = alpha[solution1];
            }
        }
       
        System.out.println("\n");
        System.out.println("Vigenere Key: \n" );
        int lengthKey = 0;
        while(lengthKey < keyArrayOut.length)
        {
            for(int l = 0; l < 80; l++)
            {
                if(lengthKey < keyArrayOut.length)
                {
                    System.out.print(keyArrayOut[lengthKey]);
                    lengthKey++;
                }
            }
            System.out.println();
        }
        
        System.out.println("Plaintext: \n");
        int lengthChar = 0;
        while(lengthChar < charArray.length)
        {
            for(int l = 0; l < 80; l++)
            {
                if(lengthChar < charArray.length)
                {
                    System.out.print(charArray[lengthChar]);
                    lengthChar++;
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Ciphertext: \n");
        int lengthSol = 0;
        while(lengthSol < solution.length)
        {
            for(int l = 0; l < 80; l++)
            {
                if(lengthSol < solution.length)
                {
                    System.out.print(solution[lengthSol]);
                    lengthSol++;
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*=============================================================================
| I Nikole Solano ni009009 affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/