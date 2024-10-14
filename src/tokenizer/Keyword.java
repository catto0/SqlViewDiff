package tokenizer;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Keyword
{
	CREATE("CREATE"), DROP("DROP"), DESC("DESC"), EXISTS("EXISTS"), ALTER("ALTER"), VIEW("VIEW"), AS("AS"), ON("ON"),
	SELECT("SELECT"), DELETE("DELETE"), UPDATE("UPDATE"), FORCE("FORCE"), FROM("FROM"), WHERE("WHERE"), OUTER("OUTER"),
	INNER("INNER"), LEFT("LEFT"), RIGHT("RIGHT"), JOIN("JOIN"), GROUP("GROUP"), BY("BY"), TOP("TOP");

	public static final Set<Keyword> PRINT_NEW_LINE_AFTER_KEYWORDS = //
			Set.of(SELECT, FROM, WHERE);

	public static final Set<Keyword> PRINT_NEW_LINE_BEFORE_KEYWORDS = //
			Set.of(SELECT, FROM, WHERE, LEFT, RIGHT, OUTER, INNER, JOIN);

	public static final Set<String> LEADING_KEYWORDS = Stream.of(CREATE, VIEW, SELECT, FROM, JOIN, WHERE) //
			.map(Keyword::value) //
			.collect(Collectors.toSet());

	public static final Set<String> DEFINED_KEYWORDS = Stream.of(Keyword.values()) //
			.map(Keyword::value) //
			.collect(Collectors.toSet());

	private String keyword;

	Keyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String value()
	{
		return keyword;
	}

	public static Keyword valueOfOrNull(String keyword)
	{
		if (DEFINED_KEYWORDS.contains(keyword))
		{
			return valueOf(keyword);
		}

		return null;
	}
}
