package groovydatabase

import com.mongodb.DBCollection
import groovy.json.*
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class UsersCollection {
    DBCollection usersCollection

    private UsersCollection() {
         usersCollection = new CardshifterDB()
                .getDB()
                .getCollection("users")
    }
    void deleteUser(String userName) {
        def jsonParse = new JsonSlurper()
        def userExists = usersCollection.findOne( { user_name : userName })
        if (!userExists) {
            println "No such user to delete: \"$userName\""
        } else {
            // using BasicDBObject here because Groovy somehow
            // passes a closure instead of allowing the standard
            // MongoDB API syntax
            //BasicDBObject userToRemove = new BasicDBObject("user_name", userName)
                    //.put("user_name", userName)
            usersCollection.remove(userExists)
            println "User \"$userName\" deleted."
        }
    }

    void insertUserIfNotExists(UserDocumentBuilder user) {
        def userExists = usersCollection.find( { user_name : user.getUserName() } )
        if (userExists) {
            println "User \"${user.getUserName()}\" already exists."
            //return userExists.toString()
        } else {
            usersCollection.insert(user)
            println "User \"${user.getUserName()}\" inserted."
            //return usersCollection.find( { user_name : user.getUserName() } ).toString()
        }
    }
}
