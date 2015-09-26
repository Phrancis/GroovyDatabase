package groovydatabase

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBCollection
import org.junit.Test
import java.security.MessageDigest

class UserDocumentTest {
    private testUser = new UserDocument("myName", "myPassword", new Date())
    def userDocBuilder = BasicDBObjectBuilder
    @Test
    void testUserDocument() {
        assert testUser.getUserName() == "myName"
        assert testUser.getPasswordHash() != "myPassword"
        assert testUser.getPasswordHash() == MessageDigest
                .getInstance("MD5")
                .digest("myPassword".bytes)
                .encodeHex()
                .toString()
        assert testUser.getDateCreated() instanceof Date
    }
    @Test
    void testUsersCollection() {
        def testUsersCollection = testUser.usersCollection()
        assert testUsersCollection instanceof DBCollection
    }
    @Test
    void testCreateUserDocument() {
        userDocBuilder = testUser.createUserDocument()
        assert userDocBuilder instanceof BasicDBObjectBuilder

    }
    /*
    2015-09-25 Phrancis: Currently failing.
     */
    @Test
    void testAddUserDetails() {

        def testMap = [
                "hello": 1,
                "world": 2
        ]
        testUser.addUserDetails(userDocBuilder, testMap)

        println testUser.toString()

    }
}