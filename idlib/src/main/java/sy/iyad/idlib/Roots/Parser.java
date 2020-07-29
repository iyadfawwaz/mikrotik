package sy.iyad.idlib.Roots;

        import java.util.Arrays;
        import java.util.LinkedList;
        import java.util.List;

        import static sy.iyad.idlib.Roots.Scanner.Token.EQUALS;
        import static sy.iyad.idlib.Roots.Scanner.Token.LEFT_BRACKET;
        import static sy.iyad.idlib.Roots.Scanner.Token.LESS;
        import static sy.iyad.idlib.Roots.Scanner.Token.MORE;
        import static sy.iyad.idlib.Roots.Scanner.Token.NOT;
        import static sy.iyad.idlib.Roots.Scanner.Token.NOT_EQUALS;
        import static sy.iyad.idlib.Roots.Scanner.Token.TEXT;

/**
 * Parse the pseudo-command line into command objects.
 *
 * @author GideonLeGrange
 */
class Parser {

    /**
     * parse the given bit of text into a Command object
     */
    static Command parse(String text) throws ParseException {
        Parser parser = new Parser(text);
        return parser.parse();
    }

    /**
     * run parse on the internal data and return the command object
     */
    private Command parse() throws ParseException {
        command();
        while (!is(Scanner.Token.WHERE, Scanner.Token.RETURN, Scanner.Token.EOL)) {
            param();
        }
        if (token == Scanner.Token.WHERE) {
            where();
        }
        if (token == Scanner.Token.RETURN) {
            returns();
        }
        expect(Scanner.Token.EOL);
        return cmd;
    }

    private void command() throws ParseException {
        StringBuilder path = new StringBuilder();
        do {
            expect(Scanner.Token.SLASH);
            path.append("/");
            next();
            expect(TEXT);
            path.append(text);
            next();
        } while (token == Scanner.Token.SLASH);
        cmd = new Command(path.toString());
    }

    private void param() throws ParseException {
        String name = text;
        next();
        if (token == EQUALS) {
            next();
            StringBuilder val = new StringBuilder();
            if (token == Scanner.Token.PIPE) { // handle cases like  hotspot=!auth
                val.append(token);
                next();
            }
            expect(TEXT);
            val.append(text);
            next();
            while (is(Scanner.Token.COMMA, Scanner.Token.SLASH)) {
                val.append(token);
                next();
                expect(TEXT);
                val.append(text);
                next();
            }
            cmd.addParameter(new Parameter(name, val.toString()));
        } else {
            cmd.addParameter(new Parameter(name));
        }
    }

    private void where() throws ParseException {
        next(); // swallow the word "where"
        expr();
    }

    private void expr() throws ParseException {
        expect(NOT, TEXT, LEFT_BRACKET);
        switch (token) {
            case NOT:
                notExpr();
                break;
            case TEXT: {
                String name = text;
                next();
                expect(EQUALS, LESS, MORE, NOT_EQUALS);
                switch (token) {
                    case EQUALS:
                        eqExpr(name);
                        break;
                    case NOT_EQUALS:
                        notExpr(name);
                        break;
                    case LESS:
                        lessExpr(name);
                        break;
                    case MORE:
                        moreExpr(name);
                        break;
                    default:
                        hasExpr(name);
                }
            }
            break;
            case LEFT_BRACKET:
                nestedExpr();
                break;
        }
        // if you get here, you had a expression, see if you want more.
        switch (token) {
            case AND:
                andExpr();
                break;
            case OR:
                orExpr();
                break;
        }
    }

    private void nestedExpr() throws ParseException {
        expect(Scanner.Token.LEFT_BRACKET);
        next();
        expr();
        expect(Scanner.Token.RIGHT_BRACKET);
        next();
    }

    private void andExpr() throws ParseException {
        next(); // eat and
        expr();
        cmd.addQuery("?#&");
    }

    private void orExpr() throws ParseException {
        next(); // eat or
        expr();
        cmd.addQuery("?#|");
    }


    private void notExpr() throws ParseException {
        next(); // eat not
        expr();
        cmd.addQuery("?#!");
    }

    private void eqExpr(String name) throws ParseException {
        next(); // eat =
        expect(Scanner.Token.TEXT);
        cmd.addQuery(String.format("?%s=%s", name, text));
        next();
    }

    private void lessExpr(String name) throws ScanException {
        next(); // eat <
        cmd.addQuery(String.format("?<%s=%s", name, text));
        next();
    }

    private void notExpr(String name) throws ScanException {
        next(); // eat !=
        cmd.addQuery(String.format("?%s=%s", name, text));
        cmd.addQuery("?#!");
        next();
    }

    private void moreExpr(String name) throws ScanException {
        next(); // eat >
        cmd.addQuery(String.format("?>%s=%s", name, text));
        next();
    }

    private void hasExpr(String name) {
        cmd.addQuery(String.format("?%s", name));
    }

    private void returns() throws ParseException {
        next();
        expect(Scanner.Token.TEXT);
        List<String> props = new LinkedList<>();
        while (!(token == Scanner.Token.EOL)) {
            if (token != Scanner.Token.COMMA) {
                props.add(text);
            }
            next();
        }
        cmd.addProperty(props.toArray(new String[props.size()]));
    }

    private void expect(Scanner.Token... tokens) throws ParseException {
        if (!is(tokens))
            throw new ParseException(String.format("Expected %s but found %s at position %d", Arrays.asList(tokens), this.token, scanner.pos()));
    }

    private boolean is(Scanner.Token... tokens) {
        for (Scanner.Token want : tokens) {
            if (this.token == want) return true;
        }
        return false;
    }

    /**
     * move to the next token returned by the scanner
     */
    private void next() throws ScanException {
        token = scanner.next();
        while (token == Scanner.Token.WS) {
            token = scanner.next();
        }
        text = scanner.text();
    }

    private Parser(String line) throws ScanException {
        line = line.trim();
        scanner = new Scanner(line);
        next();
    }

    private final Scanner scanner;
    private Scanner.Token token;
    private String text;
    private Command cmd;

}
