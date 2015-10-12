package groovydatabase
import com.mongodb.BasicDBObjectBuilder
import org.junit.Test
import org.junit.Before
import java.security.MessageDigest

class UserDocumentBuilderTest {
    UserDocumentBuilder testUserDocument
    final String USERNAME = "myName"
    final String PASSWORD = "myPassword"
    final String HASHING_ALGORITHM = "MD5"
    final Map USER_DETAILS = [
            "hello": 1,
            "world": null,
            null: [ "foo", "bar" ],
            "emptyList": []
    ]

    @Before
    public void initialize() {
        testUserDocument = new UserDocumentBuilder(USERNAME, PASSWORD)
    }
    @Test
    void testUserDocumentDataIsCorrect() {
        assert testUserDocument.getUserName() == USERNAME
        assert testUserDocument.getPasswordHash() != PASSWORD
        assert testUserDocument.getDateCreated() instanceof Date
    }
    @Test
    void testHashingAlgorithm() {
        assert testUserDocument.getPasswordHash() == MessageDigest
                .getInstance(HASHING_ALGORITHM)
                .digest(PASSWORD.bytes)
                .encodeHex()
                .toString()
    }
    @Test
    void testCreateUserDocument() {
        def testUserDocumentBuilder = testUserDocument.create()
        assert testUserDocumentBuilder instanceof BasicDBObjectBuilder
        println "testCreateUserDocument() results: ${testUserDocumentBuilder.get().toString()}"
    }
    @Test
    void testAddUserDetails() {
        def testUserDocumentBuilderWithDetails = testUserDocument
                .create()
        testUserDocument
                .addDetails(testUserDocumentBuilderWithDetails, USER_DETAILS)
        assert testUserDocumentBuilderWithDetails instanceof BasicDBObjectBuilder
        println "testAddUserDetails() results: ${testUserDocumentBuilderWithDetails.get().toString()}"
    }

}