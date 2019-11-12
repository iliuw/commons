package com.robby.app.commons.service.impl;

import com.robby.app.commons.enums.Excels;
import com.robby.app.commons.service.AnalysisService;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.Word6Extractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Override
    public Map<String, List<Map<String, Object>>> analysisExcel(InputStream input, Excels excels) throws IOException, IllegalAccessException {
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();

        // 初始化解析器
        Workbook workbook = null;
        switch (excels) {
            case xls:
                workbook = new HSSFWorkbook(input);
                break;
            case xlsx:
                workbook = new XSSFWorkbook(input);
                break;
            default:
                throw new IllegalAccessException("非法的文件类型");
        }

        // 开始解析
        if(Optional.ofNullable(workbook).isPresent()) {
            // 获取全部Sheet
            List<Sheet> sheets = new ArrayList<>();
            for(int i=0;i<workbook.getNumberOfSheets();i++) {
                Optional<Sheet> sheet = Optional.ofNullable(workbook.getSheetAt(i));
                if(sheet.isPresent() && sheet.get().getLastRowNum() > 0) {
                    sheets.add(sheet.get());
                }
            }
            // 解析全部Sheet
            sheets.parallelStream().forEach(sheet -> {
                result.put(sheet.getSheetName(), analysisSheet(sheet));
            });
        }

        return result;
    }

    @Override
    public String analysisDoc(FileInputStream input) throws IOException {
        @SuppressWarnings("resource")
        WordExtractor extractor = new WordExtractor(input);
        return extractor.getText();
    }

    @Override
    public String analysisPDF(FileInputStream input) throws IOException {
        PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(input));
        parser.parse();
        PDDocument doc = parser.getPDDocument();
        return new PDFTextStripper().getText(doc);
    }

    /**
     * 解析Sheet，获得Excel数据
     * @param sheet
     * @return
     */
    private synchronized List<Map<String, Object>> analysisSheet(Sheet sheet) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 获得table-header
        Map<Integer, String> header = readHeader(sheet.getRow(sheet.getFirstRowNum()));
        // 解析正文数据
        List<Row> rows = new ArrayList<>();
        for(int i=sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            rows.add(sheet.getRow(i));
        }

        Set<Integer> index = header.keySet();
        rows.stream().forEach(row -> {
            Map<String, Object> item = new LinkedHashMap<>();
            index.forEach(idx -> {
                Optional<Cell> cell = Optional.ofNullable(row.getCell(idx));
                String hd = header.get(idx);
                Object td = null;
                if(cell.isPresent() && cell.get().getCellType() != CellType.BLANK) {
                    Cell c = cell.get();
                    switch(c.getCellType()) {
                        default:
                            td = c.getStringCellValue();
                            break;
                        case NUMERIC:
                            String f = c.getCellStyle().getDataFormatString();
                            if("General".equalsIgnoreCase(f)) {
                                td = c.getNumericCellValue();
                            } else if(f.indexOf("yy") >= 0 && f.indexOf("mm") >= 0) {
                                td = c.getDateCellValue();
                            }
                            break;
                        case BOOLEAN:
                            td = c.getBooleanCellValue();
                            break;
                        case BLANK:
                            td = "";
                            break;
                        case FORMULA:
                            td = c.getCellFormula();
                            break;
                        case ERROR:
                            td = c.getErrorCellValue();
                            break;
                    }
                    item.put(hd, td);
                }
            });
            if(item != null && !item.isEmpty()) {
                result.add(item);
            }
        });

        return result;
    }

    private Map<Integer, String> readHeader(Row row) {
        Map<Integer, String> header = new LinkedHashMap<>();
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            Optional<Cell> cell = Optional.ofNullable(row.getCell(i));
            if(cell.isPresent() && cell.get().getCellType() != CellType.BLANK) {
                header.put(i, cell.get().getStringCellValue());
            }
        }
        return header;
    }

}
