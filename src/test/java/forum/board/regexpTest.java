package forum.board;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexpTest {

    @Test
    void phoneRegexpTest(){

        String phoneNum = "010-4047-7823";
        String wrongPhoneNum1 = "011-4047-7823";
        String wrongPhoneNum2 = "0114047-7823";
        String regexp = "^01(?:0)-(?:\\d{3}|\\d{4})-(\\d{4})$";

        Pattern com1 = Pattern.compile(regexp);
        Matcher matcher1 = com1.matcher(phoneNum);

        Pattern com2 = Pattern.compile(regexp);
        Matcher matcher2 = com2.matcher(wrongPhoneNum1);

        Pattern com3 = Pattern.compile(regexp);
        Matcher matcher3 = com2.matcher(wrongPhoneNum2);


        if(matcher3.matches()) {
            System.out.println("매치 성공");
        }else{
            System.out.println("매치 실패");
        }


    }
}
