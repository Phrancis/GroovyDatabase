import org.hibernate.annotations.GenericGenerator

import javax.persistence.CascadeType
import javax.persistence.GeneratedValue
import javax.persistence.OneToMany

@Entity
public class CardshifterUser {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    Long id

    String userName
    Date creationDate
    String country

    @OneToMany(mappedBy = "deckOwner", cascade = CascadeType.PERSIST)
    Set<Deck> ownedDecks = new HashSet<>()

    public CardshifterUser(userName, creationDate, country) {
        this.userName = userName
        this.creationDate = creationDate
        this.country = country
    }
}

