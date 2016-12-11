
/**
 * Token class that stores value and category of tokens.
 *
 * @author Matthew
 */
public class Token {

    public enum TokenType {

        DIGIT, LETTER, INT, FLOAT, ID, KEYWORD, OPERATOR, COMMA, QUERY, IDLIST, CONDLIST, COND, TERM, INVALID, EOI
    }
    private TokenType type;
    private String val;

    /**
     * Constructor inits type and val
     *
     * @param t
     * @param s
     */
    public Token(TokenType t, String s) {
        this.type = t;
        this.val = s;
    }

    /**
     * return type of the token
     *
     * @return
     */
    public TokenType getTokenType() {
        return this.type;
    }

    /**
     * return token value.
     *
     * @return
     */
    public String getTokenValue() {
        return this.val;
    }

    /**
     * Converts terminals to strings for output.
     *
     * @param tp
     * @return
     */
    public static String typeToString(TokenType tp) {
        String s = "";
        switch (tp) {
            case DIGIT: {
                s = "Digit";
                break;
            }
            case LETTER: {
                s = "Letter";
                break;
            }
            case INT: {
                s = "Int";
                break;
            }
            case FLOAT: {
                s = "Float";
                break;
            }
            case ID: {
                s = "ID";
                break;
            }
            case KEYWORD: {
                s = "KEYWORD";
                break;
            }
            case OPERATOR: {
                s = "Operator";
                break;
            }
            case COMMA: {
                s = "COMMA";
                break;
            }
            case QUERY: {
                s = "QUERY";
                break;
            }
            case IDLIST: {
                s = "IDLIST";
                break;
            }
            case CONDLIST: {
                s = "CONDLIST";
                break;
            }
            case COND: {
                s = "COND";
                break;
            }
            case TERM: {
                s = "TERM";
                break;
            }
            case EOI: {
                s = "EOI";
                break;
            }
            case INVALID: {
                s = "INVALID";
                break;
            }
        }
        return s;
    }
}
