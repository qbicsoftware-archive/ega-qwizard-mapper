import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

/**
 * ega-qwizard-mapper
 *
 * Description:
 *
 *
 * @author fillinger
 * @version Date: 12/4/15
 *          EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class Main {

    public static void main(String[] args) throws IOException{

        if(args.length != 4) {
            System.err.println("Missing arguments.");
            System.exit(1);
        }
        // Sample_metainfo.map
        String sampleMetaInfo = args[0];

        // Study_Sample_Analysis_file.map
        String sampleAnalysisFile = args[1];

        // The qWizard file
        String qWizardFile = args[2];

        // The path to the directory with the rawData
        String rawDataDirectory = args[3];

        Mapper egaMapper = MapperFactory.getMapper(Files.lines(Paths.get(args[0])),
                Files.lines(Paths.get(args[1])));

        BarcodeExtractor barcodeExtractor = new BarcodeExtractor();

        Map<String, String> analyzedIdMap = barcodeExtractor.make(args[2]).getanalyzedIdMap();

        for (String analyzedID : analyzedIdMap.keySet()){

            String rawDataName = egaMapper.getRawDataFile(analyzedID);

            Path filePath = Paths.get(rawDataDirectory + File.separator + rawDataName);

            Path newFilePath = Paths.get(rawDataDirectory + File.separator +
                    analyzedIdMap.get(analyzedID) + "_" + rawDataName);

            try {
                Files.move(filePath, newFilePath, StandardCopyOption.ATOMIC_MOVE);
                System.out.println("Renaming file " + filePath.getFileName());
            } catch (NoSuchFileException e){
                System.err.println(filePath + " not found.");
            }
        }

    }
}
