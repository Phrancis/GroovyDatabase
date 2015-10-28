package groovydatabase

import com.mongodb.DBCollection
import com.mongodb.DBObject
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class UsersCollection {
    DBCollection usersCollection

    public UsersCollection() {
         usersCollection = new CardshifterDB()
                .getDB()
                .getCollection("users")
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
        def userExists = usersCollection
                .find( { user_name : user.getUserName() } )
        if (userExists) {
            println "User \"${user.getUserName()}\" already exists."
//            /*return*/println userExists.toString()
        } else {
            usersCollection.insert(user)
            println "User \"${user.getUserName()}\" inserted."
//            /*return*/println usersCollection.find( { user_name : user.getUserName() } ).toString()
        }
    }
}
