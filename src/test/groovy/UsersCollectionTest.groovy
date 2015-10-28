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
        testUserObject = testUserDocumentBuilder.build()
        testUsersCollection = new UsersCollection()
    }
    @Test
    void testGetUserObjectFromBuilder() {
        assert testUserObject instanceof DBObject
    }
    @Test
    void testUsersCollectionIsAMongoDBCollection() {
        assert testUsersCollection.getUsersCollection() instanceof DBCollection
    }
//    @Test
//    void findAllUserDocumentsThatAlreadyExist() {
//        def userDocumentCursor = testUsersCollection
//                .find( { user_name : USERNAME } )
//        println userDocumentCursor
//    }
    @Test
    void testDeleteUser() {
        testUsersCollection.deleteUser( testUserDocumentBuilder )
//        assert testUsersCollection
//                .getUsersCollection() instanceof DBCollection
        assert !testUsersCollection
                .getUsersCollection()
                .findOne( testUserObject )
    }
    @Test
    void testInsertUserIfNotExists() {
        testUsersCollection.insertUserIfNotExists(testUserDocumentBuilder)
        assert testUsersCollection.find( { user_name : USERNAME } )
    }
}
