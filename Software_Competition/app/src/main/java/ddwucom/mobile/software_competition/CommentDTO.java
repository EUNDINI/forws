package ddwucom.mobile.software_competition;
import java.io.Serializable;

public class CommentDTO implements Serializable {
    private long _id;
    private long postId;
    private String nickname;
    private int anonymous;
    private String createDate;
    private String commentBody;

    public CommentDTO(long _id, long postId, String nickname, int anonymous, String commentBody, String createDate) {
        this._id = _id;
        this.postId = postId;
        this.nickname = nickname;
        this.anonymous = anonymous;
        this.createDate = createDate;
        this.commentBody = commentBody;
    }

    public CommentDTO(String nickname, int anonymous, String createDate, String commentBody) {
        this.nickname = nickname;
        this.anonymous = anonymous;
        this.createDate = createDate;
        this.commentBody = commentBody;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
