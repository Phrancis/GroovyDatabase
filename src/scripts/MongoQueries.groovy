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
//BasicDBObject userDoc = new BasicDBObject("hello", "world").append("foo", "bar")
//users.insert(userDoc)
//println "Count of users: ${users.getCount()}"
//users.remove(userDoc)

/*Select one user*/
//DBObject doc = users.findOne()
//println doc

DBCursor cursor = users.find()
DBObject currentUser

/*Select all users from collection */
while (cursor.hasNext()) {
    currentUser = cursor.next()
    println currentUser
}

/*Remove all users from collection */
//while(cursor.hasNext()) {
//    currentUser = cursor.next()
//    users.remove(currentUser)
//}
