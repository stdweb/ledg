package ledg.Repository

import org.springframework.data.repository.PagingAndSortingRepository
import ledg.Entity.LedgerAccount
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

public interface LedgerAccountRepository : PagingAndSortingRepository<LedgerAccount, Int>
{
    public fun findByAddress(address : ByteArray?) : LedgerAccount?

//    @Modifying
//    @Query("update  LedgerAccount set firstBlock.id  = null where firstBlock.id =:previd")
//    public fun updAccFirstBlock2null(@Param("previd") previd : Int)
//
//
//    @Modifying
//    @Query("update  LedgerAccount set lastBlock.id = null where lastBlock.id =:previd")
//    public fun updAccLastBlock2null(@Param("previd") previd : Int)


    @Modifying
    @Query("update LedgerAccount set entrCnt = :entryind where id=:accid")
    public fun updAccEntryInd(@Param("accid") accid : Int,@Param("entryind") entryind : Int)

}

