package util;

import com.xuchen.model.ParentMenu;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CreateObjectUtil {


    public static void main(String[] args) throws Exception {
//		getFiled();
//		getSQL();
        Object model=new ParentMenu();
		getModelXml(model);
//        getFiledBySql(sql);
    }

    private static String tableName = "sys_user";
    private static String sql = "SELECT smp.id,smp.name,smp.url,smp.img,smp.type,sms.id AS son_id,\n" +
            "sms.name AS son_name,sms.url AS son_url,sms.img AS son_img,sms.type AS son_type,sms.parent_id\n" +
            "\tFROM sys_menu smp\n" +
            "\tINNER JOIN sys_menu sms ON smp.id = sms.parent_id\n" +
            "\tINNER JOIN sys_role sr ON smp.id = sr.menu_id\n" +
            "\tINNER JOIN sys_user su ON su.role_id = sr.user_role_id\n" +
            "WHERE su.id = 1\n" +
            "\tORDER BY smp.order DESC,smp.id,sms.order DESC,sms.id";

    private static String url = "jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=utf8";
    private static String username = "root";
    private static String password = "root";

    private static Connection connection;
    private static DatabaseMetaData metaData;
    private static ResultSet resultSet;

    static {
        try {
            connection = DriverManager.getConnection(url, username, password);
            metaData = connection.getMetaData();
            resultSet = metaData.getColumns(null, null, tableName, null);
        } catch (SQLException e) {
            System.out.println("初始化失败!");
            e.printStackTrace();
        }
    }


    private static void getSQL() throws Exception {
        findByIdSQL();
        insertSQL();
        updateSQL();
        connection.close();
    }

    /**
     * 输入表名，生成成员变量
     */
    private static void getFiled() throws Exception {
        resultSet.beforeFirst();
        System.out.println("private static final long serialVersionUID = 1L;");
        while (resultSet.next()) {
//            if ("update_time/create_time/create_user/update_user".contains(resultSet.getString("COLUMN_NAME").toLowerCase())) {
//                continue;
//            }
            if (!"".equals(resultSet.getString("REMARKS"))) {
                System.out.println("/**" + resultSet.getString("REMARKS") + "*/");
            }
            if (resultSet.getString("TYPE_NAME").equals("VARCHAR")) {
                System.out.println("private String " + getFiled(resultSet.getString("COLUMN_NAME")) + ";");
            }
            if (("DECIMAL/FLOAT").contains(resultSet.getString("TYPE_NAME"))) {
                System.out.println("private Double " + getFiled(resultSet.getString("COLUMN_NAME")) + ";");
            }
            if ("TIMESTAMP/DATETIME".contains(resultSet.getString("TYPE_NAME"))) {
                System.out.println("private Date " + getFiled(resultSet.getString("COLUMN_NAME")) + ";");
            }
            if ("TINYINT/SMALLINT/INT UNSIGNED".contains(resultSet.getString("TYPE_NAME"))) {
                System.out.println("private Integer " + getFiled(resultSet.getString("COLUMN_NAME")) + ";");
            }
        }
        connection.close();
    }

    /**
     * 输入表名，生成成员变量
     */
    private static void getFiledBySql(String sql) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSetMetaData metaData = preparedStatement.executeQuery().getMetaData();
        System.out.println("private static final long serialVersionUID = 1L;");
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if ("VARCHAR".equals(metaData.getColumnTypeName(i).toUpperCase())) {
                System.out.println("private String " + getFiled(metaData.getColumnLabel(i)) + ";");
            }
            if ("DECIMAL".equals(metaData.getColumnTypeName(i).toUpperCase())) {
                System.out.println("private Double " + getFiled(metaData.getColumnLabel(i)) + ";");
            }
            if ("TIMESTAMP/TINYINT/SMALLINT/BIGINT".contains(metaData.getColumnTypeName(i).toUpperCase())) {
                System.out.println("private Integer " + getFiled(metaData.getColumnLabel(i)) + ";");
            }
        }
        connection.close();
    }

    private static void findByIdSQL() throws Exception {
        resultSet.beforeFirst();
        System.out.println("	<select id=\"findById\" parameterType=\"java.lang.Integer\" resultType=\"XXX.XXX.XXX\">");
        System.out.println("		SELECT");
        while (resultSet.next()) {
            if (resultSet.isFirst()) {
                System.out.print("			");
            }
            if (resultSet.isLast())
                System.out.println(resultSet.getString("COLUMN_NAME").toLowerCase());
            else
                System.out.print(resultSet.getString("COLUMN_NAME").toLowerCase() + ",");
        }
        resultSet.beforeFirst();
        resultSet.next();
        System.out.println("		FROM");
        System.out.println("			" + tableName);
        System.out.println("		WHERE " + resultSet.getString("COLUMN_NAME").toLowerCase() +
                " = #{" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + "}");
        System.out.println("	</select>");
        System.out.println();
    }

    private static void insertSQL() throws Exception {
        resultSet.beforeFirst();
        System.out.println("	<insert id=\"insertBySelective\" parameterType=\"XXX.XXX.XXX\">");
        System.out.println("		insert into " + tableName);
        System.out.println("		<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        while (resultSet.next()) {
            System.out.println("			<if test=\"" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + " != null\">");
            System.out.println("				" + resultSet.getString("COLUMN_NAME").toLowerCase() + ",");
            System.out.println("			</if>");
        }
        System.out.println("		</trim>");
        resultSet.beforeFirst();
        System.out.println("		<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        while (resultSet.next()) {
            System.out.println("			<if test=\"" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + " != null\">");
            System.out.println("				#{" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + "},");
            System.out.println("			</if>");
        }
        System.out.println("		</trim>");
        System.out.println("	</insert>");
        System.out.println();
    }

    private static void updateSQL() throws Exception {
        resultSet.beforeFirst();
        System.out.println("	<update id=\"updateBySelective\" parameterType=\"XXX.XXX.XXX\">");
        System.out.println("		update " + tableName);
        System.out.println("		<set>");
        while (resultSet.next()) {
            System.out.println("			<if test=\"" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + " != null\">");
            System.out.println("				" + resultSet.getString("COLUMN_NAME").toLowerCase() + " = #{" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + "},");
            System.out.println("			</if>");
        }
        System.out.println("		</set>");
        resultSet.beforeFirst();
        resultSet.next();
        System.out.println("		where " + resultSet.getString("COLUMN_NAME").toUpperCase() + " = #{" + getFiled(resultSet.getString("COLUMN_NAME").toLowerCase()) + "}");
        System.out.println("	</update>");
        System.out.println();
    }

    private static String getFiled(String string) {
        StringBuilder column = new StringBuilder();
        string = string.toLowerCase();
        for (int i = 0; i < string.length(); i++) {
            if ("_".equals(String.valueOf(string.charAt(i)))) {
                column.append(Character.toUpperCase(string.charAt(++i)));
                continue;
            }
            column.append(string.charAt(i));
        }
        return column.toString();
    }

    /**
     * 通过对象获取Xml的resultMap,子对象为object时需要测试
     */
    public static void getModelXml(Object obj) throws Exception {
        Class<? extends Object> objClass = obj.getClass();
        getEachProperty(objClass.newInstance());
        getObjectProperty(objClass.newInstance());
    }

    private static void getEachProperty(Object obj) throws Exception {
        Class<? extends Object> objClass = obj.getClass();
        Class<? extends Object> superClass;
        System.out.println("    <resultMap id=\"" + objClass.getSimpleName() + "\" type=\"" + objClass.getCanonicalName() + "\">");
        List<Field> list =new ArrayList<>();
        Field[] thisField = objClass.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        list.addAll(Arrays.asList(thisField));
        superClass=objClass.getSuperclass();
        while (superClass!=null){
            Field[] superField = superClass.getDeclaredFields();
            list.addAll(Arrays.asList(superField));
            superClass=superClass.getSuperclass();
        }
        Field[] field =new Field[list.size()];
        list.toArray(field);
        for (int j = 0; j < field.length; j++) { // 遍历所有属性
            StringBuilder column = new StringBuilder();
            String property = "";
            String jdbcType = "";
            property = field[j].getName();
            if (property.equals("serialVersionUID"))
                continue;
            for (int i = 0; i < property.length(); i++) {
                if (Character.isUpperCase(property.charAt(i)))
                    column.append("_");
                column.append(Character.toUpperCase(property.charAt(i)));
            }
            String type = field[j].getGenericType().toString(); // 获取属性的类型
            if (type.equals("class java.lang.String"))
                jdbcType = "VARCHAR";
            else if (type.equals("class java.lang.Integer") || type.equals("int"))
                jdbcType = "INTEGER";
            else if (type.equals("class java.lang.Boolean"))
                jdbcType = "BOOLEAN";
            else if (type.equals("byte")||type.equals("class java.lang.Byte"))
                jdbcType = "BYTE";
            else if (type.equals("long")||type.equals("class java.lang.Long"))
                jdbcType = "LONG INTEGER";
            else if (type.equals("float")||type.equals("class java.lang.Float"))
                jdbcType = "FLOAT";
            else if (type.equals("double")||type.equals("class java.lang.Double"))
                jdbcType = "DOUBLE";
            else if (type.equals("class java.util.Date"))
                jdbcType = "TIMESTAMP";
            else if (type.contains("java.util.List")) {
                System.out.println("        <collection  property=\"" + property + "\" resultMap=\"" + getClassName(type) + "\"></collection>");
                continue;
            } else {
                System.out.println("        <association property=\"" + property + "\" resultMap=\"" + getClassName(type) + "\"/>");
                continue;
            }
            System.out.println("        <result column=\"" + column + "\" property=\"" + property + "\" jdbcType=\""
                    + jdbcType + "\"/>");
        }
        System.out.println("    </resultMap>");
    }

    private static void getObjectProperty(Object obj) throws Exception {
        Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) { // 遍历所有属性
            StringBuilder column = new StringBuilder();
            String property = "";
            String jdbcType = "";
            property = field[j].getName();
            if (property.equals("serialVersionUID"))
                continue;
            for (int i = 0; i < property.length(); i++) {
                if (Character.isUpperCase(property.charAt(i)))
                    column.append("_");
                column.append(Character.toUpperCase(property.charAt(i)));
            }
            String type = field[j].getGenericType().toString(); // 获取属性的类型
            if (type.equals("class java.lang.String"))
                jdbcType = "VARCHAR";
            else if (type.equals("class java.lang.Integer") || type.equals("int"))
                jdbcType = "INTEGER";
            else if (type.equals("class java.lang.Boolean"))
                jdbcType = "BOOLEAN";
            else if (type.equals("byte")||type.equals("class java.lang.Byte"))
                jdbcType = "BYTE";
            else if (type.equals("long")||type.equals("class java.lang.Long"))
                jdbcType = "LONG INTEGER";
            else if (type.equals("float")||type.equals("class java.lang.Float"))
                jdbcType = "FLOAT";
            else if (type.equals("double")||type.equals("class java.lang.Double"))
                jdbcType = "DOUBLE";
            else if (type.equals("class java.util.Date"))
                jdbcType = "TIMESTAMP";
            else {
                Object sonObj = Class.forName(getFullClassName(type)).newInstance();
                System.out.println();
                getEachProperty(sonObj);
            }
        }
    }

    private static String getFullClassName(String type) {
        if (type.contains("<")) {//集合
            return type.split(">")[0].split("<")[1];
        } else {//对象
            return type.substring(6, type.length());
        }
    }

    private static String getClassName(String type) {
        if (type.contains("<")) {
            String[] split = type.split("\\.");
            String name = split[split.length - 1];
            return name.substring(0, name.length() - 1);
        } else {
            String[] split = type.split("\\.");
            String name = split[split.length - 1];
            return name;
        }
    }
}