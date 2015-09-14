/**
 * @author github.com/Phrancis
 */
import groovy.sql.Sql
import groovy.swing.SwingBuilder
import java.awt.*

/**
 * SQL queries to be called by the Swing application.
 * @TODO: Extract queries to a separate file
 */
def createTestTable = """
        START TRANSACTION;
        DROP TABLE IF EXISTS test;
        CREATE TABLE test (
            id SERIAL,
            first_name TEXT,
            last_name TEXT,
            phone TEXT,
            date_of_birth DATE
            );
        COMMIT;"""

def insertQuery = """
        START TRANSACTION;
        INSERT INTO test (first_name, last_name, phone, date_of_birth)
            VALUES ( ?, ?, ?, CAST(? AS DATE) );
        COMMIT;"""

/**
 * Define input field variables.
 */
class UserInput {
    String firstName
    String lastName
    String phone
    String dateOfBirth
}

/**
 * Initialize values for input fields.
 */
def userInput = new UserInput(
        firstName: null,
        lastName: null,
        phone: null,
        dateOfBirth: null)

/**
 * Swing application starts here.
 */
def swingBuilder = new SwingBuilder()
swingBuilder.edt {

    // style of form
    lookAndFeel 'nimbus'

    // outer frame size
    def width = 400
    def height = 300

    // outer frame
    frame (title: 'User information',
            size: [width, height],
            show: true,
            locationRelativeTo: null ) {

        borderLayout(vgap: 5)

        // inner panel
        panel(constraints:
                BorderLayout.CENTER,
                border: compoundBorder(
                        [emptyBorder(10),
                         titledBorder('Complete the following form:')])) {
            // input fields
            tableLayout {
                tr {
                    td { label 'First name:' }
                    td { textField id:'firstName', columns: 20 }
                }
                tr {
                    td { label 'Last name:' }
                    td { textField id:'lastName', columns: 20 }
                }
                tr {
                    td { label 'Phone:' }
                    td { textField id:'phone', columns: 20 }
                }
                tr {
                    td { label 'Date of birth:' }
                    td { textField id:'dateOfBirth', columns: 20 }
                }
            }
        }
        /**
         * Confirm user input by printing to console
         * @TODO Include this in the GUI
         */
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Submit', actionPerformed: {
                println """
You entered:
First name: ${userInput.firstName}
Last name: ${userInput.lastName}
Phone: ${userInput.phone}
Date of birth: ${userInput.dateOfBirth}
                """

                /**
                 * Open DB connection, create a table, insert data.
                 * @TODO Extract credentials to configuration file.
                 * @TODO Extract SQL queries to a separate file
                 */
                def dbUrl      = 'jdbc:postgresql://localhost/GroovyTest'
                def dbUser     = 'Phrancis'
                def dbPassword = 'test'
                def dbDriver   = 'org.postgresql.Driver'

                def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)

                println 'Sql Instance: ' + sql

                sql.execute 'SET SEARCH_PATH TO groovy_test;'

                sql.execute createTestTable

                def personData = [userInput.firstName, userInput.lastName, userInput.phone, userInput.dateOfBirth]

                sql.execute insertQuery, personData

                /**
                 * Confirm that data was inserted successfully.
                 * For admin purposes only, no need to add to GUI.
                 */

                sql.eachRow('''SELECT 1
                                  id
                                , first_name
                                , last_name
                                , phone
                                , date_of_birth
                                FROM test
                                ORDER BY id DESC;''') { row ->
                    println """
New data appended to table:
Person Id: ${row.id}
Name: ${row.first_name} ${row.last_name}
Phone: ${row.phone}
Date of birth: ${row.date_of_birth}"""
                }

                sql.close()

            }
        }
        // Bind the fields to the bean
        bean userInput, firstName: bind { firstName.text }
        bean userInput, lastName: bind { lastName.text }
        bean userInput, phone: bind { phone.text }
        bean userInput, dateOfBirth: bind { dateOfBirth.text }
    }
}
