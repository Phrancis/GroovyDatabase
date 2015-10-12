package groovydatabase

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBCollection
import groovy.transform.ToString

import java.security.MessageDigest

@ToString(includeNames = true, includeFields = true)
class UserDocumentBuilder {
    String userName
    String passwordHash
    Date dateCreated
    /**
     * Constructor.
     * TODO: Look into better hashing algorithms to use instead of MD5.
     * @param userName  The name of the new user
     * @param passwordHash  The password after it is hashed.
     * @param dateCreated  The date when the user is created, defaulting to "now" but can be passed another date
     */
    UserDocumentBuilder(String userName, String passwordHash, Date dateCreated = new Date()) {
        this.userName = userName
        this.passwordHash = MessageDigest.getInstance("MD5").digest(passwordHash.bytes).encodeHex().toString()
        this.dateCreated = dateCreated
    }
    /**
     * Build a user document object.
     * @return BasicDBObjectBuilder
     */
    def create() {
        def dboBuilder = new BasicDBObjectBuilder()
                .start("user_name", userName)
                .add("password_hash", passwordHash)
                .add("date_created", dateCreated)
        return dboBuilder
    }
    /**
     * Create a separate BasicDBObjectBuilder for details, then add it to the
     * @param userDocument
     * @param details
     * @return
     */
    static addDetails(BasicDBObjectBuilder userDocument, Map details) {
        def detailsBuilder = new BasicDBObjectBuilder()
                .start(details)
        userDocument.add("details", detailsBuilder.get())
    }
    /**
     * TODO: Move this method to a UsersCollection class
     * Access the "users" MongoDB collection.
     * @return DBCollection
     */
    static DBCollection usersCollection() {
        def usersCollection = new CardshifterDB()
                .getDB()
                .getCollection("users")
        return usersCollection
    }
}