import java.io.File;
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
public class MapperFactory {

    /**
     * Creates a Mapper that can be used for retrieving the raw data file names
     * by applying the ICGC sample id.
     * @param sampleMetaInfo -> Sample_metainfo.map
     * @param sampleAnalysisFile -> Study_Sample_Analysis_file.map
     * @return
     */
    public static Mapper getMapper(Stream sampleMetaInfo, Stream sampleAnalysisFile){
        return new EgaMapper(sampleMetaInfo, sampleAnalysisFile);
    }

}
