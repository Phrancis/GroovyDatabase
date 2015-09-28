package groovydatabase

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBCollection
import org.junit.Test
import org.junit.Before
import java.security.MessageDigest

class UserDocumentTest {
    UserDocument testUserDocument
    final String USERNAME = "myName"
    final String PASSWORD = "myPassword"
    final String HASHING_ALGORITHM = "MD5"

    @Before public void initialize() {
        testUserDocument = new UserDocument(USERNAME, PASSWORD)
    }
    @Test void testUserDocumentDataIsCorrect() {
        assert testUserDocument.getUserName() == USERNAME
        assert testUserDocument.getPasswordHash() != PASSWORD
        assert testUserDocument.getDateCreated() instanceof Date
    }
    @Test void testHashingAlgorithm() {
        assert testUserDocument.getPasswordHash() == MessageDigest
                .getInstance(HASHING_ALGORITHM)
                .digest(PASSWORD.bytes)
                .encodeHex()
                .toString()
    }

    @Test
    void testCreateUserDocument() {
        def userDocBuilder = testUserDocument.create()
        assert userDocBuilder instanceof BasicDBObjectBuilder

    }
//    @Test
//    void testAddUserDetails() {
//
//        def testMap = [
//                "hello": 1,
//                "world": 2
//        ]
//        testUserDocument.addUserDetails(userDocBuilder, testMap)
//
//        println testUserDocument.toString()
//
//    }
}