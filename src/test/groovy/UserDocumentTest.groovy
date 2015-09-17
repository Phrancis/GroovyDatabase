package groovydatabase

import org.junit.Test
import java.security.MessageDigest

class UserDocumentTest {
    @Test
    void testUserDocument() {
        def testUser = new UserDocument("myName", "myPassword", new Date())
        assert testUser.getUserName() == "myName"
        assert testUser.getPasswordHash() != "myPassword"
        assert testUser.getPasswordHash() == MessageDigest.getInstance("MD5").digest("myPassword".bytes).encodeHex().toString()
        assert testUser.getDateCreated() instanceof Date
    }
}
