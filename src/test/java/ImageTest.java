import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * @author zhangling 2021/6/2 14:32
 */
public class ImageTest {
    public static void main(String[] args) throws Exception {


        URL url = new URL("https://static.xpandago.com/upload/goods/20200320/b0d57173-55d6-47f5-a1bd-c99380c3d6f8.png");
        BufferedImage image2 = ImageIO.read(url);
        BufferedImage image = new BufferedImage(300, 800, BufferedImage.TYPE_INT_RGB);
        String name = "zhang";
        String orderNum = "12345689";
        Graphics g2 = image.getGraphics();
        //Graphics2D g2 = (Graphics2D) image.getGraphics();

        g2.setColor(Color.WHITE);
        //g2.setBackground(Color.WHITE);
        g2.fillRect(0, 0, 150, 800);

        g2.setColor(Color.BLACK);
        g2.drawString("下单人："+name,100,20);
        g2.drawString("订单号："+orderNum,100,40);
        // 图片
        g2.drawImage(image2, 100, 100, 100, 100, null);

        //g2.setFont(new Font("黑体", Font.BOLD, 15));
        //g2.setColor(Color.WHITE);
        //g2.drawString("1230", 3, 50);


        ImageIO.write(image, "JPEG", new FileOutputStream("D:/7.jpg"));


    }
}
