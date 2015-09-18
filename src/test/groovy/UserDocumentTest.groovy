package groovydatabase

import com.mongodb.DBCollection
import org.junit.Test
import java.security.MessageDigest

class UserDocumentTest {
    def testUser = new UserDocument("myName", "myPassword", new Date())

    @Test
    void testUserDocument() {
        assert testUser.getUserName() == "myName"
        assert testUser.getPasswordHash() != "myPassword"
        assert testUser.getPasswordHash() == MessageDigest.getInstance("MD5").digest("myPassword".bytes).encodeHex().toString()
        assert testUser.getDateCreated() instanceof Date
    }
    @Test
    void testCreateUserDocument() {
        def testUserDoc = testUser.createUserDocument()
        assert testUserDoc instanceof DBCollection
    }
}
