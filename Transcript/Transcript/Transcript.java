/*
 * Matthew Chin
 * mjc714
 * CSE 341 Hw 4 Part 2
 */

//imports
import java.sql.*;
import java.util.Scanner;

//prints the transcript of a student with a valid id from the database
public class Transcript {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        Exception exception = null; // used to store a thrown exception
        Scanner scan = new Scanner(System.in); //Scanner
        //variable declarations
        String userName = null;
        String passWord = null;
        String subString = "";
        //prepared statements variables
        PreparedStatement prpStmtName = null;
        PreparedStatement prpStmtTranScript = null;
        //strings to pass to get data from the database
        String searchStr = "select id, name from student where name like " + "'%";
        String tranScriptStr = "select year, semester, dept_name, course_id, title, grade from takes join course using(COURSE_ID) where"
                + " takes.ID = ? order by year asc, semester desc, course_id asc";
        //standard variables to establish connection to database
        Statement stmt = null;
        Connection conn = null;
        int tempID = 0; //store found ID
        boolean done = false;
        boolean done2 = false;
        do {
            try {
                //get user id, can read up to next line, regardless will
                //hit a check for the password even if the username is nonsense
                System.out.print("Enter Oracle user id ->");
                if (scan.hasNextLine()) {
                    userName = scan.nextLine();
                    //get password
                    System.out.print("Enter Oracle password for " + userName + "->");
                    if (scan.hasNextLine()) {
                        passWord = scan.nextLine();
                    }
                }
                //establish connection on valid login
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", userName, passWord);

                //get a substring to search for various names in database matching it
                do {
                    System.out.print("Input name search substring->");
                    subString = scan.nextLine();
                    //ignore bad input
                    if (subString.matches(".*-?[0-9]+.?[0-9]*.*\\s*") || subString.contains("'") || subString.matches("!@#$%^&*()\\s*")
                            || subString.matches("\\r*\\t*\\p{ASCII}") || subString.isEmpty()) {
                        System.out.println("No (+/-)numbers or ' nor weird symbols allowed, try again");
                        searchStr = "select id, name from student where name like " + "'%"; //reset search string
                        continue;
                    } else if (!subString.equals("")) {
                        searchStr += subString + "%'"; //close sql query
                        prpStmtName = conn.prepareStatement(searchStr);
                        prpStmtName.executeQuery();
                    }
                    try {
                        if (!prpStmtName.getResultSet().next()) {
                            System.out.println("No matches. Try again");
                            searchStr = "select id, name from student where name like " + "'%";
                            done2 = false;
                        } else {
                            done2 = true;
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage() + " Retype your input");
                        searchStr = "select id, name from student where name like " + "'%";
                        done2 = false;
                    }
                } while (!done2);
                System.out.println("Here is a list of all matching id, name tuples.");
                System.out.printf("%-8s%-16s\n", "ID", "Name");
                //loops through each tuple in the result set printint it out
                do {
                    System.out.printf("%-8s%-16s\n", prpStmtName.getResultSet().getInt(1), prpStmtName.getResultSet().getString(2));
                } while (prpStmtName.getResultSet().next());
                searchStr = "select id, name from student where name like " + "'%";
                done2 = false; // reset done2
                System.out.println("Enter the student ID for whose transcript you seek.");
                //get an id for the student to print the transcript for, then do it
                do {
                    System.out.print("Please enter an integer between 0 and 99999->");
                    subString = scan.nextLine();
                    if (subString.matches("[0-9]+")) { //check for SOME number input, not neccessarily in range
                        int id = Integer.parseInt(subString); //convert to int
                        if (id >= 0 && id <= 99999) { // check range of integer input
                            prpStmtTranScript = conn.prepareStatement(tranScriptStr); // load the string
                            prpStmtTranScript.setInt(1, id); // set ? to id in where clause
                            prpStmtTranScript.executeQuery(); //execute query
                            tempID = id;
                            subString = "";
                            done2 = true;
                        }
                        try {
                            if (!prpStmtTranScript.getResultSet().next()) {
                                System.out.println("No matches. Try again");
                                done2 = false;
                            }
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage() + " Retype your input");
                            done2 = false;
                        }
                    } else {
                        System.out.println("Invalid input, try again");
                        done2 = false;
                    }
                } while (!done2);
                //output transcript
                System.out.println("Transcript for student " + tempID);
                System.out.printf("%-4s\t%-8s\t%-32s\t%-9s\t%-50s\t%-2s\n", "Year", "Semester", "Department", "Course_ID", "Course", "Grade");
                do {
                    System.out.printf("%-4s\t%-8s\t%-32s\t%-9s\t%-50s\t%-2s\n", prpStmtTranScript.getResultSet().getInt(1), prpStmtTranScript.getResultSet().getString(2), prpStmtTranScript.getResultSet().getString(3),
                            prpStmtTranScript.getResultSet().getInt(4), prpStmtTranScript.getResultSet().getString(5), prpStmtTranScript.getResultSet().getString(6));
                } while (prpStmtTranScript.getResultSet().next());
            } catch (SQLException e) { //catch sql exceptions such as bad login etc. will result in a fresh login attempt
                System.out.println("This went wrong: " + e.getMessage());
                System.out.println("Restarting...");
                exception = e;
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot find class " + e.getMessage());
                exception = e;
            } catch (NumberFormatException e) {
                System.out.println("Bad number " + e.getMessage());
                System.out.println("Restarting...");
                exception = e;
            } finally { //connections closed here, restart
                if (stmt != null) {
                    stmt.close();
                    System.out.println("Statement closed!");
                }
                if (conn != null) { //check if any exceptions are thrown upon closing connection
                    try {
                        conn.close();
                        System.out.println("Connection closed!");
                    } catch (SQLException e) {
                        System.out.println("This went wrong!: " + e.getMessage());
                        System.out.println("Restarting...");
                        conn.close();
                        exception = e;
                    } finally {
                        if (exception != null) {
                            exception = null;
                            continue;
                        }
                    }
                } else if (conn == null && exception != null) {
                    continue;
                }
                done = true;
            }
        } while (!done);
    }
}
