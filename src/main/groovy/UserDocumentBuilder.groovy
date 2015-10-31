package groovydatabase

import com.mongodb.BasicDBObjectBuilder
import groovy.transform.ToString

import java.security.MessageDigest

/**
 * The UserDocumentBuilder is a builder pattern to facilitate the creation of user documents for insertion into
 * a document database. At any point after calling `begin()` on the builder instance, we can `get()` on the BasicDBObjectBuilder
 * to obtain a DBObject ready to be inserted or otherwise used in the document database.
 */
@ToString(includeNames = true, includeFields = true)
class UserDocumentBuilder {

    String userName
    String passwordHash
    Date dateCreated
    BasicDBObjectBuilder userDocumentBuilder = new BasicDBObjectBuilder()

    /**
     * Constructor.
     * TODO: Look into better hashing algorithms to use instead of MD5.
     * @param userName  The name of the new user
     * @param passwordHash  The password after it is hashed
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
     * Start the user document builder and add in the basic information from constructor.
     * @return BasicDBObjectBuilder  the user DBObject builder
     */
    public BasicDBObjectBuilder begin() {
        def builder = userDocumentBuilder
                .start("user_name", userName)
                .add("password_hash", passwordHash)
                .add("date_created", dateCreated)
        return builder
    }
    /**
     * Create a separate BasicDBObjectBuilder for details, then add it to the userDocumentBuilder
     * @param userDocumentBuilder  The initial UserDocumentBuilder
     * @param details  a Key-Value map of details to add to the initial UserDocumentBuilder
     */
    public static void addDetails(BasicDBObjectBuilder userDocumentBuilder, Map details) {
        def detailsBuilder = new BasicDBObjectBuilder()
                .start(details)
        userDocumentBuilder.add("details", detailsBuilder.get())
    }
}