package com.robby.app.commons.service;

import com.robby.app.commons.enums.Excels;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件解析服务
 * Created @ 2019/11/12
 * @author liuwei
 */
public interface AnalysisService {

    /**
     * 解析excel文件
     * @param input
     * @param excels
     * @return
     */
    Map<String, List<Map<String, Object>>> analysisExcel(InputStream input, Excels excels) throws IOException, IllegalAccessException;

    /**
     * 解析word文件
     * @param input
     * @return
     * @throws IOException
     */
    String analysisDoc(FileInputStream input) throws IOException;

    /**
     * 解析PDF文件
     * @param input
     * @return
     * @throws IOException
     */
    String analysisPDF(FileInputStream input) throws IOException;

}
