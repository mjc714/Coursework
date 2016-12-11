
/**
 * Lexer that will read in a stream of chars and returns a token.
 *
 * @author Matthew
 */
public class Lexer {

    private static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits = "0123456789";
    String in = "";
    int index = 0;
    char ch;

    /**
     * Lexer constructor
     *
     * @param input
     */
    public Lexer(String input) {
        this.in = input;
        this.index = 0;
        this.ch = nextChar();
    }

    /**
     * reads next available character from stream.
     *
     * @return
     */
    private char nextChar() {
        ch = in.charAt(index);
        index += 1;
        return ch;
    }

    /**
     * reads in seres of chars until a space, then concatenates them into a
     * string for better readability
     *
     * @param st
     * @return
     */
    private String concat(String st) {
        StringBuffer read = new StringBuffer("");
        do {
            read.append(ch);
            ch = nextChar();
        } while (st.indexOf(ch) >= 0);
        return read.toString();
    }

    /**
     * Output error mesasge.
     *
     * @param msg
     */
    public void error(String msg) {
        System.err.println("\nError: location " + index + " " + msg);
        System.exit(1);
    }

    /**
     * Reads in chars to form new tokens returns tokens to be parsed by Parser
     *
     * @return
     */
    public Token nextToken() {
        do {
            if (Character.isLetter(ch)) {
                String id = concat(letters + digits);
                if (id.equals("SELECT") || id.equals("FROM") || id.equals("WHERE") || id.equals("AND")) {
                    return new Token(Token.TokenType.KEYWORD, id);
                } else {
                    return new Token(Token.TokenType.ID, id);
                }
            } else if (Character.isDigit(ch)) {
                String num = concat(digits);
                if (ch != '.') {
                    return new Token(Token.TokenType.INT, num);
                }
                num += ch;
                ch = nextChar();
                if (Character.isDigit(ch)) {
                    num += concat(digits);
                    return new Token(Token.TokenType.FLOAT, num);
                } else {
                    return new Token(Token.TokenType.INVALID, num);
                }
            } else {
                switch (ch) {
                    case ' ':
                        ch = nextChar();
                        break;
                    case ',':
                        char comma = ch;
                        ch = nextChar();
                        return new Token(Token.TokenType.COMMA, Character.toString(comma));
                    case '=':
                        char temp = ch;
                        ch = nextChar();
                        return new Token(Token.TokenType.OPERATOR, Character.toString(temp));
                    case '<':
                        char temp1 = ch;
                        ch = nextChar();
                        return new Token(Token.TokenType.OPERATOR, Character.toString(temp1));
                    case '>':
                        char temp2 = ch;
                        ch = nextChar();
                        return new Token(Token.TokenType.OPERATOR, Character.toString(temp2));
                    case '$':
                        return new Token(Token.TokenType.EOI, "EndOfInput");
                    default:
                        ch = nextChar();
                        error("Invalid token type");
                        return new Token(Token.TokenType.INVALID, Character.toString(ch));
                }
            }
        } while (true);
    }
}
