package zengsu.example.com.keepaccounts.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zeng on 2017/5/8.
 *
 * @Table创建表的名字为acounts
 * @Column列名
 */
@Table(name = "accounts")
public class Account {
    @Column(name = "_id", isId = true)
    private int id;
    @Column(name = "figure")
    private float figure;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "category")
    private String category;
    @Column(name = "date")
    private String date;
    @Column(name = "type")
    private String type;

//    public int getUserid() {
//        return userid;
//    }
//
//    @Column(name = "userid", property = "UNIQUE")
//    private int userid;

//    public String getUserid() {
//        return userid;
//    }
//
//    @Column(name = "userid")
//    private String userid;
//@Foreign(column = "parentId", foreign = "id")
//@Foreign(name ="userid")



public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFigure() {
        return figure;
    }

    public void setFigure(float figure) {
        this.figure = figure;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
