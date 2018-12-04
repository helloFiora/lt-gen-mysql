package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.generator.domain.TableInfo;

/**
 * 代码生成 服务层
 * 
 * @author ruoyi
 */
public interface IGenService
{
    /**
     * 查询ry数据库表信息
     * 
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    public List<TableInfo> selectTableList(TableInfo tableInfo);

    /**
     * 生成代码
     * 
     * @param tableName 表名称
     * @param author 作者
     * @param packageName 
     * @param prefix 
     * @return 数据
     */
    public byte[] generatorCode(String tableName, String author, String packageName, String prefix);

    /**
     * 批量生成代码
     * 
     * @param tableNames 表数组
     * @param author 作者
     * @param packageName 
     * @param prefix 
     * @return 数据
     */
    public byte[] generatorCode(String[] tableNames,String author, String packageName, String prefix);
}
