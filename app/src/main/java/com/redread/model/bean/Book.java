package com.redread.model.bean;

import com.alibaba.fastjson.JSON;
import com.redread.model.entity.DownLoad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/17.
 */

public class Book extends DownLoad implements Serializable{
    private  static final long serialVersionUID =536871008;

    private boolean showCheckBtn=false;//是否显示选则按钮
    private boolean checked=false;//是否已选中

    public boolean isShowCheckBtn() {
        return showCheckBtn;
    }

    public void setShowCheckBtn(boolean showCheckBtn) {
        this.showCheckBtn = showCheckBtn;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static DownLoad conver2Download(Book book){
        String json=JSON.toJSONString(book);
        DownLoad downLoad=JSON.parseObject(json,DownLoad.class);
        return downLoad;
    }
    public static Book conver2Book(DownLoad downLoad){
        String json=JSON.toJSONString(downLoad);
        Book book=JSON.parseObject(json,Book.class);
        return  book;
    }


    public static List<Book> conver2ListBook(List<DownLoad> downLoads){
        String json=JSON.toJSONString(downLoads);
        List<Book> books=JSON.parseArray(json,Book.class);
        return books;
    }
}
