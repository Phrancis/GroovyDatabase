/**
 * @author github.com/Phrancis
 */
import groovy.sql.Sql
import groovy.swing.SwingBuilder
import groovy.beans.Bindable
import java.awt.*



/**
 * Define possible input fields.
 */
@Bindable
class UserInput {
    String firstName;
    String lastName;
}

/**
 * Initialize values for input fields.
 */
def userInput = new UserInput(
        firstName: null,
        lastName: null)

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
            tableLayout {
                tr {
                    td { label 'First name:' }
                    td { textField id:'firstName', columns: 20 }
                }
                tr {
                    td { label 'Last name:' }
                    td { textField id:'lastName', columns: 20 }
                }
            }
        }
        // testing code only
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Print', actionPerformed: {
                println """You entered:"""
                println """First name: ${userInput.firstName}"""
                println """Last name: ${userInput.lastName}"""
            }
        }
        // Bind the fields to the bean
        bean userInput, firstName: bind { firstName.text }
        bean userInput, lastName: bind { lastName.text }
    }
}


def dbUrl      = 'jdbc:postgresql://localhost/GroovyTest'
def dbUser     = 'Phrancis'
def dbPassword = 'test'
def dbDriver   = 'org.postgresql.Driver'

def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)

println 'Sql Instance: ' + sql

sql.execute 'SET SEARCH_PATH TO groovy_test;'

sql.execute """
        START TRANSACTION;
        DROP TABLE IF EXISTS test;
        CREATE TABLE test (
            id SERIAL,
            string TEXT,
            num INTEGER,
            decimal NUMERIC,
            datetime TIMESTAMP
            );
        COMMIT;"""


def sqlInsert = { List<Object> params ->
    sql.execute """
        START TRANSACTION;
        INSERT INTO test (string, number, decimal, datetime)
            VALUES (?, ?, ?, CAST(? AS TIMESTAMP));
        COMMIT;""", params
}

sql.eachRow('SELECT * FROM test;') { row ->
    println """
The row Id is: ${row.id}
The string is: ${row.string}
The number is: ${row.number}
The decimal is: ${row.decimal}
The date-time is: ${row.datetime}"""
}
sql.close()
