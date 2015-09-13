@Grapes(
        @Grab(group='org.mongodb', module='mongo-java-driver', version='3.0.3')
)
import org.hibernate.*
import com.mongodb.*
import com.mongodb.util.JSON

MongoClient mongoClient = new MongoClient("localhost", 27017)
DB db = mongoClient.getDB("local");
DBCollection collection = db.getCollection("testCollection")

println "mongoClient: $mongoClient"
println "db: $db"
println "collection: $collection"

// DELETE FROM collection;
collection.remove(new BasicDBObject())

// 1. Using BasicDBObject
println "1. Using BasicDBObject"
BasicDBObject userDoc1 = new BasicDBObject()
    userDoc1.put("method", "BasicDBObject")
    userDoc1.put("type", "user")
    userDoc1.put("name", "Phrancis")
BasicDBObject userDetails1 = new BasicDBObject()
    userDetails1.put("created", "2015-09-12")
    userDetails1.put("country", "USA")
userDoc1.put("details", userDetails1)

println "userDoc1: ${userDoc1.toString()}"
collection.insert(userDoc1)

// 2. Using BasicDBObjectBuilder
println "2. Using BasicDBObjectBuilder"

BasicDBObjectBuilder userDoc2 = BasicDBObjectBuilder.start()
    .add("method", "BasicDBObjectBuilder")
    .add("type", "user")
    .add("name", "Phrancis")
BasicDBObjectBuilder userDetails2 = BasicDBObjectBuilder.start()
    .add("created", "2015-09-12")
    .add("country", "USA")
userDoc2.add("details", userDetails2.get())

println "userDoc2: ${userDoc2.toString()}"
collection.insert(userDoc2.get())

// 3. Using HashMap
println "3. Using HashMap"

Map<String, Object> userMap = new HashMap<String, Object>()
    userMap.put("method", "HashMap")
    userMap.put("type", "user")
    userMap.put("name", "Phrancis")
Map<String, Object> userDetailMap = new HashMap<String, Object>()
    userDetailMap.put("created", "2015-09-12")
    userDetailMap.put("country", "USA")
userMap.put("details", userDetailMap)

println "userMap: ${userMap.toString()}"
collection.insert(new BasicDBObject(userMap))

// 4. Using JSON.parse
println "4. Using JSON.parse"

String userJson = '''{
    "method" : "JSON.parse",
    "type" : "user",
    "name" : "Phrancis" ,
    "details" : {
        "country" : "USA" ,
        "created" : "2015-09-12"
    }
}'''
DBObject userJsonObject = (DBObject)JSON.parse(userJson)
println "userJsonObject: ${userJsonObject.toString()}"
collection.insert(userJsonObject)

// SELECT * FROM collection;
println "\nSELECT * FROM collection;"

DBCursor cursor = collection.find()
i = 1
while (cursor.hasNext()) {
    println "Document $i in $collection: \n${cursor.next().toString()}"
    i++
}

// DELETE FROM collection;
collection.remove(new BasicDBObject());