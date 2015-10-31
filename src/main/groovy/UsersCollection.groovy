package groovydatabase

import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.BasicDBObject
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class UsersCollection {
    DBCollection usersCollection

    public UsersCollection() {
         usersCollection = new CardshifterDB()
                .getDB()
                .getCollection("users")
    }
    public getDBCollection() {
        return usersCollection.getCollection("users")
    }

    void deleteUser(UserDocumentBuilder user) {
        def userExists = usersCollection
                .find( { user_name : user.getUserName() } )
        if (!userExists) {
            println "No such user to delete: \"${user.getUserName()}\""
        } else {
            usersCollection.remove(user.build())
            println "User \"${user.getUserName()}\" deleted."
        }
    }

    void insertUserIfNotExists(UserDocumentBuilder user) {
        BasicDBObject userExists = new BasicDBObject()
        userExists.put("user_name", user.getUserName())
        DBCursor cursor = usersCollection.find(userExists)

        if (cursor.hasNext()) {
            println "User \"${user.getUserName()}\" already exists."
//            /*return*/println userExists.toString()
        } else {
            usersCollection.insert(user.build())
            println "User \"${user.getUserName()}\" inserted."
//            /*return*/println usersCollection.find( { user_name : user.getUserName() } ).toString()
        }
    }
}
