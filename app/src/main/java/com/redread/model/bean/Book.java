package com.redread.model.bean;

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
        DownLoad downLoad=new DownLoad();
        downLoad.setBookDir(book.getBookDir());
        downLoad.setBookName(book.getBookName());
        downLoad.setAuthor(book.getAuthor());
        downLoad.setBookType(book.getBookType());
        downLoad.setCoverUrl(book.getCoverUrl());
        downLoad.setCurrentPage(book.getCurrentPage());
        downLoad.setId(book.getId());
        downLoad.setTotalPage(book.getTotalPage());
        downLoad.setReadProgress(book.getReadProgress());
        downLoad.setUpDate(book.getUpDate());
        downLoad.setUrl(book.getUrl());
        return downLoad;
    }
    public static Book conver2Book(DownLoad downLoad){
        Book book=new Book();
        book.setBookDir(downLoad.getBookDir());
        book.setBookName(downLoad.getBookName());
        book.setAuthor(downLoad.getAuthor());
        book.setBookType(downLoad.getBookType());
        book.setCoverUrl(downLoad.getCoverUrl());
        book.setCurrentPage(downLoad.getCurrentPage());
        book.setId(downLoad.getId());
        book.setTotalPage(downLoad.getTotalPage());
        book.setReadProgress(downLoad.getReadProgress());
        book.setUpDate(downLoad.getUpDate());
        book.setUrl(downLoad.getUrl());
        return  book;
    }


    public static List<Book> conver2ListBook(List<DownLoad> downLoads){
        List<Book> books=new ArrayList<>();
        for (int i=0;i<downLoads.size();i++){
            books.add(conver2Book(downLoads.get(i)));
        }
        return books;
    }
}
