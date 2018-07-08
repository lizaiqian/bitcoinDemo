package com.itheima.btcoin.btcoindemo.bean;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.btcoin.btcoindemo.utils.HashUtils;
import com.itheima.btcoin.btcoindemo.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:NoteBook
 * Description: notebook is created as single instance, with CRUD of block in the notebook list;
 * notebook list is saved in "note.json". while application restart, notebook will load list from
 * this file.
 */
public class NoteBook {

    private List<Block> list = null;

    /**
     * mine difficulty, for example, while difficulty = 6, hash must start with "000000"
     */
    private final int DIFFICULTY = 6;


    private final String PATH = "note.json";


    private static NoteBook notebook;


    public static NoteBook getInstance() {

        if(notebook == null) {
            synchronized (NoteBook.class) {
                if(notebook == null) {
                    notebook = new NoteBook();
                }
            }
        }
        return notebook;
    }

    /**
     * load list while create instance of notebook
     */
    private NoteBook() {

        ObjectMapper om = new ObjectMapper();

        File file = new File(PATH);
        if(!file.exists()) {
            list = new ArrayList<>();
        } else {
            JavaType javaType = om.getTypeFactory().constructParametricType(List.class, Block.class);
            try {
                list = om.readValue(file, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * packing transaction as a block and put hash ,prehash , nonce in the block.
     * @param transaction
     */
    public void addNote(String transaction) {

        Block block = new Block();
        block.setTimestamp(TimeUtils.getTimeStamp());
        block.setContent(transaction);

        if(list.size() == 0) {
            block.setPrehash("0000000000000000000000000000000000000000000000000000000000000000");
        } else {
            Block preBlock = list.get(list.size() - 1);
            block.setPrehash(preBlock.getHash());
        }

        int nonce = mine(block.getTimestamp() + block.getPrehash() + block.getContent());
        block.setNonce(nonce);

        String hash = HashUtils.hash(nonce + (block.getTimestamp() + block.getPrehash() + block.getContent()), "SHA-256");
        block.setHash(hash);

        list.add(block);
        try {
            saveToDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show the block list
     * @return block list
     */
    public List<Block> showList() {
        return list;
    }

    /**
     * save list to file , while operator "addnote" is executed
     * @throws IOException
     */
    public void saveToDisk() throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(new File(PATH), list);

    }

    /**
     * check whether list of notebook has been disorted. hash and prehash in each block
     * has been checked.
     * @return
     */
    public String checkList() {

        Block block = null;
        for(int i = 0; i < list.size(); i++) {
            block = list.get(i);
            if(!block.getHash().equals(HashUtils.hash(block.getNonce() + "" + block.getTimestamp() + block.getPrehash() + block.getContent(), "SHA-256"))) {
                System.out.println(block.getNonce() + "" + block.getTimestamp() + block.getPrehash() + block.getContent());
                return "第" + i + "个区块校验失败";
            }

            if(i > 0 && !block.getPrehash().equals(list.get(i - 1).getHash())) {
                return "第" + i + "个区块和前一个区块hash值不匹配";
            }

        }

        return "校验成功";
    }


    /**
     * mine the first nonce which can match the requirement that hash start with "000000",
     * the difficulty decide the number of zero.
     * @param input
     * @param difficulty
     * @return
     */
    private int mine(String input, int difficulty) {

        String difficultStr = "";
        for (int i = 0; i < difficulty; i++) {
            difficultStr += "0";
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if(HashUtils.hash(i + input, "SHA-256").startsWith(difficultStr)) {
                return i;
            }
        }

        return mine(input, difficulty - 1);
    }

    private int mine(String input) {
        return mine(input, DIFFICULTY);
    }

    /**
     * while other nodes broadcast a block list which size longer than local list, update it.
     * @param list
     */
    public void updateList(List<Block> list) {
        this.list = list;
    }

}
