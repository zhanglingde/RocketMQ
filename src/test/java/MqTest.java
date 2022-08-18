import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.ling.RocketMQApplication;
import com.ling.roecketmq.MqUtils;
import com.ling.roecketmq.User;
import com.ling.roecketmq.config.MqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Random;

/**
 * @author zhangling 2021/6/2 14:32
 */
@SpringBootTest(classes = RocketMQApplication.class)
public class MqTest {

    @Test
    public void normalTest(){
        boolean normal = MqUtils.normal("normal"+ RandomUtil.randomNumbers(4), "hello world" , "tagC");
        System.out.println("normal = " + normal);
    }


    public static void main(String[] args) {
        // for (int i = 0; i < 5; i++) {
        //     boolean normal = MqUtils.normal("normal" + i, "hello world" + i, "tagC");
        //     System.out.println("normal = " + normal);
        // }
        boolean normal = MqUtils.normal("normal", "hello world" , "tagC");
        System.out.println("normal = " + normal);
        // boolean delay = MqUtils.delay("delay2", "hello world", 1000L, "delay_tag");
        // System.out.println("delay = " + delay);
    }


    // public static void main(String[] args) {
    //     BigDecimal d1 = new BigDecimal("123.456789");
    //     BigDecimal d2 = d1.setScale(4, RoundingMode.HALF_UP); // 四舍五入，123.4568
    //     BigDecimal d3 = d1.setScale(4, RoundingMode.DOWN);   // 直接截断，123.4567
    //     BigDecimal d4 = d1.setScale(4, RoundingMode.HALF_DOWN);
    //     BigDecimal d5 = d1.setScale(4, RoundingMode.UP);
    //
    //     System.out.println("d2 = " + d2);
    //     System.out.println("d3 = " + d3);
    //     System.out.println("d4 = " + d4);
    //     System.out.println("d5 = " + d5);
    // }

    /**
     * 延时消息
     */
    @Test
    public void test02() {
        User user = new User();
        user.setId(1);
        user.setName("zhangling");

        boolean normal = MqUtils.delay(MqConfig.rocketMQ_TEST_KEY + "test", JSONUtil.toJsonStr(user), 10000L, MqConfig.rocketMQ_TEST);

        System.out.println("normal = " + normal);

    }


    public void test002() throws Exception {


        URL url = new URL("https://static.xpandago.com/upload/goods/20200320/b0d57173-55d6-47f5-a1bd-c99380c3d6f8.png");
        BufferedImage image2 = ImageIO.read(url);
        BufferedImage image = new BufferedImage(300, 800, BufferedImage.TYPE_INT_RGB);
        String name = "zhang";
        String orderNum = "12345689";
        Graphics g2 = image.getGraphics();
        // Graphics2D g2 = (Graphics2D) image.getGraphics();

        g2.setColor(Color.WHITE);
        // g2.setBackground(Color.WHITE);
        g2.fillRect(0, 0, 150, 800);

        g2.setColor(Color.BLACK);
        g2.drawString("下单人：" + name, 100, 20);
        g2.drawString("订单号：" + orderNum, 100, 40);
        // 图片
        g2.drawImage(image2, 100, 100, 100, 100, null);

        // g2.setFont(new Font("黑体", Font.BOLD, 15));
        // g2.setColor(Color.WHITE);
        // g2.drawString("1230", 3, 50);


        ImageIO.write(image, "JPEG", new FileOutputStream("D:/7.jpg"));


    }
}
