package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tokenizer.Tokenizer;

public class App {
    public static void main(String[] args) 
    {
        Tokenizer tokenizer = new Tokenizer();
        StringBuilder viewBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("./resources/view1.txt")))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                viewBuilder.append(line).append(" ");    
            }
        } 
        catch(IOException e)
        {
            System.err.println(e);
        }

        String formattedView = tokenizer.unifyFormat(viewBuilder.toString());

        System.out.println(formattedView);
    }
}
