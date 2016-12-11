
/**
 * Test class to test different query strings
 *
 * @author Matthew
 */
public class Test {

    /**
     * Tests various inputs
     *
     * @param args
     */
    public static void main(String args[]) {
        // testing the parser
        Parser parser = new Parser("SELECT C1,C2 FROM T1 WHERE C1=5.23");
        parser.run();
        parser = new Parser("SELECT col1, c99 FROM tab1, t2, t9");
        parser.run();
        parser = new Parser("SELECT c1, c2 FROM t1, t2 WHERE c3 = 7");
        parser.run();
        parser = new Parser("SELECT c1, c2 FROM t1, t2 WHERE c3<4.5");
        parser.run();
        parser = new Parser("SELECT c1, c2 FROM t1, t2 WHERE c3>c123");
        parser.run();
        parser = new Parser("SELECT c1 FROM t1,t2 WHERE t1a=t2a");
        parser.run();
        parser = new Parser("SELECT c1 FROM t1 WHERE c2>2.78 AND c2<5.9245");
        parser.run();
        parser = new Parser("SELECT c1,c2a,c3b2 FROM t1,t2,t3 WHERE c2>2.78 AND c2<5.9245 AND c3=3");
        parser.run();

    }
}
