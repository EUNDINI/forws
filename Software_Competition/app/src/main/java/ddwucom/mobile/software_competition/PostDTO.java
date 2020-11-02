package ddwucom.mobile.software_competition;

import java.io.Serializable;

public class PostDTO implements Serializable {

    private long _id;
    private String title;
    private String nickname;
    private int anonymous;
    private String createDate;
    private String createTime;
    private String body;
    private String category;
    private int views;
    private int likes;

    public PostDTO(long _id, String title, String nickname, int anonymous, String createDate, String body, String category, int views, int likes) {
        this._id = _id;
        this.title = title;
        this.nickname = nickname;
        this.anonymous = anonymous;
        this.createDate = createDate;
        this.body = body;
        this.category = category;
        this.views = views;
        this.likes = likes;
    }

    public PostDTO(long _id, String title, String nickname, int anonymous, String createDate){
        this._id = _id;
        this.title = title;
        this.nickname = nickname;
        this.anonymous = anonymous;
        this.createDate = createDate;
    }

    public long get_id() { return _id; }

    public void set_id(long _id) { this._id = _id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public int getAnonymous() { return anonymous; }

    public void setAnonymous(int anonymous) { this.anonymous = anonymous; }

    public String getCreateDate() { return createDate; }

    public void setCreateDate(String createDate) { this.createDate = createDate; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public int getViews() { return views; }

    public void setViews(int views) { this.views = views; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }
}
