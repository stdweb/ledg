package Core;


import org.spongycastle.util.encoders.Hex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by bitledger on 20.09.15.
 */
public class Convert2json {


    public static String BD2ValStr(BigDecimal val,boolean hideZero) {
        return BI2ValStr(val.toBigInteger(),hideZero);
    }
    public static String BI2ValStr(BigInteger val,boolean hideZero) {
        try {
            BigDecimal dec = new BigDecimal(val);
            BigDecimal dec1 = dec.divide(BigDecimal.valueOf(Math.pow(10, 15)));

            String ret = new DecimalFormat("###,###,##0.##################").format(dec1);
            if (hideZero && dec1.signum() == 0)
                ret = "";
            return ret;
        }
        catch (NumberFormatException e)
        {
            return e.toString();
        }
    }


    public static BigDecimal val2BigDec(byte[] val)
    {
        return new BigDecimal(new BigInteger(1,val));
    }

    public static String Num2ValStr(long val,boolean hideZero) {

        String ret=String.valueOf(val);
        if (hideZero && val==0)
            ret="";
        return ret;
    }

    public static String addParentheses(String str)
    {
        return "\""+str+"\"";
    }


    public static String convertTimestamp2str(long timestamp) {
        Date d=new Date(timestamp*1000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd' - 'HH:mm:ss");
        return sdf.format(d);
    }

    public static String map2json(HashMap<String, String> hashMap) {
        String key_value_separator=" "; //edn format, use ":" for json

        String result="{";
        for (String key : hashMap.keySet())
        {
            result+=addParentheses(key)+key_value_separator+hashMap.get(key)+",";
        }

        if (result.endsWith(","))
            result=result.substring(0,result.length()-1);

        result+="}";

        return result;
    }



//    public static String tx2json(ReplayBlock replayBlock, Transaction tx, int ind, long gasUsed)
//    {
//        Block bl = replayBlock.getBlock();
//
//        HashMap<String, String> hashMap = new HashMap<>();
//
//        String indStr=String.valueOf(ind);
//        if (tx instanceof InternalTransaction) {
//            indStr += ":" + ((InternalTransaction) tx).getIndex();
//            if (((InternalTransaction) tx).getDeep()!=0)
//                indStr+= " (deep " + ((InternalTransaction) tx).getDeep() + ")";
//        }
//
//        hashMap.put("txno",addParentheses(indStr));
//
//        String txhash="";
//        if (tx instanceof InternalTransaction)
//            txhash=Hex.toHexString(((InternalTransaction) tx).getParentHash());
//        else
//            txhash=Hex.toHexString(tx.getHash());
//        hashMap.put("hash",addParentheses("0x"+txhash));
//
//        hashMap.put("block",String.valueOf(bl.getNumber()) );
//
//        String dateStr = convertTimestamp2str(bl.getTimestamp());
//        hashMap.put("timestamp",addParentheses(dateStr));
//
//
//        hashMap.put("sender",addParentheses("0x"+Hex.toHexString(tx.getSender())));
//
//        byte[] recAddress;
//        if (tx.getReceiveAddress()==null)
//            recAddress=tx.getContractAddress();
//        else
//            recAddress=tx.getReceiveAddress();
//
//        hashMap.put("receiver",addParentheses("0x"+Hex.toHexString(recAddress)));
//
//        BigInteger bi=new BigInteger(1,tx.getValue());
//        String valStr = BI2ValStr(bi,true);
//
//        hashMap.put("valueStr",addParentheses(valStr));
//
//
//        hashMap.put("gasused",addParentheses(String.valueOf(gasUsed)));
//
//        hashMap.put("txtype",addParentheses(getTxType(replayBlock,tx)));
//
//        BigInteger fee = (new BigInteger(1, tx.getGasPrice())).multiply(BigInteger.valueOf(gasUsed));
//        hashMap.put("fee",addParentheses(BI2ValStr(fee,true)));
//
////        if (replayBlock.getInternalTxCount().containsKey(tx))
////            hashMap.put("internaltxcount",String.valueOf(replayBlock.getInternalTxCount().get(tx)));
////        else
////            hashMap.put("internaltxcount","0");
//
//        return map2json(hashMap);
//    }



//    public static String TxList2json(ReplayBlock replayBlock)
//    {
//        Block block = replayBlock.getBlock();
//        List<Transaction> txList = replayBlock.getTxList();
//        HashMap<Transaction, Long> txGasUsedList = replayBlock.getTxGasUsedList();
//
//        BigInteger block_receved = BigInteger.valueOf(0);
//        BigInteger block_sent = BigInteger.valueOf(0);
//        BigInteger block_gasUsed= BigInteger.valueOf(0);
//        BigInteger block_fee= BigInteger.valueOf(0);
//
//        String result="[";
//
//        result+=rewardBlockEntry2json(block,block.getCoinbase(), replayBlock.getBlockReward(), EntryType.CoinbaseReward);
//        for (BlockHeader blockHeader : block.getUncleList()) {
//            result+=rewardBlockEntry2json(block,blockHeader.getCoinbase(),replayBlock.getUncleReward(blockHeader).toBigInteger(), EntryType.UncleReward);
//        }
//
//        result+=rewardBlockEntry2json(block,block.getCoinbase(), replayBlock.getBlockFee(), EntryType.FeeReward);
//
//        int ind=-1;
//        for (Transaction tx : txList)
//        {
//            BigInteger bi_val = new BigInteger(1, tx.getValue());
//
//            if (!(tx instanceof InternalTransaction))
//                ind++;
//
//            long gasUsed=0;
//            if (txGasUsedList.containsKey(tx))
//                gasUsed=txGasUsedList.get(tx);
//
//
//            result+=tx2json(replayBlock, tx, ind,gasUsed)+",";
//
//        }

//
//
//        if (result.endsWith(","))
//            result=result.substring(0,result.length()-1);
//
//        result+="]";
//        return result;
//    }


//    public static String rewardBlockEntry2json(Block bl,byte[] address,BigInteger bi,EntryType txtype)
//    {
//        if (bi.equals(BigInteger.ZERO))
//            return "";
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("txno",addParentheses(""));
//        hashMap.put("hash",addParentheses(""));
//        hashMap.put("block",String.valueOf(bl.getNumber()) );
//        String dateStr = convertTimestamp2str(bl.getTimestamp());
//        hashMap.put("timestamp",addParentheses(dateStr));
//
//        String sender= (txtype==EntryType.FeeReward) ? "tx" : "Genesis";
//        hashMap.put("sender",addParentheses(sender));
//        hashMap.put("receiver",addParentheses("0x"+Hex.toHexString(address)));
//
//        String valStr = BI2ValStr(bi,true);
//        hashMap.put("valueStr",addParentheses(valStr));
//        hashMap.put("gasused",addParentheses(""));
//        hashMap.put("txtype",addParentheses(txtype.toString()));
//        hashMap.put("fee",addParentheses(""));
//
//        return map2json(hashMap);
//    }
}
