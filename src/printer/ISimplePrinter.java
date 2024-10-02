package printer;

import java.util.List;

import tokenizer.Token;

public interface ISimplePrinter
{
	void printToConsole(List<Token> tokens);

	void printToFile(List<Token> tokens);
}
