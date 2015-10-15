package groovydatabase
import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
import org.junit.Test
import org.junit.Before
import java.security.MessageDigest

class UserDocumentBuilderTest {
    UserDocumentBuilder testUserDocumentBuilder
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
    }

    @Test
    void testUserDocumentDataIsCorrect() {
        assert testUserDocumentBuilder.getUserName() == USERNAME
        assert testUserDocumentBuilder.getPasswordHash() != PASSWORD
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
    void testCreateUserDocumentAsBuilder() {
        def testUserDocumentBuilder = testUserDocumentBuilder.create()
        assert testUserDocumentBuilder instanceof BasicDBObjectBuilder
        assert testUserDocumentBuilder.get() instanceof DBObject;
    }
    @Test
    void testAddUserDetails() {
        def testUserDocumentBuilderWithDetails = testUserDocumentBuilder
                .create()
        testUserDocumentBuilder
                .addDetails(testUserDocumentBuilderWithDetails, USER_DETAILS)
        assert testUserDocumentBuilderWithDetails instanceof BasicDBObjectBuilder
    }

}