package forum.board;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForumApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mathTest()
    {
        double btnNum = 1;
        double btnCnt = 5;
        double ceil = btnNum / btnCnt;
        double nowBlock = Math.ceil((btnNum/btnCnt) + 1);
        System.out.println(nowBlock);
    }

}
