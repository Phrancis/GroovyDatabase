package groovydatabase

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
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
    public UserDocumentBuilder(String userName, String passwordHash, Date dateCreated = new Date()) {
        this.userName = userName
        this.passwordHash = MessageDigest
                .getInstance("MD5")
                .digest(passwordHash.bytes)
                .encodeHex()
                .toString()
        this.dateCreated = dateCreated
    }
    /**
     * Build a user document object.
     * @return BasicDBObjectBuilder
     */
    public begin() {
        def dboBuilder = new BasicDBObjectBuilder()
                .start("user_name", userName)
                .add("password_hash", passwordHash)
                .add("date_created", dateCreated)
        return dboBuilder
    }
    /**
     * Create a separate BasicDBObjectBuilder for details, then add it to the userDocument
     * @param userDocument
     * @param details
     * @return
     */
    public static void addDetails(BasicDBObjectBuilder userDocument, Map details) {
        def detailsBuilder = new BasicDBObjectBuilder()
                .start(details)
        userDocument.add("details", detailsBuilder.get())
    }

    public static DBObject build(BasicDBObjectBuilder userDocument) {
        DBObject userDocumentObject = userDocument.get()
        return userDocumentObject
    }
}