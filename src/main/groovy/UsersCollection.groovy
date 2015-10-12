package groovydatabase

import com.mongodb.DBCollection
import groovy.transform.ToString
import groovydatabase.CardshifterDB

@ToString(includeNames = true, includeFields = true)
class UsersCollection {
    DBCollection usersCollection

    private UsersCollection() {
         usersCollection = new CardshifterDB()
                .getDB()
                .getCollection("users")
    }
}
