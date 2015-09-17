import org.hibernate.annotations.GenericGenerator

import javax.persistence.GeneratedValue

@Entity
public class CardshifterDeck {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    Long id

    String deckName
    Date creationDate

    @ManyToOne
    CardshifterUser deckOwner
    public CardshifterDeck(deckName, creationDate, deckOwner) {
        this.deckName = deckName
        this.creationDate = creationDate
        this.deckOwner = deckOwner
    }
}