package com.cmc.dashboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

/**
 * 
 * @author duyhieu
 * @Date 07/05/2018
 */
public class ResourceUnallocationXlsxStreaming extends AbstractXlsxStreamingView {

	private String monthYear;

	public ResourceUnallocationXlsxStreaming(String monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final LocalDate date = LocalDate.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DateFormart.DATE_D_M_Y_FORMAT2);
		String fileName = "Resource Unallocation Month " + monthYear + " Day " + date.format(formatter)
				+ Constants.FORMAT_EXPORT_FILE;
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		UnallocationListDTO writter = (UnallocationListDTO) model.get(Constants.StringPool.MODEL_NAME);
		Sheet sheet = workbook.createSheet(Constants.DefaultSheet.RESOURCE_UNALLOCATION_DEFAULT_SHEET);
		Font font = workbook.createFont();
		Row header = sheet.createRow(0);

		for (int i = 0; i < Constants.HeaderExportFile.RESOURCE_UNALLOCATION_XLSX_HEADER.size(); i++) {
			font.setBold(true);
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);
			Cell cell = header.createCell(i);
			cell.setCellValue(Constants.HeaderExportFile.RESOURCE_UNALLOCATION_XLSX_HEADER.get(i));
			cell.setCellStyle(style);
		}
		int rowCount = 1;

		for (UnallocationDTO unallocationDTO : writter.getUnallocationDTO()) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(unallocationDTO.getDuName());
			row.createCell(1).setCellValue(unallocationDTO.getResourceName());
			row.createCell(2).setCellValue(MethodUtil.formatFloatNumberType(unallocationDTO.getAllocation()) + Constants.StringPool.PERCENT_CHAR);
			row.createCell(3).setCellValue(unallocationDTO.getStatus());
		}
	}

}
