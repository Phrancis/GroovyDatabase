package groovydatabase
import com.mongodb.DBCollection
import com.mongodb.DBObject
import groovy.json.*
import org.junit.Test
import org.junit.Before

class UsersCollectionTest {
    final String USERNAME = "myName"
    final String PASSWORD = "myPassword"
    UserDocumentBuilder testUserDocumentBuilder
    UsersCollection testUsersCollection = new UsersCollection()
    DBObject testUserObject

    @Before
    public void initialize() {
        testUserDocumentBuilder = new UserDocumentBuilder(USERNAME, PASSWORD)
                .create()
    }
    @Test
    void testGetUserObjectFromBuilder() {
        assert testUserObject instanceof DBObject
    }

    @Test
    void testUsersCollectionIsAccessed() {
        assert testUsersCollection.getUsersCollection() instanceof DBCollection
    }
    @Test
    void testDeleteUser() {
        testUserObject = testUserDocumentBuilder
                .create()
                .get()
        assert testUserObject instanceof DBObject
        testUsersCollection.deleteUser( testUserObject.get() )
        assert !testUsersCollection.find( { user_name : USERNAME } )
    }
//    @Test
//    void testInsertUserIfNotExists() {
//        testUsersCollection.insertUserIfNotExists(testUserDocumentBuilder)
//        assert testUsersCollection.find( { user_name : USERNAME } )
//    }
}
