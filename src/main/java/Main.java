import com.vvvtimes.server.MainServer;

/**
 * Created by shicz on 2017/12/12.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Integer port = null;
        try {
            if (args.length>0){
                port = Integer.parseInt(args[0]);
            }
        }catch (Exception e){

        }
        if (port == null){
            port = 8888;
        }
        MainServer.run(port);

    }
}
