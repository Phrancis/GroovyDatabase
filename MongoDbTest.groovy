@Grapes(
        @Grab(group='org.mongodb', module='mongo-java-driver', version='3.0.3')
)
import org.hibernate.*
import com.mongodb.*

MongoClient mongoClient = new MongoClient("localhost", 27017)
DB db = mongoClient.getDB("local");
DBCollection collection = db.getCollection("testCollection")

println "mongoClient: $mongoClient"
println "db: $db"
println "collection: $collection"

def i // counter

// Clear collection
collection.remove(new BasicDBObject())

// 1. Using BasicDBObject
println "1. Using BasicDBObject"

BasicDBObject userDoc1 = new BasicDBObject()
    userDoc1.put("type", "user")
    userDoc1.put("name", "Phrancis")
BasicDBObject userDetails1 = new BasicDBObject()
    userDetails1.put("created", "2015-09-12")
    userDetails1.put("country", "USA")
userDoc1.put("userDetails1", userDetails1)

println "userDoc1: ${userDoc1.toString()}"

collection.insert(userDoc1)

DBCursor cursor1 = collection.find()
i = 1
while (cursor1.hasNext()) {
    println "Document $i in collection: ${cursor1.next().toString()}"
    i++
}
println()
// Clear collection
collection.remove(new BasicDBObject());

// 2. Using BasicDBObjectBuilder
println "2. Using BasicDBObjectBuilder"

BasicDBObjectBuilder userDoc2 = BasicDBObjectBuilder.start()
    .add("type", "user")
    .add("name", "Phrancis")
BasicDBObjectBuilder userDetails2 = BasicDBObjectBuilder.start()
    .add("created", "2015-09-12")
    .add("country", "USA")
userDoc2.add("detail", userDetails2.get())
collection.insert(userDoc2.get())

println "userDoc2: ${userDoc2.toString()}"

DBCursor cursor2 = collection.find()
i = 1
while (cursor2.hasNext()) {
    println "Document $i in collection: ${cursor2.next().toString()}"
    i++
}
println()
// Clear collection
collection.remove(new BasicDBObject());

