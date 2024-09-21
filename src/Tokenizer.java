import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer implements ITokenizer 
{
    private static final int SPACE_SIZE = 1;
    private static final List<String> KEYWORDS = Arrays.asList("CREATE", "DROP", "DESC", "EXISTS", "ALTER", "VIEW", "AS", "SELECT", "DELETE", "UPDATE", "FORCE", "FROM", "INNER", "LEFT", "RIGHT", "JOIN", "GROUP", "BY", "TOP", ";");

    @Override
    public String unifyFormat(String view) 
    {
        StringBuilder builder = new StringBuilder(view.length());
        for (String word : view.split(" "))
        {
            if (word.isBlank())
            {
                continue;
            }

            word = word.replace(";", "").trim();

            if (word.contains(".") || word.contains(","))
            {
                for (String subWord : word.split("[\\.\\,]"))
                {
                    processBrackets(builder, subWord);
                    builder.append(". ");
                }

                builder.delete(builder.length() - 2, builder.length());
                continue;
            }

            if (word.matches("[\\(\\[]{1}.+[\\)\\]]{1}"))
            {
                processBrackets(builder, word);
                continue;
            }
            
            builder.append(word).append(" ");
        }

        builder.append(";");
        return builder.toString();
    }

    private static void processWords(StringBuilder builder, String words, String splitRegex)
    {
        for (String word : words.split(splitRegex))
        {
            if (word.isBlank())
            {
                continue;
            }

            word = word.replace(";", "").trim();
            // TODO: recursive
        }
    }


    private static void processBrackets(StringBuilder builder, String word)
    {
        int len = word.length();
        builder.append(word.charAt(0)).append(" ");
        builder.append(word.substring(1, len - 2).trim()).append(" ");
        builder.append(word.charAt(len - 1)).append(" ");
    }

    @Override
    public List<Token> tokenize(String view) 
    {
        List<Token> tokens = createTokens(view);
        classifyTokens(tokens);

        return tokens;
    }   

    private static List<Token> createTokens(String view)
    {
        List<Token> tokens = new ArrayList<>();
        int end = 0;
        for (String word : view.split(" "))
        {
            int beginning = end + SPACE_SIZE;
            end = beginning + word.length();



            tokens.add(new Token(word, beginning, end));
        }

        return tokens;
    }

    private static void classifyTokens(List<Token> tokens)
    {
        // TODO: set token kind
    }
}
