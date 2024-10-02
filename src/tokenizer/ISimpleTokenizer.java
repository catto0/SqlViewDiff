package tokenizer;

import java.util.List;

public interface ISimpleTokenizer
{
	String unifyFormat(String view);

	List<Token> tokenize(String reformattedView);
}
