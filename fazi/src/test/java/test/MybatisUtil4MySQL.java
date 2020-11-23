package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
 
/**
 *  更改了生成的Mapper.java
 */
public class MybatisUtil4MySQL {
 
    /**
     **********************************使用前必读*******************
     **
     ** 使用前请将moduleName更改为自己模块的名称即可（一般情况下与数据库名一致），其他无须改动。
     **
     ***********************************************************
     */
 
    private final String driverName = "com.mysql.jdbc.Driver";
    private final String user = "root";
    private final String password = "123456";
    private final String moduleName = "fazi1127"; // 对应模块名称（根据自己模块做相应调整!!!务必修改^_^）
    private final String url = "jdbc:mysql://192.168.20.50:3306/" + moduleName + "?characterEncoding=utf8";
	
    private final String template_path = "d:/mybatis/templates";
    
    private final String controller_path = "d:/mybatis/controller/admin";
    
    private final String service_path = "d:/mybatis/service";
    
    private final String bean_path = "d:/mybatis/entity";
 
    private final String mapper_path = "d:/mybatis/mapper";
 
    private final String xml_path = "d:/mybatis/mapper/xml";
 
    private final String controller_package = "com.zhengbangnet.modules.controller.admin";
    
    private final String service_package = "com.zhengbangnet.modules.service";
    
    private final String bean_package = "com.zhengbangnet.modules.entity";
 
    private final String mapper_package = "com.zhengbangnet.modules.mapper";
 
    private final String type_char = "char";
    private final String type_date = "date";
    private final String type_timestamp = "timestamp";
    private final String type_int = "int";
    private final String type_bigint = "bigint";
    private final String type_text = "text";
    private final String type_bit = "bit";
    private final String type_decimal = "decimal";
    private final String type_blob = "blob";
    
    private String tableName = null;
    
    private String[] pkName = null;
    private String[] pkType = null;
    
    private String beanName = null;
 
    private String mapperName = null;

