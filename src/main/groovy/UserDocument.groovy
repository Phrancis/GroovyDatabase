package groovydatabase
import java.security.MessageDigest

class UserDocument {
    String userName
    String passwordHash
    Date dateCreated

    UserDocument(String userName, String passwordHash, Date dateCreated) {
        this.userName = userName
        this.passwordHash = MessageDigest.getInstance("MD5").digest(passwordHash.bytes).encodeHex().toString()
        this.dateCreated = new Date()
    }

    def createUserDocument() {
        def userCollection = new CardshifterDB()
            .getDB()
            .getCollection("users")
        userCollection
    }
}