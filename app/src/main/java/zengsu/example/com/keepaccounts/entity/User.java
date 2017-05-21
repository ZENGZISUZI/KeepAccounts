package zengsu.example.com.keepaccounts.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zeng on 2017/5/16 0016.
 */
@Table(name="user")
public class User {
    @Column(name="email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "-id",isId = true)
    private int userid;





}
