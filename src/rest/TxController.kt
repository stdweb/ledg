package ledg.rest


import Core.EntryType

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ledg.Entity.LedgerAccount
import ledg.Entity.LedgerEntry
import ledg.Repository.LedgerBlockRepository
import ledg.Repository.LedgerEntryRepository
import ledg.Repository.LedgerTxRepository
import java.util.*
import javax.servlet.http.HttpServletRequest
import ledg.Utils

@RestController
class TxController {

    private val logger = LoggerFactory.getLogger("rest")

    @Autowired var ledgRepo     : LedgerEntryRepository?    = null
    @Autowired var blockRepo    : LedgerBlockRepository?    = null
    @Autowired var txRepo       : LedgerTxRepository?       = null

    @RequestMapping(value = "/tx/{txId}", method = arrayOf( RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun gettx(@PathVariable  txId : String,request : HttpServletRequest )  : List<LedgerEntry> {

        val t1=System.currentTimeMillis();
        val ret : ResponseEntity<LedgerEntry>
        ret = ResponseEntity(null, HttpStatus.OK)

        val tx      = txRepo    ?.findByHash(Utils.hash_decode(txId))
        val content = ledgRepo  ?.getByTxId(tx?.id) ?: ArrayList<LedgerEntry>()

        val result=content.filter { it.entryType in
                listOf(
                        EntryType.Send ,
                        EntryType.InternalCall,
                        EntryType.Call,
                        EntryType.NA,
                        EntryType.Genesis,
                        EntryType.ContractCreation )
        }

        result.forEach { it.amount=it.amount.negate() }

        Utils.log("tx",t1,request,ret);
        return result
    }

    @RequestMapping(value = "/txs/{blockId}",method = arrayOf( RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun getTxList(@PathVariable blockId : String ,request : HttpServletRequest ) : List<LedgerEntry> {
        val t1=System.currentTimeMillis();
        val ret : ResponseEntity<LedgerEntry>
        ret = ResponseEntity(null, HttpStatus.OK)

        val block   = blockRepo ?.get(blockId)
        val content = ledgRepo  ?.getByBlockId(block?.id) ?: ArrayList<LedgerEntry>()

        val result  = ArrayList<LedgerEntry>()
        with (content){
            val f1=filter { it.entryType in
                    listOf(
                            EntryType.CoinbaseReward,
                            EntryType.UncleReward,
                            EntryType.FeeReward) }

            val f2=filter { it.entryType in
                    listOf(
                            EntryType.Send ,
                            EntryType.InternalCall,
                            EntryType.Call,
                            EntryType.NA,
                            EntryType.Genesis,
                            EntryType.ContractCreation )}

            f1.forEach { it.offsetAccount = it.account; it.account = LedgerAccount(Utils.ZERO_BYTE_ARRAY_20) }
            f2.forEach { it.amount= it.amount.abs() }
            result.addAll(f1.union(f2))
        }

        Utils.log("txs",t1,request,ret);
        return result
    }

    //    @Autowired
    //    EthereumBean_DEL ethereumBean;


    //    @RequestMapping(value = "/txs/{blockId}", method = GET, produces = APPLICATION_JSON_VALUE)
    //    @ResponseBody
    //    public String getTxList(@PathVariable String blockId,HttpServletRequest request) throws IOException {
    //        long t1=System.currentTimeMillis();
    //        try {
    //            Block block=ethereumBean.getBlock(blockId);
    //            LedgerQuery ledgerQuery = SqlDb.getSqlDb().getQuery();
    //
    //            String s = ledgerQuery.LedgerSelectByBlock(block.getNumber());
    //
    //            s=s.replace(":"," ");
    //            Utils.log("TxList",t1,request);
    //            return s;
    //
    //
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //
    //        return  null;
    //    }


}
