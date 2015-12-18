package ledg.Repository

import org.springframework.data.repository.PagingAndSortingRepository
import ledg.Entity.LedgerAccount

public interface LedgerAccountRepository : PagingAndSortingRepository<LedgerAccount, Int>
{
    public fun findByAddress(address : ByteArray?) : LedgerAccount?
}

