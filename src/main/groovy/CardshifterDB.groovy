package groovydatabase

import com.mongodb.DB
import com.mongodb.MongoClient

class CardshifterDB {
    String host
    int port
    String databaseName

    CardshifterDB(String host = "localhost", int port = 27017, String databaseName = "local") {
        this.host = host
        this.port = port
        this.databaseName = databaseName
    }

    static DB getDB(String host = "localhost", int port = 27017, String databaseName = "local") {
        new MongoClient(host, port)
                .getDB(databaseName)
    }

}

