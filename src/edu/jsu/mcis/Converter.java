package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            //Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            for(int i = 0; i < full.size(); ++i){
                String[] line = full.get(i);
                for(int j = 0; j < line.length; ++j){
                    System.out.print(line[j]+ " ");
                }
                
                System.out.println("\n");
            }
            
            JSONObject json = new JSONObject();
            JSONArray rowHeader = new JSONArray();
            JSONArray colHeader = new JSONArray();
            JSONArray data = new JSONArray();
            
            JSONArray row;
            
            //System.out.println("Parsing colHeader"); --test
            
            for(int i = 0; i < full.get(0).length; ++i){ //add colHeader to JSONArray
                colHeader.add(full.get(0)[i]);
            }
            //System.out.println("Parsing rows"); --test
            for(int i = 1; i < full.size(); ++i){
                rowHeader.add(full.get(i)[0]);
                row = new JSONArray();
                for(int j = 1; j <full.get(i).length; ++j){
                   
                    row.add(Integer.parseInt(full.get(i)[j]));
                    
                }
                data.add(row);
            }
            
            json.put("colHeaders", colHeader);
            json.put("rowHeaders", rowHeader);
            json.put("data", data);
            
            results = JSONValue.toJSONString(json);
            
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            JSONArray jsonColHeader = (JSONArray)jsonObject.get("colHeaders");
            String[] colHeaders = new String[jsonColHeader.size()];
            for (int i = 0; i < colHeaders.length;++i ){
                colHeaders [i] = jsonColHeader.get(i).toString();
            } 
            csvWriter.writeNext(colHeaders);
            
            JSONArray jsonRowHeader = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray jsonData = (JSONArray)jsonObject.get("data");
            
            String[] row;
            for (int i = 0; i < jsonRowHeader.size() ; ++i){
                JSONArray dataString = (JSONArray)parser.parse(jsonData.get(i).toString());
                row = new String[dataString.size() + 1];
                row [0] = jsonRowHeader.get(i).toString();
                for (int j = 0; j <dataString.size(); ++j){
                    row[j + 1] = dataString.get(j).toString();
                }
                csvWriter.writeNext(row);
                
            }
            
            String csvString = writer.toString();
            results = csvString;
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}