package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/9/20.
 *
 * 图书对象
 */

public class NetBeanBook {
    private String NotifyTitle;//通知标题，用于通知中的滚动显示
    private String bookName;//书名
    private String author;//作者
    private String cover;//封面地址
    private String bookDes;//图书简介
    private String bookId;//图书id，用于获取图书详情
    private String type;//类型，是txt还是pdf
    private String downLoadUrl;//图书下载地址
    private String borrowUrl;//借阅纸书，图书馆地址

    public String getNotifyTitle() {
        return NotifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        NotifyTitle = notifyTitle;
    }
    public String getBookDes() {
        return bookDes;
    }

    public void setBookDes(String bookDes) {
        this.bookDes = bookDes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
