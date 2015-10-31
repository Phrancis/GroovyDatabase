package groovydatabase
import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
import org.junit.Test
import org.junit.Before
import java.security.MessageDigest

/**
 * These tests are focused on the UserDocumentBuilder class.
 */
class UserDocumentBuilderTest {

    UserDocumentBuilder testUserDocumentBuilder
    DBObject emptyDBObject
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
        testUserDocumentBuilder = new UserDocumentBuilder(USERNAME, PASSWORD)
        emptyDBObject = new BasicDBObjectBuilder().get()
    }

    @Test
    void testUserDocumentDataIsCorrect() {
        assert testUserDocumentBuilder.getUserName() == USERNAME
        assert testUserDocumentBuilder.getDateCreated() instanceof Date
    }
    @Test
    void testHashingAlgorithm() {
        assert testUserDocumentBuilder.getPasswordHash() == MessageDigest
                .getInstance(HASHING_ALGORITHM)
                .digest(PASSWORD.bytes)
                .encodeHex()
                .toString()
    }
    @Test
    void testBeginUserDocumentAsBasicDBObjectBuilder() {
        def testBasicDBObjectBuilder = testUserDocumentBuilder.begin()
        assert testBasicDBObjectBuilder instanceof BasicDBObjectBuilder
        assert testBasicDBObjectBuilder.get() != emptyDBObject
    }
    @Test
    void testAddUserDetailsToBuilder() {
        BasicDBObjectBuilder testUserDocumentBuilderWithDetails = testUserDocumentBuilder
                .begin()
        testUserDocumentBuilder
                .addDetails(testUserDocumentBuilderWithDetails, USER_DETAILS)
        assert testUserDocumentBuilderWithDetails instanceof BasicDBObjectBuilder
        assert testUserDocumentBuilderWithDetails.get() != emptyDBObject
    }
    @Test
    void testGetDBObjectFromBuilder() {
        DBObject testUserDocumentDBObject = testUserDocumentBuilder
                .begin()
                .get()
        assert testUserDocumentDBObject instanceof DBObject
        assert testUserDocumentDBObject != emptyDBObject
    }
}