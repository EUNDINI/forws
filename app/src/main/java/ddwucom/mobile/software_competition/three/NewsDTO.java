package ddwucom.mobile.software_competition.three;

public class NewsDTO {
    private int _id;
    private String regDt;
    private String thumbUrl;
    private String title;
    private String viewUrl;

    public NewsDTO(int _id, String regDt, String thumbUrl, String title, String viewUrl) {
        this._id = _id;
        this.regDt = regDt;
        this.thumbUrl = thumbUrl;
        this.title = title;
        this.viewUrl = viewUrl;
    }

    public int get_id() {
        return _id;
    }

    public String getRegDt() {
        return regDt;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getViewUrl() {
        return viewUrl;
    }
}
