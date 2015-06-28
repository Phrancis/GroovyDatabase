/**
 * @author github.com/Phrancis
 */
import groovy.sql.Sql
// import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import groovy.beans.Bindable
// import static javax.swing.JFrame.EXIT_ON_CLOSE
import java.awt.*



/**
 * Define possible input fields.
 */
@Bindable
class UserInput {
    String word;
    String num;
}

/**
 * Initialize values for input fields.
 */
def userInput = new UserInput(
        word: '',
        num: 0)

def swingBuilder = new SwingBuilder()

swingBuilder.edt {

    // style of form
    lookAndFeel 'nimbus'

    // frame sizes
    def width = 400
    def height = 300

    // outer frame
    frame (title: 'Application',
            size: [width, height],
            show: true,
            locationRelativeTo: null ) {

        borderLayout(vgap: 5)

        // inner panel
        panel(constraints:
                BorderLayout.CENTER,
                border: compoundBorder(
                        [emptyBorder(10),
                         titledBorder('Enter data:')])) {
            tableLayout {
                tr {
                    td { label 'Enter a word: ' }
                    td { textField id:'word', columns: 20 }
                }
                tr {
                    td { label 'Enter a number: ' }
                    td { textField id:'num', columns: 20 }
                }
            }
        }
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Print', actionPerformed: {
                println """Word: ${userInput.word}"""
                println """Number: ${userInput.num}"""
            }
        }
        // Bind the fields to the bean
        bean userInput, word: bind { word.text }
        bean userInput, num: bind { num.text }
    }
}


/*def dbUrl      = 'jdbc:postgresql://localhost/GroovyTest'
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
        COMMIT;"""*/


/*def sqlInsert = { List<Object> params ->
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
}*/
/*
sql.close()*/
