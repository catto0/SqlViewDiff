package printer;

import java.util.List;

import tokenizer.Token;

public interface IPrinter
{
	void printToConsole(List<Token> tokens);

	void printToFile(List<Token> tokens);
}
