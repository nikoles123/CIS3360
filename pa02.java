/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Nikole Solano
| Language: Java
|
| To Compile: javac pa02.java
|
| To Execute: java -> java pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2022
| Instructor: McAlpin
| Due Date: November 27th, 2022
|
+=============================================================================*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class pa02{
    public static void main(String [] args) throws IOException
    {
        System.out.println();
        //taking in input file
        Path inputText = Path.of(args[0]);
        String input = Files.readString(inputText);

        //takes in checksum size & checks to see if it is a valid size
        String type = args[1];
        int size = Integer.parseInt(type);

        if(size != 8 && size != 16 && size != 32)
        {
            System.err.println("Valid checksum sizes are 8, 16, or 32");
            System.exit(0);
        }

        int length = input.length();
        //turns input string into an array
        char inputArray[] = new char[length];
        for(int i = 0; i < length; i++)
        {
            inputArray[i] = input.charAt(i);
        }


        String checksum = "sum";
        if(size == 8)
        {
            checksum = check8bits(inputArray);
        }

        char [] newArray;
        //padding flag
        int flag = 0;
        int inputKey;
        //check if it needs to be padded + pad if neccessary
        //needs to be in blocks of 2
        if(size == 16)
        {
            //needs to be padded
            if(input.length() % 2 != 0)
            {
                inputKey = 0;
                newArray = Arrays.copyOf(inputArray, length + 1);
                newArray[length] = 'X';
                length = length + 1;
                flag = 1;
                while(inputKey < length)
                {
                    for(int j = 0; j < 80; j++)
                    {
                        if(inputKey < length)
                        {
                            System.out.print(newArray[inputKey]);
                            inputKey++;
                        }
                    }
                    System.out.println();
                    checksum = check16bits(newArray);
                }

            }
            //doesn't need to be padded
            else
            {
                checksum = check16bits(inputArray);
            }
            
        }
        //needs to be in blocks of 4
        if(size == 32)
        {
            //needs to be padded
            if(input.length() % 4 != 0)
            {
                //pad w 3 X's
                if(input.length() % 4 == 1)
                {
                    newArray = Arrays.copyOf(inputArray, length + 3);
                    newArray[length] = 'X';
                    newArray[length + 1] = 'X';
                    newArray[length + 2] = 'X';
                    length = length + 3;
                    flag = 1;
                    inputKey = 0;
                    while(inputKey < length)
                    {
                        for(int j = 0; j < 80; j++)
                        {
                            if(inputKey < length)
                            {
                                System.out.print(newArray[inputKey]);
                                inputKey++;
                            }
                        }
                        System.out.println();
                    }
                    checksum = check32bits(newArray);
                }
                //pad w 2 X's
                if(input.length() % 4 == 2)
                {
                    newArray = Arrays.copyOf(inputArray, length + 2);
                    newArray[length] = 'X';
                    newArray[length + 1] = 'X';
                    length = length + 2;
                    flag = 1;
                    inputKey = 0;
                    while(inputKey < length)
                    {
                        for(int j = 0; j < 80; j++)
                        {
                            if(inputKey < length)
                            {
                                System.out.print(newArray[inputKey]);
                                inputKey++;
                            }
                        }
                        System.out.println();
                    }
                    checksum = check32bits(newArray);
                }
                //pad w 1 X
                if(input.length() % 4 == 3)
                {
                    newArray = Arrays.copyOf(inputArray, length + 1);
                    newArray[length] = 'X';
                    length = length + 1;
                    flag = 1;
                    inputKey = 0;
                    while(inputKey < length)
                    {
                        for(int j = 0; j < 80; j++)
                        {
                            if(inputKey < length)
                            {
                                System.out.print(newArray[inputKey]);
                                inputKey++;
                            }
                        }
                        System.out.println();
                    }
                    checksum = check32bits(newArray);
                }
            }
            //doesn't need to be padded
            else
            {
                checksum = check32bits(inputArray);
            }

        }
        
        inputKey = 0;
        //we did not pad
        if(flag == 0)
        {
            while(inputKey < length)
            {
                for(int j = 0; j < 80; j++)
                {
                    if(inputKey < length)
                    {
                        System.out.print(inputArray[inputKey]);
                        inputKey++;
                    }
                }
                System.out.println();
            }
        } 
        System.out.printf("%2d bit checksum is %8s for all %4d chars\n", size, checksum, length);
        
    }

    public static String check8bits(char[] inputArray){
        int sum = 0;
        int [] asciiArray = new int[inputArray.length];
        for(int i = 0; i < inputArray.length; i++)
        {
            asciiArray[i] = (int)inputArray[i];
        }
        for(int j = 0; j < inputArray.length; j++)
        {
            //System.out.print("{" + asciiArray[j] + "}");
            sum = sum + asciiArray[j];
        }
        sum = sum % 256;

        String checksum = Integer.toHexString(sum);

        return checksum;
    }
    public static String check16bits(char[] inputArray){
        int sum = 0;
        int [] asciiArray = new int[inputArray.length];
        for(int i = 0; i < inputArray.length; i++)
        {
            asciiArray[i] = (int)inputArray[i];
        }
        int left = 0;
        int right = 1;
        int leftSum;
        int rightSum;
        while(left < inputArray.length)
        {
            leftSum = asciiArray[left] * 256;
            rightSum = asciiArray[right];
            sum = sum + leftSum + rightSum;
            left = left + 2;
            right = right + 2;
        }
        sum = sum % 65536;

        String checksum = Integer.toHexString(sum);
        return checksum;
    }
    public static String check32bits(char[] inputArray){
        long sum = 0;
        long [] asciiArray = new long[inputArray.length];
        for(int i = 0; i < inputArray.length; i++)
        {
            asciiArray[i] = (long)inputArray[i];
        }
        int one = 0;
        int two = 1;
        int three = 2;
        int four = 3;
        while(four < inputArray.length)
        {
            sum = sum + (asciiArray[one] * 16777216) + (asciiArray[two] * 65536) + (asciiArray[three] * 256) + asciiArray[four];
            one = one + 4;
            two = two + 4;
            three = three + 4;
            four = four + 4;
        }
        sum = sum % 4294967296l; 
        String checksum = Integer.toHexString((int)sum);
        return checksum;
    }
}

/*=============================================================================
| I Nikole Solano 5252251 affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+============================================================================*/