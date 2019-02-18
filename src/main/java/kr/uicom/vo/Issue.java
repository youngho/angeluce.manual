package kr.uicom.vo;

public class Issue {
    private int id = 0;              // 일감번호
    private String auid = "";        // AUI001
    private String subject = "";     // 일감제목
    private String description = ""; // 일감내용
    private int project_id = 0;
    private int fixed_version_id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getFixed_version_id() {
        return fixed_version_id;
    }

    public void setFixed_version_id(int fixed_version_id) {
        this.fixed_version_id = fixed_version_id;
    }
}
