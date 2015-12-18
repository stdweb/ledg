package ledg.Entity

import Core.Amount
import org.hibernate.annotations.Type


import javax.persistence.GeneratedValue
import javax.persistence.Id

//@Entity
open class AmountEntity
{
    @Id @GeneratedValue
    var id: Int = -1


    @Type(type="AmountUserType")
    var amount = Amount.Zero

}