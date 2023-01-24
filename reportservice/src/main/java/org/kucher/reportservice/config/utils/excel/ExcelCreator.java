package org.kucher.reportservice.config.utils.excel;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kucher.reportservice.service.dto.Composition;
import org.kucher.reportservice.service.dto.JournalFoodDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ExcelCreator {

    public byte[] createBook(List<JournalFoodDTO> dtoList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int rowNumber = 0;

        XSSFWorkbook book = new XSSFWorkbook();

        XSSFSheet sheet = book.createSheet();

        for (int i = 0; i < 100; i++) {
            sheet.createRow(i);
        }

        Row row = sheet.getRow(rowNumber);

        Cell cell = row.createCell(0);
        cell.setCellValue("Date supply");

        cell = row.createCell(1);
        cell.setCellValue("Total weight");

        cell = row.createCell(2);
        cell.setCellValue("Product title");

        cell = row.createCell(3);
        cell.setCellValue("Calories");

        cell = row.createCell(4);
        cell.setCellValue("Fats");

        cell = row.createCell(5);
        cell.setCellValue("Carbohydrates");

        cell = row.createCell(6);
        cell.setCellValue("Proteins");

        cell = row.createCell(7);
        cell.setCellValue("Recipe title");

        cell = row.createCell(8);
        cell.setCellValue("Ingredient");

        cell = row.createCell(9);
        cell.setCellValue("Weight");

        cell = row.createCell(10);
        cell.setCellValue("Calories");

        cell = row.createCell(11);
        cell.setCellValue("Fats");

        cell = row.createCell(12);
        cell.setCellValue("Carbohydrates");

        cell = row.createCell(13);
        cell.setCellValue("Proteins");

        rowNumber++;

        for (JournalFoodDTO jF : dtoList) {
            row = sheet.getRow(rowNumber);

            row.createCell(0).setCellValue(jF.getDtSupply());
            row.createCell(1).setCellValue(jF.getWeight());

            if(jF.getProduct() != null) {
                row.createCell(2).setCellValue(jF.getProduct().getTitle());
                row.createCell(3).setCellValue(jF.getProduct().getCalories());
                row.createCell(4).setCellValue(jF.getProduct().getFats());
                row.createCell(5).setCellValue(jF.getProduct().getCarbohydrates());
                row.createCell(6).setCellValue(jF.getProduct().getProteins());
                rowNumber++;
            }
            else if (jF.getRecipe() != null){
                row.createCell(7).setCellValue(jF.getRecipe().getTitle());
                for (Composition composition : jF.getRecipe().getComposition()) {
                    row = sheet.getRow(rowNumber);
                    row.createCell(8).setCellValue(composition.getProduct().getTitle());
                    row.createCell(9).setCellValue(composition.getWeight());
                    row.createCell(10).setCellValue(composition.getProduct().getCalories());
                    row.createCell(11).setCellValue(composition.getProduct().getFats());
                    row.createCell(12).setCellValue(composition.getProduct().getCarbohydrates());
                    row.createCell(13).setCellValue(composition.getProduct().getProteins());
                    rowNumber++;
                }
            }
        }
        book.write(outputStream);

        return outputStream.toByteArray();
    }

}
