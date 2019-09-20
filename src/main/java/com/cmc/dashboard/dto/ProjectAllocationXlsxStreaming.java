package com.cmc.dashboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

/**
 * @author: NVKhoa
 * @Date: Apr 24, 2018
 */
public class ProjectAllocationXlsxStreaming extends AbstractXlsxStreamingView {
	private static final List<String> XLSX_HEADER = Arrays.asList("DU", "DU PIC", "PROJECT NAME", "ALLOCATION", "BILLABLE");
	private static final String DEFAULT_SHEET = "Project Allocation";
	private String monthYear;
	private static final String DD_MM_YYYY = "dd-MM-yyyy";
	
	public ProjectAllocationXlsxStreaming(String monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY);
		String fileName = "Project Allocation MONTH "+ monthYear+" DAY " + date.format(formatter) +".xlsx";
		response.setHeader("Content-Disposition", "attachment; filename="+ fileName);

        @SuppressWarnings("unchecked")
        List<ProjectAllocationDTO> writter = (List<ProjectAllocationDTO>) model.get("writter");
        
        Sheet sheet = workbook.createSheet(DEFAULT_SHEET);
        Font font = workbook.createFont();
        Row header = sheet.createRow(0);
        for(int i = 0; i < XLSX_HEADER.size(); i++) {
        	font.setBold(true);
        	CellStyle style = workbook.createCellStyle();
        	style.setFont(font);
        	Cell cell = header.createCell(i);
        	cell.setCellValue(XLSX_HEADER.get(i));
        	cell.setCellStyle(style);
        }
        
        int rowCount = 1;
        for(ProjectAllocationDTO allocation : writter) {
        	List<DuAllocation> duAlloc = allocation.getDuAllocation();
        	if (!duAlloc.isEmpty()) {
	        	for(DuAllocation alloc : allocation.getDuAllocation()) {
	        		Row row = sheet.createRow(rowCount++);
	        		row.createCell(0).setCellValue(alloc.getName());
	        		row.createCell(1).setCellValue(allocation.getDuPic());
	        		row.createCell(2).setCellValue(allocation.getProjectName());
	        		row.createCell(3).setCellValue(alloc.getAllocation());
	        		row.createCell(4).setCellValue(alloc.getBillAble());
	        	}
        	}
        }
	}

}
