package groovydatabase

import com.mongodb.DB
import com.mongodb.DBCollection
import org.junit.Test


class CardshifterDBTest {
    @Test
    void testCardshifterDB() throws Exception {
        def testCSDB = new CardshifterDB()
        assert testCSDB instanceof CardshifterDB
        assert testCSDB.getHost() == "localhost"
        def testDB = testCSDB.getDB()
        assert testDB instanceof DB
        def testDBCollection = testDB.getCollection("test")
        assert testDBCollection instanceof DBCollection
    }

}