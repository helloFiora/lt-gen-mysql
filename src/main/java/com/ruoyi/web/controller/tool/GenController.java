package com.ruoyi.web.controller.tool;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.support.Convert;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.generator.domain.TableInfo;
import com.ruoyi.generator.service.IGenService;
import com.ruoyi.framework.web.base.BaseController;

/**
 * 代码生成 操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    private String prefix = "tool/gen";

    @Autowired
    private IGenService genService;

    @GetMapping()
    public String gen(ModelMap mmap)
    {
    	mmap.put("author", Global.getAuthor());
    	mmap.put("packageName", Global.getPackageName());
        return prefix + "/gen";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableInfo tableInfo)
    {
        startPage();
        List<TableInfo> list = genService.selectTableList(tableInfo);
        return getDataTable(list);
    }

    /**
     * 生成代码
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName,String author,String packageName,String prefix) throws IOException
    {
        byte[] data = genService.generatorCode(tableName,author,packageName,prefix);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"table.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 批量生成代码
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables,String author,String packageName,String prefix) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genService.generatorCode(tableNames,author,packageName,prefix);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"tables.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
