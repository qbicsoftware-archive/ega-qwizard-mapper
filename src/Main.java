
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
public class Main {

    public static void main(String[] args) throws IOException{

        if(args.length != 2) {
            System.err.println("Missing arguments.");
            System.exit(1);
        }

        Mapper egaMapper = MapperFactory.getMapper(Files.lines(Paths.get(args[0])),
                Files.lines(Paths.get(args[1])));

        // Example query
        System.out.println(egaMapper.getRawDataFile("AOCS-058-5-X"));
    }
}
