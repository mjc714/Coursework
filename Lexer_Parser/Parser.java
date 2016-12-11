
/**
 * Parser class to parse tokens returned by Lexer
 *
 * @author Matthew
 */
public class Parser {

    Token token;
    Lexer lexer;

    /**
     * Constructor to init Lexer and Token objects checks for multiple instances
     * of Query or QUERY in invalid strings
     *
     * @param input
     */
    public Parser(String input) {
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == 'Q' && input.charAt(i + 1) == 'U') || (input.charAt(i) == 'Q' && input.charAt(i + 1) == 'u')) {
                System.err.println("Only one instance of QUERY expected.");
                System.exit(1);
            }
        }
        lexer = new Lexer(input + '$');
        token = lexer.nextToken();
    }

    /**
     * Start parsing by calling query()
     */
    public void run() {
        query();
    }

    /**
     * reads in next token
     */
    private void next() {
        token = lexer.nextToken();
    }

    /**
     * query(), parses grammar SELECT <IDList> FROM <IDList> [WHERE <CondList>]
     */
    public void query() {
        System.out.println("<Query>");
        if (token.getTokenType() == Token.TokenType.KEYWORD && (token.getTokenValue().equals("SELECT"))) {
            System.out.println("\t<Keyword>" + token.getTokenValue() + "</Keyword>");
            next();
            idList();
        } else {
            error(Token.TokenType.KEYWORD);
        }
        if (token.getTokenType() == Token.TokenType.KEYWORD && (token.getTokenValue().equals("FROM"))) {
            System.out.println("\t<Keyword>" + token.getTokenValue() + "</Keyword");
            next();
            idList();
        } else {
            error(Token.TokenType.KEYWORD);
        }
        if (token.getTokenType() == Token.TokenType.KEYWORD && (token.getTokenValue().equals("WHERE"))) {
            System.out.println("\t<Keyword>" + token.getTokenValue() + "</Keyword");
            next();
            condList();
        }
        if (token.getTokenType() == Token.TokenType.EOI) {
            System.out.println("</Query>");
        }
        if (token.getTokenType() == Token.TokenType.INVALID) {
            error(token.getTokenType());
            next();
        }
    }

    /**
     * idList() parses grammar <id> {, <id>}
     */
    public void idList() {
        System.out.println("\t<IdList>");
        if (token.getTokenType() == Token.TokenType.ID) {
            System.out.println("\t\t<Id>" + token.getTokenValue() + "</Id>");
            next();
        }
        while (token.getTokenType() == Token.TokenType.COMMA) {
            System.out.println("\t\t<Comma>" + "," + "</Comma>");
            next();
            if (token.getTokenType() == Token.TokenType.ID) {
                System.out.println("\t\t<Id>" + token.getTokenValue() + "</Id>");
                next();
            } else {
                error(Token.TokenType.ID);
            }
        }
        System.out.println("\t</IdList>");
        //next();
    }

    /**
     * condList(), parses grammar <Cond> {AND <Cond>}
     */
    public void condList() {
        System.out.println("\t<CondList>");
        if (token.getTokenType() == Token.TokenType.ID) {
            //next();
            cond();
        } else {
            error(token.getTokenType());
        }
        while (token.getTokenType() == Token.TokenType.KEYWORD && token.getTokenValue().equals("AND")) {
            System.out.println("\t<Keyword>" + token.getTokenValue() + "</Keyword");
            next();
            if (token.getTokenType() == Token.TokenType.ID) {
                //next();
                cond();
            } else {
                error(Token.TokenType.CONDLIST);
            }
        }
        System.out.println("\t</CondList>");
    }

    /**
     * cond(), parses grammar <id> <operator> <Term>
     */
    public void cond() {
        System.out.println("\t   <Cond>");
        System.out.println("\t\t<Id>" + token.getTokenValue() + "</Id>");
        next();
        if (token.getTokenType() == Token.TokenType.OPERATOR) {
            System.out.println("\t\t<Operator>" + token.getTokenValue() + "</Operator>");
            next();
            term();
        } else {
            error(Token.TokenType.COND);
        }
        System.out.println("\t   </Cond>");
    }

    /**
     * term() parses grammar, <id> | <int> | <float>
     */
    public void term() {
        System.out.println("\t\t<Term>");
        if (token.getTokenType() == Token.TokenType.ID) {
            System.out.println("\t\t\t<Id>" + token.getTokenValue() + "</Id>");
            next();
        } else if (token.getTokenType() == Token.TokenType.INT) {
            System.out.println("\t\t\t<Int>" + token.getTokenValue() + "</Int>");
            next();
        } else if (token.getTokenType() == Token.TokenType.FLOAT) {
            System.out.println("\t\t\t<Float>" + token.getTokenValue() + "</Float>");
            next();
        } else {
            error(Token.TokenType.TERM);
        }
        System.out.println("\t\t</Term>");
        //next();
    }

    /**
     * output error message when unexpected input is found.
     *
     * @param type
     */
    private void error(Token.TokenType type) {
        System.err.println("Syntax error: Expecting: " + Token.typeToString(type) + "; saw: " + Token.typeToString(token.getTokenType()));
        System.exit(1);
    }
}
