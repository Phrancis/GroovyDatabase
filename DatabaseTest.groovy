/**
 * Created by francisveilleux-gaboury on 6/24/15.
 */
import groovy.sql.Sql

def dbUrl      = "jdbc:postgresql://localhost/GroovyTest"
def dbUser     = "Phrancis"
def dbPassword = "test"
def dbDriver   = "org.postgresql.Driver"

def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)

println "Sql Instance: " + sql

sql.execute """
        DROP TABLE IF EXISTS test;
        CREATE TABLE test (
            id SERIAL,
            string TEXT,
            number INTEGER,
            decimal NUMERIC,
            datetime TIMESTAMP
            );
        """

def params = ['''');drop table test;--''', 42, 3.14159, 'NOW()']

sql.execute """
        INSERT INTO test (string, number, decimal, datetime)
            VALUES (?, ?, ?, CAST(? AS TIMESTAMP));
        """, params

sql.eachRow("SELECT * FROM test;") { row ->
    println """
        The row Id is: ${row.id}
        The string is: ${row.string}
        The number is: ${row.number}
        The decimal is: ${row.decimal}
        The date-time is: ${row.datetime}
    """
}
/*sql.eachRow("SELECT * FROM test;") { row ->
    print ${row}
}*/
sql.close()