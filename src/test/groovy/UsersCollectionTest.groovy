package groovydatabase
import com.mongodb.DBCollection
import org.junit.Test
import org.junit.Before

class UsersCollectionTest {
    final String USERNAME = "myName"
    final String PASSWORD = "myPassword"
    UserDocumentBuilder testUserDocument
    UsersCollection testUsersCollection

    @Before
    public void initialize() {
        testUsersCollection = new UsersCollection()
        testUserDocument = new UserDocumentBuilder(USERNAME, PASSWORD)
    }

    @Test
    void testUsersCollectionIsAccessed() {
        assert testUsersCollection.getUsersCollection() instanceof DBCollection
    }
}
