package ledg.Entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name="Weblog")
class WeblogEntry
{
    @Id @GeneratedValue
    var id          : Int = 0
    var entryDate   : Date? = null

    var ip          : String?=""
    var controller  : String ?=""
    var queryObj    : String ?=""
    var page        : Int =0
    var duration    : Int =0
    var status      : Int = 0

}
