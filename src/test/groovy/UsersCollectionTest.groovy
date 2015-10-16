package groovydatabase

import com.mongodb.DBCollection
import com.mongodb.DBObject
import org.junit.Test
import org.junit.Before

class UsersCollectionTest {
    final String USERNAME = "myName"
    final String PASSWORD = "myPassword"
    DBObject testUserObject
    def testUsersCollection
    UserDocumentBuilder testUserDocumentBuilder

    @Before
    public void initialize() {
        testUserDocumentBuilder = new UserDocumentBuilder(USERNAME, PASSWORD)
        testUserDocumentBuilder.begin()
        testUsersCollection = new UsersCollection()
    }
    @Test
    void testGetUserObjectFromBuilder() {
        testUserObject = testUserDocumentBuilder.build()
        assert testUserObject instanceof DBObject
    }

    @Test
    void testUsersCollectionIsAMongoDBCollection() {
        assert testUsersCollection.getUsersCollection() instanceof DBCollection
    }
    @Test
    void testDeleteUser() {
        testUserObject = testUserDocumentBuilder.build()
        assert testUserObject instanceof DBObject
        testUsersCollection.deleteUser( testUserObject )
        assert !testUsersCollection.find( { user_name : USERNAME } )
    }
//    @Test
//    void testInsertUserIfNotExists() {
//        testUsersCollection.insertUserIfNotExists(testUserDocumentBuilder)
//        assert testUsersCollection.find( { user_name : USERNAME } )
//    }
}
