package utils.printer;

import java.util.List;

import tokenizer.Token;

public interface ISimplePrinter
{
	void printToConsole(List<Token> tokens, int averageWordLength);

	void writeToFile(String fileName, List<Token> tokens, int averageWordLength);
}
