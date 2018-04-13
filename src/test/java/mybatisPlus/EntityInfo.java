package mybatisPlus;


public class EntityInfo {
    private String beanName;
    private String beanType;
    private String beanDesc;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }

    public String getBeanDesc() {
        return beanDesc;
    }

    public void setBeanDesc(String beanDesc) {
        this.beanDesc = beanDesc;
    }

    @Override
    public String toString() {
        return "EntityInfo{" +
                "beanName='" + beanName + '\'' +
                ", beanType=" + beanType +
                ", beanDesc='" + beanDesc + '\'' +
                '}';
    }
}
