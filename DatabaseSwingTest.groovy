/**
 * Created by francisveilleux-gaboury on 6/24/15.
 */
import groovy.sql.Sql
// import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import groovy.beans.Bindable
// import static javax.swing.JFrame.EXIT_ON_CLOSE
import java.awt.*

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

//String word

@Bindable
class UserInput { public
    string,
    num,
    decimal,
    datetime
}

def userInput = new UserInput(
        string: null,
        num: null,
        decimal: null,
        datetime: null
)

new SwingBuilder().edt {
    lookAndFeel 'nimbus'
    // frame size
    def width = 350
    def height = 230
    frame (title: 'Data Input Application',
            size: [width, height],
            show: true,
            locationRelativeTo: null ) {
        borderLayout(vgap: 5)
        panel(constraints: BorderLayout.CENTER,
            border: compoundBorder([emptyBorder(10),
            titledBorder('Please enter values:')])) {
            tableLayout {
                tr {
                    td { label 'String: ' }
                    td { textField id:'string', columns: 20 }
                    def string = {string}
                    bean userInput, {string}: bind { userinput.string }
                }
                tr {
                    td { label 'Num: ' }
                    td { textField id:'num', columns: 20 }
                    def num = {num}
                    bean userInput, {num}: bind { userInput.num }
                }
            }
        }
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Print!', actionPerformed: {
                println """String: ${userInput.string}"""
                println """Num: ${userInput.num}"""}
        }
        // Bind the text fields to the beans

    }
}


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
sql.close()