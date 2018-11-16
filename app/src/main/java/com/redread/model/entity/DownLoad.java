package com.redread.model.entity;

import com.redread.utils.Constant;

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


    @Id(autoincrement = true)
    private Long id;
    private String bookName;//书名
    private String author;//作者
    private String url;//下载链接,不存服务器地址，只存书的地址
    private String bookDir;//存入本地的路径
    private String bookType;//书的类型，暂只支持pdf和txt
    private int readProgress=0;//阅读进度
    private int totalPage=0;//总页数
    private int currentPage=0;//当前页数
    private Date upDate;//更新日期，用于阅读排序
    private String coverUrl;//封面url
    private String coverDir;//封面的本地路径

    private long dataLongth=0;//数据长度
    private long downProgress=0;//当前下载位置
    /**
     * @see com.redread.utils.Constant
     */
    private int status= Constant.DOWN_STATUS_CLEAR;
    @Generated(hash = 1587849519)
    public DownLoad(Long id, String bookName, String author, String url,
            String bookDir, String bookType, int readProgress, int totalPage,
            int currentPage, Date upDate, String coverUrl, String coverDir,
            long dataLongth, long downProgress, int status) {
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
        this.coverDir = coverDir;
        this.dataLongth = dataLongth;
        this.downProgress = downProgress;
        this.status = status;
    }

    @Generated(hash = 89475367)
    public DownLoad() {
    }
    public Long getId() {
        return this.id;
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

    public String getCoverDir() {
        return this.coverDir;
    }

    public void setCoverDir(String coverDir) {
        this.coverDir = coverDir;
    }

    public long getDataLongth() {
        return this.dataLongth;
    }

    public void setDataLongth(long dataLongth) {
        this.dataLongth = dataLongth;
    }

    public long getDownProgress() {
        return this.downProgress;
    }

    public void setDownProgress(long downProgress) {
        this.downProgress = downProgress;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
