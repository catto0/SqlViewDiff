package comparator;

import java.util.List;

import tokenizer.Token;

public interface ISimpleComparator
{
	void compare(List<Token> first, List<Token> second);
}
