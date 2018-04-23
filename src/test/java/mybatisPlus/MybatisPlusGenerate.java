package mybatisPlus;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.io.File;

/**
 * 生成相关代码
 */
public class MybatisPlusGenerate {
    /**
     * 数据库表名
     */
    protected static String[] tableNames = {"order"};
    /**
     * 项目路径
     */
    protected static String projectPath = "D:\\git\\MyManage\\src\\main\\java";
    /**
     * 包名称
     */
    protected static String packageName = "com.xuchen";
    /**
     * 作者
     */
    protected static String author = "xuchen";
    /**
     * 生成controller通用模版(需手动微调)
     */
    protected static boolean createController = true;

    protected static String dbUrl = "jdbc:mysql://localhost:3306/manage";
    protected static String dbName = "root";
    protected static String dbPassword = "xuchen93";
    protected static String driverName = "com.mysql.jdbc.Driver";
    protected static boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService

    @Test
    public void generateCode() throws Exception {
        boolean checkControllerFile = checkControllerFile(tableNames);
        if (!checkControllerFile) {
            return;
        }
        generateByTables(serviceNameStartWithI, packageName, tableNames);
        GenerateUtil.moveMapperXmlFile(projectPath, packageName);
        for (String tableName : tableNames) {
            TableNameInfo tableNameInfo = GenerateUtil.getTableNameInfo(tableName,projectPath,packageName);
            if (createController) {
                GenerateUtil.createControllerFile(tableNameInfo,projectPath);
            }
            GenerateUtil.createHtmlFile(tableNameInfo,projectPath);
        }
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(dbName)
                .setPassword(dbPassword)
                .setDriverName(driverName);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor(author)
                .setOutputDir(projectPath)
                .setFileOverride(true)
                .setEnableCache(false)
        ;
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }


    private static boolean checkControllerFile(String... tableNames) {
        for (String tableName : tableNames) {
            File file = new File(projectPath + "\\" + packageName.replace(".", "\\") + "\\controller\\" + GenerateUtil.toJavaName(true, tableName) + "Controller.java");
            if (file.exists()) {
                System.out.println("已经存在" + tableName + "表的Controller文件,如确定使用生成的,请先删除该文件");
                return false;
            }
        }
        return true;
    }

}