    private Connection conn = null;
    
    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }
 
    static{
    	FreeMarkerUtils.initFreeMarker(MybatisUtil4MySQL.class.getResource("/templates").getPath());
    }
 
    /**
     *  获取所有的表
     *
     * @return
     * @throws SQLException 
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            //          if ( tableName.toLowerCase().startsWith("yy_") ) {
            tables.add(tableName);
            //          }
        }
        return tables;
    }
 
 
    private void processTable( String table ) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for ( int i = 0 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        mapperName = beanName + "Mapper";
    }
 
 
    private String processType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "Long";
        } else if ( type.indexOf(type_int) > -1 ) {
            return "Integer";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_bit) > -1 ) {
            return "Boolean";
        } else if ( type.indexOf(type_decimal) > -1 ) {
            return "java.math.BigDecimal";
        } else if ( type.indexOf(type_blob) > -1 ) {
            return "byte[]";
        }
        return null;
    }
 
 
    private String processField( String field ) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for ( int i = 1 ; i < fields.length ; i++ ) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }
 
 
    /**
     *  将实体类名首字母改为小写
     *
     * @param beanName
     * @return 
     */
    private String processResultMapId( String beanName ) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }
 
 
    /**
     *  构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException 
     */
    private BufferedWriter buildClassComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" **/");
        return bw;
    }
 
 
    /**
     *  构建方法上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException 
     */
    private BufferedWriter buildMethodComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t **/");
        return bw;
    }
 
 
    private void buildService( List<String> columns, List<String> types, List<String> comments, String tableComment ) throws IOException{
    	
    	{
	    	File folder = new File(service_path);
	        if ( !folder.exists() ) {
	            folder.mkdir();
	        }
	        
	        File serviceFile = new File(service_path, beanName + "Service.java");
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile)));
	        bw.write("package " + service_package + ";");
	        bw.newLine();
	        bw.write("import com.zhengbangnet.common.service.BaseService;");
	        bw.newLine();
	    	bw.write("import com.zhengbangnet.modules.entity."+beanName+";");
	    	bw.newLine();
	    	bw.newLine();
	    	bw.write("public interface " + beanName + "Service extends BaseService<"+beanName+"," + "Long> {");
	    	bw.newLine();
	    	bw.newLine();
	    	bw.newLine();
	    	bw.write("}");
	    	bw.flush();
	        bw.close();
    	}
    	
    	{
	        File folder = new File(service_path+"/impl");
	        if ( !folder.exists() ) {
	            folder.mkdir();
	        }
	        
	        File serviceFile = new File(service_path+"/impl", beanName + "ServiceImpl.java");
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile)));
	        bw.write("package " + service_package + ".impl;");
	        bw.newLine();
	        bw.write("import javax.annotation.Resource;");
	        bw.newLine();
	        bw.write("import org.springframework.stereotype.Service;");
	        bw.newLine();
	        bw.write("import org.springframework.transaction.annotation.Transactional;");
	        bw.newLine();
	        bw.write("import com.zhengbangnet.common.mapper.BaseMapper;");
	        bw.newLine();
	        bw.write("import com.zhengbangnet.common.service.impl.BaseServiceImpl;");
	        bw.newLine();
	    	bw.write("import com.zhengbangnet.modules.entity."+beanName+";");
	    	bw.newLine();
	    	bw.write("import com.zhengbangnet.modules.service."+beanName+"Service;");
	    	bw.newLine();
	    	bw.write("import com.zhengbangnet.modules.mapper."+beanName+"Mapper;");
	    	bw.newLine();
	    	
	    	String nbn = beanName.substring(0, 1).toLowerCase()+beanName.substring(1,beanName.length());
	    	
	    	bw.newLine();
	    	bw.write("@Service(\""+nbn+"ServiceImpl\")");
	    	bw.newLine();
	    	bw.write("@Transactional(readOnly=true)");
	    	bw.newLine();
	    	bw.write("public class " + beanName + "ServiceImpl extends BaseServiceImpl<"+beanName+"," + "Long> implements "+beanName+"Service{");
	    	bw.newLine();
	    	bw.newLine();
	    	bw.write("\t@Resource(name=\""+nbn+"Mapper\")");
	    	bw.newLine();
	    	bw.write("\tprivate "+beanName+"Mapper "+nbn+"Mapper;");
	    	bw.newLine();
	    	bw.newLine();
	    	bw.write("\t@Resource(name=\""+nbn+"Mapper\")");
	    	bw.newLine();
	    	bw.write("\tpublic void setBaseDao(BaseMapper<"+beanName+",Long> baseMapper) {");
	    	bw.newLine();
	    	bw.write("\t\tsuper.setBaseDao(baseMapper);");
	    	bw.newLine();
	    	bw.write("\t}");
	    	
	    	bw.newLine();
	    	bw.write("}");
	    	bw.flush();
	        bw.close();
    	}
        
    }
    
    /**
     * 生成controller
     */
    private void buildController( List<String> columns, List<String> types) throws IOException{
    	List<String> list = new ArrayList<String>();
		for(int i=0;i<types.size();i++){
			String t = processType(types.get(i));
			list.add(t);
		}
    	Map<String,Object> templateData = new HashMap<String, Object>();  
		templateData.put("columns",columns);
		templateData.put("types",list);
		templateData.put("beanName",beanName);
		templateData.put("humpBeanName",beanName.substring(0,1).toLowerCase()+beanName.substring(1, beanName.length()));
		templateData.put("table",processToUnderline(beanName));
		// 生成文件  
		FreeMarkerUtils.crateFile(templateData, "controller.ftl", controller_path+"/"+beanName+"Controller.java", true);  
    }
    
    private void buildTemplate( List<String> columns, List<String> types, List<String> comments, String tableComment) throws IOException{
    	List<String> list = new ArrayList<String>();
    	for(int i=0;i<types.size();i++){
    		String t = processType(types.get(i));
    		list.add(t);
    	}
    	Map<String,Object> templateData = new HashMap<String, Object>();  
    	templateData.put("columns",columns);
    	templateData.put("types",list);
    	templateData.put("comments",comments);
    	templateData.put("beanName",beanName);
    	templateData.put("humpBeanName",beanName.substring(0,1).toLowerCase()+beanName.substring(1, beanName.length()));
    	templateData.put("table",processToUnderline(beanName));
    	// 生成文件  
    	FreeMarkerUtils.crateFile(templateData, "list.ftl", template_path+"/"+processToUnderline(beanName)+"/list.jsp", true);
    	FreeMarkerUtils.crateFile(templateData, "add.ftl", template_path+"/"+processToUnderline(beanName)+"/add.jsp", true);
    	FreeMarkerUtils.crateFile(templateData, "edit.ftl", template_path+"/"+processToUnderline(beanName)+"/edit.jsp", true);
    }
    
    /**
     *  生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildEntityBean( List<String> columns, List<String> types, List<String> comments, String tableComment ) throws IOException {
        File folder = new File(bean_path);
        if ( !folder.exists() ) {
            folder.mkdir();
        }
 
        File beanFile = new File(bean_path, beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + bean_package + ";");
        bw.newLine();
        bw.write("import com.zhengbangnet.common.entity.BaseEntity;");
        bw.newLine();
        //bw.write("import lombok.Data;");
        //      bw.write("import javax.persistence.Entity;");
        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("@SuppressWarnings(\"serial\")");
        bw.newLine();
        //      bw.write("@Entity");
        //bw.write("@Data");
        //bw.newLine();
        bw.write("public class " + beanName + " extends BaseEntity {");
        bw.newLine();
        bw.newLine();
        int size = columns.size();
        for ( int i = 0 ; i < size ; i++ ) {
        	if("modifyDate".equals(columns.get(i))||"createDate".equals(columns.get(i))||"id".equals(columns.get(i))){
        		continue;
        	}
            bw.write("\t/**" + comments.get(i) + "**/");
            bw.newLine();
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        bw.newLine();
        // 生成get 和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for ( int i = 0 ; i < size ; i++ ) {
        	
        	if("modifyDate".equals(columns.get(i))||"createDate".equals(columns.get(i))||"id".equals(columns.get(i))){
        		continue;
        	}
        	
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            //          bw.write("\tpublic void set" + tempField + "(" + tempType + " _" + _tempField + "){");
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + "){");
            bw.newLine();
            //          bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "(){");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }
 
 
    /**
     *  构建Mapper文件
     *
     * @throws IOException 
     */
    private void buildMapper() throws IOException {
        File folder = new File(mapper_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        File mapperFile = new File(mapper_path, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + mapper_package + ";");
        bw.newLine();
        bw.write("import com.zhengbangnet.common.mapper.BaseMapper;");
        bw.newLine();
        bw.write("import " + bean_package + "." + beanName + ";");
        bw.newLine();
        bw.write("import org.springframework.stereotype.Repository;");
        bw = buildClassComment(bw, mapperName + "数据库操作接口类");
        bw.newLine();
        bw.newLine();

        String prefix = beanName.substring(0, 1);
        String suffix = beanName.substring(1, beanName.length());
        
        bw.write("@Repository(\""+prefix.toLowerCase()+suffix+"Mapper\")");
        bw.newLine();
        bw.write("public interface " + mapperName + " extends BaseMapper<"+beanName+",Long>{");
        bw.newLine();
        bw.newLine();
        // ----------定义Mapper中的方法Begin----------
        
        bw.newLine();
        
        // ----------定义Mapper中的方法End----------
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }
 
 
    /**
     *  构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException 
     */
    private void buildMapperXml( List<String> columns, List<String> types, List<String> comments ) throws IOException {
        File folder = new File(xml_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }
 
        File mapperXmlFile = new File(xml_path, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName + "\">");
        bw.newLine();
        bw.newLine();
 
        /*bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) + "ResultMap\" type=\"" + beanName + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) + "\" column=\"" + columns.get(0) + "\" />");
        bw.newLine();
        int size = columns.size();
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            bw.write("\t\t<result property=\""
                    + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) + "\" />");
            bw.newLine();
        }
        bw.write("\t</resultMap>");
 
        bw.newLine();
        bw.newLine();
        bw.newLine();*/
 
        // 下面开始写SqlMapper中的方法
        // this.outputSqlMapperMethod(bw, columns, types);
        buildSQL(bw, columns, types);
 
        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }
 
 
    private void buildSQL( BufferedWriter bw, List<String> columns, List<String> types ) throws IOException {
        int size = columns.size();
        // 通用结果列
        bw.write("\t<!-- 通用查询结果列-->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Column_List\">");
        bw.newLine();
 
        bw.write("\t\t");
        for ( int i = 0 ; i < size ; i++ ) {
            bw.write(columns.get(i));
            if ( i != size - 1 ) {
                bw.write(", ");
            }
        }
 
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
 
 
        // 查询（根据主键ID查询）
        bw.write("\t<!-- 查询（根据主键ID查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"getByPrimaryKey\" resultType=\""
        		+ bean_package + "." + beanName + "\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        // 查询完
 
 
        // 删除（根据主键ID删除）
        bw.write("\t<!--删除：根据主键ID删除-->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\t DELETE FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
        // 删除完
 
 
        // 添加insert方法
        bw.write("\t<!-- 添加 -->");
        bw.newLine();
        bw.write("\t<insert id=\"insert\" parameterType=\"" + bean_package + "." + beanName + "\">");
        bw.newLine();
        bw.write("\t\t INSERT INTO " + tableName);
        bw.newLine();
        bw.write(" \t\t(");
        for ( int i = 0 ; i < size ; i++ ) {
        	if(i==0){
        		continue;
        	}
            bw.write(columns.get(i));
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
        bw.write(") ");
        bw.newLine();
        bw.write("\t\t VALUES ");
        bw.newLine();
        bw.write(" \t\t(");
        for ( int i = 0 ; i < size ; i++ ) {
        	if(i==0){
        		continue;
        	}
            bw.write("#{" + processField(columns.get(i)) + "}");
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
        bw.write(")");
        
        bw.newLine();
        bw.write("\t <selectKey resultType=\"java.lang.Long\" keyProperty=\"id\">");
        bw.newLine();
        bw.write("\t\t SELECT ");
        bw.newLine();
        bw.write("\t\t LAST_INSERT_ID() ");
        bw.newLine();
        bw.write("\t </selectKey> ");
        
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
        // 添加insert完
 
 
        //---------------  insert方法（匹配有值的字段）
        bw.write("\t<!-- 添加 （匹配有值的字段）-->");
        bw.newLine();
        bw.write("\t<insert id=\"insertSelective\" parameterType=\"" + bean_package + "." + beanName + "\">");
        bw.newLine();
        bw.write("\t\t INSERT INTO " + tableName);
        bw.newLine();
        bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
 
        String tempField = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            if(tempField.equals("id")){
            	continue;
            }
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + ",");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write("\t\t </trim>");
        bw.newLine();
 
        bw.write("\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();
 
        tempField = null;
        for ( int i = 0 ; i < size ; i++ ) {
        	if(i==0){
            	continue;
            }
            tempField = processField(columns.get(i));
            bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
            bw.newLine();
            bw.write("\t\t\t\t #{" + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.write("\t\t </trim>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
        //---------------  完毕
 
 
        // 修改update方法
        bw.write("\t<!-- 修 改-->");
        bw.newLine();
        bw.write("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + bean_package + "." + beanName + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableName);
        bw.newLine();
        bw.write(" \t\t <set> ");
        bw.newLine();
 
        tempField = null;
        for ( int i = 1 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            if(tempField.equals("createDate")){
            	continue;
            }
            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            bw.newLine();
            bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }
 
        bw.newLine();
        bw.write(" \t\t </set>");
        bw.newLine();
        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        // update方法完毕
 
        // ----- 修改（匹配有值的字段）
        bw.write("\t<!-- 修 改-->");
        bw.newLine();
        bw.write("\t<update id=\"updateByPrimaryKey\" parameterType=\"" + bean_package + "." + beanName + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableName);
        bw.newLine();
        bw.write("\t\t SET ");
 
        bw.newLine();
        tempField = null;
        for ( int i = 1 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            if(tempField.equals("createDate")){
            	continue;
            }
            bw.write("\t\t\t " + columns.get(i) + " = #{" + tempField + "}");
            if ( i != size - 1 ) {
                bw.write(",");
            }
            bw.newLine();
        }
 
        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
        
        // ----- 根据条件分页查询
        bw.write("\t<!-- 分页查询-->");
        bw.newLine();
        bw.write("\t<select id=\"findPageByParams\" resultType=\"java.util.Map\" >");
        bw.newLine();
        bw.write("\t\t SELECT ");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM "+tableName);
        bw.newLine();
        bw.write("\t\t where 1=1 ");
        bw.newLine();
        bw.write(" <!-- ");
        bw.newLine();
        bw.write("\t\t\t<if test=\"id != null \">");
        bw.newLine();
        bw.write("\t\t\t\t AND "+tableName+".id = #{id} ");
        bw.newLine();
        bw.write("\t\t\t</if>");
        bw.newLine();
        bw.write(" --> ");
        bw.newLine();
        bw.write("\t\t order by "+tableName+".createDate desc ");
        bw.newLine();
        bw.write("\t\t LIMIT #{pageable.firstResult},#{pageable.pageSize} ");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        
        bw.write("\t<select id=\"getCountByParams\" resultType=\"java.lang.Long\" >");
        bw.newLine();
        bw.write("\t\t SELECT COUNT("+pkName[0]+") FROM "+tableName);
        bw.newLine();
        bw.write("\t\t WHERE 1=1 ");
        bw.newLine();
        bw.write(" <!-- ");
        bw.newLine();
        bw.write("\t\t\t<if test=\"id != null \">");
        bw.newLine();
        bw.write("\t\t\t\t AND "+tableName+".id = #{id} ");
        bw.newLine();
        bw.write("\t\t\t</if>");
        bw.newLine();
        bw.write(" --> ");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
        
        // ----- 查找所有
        bw.write("\t<select id=\"findAll\" resultType=\"" + bean_package + "." + beanName + "\" >");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM "+tableName);
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
        
    }
 
 
    /**
     *  获取所有的数据库表注释
     *
     * @return
     * @throws SQLException 
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }
 
    private String processToUnderline(String field){
    	String[] array = field.split("");
    	StringBuffer buffer = new StringBuffer();
    	for(String a:array){
    		if(StringUtils.isBlank(a)){
    			continue;
    		}
    		if(Character.isUpperCase(a.charAt(0))){
    			buffer.append("_");
    			buffer.append(a.toLowerCase());
    		}else{
    			buffer.append(a);
    		}
    	}
    	String string = buffer.toString();
    	return string.substring(1, string.length());
    }
    
    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for ( String table : tables ) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while ( results.next() ) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet keyRS = metaData.getPrimaryKeys(null, null, table);
            
            String tempPK = "";
            String tempPKType = "";
            while (keyRS.next())  {  
               tempPK = keyRS.getString("COLUMN_NAME") + ",";  
//               tempPKType = keyRS.getString("PK_NAME") + ",";
            }  
            pkName = tempPK.split(","); 
            pkType = tempPKType.split(","); 
            
            tableName = table;
            processTable(table);
            //          this.outputBaseBean();
            String tableComment = tableComments.get(tableName);
            buildEntityBean(columns, types, comments, tableComment);
            buildService(columns, types, comments, tableComment);
            buildMapper();
            buildMapperXml(columns, types, comments);
            
            buildController(columns,types);
            buildTemplate(columns,types,comments,tableComment);
            
        }
        conn.close();
    }
 
 
    public static void main( String[] args ) {
        try {
            new MybatisUtil4MySQL().generate();
            // 自动打开生成文件的目录
//            Runtime.getRuntime().exec("cmd /c start explorer D:\\mybatis");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}