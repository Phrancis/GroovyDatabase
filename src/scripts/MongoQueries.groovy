import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.MongoClient
import com.mongodb.DBCursor

/**
 * Ad hoc queries to check the MongoDB test database.
 */

DB db = new MongoClient("localhost", 27017).getDB("local")
DBCollection users = db.getCollection("users")


println "Count of users: ${users.getCount()}"
/*Test insert*/
//BasicDBObject userDocFoo = new BasicDBObject("user_name": "foo").append("hello", 42)
//BasicDBObject userDocBar = new BasicDBObject("user_name": "bar").append("world", null)
//users.insert(userDocFoo)
//users.insert(userDocBar)

//println "Count of users: ${users.getCount()}"
//users.remove(userDocFoo)
//users.remove(userDocBar)

/*Select one user*/
//DBObject doc = users.findOne()
//println doc

DBCursor cursor = users.find()
DBObject currentUser

/*Select all users from collection */
//while (cursor.hasNext()) {
//    currentUser = cursor.next()
//    println currentUser
//}

/*Select with a where query*/
String userName = "foo"
BasicDBObject whereUserNameEquals = new BasicDBObject()
whereUserNameEquals.put("user_name", userName)
DBCursor cursor2 = users.find(whereUserNameEquals)
while (cursor2.hasNext()) {
    println cursor2.next()
}


/*Remove one user from collection*/
//String userNameToDelete = "bar"
//BasicDBObject userToDelete = new BasicDBObject()
//userToDelete.put("user_name", userNameToDelete)
//users.remove(userToDelete)

/*Remove all users from collection */
//while(cursor.hasNext()) {
//    currentUser = cursor.next()
//    users.remove(currentUser)
//}
