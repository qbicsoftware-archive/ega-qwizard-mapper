/**
 * ega-qwizard-mapper
 * Description:
 *
 * @author fillinger
 * @version ${VERSION}
 *          Date: 1/5/16
 *          EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class MapperInitException extends RuntimeException {

    public MapperInitException(){
        super();
    }

    public MapperInitException(String s){
        super();
    }

    public MapperInitException(String s, Throwable throwable){
        super(s, throwable);
    }

    public MapperInitException(Throwable throwable){
        super(throwable);
    }


}
