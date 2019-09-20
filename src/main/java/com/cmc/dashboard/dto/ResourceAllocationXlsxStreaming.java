package com.cmc.dashboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.cmc.dashboard.qms.model.ResourceAllocationQms;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

/**
 * 
 * @author duyhieu
 * @Date 07/05/2018
 */
public class ResourceAllocationXlsxStreaming extends AbstractXlsxStreamingView {

	private String monthYear;

	public ResourceAllocationXlsxStreaming(String monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DateFormart.DATE_D_M_Y_FORMAT2);
		String fileName = "Resource Allocation Month " + monthYear + " Day " + date.format(formatter) + Constants.FORMAT_EXPORT_FILE;
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		@SuppressWarnings("unchecked")
		List<ResourceAllocationQms> writter = (List<ResourceAllocationQms>) model.get(Constants.StringPool.MODEL_NAME);
		Sheet sheet = workbook.createSheet(Constants.DefaultSheet.RESOURCE_ALLOCATION_DEFAULT_SHEET);
		Font font = workbook.createFont();
		Row header = sheet.createRow(0);

		for (int i = 0; i < Constants.HeaderExportFile.RESOURCE_ALLOCATION_XLSX_HEADER.size(); i++) {
			font.setBold(true);
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);
			Cell cell = header.createCell(i);
			cell.setCellValue(Constants.HeaderExportFile.RESOURCE_ALLOCATION_XLSX_HEADER.get(i));
			cell.setCellStyle(style);
		}
		int rowCount = 1;

		for (ResourceAllocationQms resourceAllocationQms : writter) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(resourceAllocationQms.getDu());
			row.createCell(1).setCellValue(resourceAllocationQms.getUsername());
			row.createCell(2).setCellValue(resourceAllocationQms.getRole());
			row.createCell(3).setCellValue(resourceAllocationQms.getDuPic());
			row.createCell(4).setCellValue(resourceAllocationQms.getName());
			row.createCell(5).setCellValue(resourceAllocationQms.getProjectCode());
			row.createCell(6).setCellValue(resourceAllocationQms.getProjectType());
			if (Constants.Numbers.STATUS_OPEN == resourceAllocationQms.getStatus())
				row.createCell(7).setCellValue(Constants.StringPool.OPEN);
			else
				row.createCell(7).setCellValue(Constants.StringPool.CLOSED);
			
			if(Float.compare(Constants.Numbers.MAN_POWER_NULL , resourceAllocationQms.getPlanAllocation()) == 0) {
				row.createCell(8).setCellValue(Constants.StringPool.NA);
			} else if(Float.compare(Constants.Numbers.ALLOCATION_NULL , resourceAllocationQms.getPlanAllocation()) == 0) {
				row.createCell(8).setCellValue(Constants.StringPool.HYPHEN_CHAR);
			} else {
				row.createCell(8).setCellValue(MethodUtil.formatFloat(resourceAllocationQms.getPlanAllocation()) + Constants.StringPool.PERCENT_CHAR);
			}
			
			if (Float.compare(Constants.Numbers.ALLOCATION_NULL , resourceAllocationQms.getAllocation()) == 0)
				row.createCell(9).setCellValue(Constants.StringPool.HYPHEN_CHAR);
			else
				row.createCell(9).setCellValue(MethodUtil.formatFloat(resourceAllocationQms.getAllocation()) + Constants.StringPool.PERCENT_CHAR);
			
		}

	}

}
