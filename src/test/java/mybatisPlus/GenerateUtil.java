package mybatisPlus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class GenerateUtil {

    public static void createControllerFile(TableNameInfo tableNameInfo, String projectPath) throws Exception {
        List<String> urlList = FileUtils.readLines(new File(projectPath.replace("src\\main\\java", "src\\test\\java") + "\\mybatisPlus\\template\\controllerTemplate.txt"));
        List<String> result = new ArrayList<>();
        for (String s : urlList) {
            result.add(s.replace("@ObjEntity@", tableNameInfo.getEntityName()).replace("@classMapping@", tableNameInfo.getEntityObjName())
                    .replace("@ClassController@", tableNameInfo.getEntityName() + "Controller").replace("@htmlPackage@", tableNameInfo.getHtmlPackage())
                    .replace("@Service@", tableNameInfo.getEntityObjName() + "Service").replace("@ClassService@", tableNameInfo.getEntityName() + "Service")
                    .replace("@htmlUrl@", tableNameInfo.getHtmlUrl()));
        }
        FileUtils.writeLines(new File(tableNameInfo.getControllerFilePath() + tableNameInfo.getEntityName() + "Controller.java"), result);
    }

    /**
     * 移动XXXMapper.xml文件到resources下
     *
     * @param projectPath
     * @param packageName
     */
    public static void moveMapperXmlFile(String projectPath, String packageName) {
        File file = new File(projectPath + "/" + packageName.replace(".", "/") + "/mapper/xml");
        if (file == null || file.isFile()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File eachFile : files) {
            File newFile = new File(eachFile.getPath().replace("java\\" + packageName.replace(".", "\\") + "\\mapper\\xml", "resources\\mapper"));
            if (newFile.exists()) {
                newFile.delete();
            }
            eachFile.renameTo(newFile);
        }
        new File(projectPath + "/" + packageName.replace(".", "/") + "/mapper/xml").delete();
    }

    /**
     * 生成htmlFile文件
     */
    public static void createHtmlFile(TableNameInfo tableNameInfo, String projectPath) throws Exception {
        //list.txt
        List<String> urlList = FileUtils.readLines(new File(projectPath.replace("src\\main\\java", "src\\test\\java") + "\\mybatisPlus\\template\\listTemplate.txt"));
        List<String> result = new ArrayList<>();
        List<EntityInfo> entityList = getEntityIntoList(new File(tableNameInfo.getEntityFilePath() + tableNameInfo.getEntityName() + ".java"));
        String tableDetail = getTableDetail(entityList);
        for (String s : urlList) {
            result.add(s.replace("@tableDetail@", tableDetail).replace("@entityObjName@", tableNameInfo.getEntityObjName()).replace("@myEntityId@",entityList.get(0).getBeanName()));
        }
        FileUtils.writeLines(new File(tableNameInfo.getHtmlDirPath() + "\\" + tableNameInfo.getHtmlUrl() + "-list.html"), result);
        //add.txt
        urlList = FileUtils.readLines(new File(projectPath.replace("src\\main\\java", "src\\test\\java") + "\\mybatisPlus\\template\\addTemplate.txt"));
        result = new ArrayList<>();
        String itemsList = getAddItems(entityList);
        for (String s : urlList) {
            result.add(s.replace("@entityObjName@", tableNameInfo.getEntityObjName()).replace("@itemsList@",itemsList));
        }
        FileUtils.writeLines(new File(tableNameInfo.getHtmlDirPath() + "\\" + tableNameInfo.getHtmlUrl() + "-add.html"), result);
        //edit.txt
        urlList = FileUtils.readLines(new File(projectPath.replace("src\\main\\java", "src\\test\\java") + "\\mybatisPlus\\template\\editTemplate.txt"));
        result = new ArrayList<>();
        itemsList = getEditItems(entityList);
        for (String s : urlList) {
            result.add(s.replace("@entityObjName@", tableNameInfo.getEntityObjName()).replace("@itemsList@",itemsList));
        }
        FileUtils.writeLines(new File(tableNameInfo.getHtmlDirPath() + "\\" + tableNameInfo.getHtmlUrl() + "-edit.html"), result);
    }

    /**
     * 生成xxx-list.html中表格内容
     */
    private static String getTableDetail(List<EntityInfo> list) {
        StringBuilder replace = new StringBuilder();
        for (EntityInfo info : list) {
            replace.append("\n");
            if (info.getBeanType().contains("Integer") || info.getBeanType().contains("BigDecimal") || info.getBeanType().contains("Date")) {
                replace.append("            <th lay-data=\"{field:'" + info.getBeanName() + "',align:'center', sort: true}\">" + (info.getBeanDesc() == null ? info.getBeanName() : info.getBeanDesc()) + "</th>");
            } else {
                replace.append("            <th lay-data=\"{field:'" + info.getBeanName() + "',align:'center'}\">" + (info.getBeanDesc() == null ? info.getBeanName() : info.getBeanDesc()) + "</th>");
            }
        }
        return replace.toString();
    }

    /**
     * 生成xxx-add.html中每个div内容
     */
    private static String getAddItems(List<EntityInfo> list) {
        StringBuilder replace = new StringBuilder();
        String layuiCheck = "";
        String layuiCheckDesc = "";
        for (EntityInfo info : list) {
            if (info.getBeanType().contains("Integer")) {
                layuiCheck = "checkInt";
                layuiCheckDesc = "正整数";
            }
            if (info.getBeanType().contains("BigDecimal")) {
                layuiCheck = "checkDec";
                layuiCheckDesc = "正数";
            }
            if (info.getBeanType().contains("Integer") || info.getBeanType().contains("BigDecimal")) {
                replace.append("        <div class=\"layui-inline\" style=\"margin-bottom: 15px\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-inline\" style=\"width: 300px\">\n");
                replace.append("                <select name=\"" + info.getBeanName() + "\" lay-verify=\"required\" lay-search=\"\">\n");
                replace.append("                    <option value=\"\">直接选择或搜索选择</option>\n");
                replace.append("                    <th:block th:each=\"map: ${枚举类}\">\n");
                replace.append("                        <option th:value=\"${map.key}\" th:text=\"${map.value}\"></option>\n");
                replace.append("                    </th:block>\n");
                replace.append("                </select>\n");
                replace.append("            </div>\n");
                replace.append("若为枚举 下拉框（上）、单选框（下）取其一\n");
                replace.append("            <div class=\"layui-input-block\" pane>\n");
                replace.append("                <th:block lay-verify=\"required\" th:each=\"map: ${枚举类}\">\n");
                replace.append("                    <input type=\"radio\" name=\"" + info.getBeanName() + "\" th:value=\"${map.key}\" th:title=\"${map.value}\" th:checked=\"${map.key}==1\">\n");
                replace.append("                </th:block>\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
                replace.append("枚举（上）、数值型（下）取其一\n");
                replace.append("        <div class=\"layui-form-item\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-block\" style=\"width: 300px\">\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" lay-verify=\"" + layuiCheck + "\" placeholder=\"非必填," + layuiCheckDesc + "\" autocomplete=\"off\" class=\"layui-input\">\n");
                replace.append("                非必填（上）、必填（下）取其一\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" lay-verify=\"required|" + layuiCheck + "\" placeholder=\"必填," + layuiCheckDesc + "\" autocomplete=\"off\" class=\"layui-input\">\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
            } else if (info.getBeanType().contains("Date")&&!info.getBeanName().contains("create")&&!info.getBeanName().contains("update")){
                replace.append("        <div class=\"layui-form-item\">");
                replace.append("            <label class=\"layui-form-label\">"+info.getBeanDesc()+"</label>");
                replace.append("            <div class=\"layui-input-inline\">");
                replace.append("                <input type=\"text\" name=\""+info.getBeanName()+"\" lay-verify=\"required\" placeholder=\"yyyy-MM-dd HH:mm:ss\"");
                replace.append("                       class=\"layui-input dayTime\">");
                replace.append("            </div>");
                replace.append("        </div>");
            }else {
                replace.append("        <div class=\"layui-form-item\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-block\">\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" lay-verify=\"required\" placeholder=\"必填\" autocomplete=\"off\" class=\"layui-input\">\n");
                replace.append("                非必填（上）、必填（下）取其一\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" placeholder=\"非必填\" autocomplete=\"off\" class=\"layui-input\">\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
            }
            replace.append("\n");
        }
        return replace.toString();
    }

    /**
     * 生成xxx-edit.html中每个div内容
     * 默认第一个字段为id
     */
    private static String getEditItems(List<EntityInfo> list) {
        StringBuilder replace = new StringBuilder();
        String layuiCheck = "";
        String layuiCheckDesc = "";
        boolean idFlag = true;
        for (EntityInfo info : list) {
            if (idFlag == true) {
                replace.append("        <input type=\"hidden\" name=\"" + info.getBeanName() + "\" th:value=\"${myEntity." + info.getBeanName() + "}\">\n");
                idFlag = false;
                continue;
            }
            if (info.getBeanType().contains("Integer")) {
                layuiCheck = "checkInt";
                layuiCheckDesc = "正整数";
            }
            if (info.getBeanType().contains("BigDecimal")) {
                layuiCheck = "checkDec";
                layuiCheckDesc = "正数";
            }
            if (info.getBeanType().contains("Integer") || info.getBeanType().contains("BigDecimal")) {
                replace.append("        <div class=\"layui-inline\" style=\"margin-bottom: 15px\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-inline\" style=\"width: 300px\">\n");
                replace.append("                <select name=\"" + info.getBeanName() + "\" lay-verify=\"required\" lay-search=\"\">\n");
                replace.append("                    <option value=\"\">直接选择或搜索选择</option>\n");
                replace.append("                    <th:block th:each=\"map: ${枚举类}\">\n");
                replace.append("                        <option th:value=\"${map.key}\" th:text=\"${map.value}\" th:selected=\"${myEntity."+info.getBeanName()+"==map.key}></option>\n");
                replace.append("                    </th:block>\n");
                replace.append("                </select>\n");
                replace.append("            </div>\n");
                replace.append("若为枚举 下拉框（上）、单选框（下）取其一\n");
                replace.append("            <div class=\"layui-input-block\" pane>\n");
                replace.append("                <th:block lay-verify=\"required\" th:each=\"map: ${枚举类}\">\n");
                replace.append("                    <input type=\"radio\" name=\"" + info.getBeanName() + "\" th:value=\"${map.key}\" th:title=\"${map.value}\" th:checked=\"${myEntity."+info.getBeanName()+"}==${map.key}\">\n");
                replace.append("                </th:block>\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
                replace.append("枚举（上）、数值型（下）取其一\n");
                replace.append("        <div class=\"layui-form-item\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-block\" style=\"width: 300px\">\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" autocomplete=\"off\" class=\"layui-input\" th:value=\"${myEntity."+info.getBeanName()+"}\"\n");
                replace.append("                    lay-verify=\"" + layuiCheck + "\" placeholder=\"非必填," + layuiCheckDesc + "\">\n");
                replace.append("                必填、非必填取其一\n");
                replace.append("                    lay-verify=\"required|" + layuiCheck + "\" placeholder=\"必填," + layuiCheckDesc + "\">\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
            } else if (info.getBeanType().contains("Date")&&!info.getBeanName().contains("create")&&!info.getBeanName().contains("update")){
                replace.append("        <div class=\"layui-form-item\">");
                replace.append("            <label class=\"layui-form-label\">"+info.getBeanDesc()+"</label>");
                replace.append("            <div class=\"layui-input-inline\">");
                replace.append("                <input type=\"text\" name=\""+info.getBeanName()+"\" lay-verify=\"required\" placeholder=\"yyyy-MM-dd HH:mm:ss\"");
                replace.append("                       class=\"layui-input dayTimeNoValue\">");
                replace.append("            </div>");
                replace.append("        </div>");
            }else {
                replace.append("        <div class=\"layui-form-item\">\n");
                replace.append("            <label class=\"layui-form-label\">" + info.getBeanDesc() + "</label>\n");
                replace.append("            <div class=\"layui-input-block\">\n");
                replace.append("                <input type=\"text\" name=\"" + info.getBeanName() + "\" autocomplete=\"off\" class=\"layui-input\" th:value=\"${myEntity."+info.getBeanName()+"}\"\n");
                replace.append("                    lay-verify=\"required\" placeholder=\"必填\">\n");
                replace.append("                必填、非必填取其一\n");
                replace.append("                    placeholder=\"非必填\">\n");
                replace.append("            </div>\n");
                replace.append("        </div>\n");
            }
        }
        return replace.toString();
    }

    /**
     * xxx_xxx字符串转换成xxxXxx
     *
     * @param flag true 首字母大写  false 首字母小写
     */
    public static String toJavaName(boolean flag, String str) {
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (flag && i == 0) {
                sb.append(Character.toUpperCase(str.charAt(i)));
                continue;
            }
            if ("_".equals(String.valueOf(str.charAt(i)))) {
                sb.append(Character.toUpperCase(str.charAt(++i)));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static TableNameInfo getTableNameInfo(String tableName, String projectPath, String packageName) {
        TableNameInfo tableNameInfo = new TableNameInfo();
        tableNameInfo.setEntityName(GenerateUtil.toJavaName(true, tableName));
        tableNameInfo.setEntityObjName(GenerateUtil.toJavaName(false, tableName));
        tableNameInfo.setHtmlPackage(tableName.replace("_", "/"));
        tableNameInfo.setHtmlUrl(tableName.replace("_", "-"));
        tableNameInfo.setEntityFilePath(projectPath + "\\" + packageName.replace(".", "\\") + "\\entity\\");
        tableNameInfo.setControllerFilePath(projectPath + "\\" + packageName.replace(".", "\\") + "\\controller\\");
        tableNameInfo.setHtmlDirPath(GenerateUtil.getHtmlDirPath(tableName, projectPath));
        return tableNameInfo;
    }

    /**
     * 创建html文件的文件夹
     *
     * @param tableName
     * @return 返回最后一级文件目录
     */
    public static String getHtmlDirPath(String tableName, String projectPath) {
        String xmlPackage = projectPath.replace("main\\java", "main\\resources\\templates");
        String[] split = tableName.split("_");
        for (String s : split) {
            xmlPackage = xmlPackage + "\\" + s;
            File file = new File(xmlPackage);
            file.mkdir();
        }
        return xmlPackage;
    }

    public static List<EntityInfo> getEntityIntoList(File file) throws Exception {
        List<String> list = FileUtils.readLines(file);
        List<EntityInfo> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("serialVersionUID")) {
                continue;
            }
            if (list.get(i).contains("private ")) {
                EntityInfo entityInfo = new EntityInfo();
                String[] split = list.get(i).split(" ");
                entityInfo.setBeanName(split[split.length - 1].replace(";", ""));
                entityInfo.setBeanType(split[split.length - 2]);
                if (list.get(i - 2).contains("     * ")) {
                    String beanDesc = list.get(i - 2).replace("     * ", "");
                    entityInfo.setBeanDesc(beanDesc);
                }
                resultList.add(entityInfo);
            }
        }
        return resultList;
    }
}
