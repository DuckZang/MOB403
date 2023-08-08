package giangpdph27260.fpoly.mob403.buoi8;

import java.util.HashMap;
import java.util.Objects;

public class Model {
    private String id;
    private String title;
    private String content;

    public Model() {
    }
    // phuong thuc su ly du lieu, thao tac voi firebase
    public HashMap<String, Object> hashMap(){
        HashMap<String, Object> work = new HashMap<>();
        work.put("id",id);
        work.put("title",title);
        work.put("content",content);
        return work;
    }

    public Model(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
