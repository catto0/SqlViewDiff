package tokenizer;

import java.util.List;

public interface ITokenizer 
{    
    String unifyFormat(String view);

    List<Token> tokenize(String reformattedView);
}
