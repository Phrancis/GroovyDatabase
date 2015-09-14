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

/* OUTPUT:
1. Using BasicDBObject
userDoc1: { "method" : "BasicDBObject" , "type" : "user" , "name" : "Phrancis" , "details" : { "created" : "2015-09-12" , "country" : "USA"}}
2. Using BasicDBObjectBuilder
userDoc2: com.mongodb.BasicDBObjectBuilder@8c11eee
3. Using HashMap
userMap: [method:HashMap, name:Phrancis, details:[country:USA, created:2015-09-12], type:user]
4. Using JSON.parse
userJsonObject: { "method" : "JSON.parse" , "type" : "user" , "name" : "Phrancis" , "details" : { "country" : "USA" , "created" : "2015-09-12"}}

SELECT * FROM collection;
Document 1 in DBCollection{database=DB{name='local'}, name='testCollection'}:
{ "_id" : { "$oid" : "55f5a84ae2d644685ef98c4d"} , "method" : "BasicDBObject" , "type" : "user" , "name" : "Phrancis" , "details" : { "created" : "2015-09-12" , "country" : "USA"}}
Document 2 in DBCollection{database=DB{name='local'}, name='testCollection'}:
{ "_id" : { "$oid" : "55f5a84ae2d644685ef98c4e"} , "method" : "BasicDBObjectBuilder" , "type" : "user" , "name" : "Phrancis" , "details" : { "created" : "2015-09-12" , "country" : "USA"}}
Document 3 in DBCollection{database=DB{name='local'}, name='testCollection'}:
{ "_id" : { "$oid" : "55f5a84ae2d644685ef98c4f"} , "method" : "HashMap" , "name" : "Phrancis" , "details" : { "country" : "USA" , "created" : "2015-09-12"} , "type" : "user"}
Document 4 in DBCollection{database=DB{name='local'}, name='testCollection'}:
{ "_id" : { "$oid" : "55f5a84ae2d644685ef98c50"} , "method" : "JSON.parse" , "type" : "user" , "name" : "Phrancis" , "details" : { "country" : "USA" , "created" : "2015-09-12"}}
 */