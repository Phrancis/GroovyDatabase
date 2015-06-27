/**
 * Created by francisveilleux-gaboury on 6/24/15.
 */
import groovy.sql.Sql
import groovy.json.JsonSlurper

def dbUrl      = 'jdbc:postgresql://localhost/GroovyTest'
def dbUser     = 'Phrancis'
def dbPassword = 'test'
def dbDriver   = 'org.postgresql.Driver'

def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)

println 'Sql Instance: ' + sql

sql.execute 'SET SEARCH_PATH TO groovy_test;'

sql.execute '''
        START TRANSACTION;
        DROP TABLE IF EXISTS test;
        CREATE TABLE test (
            id SERIAL,
            string TEXT,
            number INTEGER,
            decimal NUMERIC,
            datetime TIMESTAMP
            );
        COMMIT;'''

def sqlInsert = { List<Object> params ->
    sql.execute """
        START TRANSACTION;
        INSERT INTO test (string, number, decimal, datetime)
            VALUES (?, ?, ?, CAST(? AS TIMESTAMP));
        COMMIT;""", params
}

def params1 = ['''');DROP TABLE test;--''', 42, 3.14159, 'NOW()']
def params2 = ['Hello, World!', 99999999, 0.1209823098234, '2015-06-25']

sqlInsert(params1)
sqlInsert(params2)

sql.eachRow('SELECT * FROM test;') { row ->
    println """
The row Id is: ${row.id}
The string is: ${row.string}
The number is: ${row.number}
The decimal is: ${row.decimal}
The date-time is: ${row.datetime}"""
}
sql.close()