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

import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

/**
 * 
 * @author duyhieu
 * @Date 07/05/2018
 */
public class DeliveryUnitXlsxStreaming extends AbstractXlsxStreamingView {

	private final String monthYear;

	public DeliveryUnitXlsxStreaming(String monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	protected void buildExcelDocument(final Map<String, Object> model,final Workbook workbook,final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final LocalDate date = LocalDate.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DateFormart.DATE_D_M_Y_FORMAT2);
		String fileName = "Delivery Unit Month " + monthYear + " Day " + date.format(formatter)
				+ Constants.FORMAT_EXPORT_FILE;
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		@SuppressWarnings("unchecked")
		List<DeliveryUnitDTO> writter = (List<DeliveryUnitDTO>) model.get(Constants.StringPool.MODEL_NAME);
		final Sheet sheet = workbook.createSheet(Constants.DefaultSheet.DELIVERY_UNIT_DEFAULT_SHEET);
		Font font = workbook.createFont();
		Row header = sheet.createRow(0);

		for (int i = 0; i < Constants.HeaderExportFile.DELIVERY_UNIT_XLSX_HEADER.size(); i++) {
			font.setBold(true);
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);
			Cell cell = header.createCell(i);
			cell.setCellValue(Constants.HeaderExportFile.DELIVERY_UNIT_XLSX_HEADER.get(i));
			cell.setCellStyle(style);
		}
		int rowCount = 1;
//
//		for (final DeliveryUnitDTO deliveryUnit : writter) {
//			Row row = sheet.createRow(rowCount++);
//			row.createCell(0).setCellValue(deliveryUnit.getName());
//			row.createCell(1).setCellValue(deliveryUnit.getTotalManPower());
//			row.createCell(2).setCellValue(MethodUtil.formatFloat(deliveryUnit.getTotalAllocation()));
//			row.createCell(3).setCellValue(Constants.StringPool.NA);
//			if (Float.compare((float)Constants.Numbers.UTILIZED_RATE_NULL , deliveryUnit.getUltilizedRate()) == 0)
//				row.createCell(4).setCellValue(Constants.StringPool.NA);
//			else
//				row.createCell(4).setCellValue(MethodUtil.formatFloat(deliveryUnit.getUltilizedRate()));
//			row.createCell(5).setCellValue(MethodUtil.formatFloat(deliveryUnit.getAllocatedToOther()));
//			row.createCell(6).setCellValue(Constants.StringPool.NA);
//			row.createCell(7).setCellValue(deliveryUnit.getExternalBillable());
//			if (Float.compare((float)Constants.Numbers.EFFORT_EFFICIENCY_NULL , deliveryUnit.getEffortEfficiency()) == 0)
//				row.createCell(8).setCellValue(Constants.StringPool.NA);
//			else
//				row.createCell(8).setCellValue(MethodUtil.formatFloat(deliveryUnit.getEffortEfficiency()) + Constants.StringPool.PERCENT_CHAR);
//		}

	}

}
