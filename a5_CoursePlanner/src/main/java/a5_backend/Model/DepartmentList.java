package a5_backend.Model;

import a5_backend.Model.CSVHelperClasses.CSVPrinter;
import a5_backend.Model.CSVHelperClasses.CSVReader;

public interface DepartmentList {

    void readCSVFile(String CSVFile, CSVReader reader);

    void printCSVFile();



}
