
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * ega-qwizard-mapper
 * <p>
 * Description:
 * <- content ->
 *
 * @author fillinger
 * @version Date: 12/4/15
 *          EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class EgaMapper implements Mapper {

    /**
     * The File containing the meta data information
     * for the given EGA repository.
     * Contains the EGAN.... id, and the ICGC Sample ID
     * which will be the important link between the metadata
     * and raw data.
     */
    private Stream<String> sampleMetaInfo;

    /**
     * The File containing the EGAN.... id and the corresponding
     * physical raw data file name of the raw data.
     */
    private Stream<String> sampleAnalysisFile;

    /**
     * HashMap that will map ICGC Sample ID -> EGAN-id
     */
    private HashMap<String, String> metaInfoMap = new HashMap<>();

    /**
     * HashMap that will map EGAN-id -> raw data file name
     */
    private HashMap<String, String> analyisFileMap = new HashMap<>();

    /**
     * EgaMapper constructor, must have two files, containing
     * the information from 'Sample_metainfo.map'
     * and 'Study_Sample_Analysis_file.map'
     * @param sampleMetaInfo -> Sample_metainfo.map
     * @param sampleAnalysisFile -> Study_Sample_Analysis_file.map
     */
    protected EgaMapper(Stream sampleMetaInfo, Stream sampleAnalysisFile){
        this.sampleMetaInfo = sampleMetaInfo;
        this.sampleAnalysisFile = sampleAnalysisFile;
        createAnalysisFileMap();
        createMetaInfoMap();
    }


    private void createAnalysisFileMap(){
        this.sampleAnalysisFile.parallel().forEach(this::parseAnalyzedInfo);
    }


    private void createMetaInfoMap(){
        this.sampleMetaInfo.parallel().forEach(this::parseMetaInfo);
    }


    @Override
    public String getRawDataFile(String icgcSampleId) {
        return this.analyisFileMap.get(this.metaInfoMap.get(icgcSampleId));
    }

    /**
     * Extracts the EGAN...id and the ICGC analyzed ID
     * and puts it into the corresponding hash map
     * @param lineMetaInfo the line that is to parse
     */
    private void parseAnalyzedInfo(String lineMetaInfo){
        String[] lineContent = lineMetaInfo.split("\t");
        String egaSampleID = lineContent[2];
        String rawFileName = lineContent[6];
        this.analyisFileMap.put(egaSampleID, rawFileName);
    }

    /**
     * Extracts the EGAN...id and the raw data file name
     * and puts it into the corresponding hash map
     * @param lineAnalyzedInfo the line that is to parse
     */
    private void parseMetaInfo (String lineAnalyzedInfo){
        String[] lineContent = lineAnalyzedInfo.split("\t");
        String sampleID = lineContent[4].split(";")[0];
        String icgcAnalyzedId = sampleID.substring(sampleID.indexOf("=")+1);
        this.metaInfoMap.put(icgcAnalyzedId, lineContent[0]);
    }
}
