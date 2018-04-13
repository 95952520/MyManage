package mybatisPlus;

public class TableNameInfo {
    /**
     * XxXx首字母大写
     */
    private String entityName;
    /**
     * xxXx首字母小写
     */
    private String entityObjName;
    /**
     * xx/xx/
     */
    private String htmlPackage;
    /**
     * xx-xx-
     */
    private String htmlUrl;
    private String controllerFilePath;
    private String entityFilePath;
    private String htmlDirPath;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityObjName() {
        return entityObjName;
    }

    public void setEntityObjName(String entityObjName) {
        this.entityObjName = entityObjName;
    }

    public String getHtmlPackage() {
        return htmlPackage;
    }

    public void setHtmlPackage(String htmlPackage) {
        this.htmlPackage = htmlPackage;
    }

    public String getEntityFilePath() {
        return entityFilePath;
    }

    public void setEntityFilePath(String entityFilePath) {
        this.entityFilePath = entityFilePath;
    }

    public String getControllerFilePath() {
        return controllerFilePath;
    }

    public void setControllerFilePath(String controllerFilePath) {
        this.controllerFilePath = controllerFilePath;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getHtmlDirPath() {
        return htmlDirPath;
    }

    public void setHtmlDirPath(String htmlDirPath) {
        this.htmlDirPath = htmlDirPath;
    }

    @Override
    public String toString() {
        return "TableNameInfo{" +
                "entityName='" + entityName + '\'' +
                ", entityObjName='" + entityObjName + '\'' +
                ", htmlPackage='" + htmlPackage + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", controllerFilePath='" + controllerFilePath + '\'' +
                ", entityFilePath='" + entityFilePath + '\'' +
                ", htmlDirPath='" + htmlDirPath + '\'' +
                '}';
    }
}
