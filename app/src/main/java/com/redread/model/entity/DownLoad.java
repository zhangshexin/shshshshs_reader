package com.redread.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by zhangshexin on 2018/9/14.
 *
 * 封面和书的路径都是由相对应的路径+书的md5值
 */
@Entity
public class DownLoad {


    @Id
    private long id;
    private String bookName;//书名
    private String author;//作者
    private String url;//下载链接
    private String bookDir;//存入本地的路径
    private String bookType;//书的类型，暂只支持pdf和txt
    private int readProgress=0;//阅读进度
    private int totalPage=0;//总页数
    private int currentPage=0;//当前页数
    private Date upDate;//更新日期，用于阅读排序
    private String coverUrl;//封面url
    @Generated(hash = 1426816711)
    public DownLoad(long id, String bookName, String author, String url,
            String bookDir, String bookType, int readProgress, int totalPage,
            int currentPage, Date upDate, String coverUrl) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.url = url;
        this.bookDir = bookDir;
        this.bookType = bookType;
        this.readProgress = readProgress;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.upDate = upDate;
        this.coverUrl = coverUrl;
    }

    @Generated(hash = 89475367)
    public DownLoad() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getReadProgress() {
        return this.readProgress;
    }
    public void setReadProgress(int readProgress) {
        this.readProgress = readProgress;
    }
    public int getTotalPage() {
        return this.totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getCurrentPage() {
        return this.currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public Date getUpDate() {
        return this.upDate;
    }
    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }
    public String getBookType() {
        return this.bookType;
    }
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
    public String getCoverUrl() {
        return this.coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public String getBookDir() {
        return this.bookDir;
    }
    public void setBookDir(String bookDir) {
        this.bookDir = bookDir;
    }
}
