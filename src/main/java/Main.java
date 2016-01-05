import org.apache.commons.cli.*;

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

        Options options = new Options();
        options.addOption("h", "help", false, "show this help page");
        options.addOption("s1", "metainfo", true, "The EGA file: Sample_metainfo.map");
        options.addOption("s2", "analysis", true, "The EGA file: Study_Sample_Analysis_File.map");
        options.addOption("p", "project", true, "The qwizard project tsv-table");
        options.addOption("r", "raw", true, "The path to the raw data from EGA");

        HelpFormatter helpFormatter = new HelpFormatter();

        CommandLineParser parser = new DefaultParser();

        if (args.length == 0){
            System.err.println("You have to provide the proper number of input files!");
            helpFormatter.printHelp("ega-qwizard-mapper.jar -s1 <FILE> -s2 <FILE> -p <FILE> -r <FILE> [-h]", options);
            System.exit(0);
        }

        try{
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption('h')){
                helpFormatter.printHelp("ega-qwizard-mapper.jar -s1 <FILE> -s2 <FILE> -p <FILE> -r <FILE> [-h]", options);
                System.exit(0);
            }

            if(cmd.hasOption("s1") && cmd.hasOption("s2") && cmd.hasOption("p") && cmd.hasOption("r")){
                // Sample_metainfo.map
                String sampleMetaInfo = cmd.getOptionValue("s1");

                // Study_Sample_Analysis_file.map
                String sampleAnalysisFile = cmd.getOptionValue("s2");

                // The qWizard file
                String qWizardFile = cmd.getOptionValue("p");

                // The path to the directory with the rawData
                String rawDataDirectory = cmd.getOptionValue("r");

                run(sampleMetaInfo, sampleAnalysisFile, qWizardFile, rawDataDirectory);

            } else {
                helpFormatter.printHelp("ega-qwizard-mapper.jar -s1 <FILE> -s2 <FILE> -p <FILE> -r <FILE> [-h]", options);
                System.exit(1);
            }


        } catch (ParseException e){
            System.err.println("Something went wrong reading your command line arguments!\n");
            helpFormatter.printHelp("ega-qwizard-mapper.jar -s1 <FILE> -s2 <FILE> -p <FILE> -r <FILE> [-h]", options);
            System.exit(1);
        }

        if(args.length != 4) {
            System.err.println("Missing arguments.");
            System.exit(1);
        }
    }


    private static void run(String sampleMetaInfo, String sampleAnalysisFile,
                     String qWizardFile, String rawDataDirectory) throws MapperInitException{

        try {
            Mapper egaMapper = MapperFactory.getMapper(Files.lines(Paths.get(sampleMetaInfo)),
                    Files.lines(Paths.get(sampleAnalysisFile)));
            BarcodeExtractor barcodeExtractor = new BarcodeExtractor();

            Map<String, String> analyzedIdMap = barcodeExtractor.make(qWizardFile).getanalyzedIdMap();

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
        } catch (IOException e){
            throw new MapperInitException("Could not retrieve EGA Mapper object", e);
        }


    }
}
