package model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionSheet {

    private String filePath;
    private List<Integer> accessedIndices;

    public QuestionSheet() {
        URL resource = getClass().getResource("/questions/questions-list.xlsx");
        filePath = new File(resource.getFile()).getAbsolutePath();
        this.filePath = filePath;
        this.accessedIndices = new ArrayList<>();
    }

    public void getRandomQuestion() {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the sheet index is 0

            int startRow = 1; // Row 1
            int endRow = 121; // Row 121

            int randomRowIndex = getRandomIndex(startRow, endRow);

            Row row = sheet.getRow(randomRowIndex);
            String question = getCellValue(row, 2); // Column C (index 2)
            String choiceA = getCellValue(row, 3); // Column D (index 3)
            String choiceB = getCellValue(row, 4); // Column E (index 4)
            String choiceC = getCellValue(row, 5); // Column F (index 5)
            String choiceD = getCellValue(row, 6); // Column G (index 6)
            String correctChoice = getCellValue(row, 7); // Column H (index 7)

            // Store the values to attributes or use them as needed
            String questionAttribute = question;
            String A = choiceA;
            String B = choiceB;
            String C = choiceC;
            String D = choiceD;
            String correct = correctChoice;

            System.out.println("Question: " + question);
            System.out.println("A: " + choiceA);
            System.out.println("B: " + choiceB);
            System.out.println("C: " + choiceC);
            System.out.println("D: " + choiceD);
            System.out.println("Correct choice: " + correctChoice);

            // Add the accessed index to the list
            accessedIndices.add(randomRowIndex);

            workbook.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private int getRandomIndex(int start, int end) {
        int randomIndex;
        Random random = new Random();
        do {
            randomIndex = random.nextInt(end - start + 1) + start;
        } while (accessedIndices.contains(randomIndex)); // Generate a new index if it has already been accessed
        return randomIndex;
    }

    private String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                case 1: // STRING
                    return cell.getStringCellValue();
                case 0: //NUMERIC
                    return String.valueOf(cell.getNumericCellValue());
                case 4: // BOOLEAN
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "";
            }
        }
        return "";
    }

    public static void main(String[] args) {
        QuestionSheet qs = new QuestionSheet();
        qs.getRandomQuestion();
        qs.getRandomQuestion();
        qs.getRandomQuestion();
    }

}