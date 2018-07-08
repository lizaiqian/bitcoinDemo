package com.itheima.btcoin.btcoindemo.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.btcoin.btcoindemo.bean.Block;
import com.itheima.btcoin.btcoindemo.bean.NoteBook;
import com.itheima.btcoin.btcoindemo.bean.Transaction;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:BlockController
 * Description:
 *
 */
@RestController
public class BlockController {

    NoteBook noteBook = NoteBook.getInstance();

    /**
     *
     * if verrified successfully, transaction is added to notebook
     *
     * @param transaction
     * @return successful or failed
     */
    @RequestMapping("/addNote")
    public String addNote(Transaction transaction) {

        //验证签名
        boolean result = transaction.verify();

        if(result) {
            try {
                String transactionStr = JSON.toJSONString(transaction);
                noteBook.addNote(transactionStr);
                return "添加成功";
            } catch (Exception e) {
                return "添加失败";
            }
        }

        return "验证失败";

    }

    /**
     * show all blocks from list of notebook
     * @return block list
     */
    @RequestMapping("/showList")
    public List<Block> showList() {
        return noteBook.showList();
    }


    /**
     * check whether list of notebook has been disorted by hash and prehash
     * @return
     */
    @RequestMapping("/checkList")
    public String checkList() {
        return noteBook.checkList();
    }


}
