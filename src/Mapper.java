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
public interface Mapper {

    /**
     * Put in a ICGC Sample ID and get back the raw data's file name
     * mentioned in the mapping file from the EGA repository
     * @param icgcSampleId the ICGC sample id
     * @return The raw data's file name
     */
    public String getRawDataFile(String icgcSampleId);

}
