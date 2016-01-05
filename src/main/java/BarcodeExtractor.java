import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ega-qwizard-mapper
 * Description:
 *
 * @author fillinger
 * @version ${VERSION}
 *          Date: 12/11/15
 *          EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class BarcodeExtractor {

    /**
     * The search string, filteres lines of type Q_NGS_SINGLE_SAMPLE_RUN
     */
    private final String SAMPLE_TYPE = "Q_NGS_SINGLE_SAMPLE_RUN";

    /**
     * The qWizard file name
     */
    private String qWizardFileName;

    /**
     * A Map containing analyzedId:barcode after creation
     */
    private Map<String, String> analyzedIdMap;

    /**
     * Nullary constructor
     */
    public BarcodeExtractor(){}

    /**
     * Make the BarcodeExtractor, will parse the qWizardFile
     * @param fileName the qWizard file path
     * @return this
     */
    public BarcodeExtractor make(String fileName){
        this.qWizardFileName = fileName;
        extractBarcodes();
        return this;
    }

    /**
     * Request the calculated analyzedId:barcode map
     * @return Map String analyzedId : String barcode
     */
    public Map<String, String> getanalyzedIdMap() {
        if (this.analyzedIdMap == null){
            this.analyzedIdMap = new HashMap<>();
        }
        return this.analyzedIdMap;
    }

    /**
     * Extracts the barcodes and analyzedIds from lines of type
     * 'SAMPLE_TYPE'
     */
    private void extractBarcodes(){
        this.analyzedIdMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.qWizardFileName))){

            String line;
            while ((line = bufferedReader.readLine()) != null){
                if (line.contains(SAMPLE_TYPE)){
                    String[] lineContent = line.split("\t");
                    String barcode = lineContent[0];
                    String analyzedId = lineContent[4];
                    this.analyzedIdMap.put(analyzedId, barcode);
                }
            }
            bufferedReader.close();
        } catch (IOException e){
            System.err.println("Could not read from file: " + this.qWizardFileName);
        }

    }

}
