package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel  extends JPanel implements KeyListener, ActionListener {
    //声明右侧蛇头和身体图片
    ImageIcon right = new ImageIcon("images/right.png");
    ImageIcon body = new ImageIcon("images/body.png");

    //声明上，下，左册蛇头图片
    ImageIcon top = new ImageIcon("images/top.png");
    ImageIcon bottom = new ImageIcon("images/bottom.png");
    ImageIcon left = new ImageIcon("images/left.png");

    //声明一个初始值，表示蛇的初始长度为3
    int len = 3;
    //声明两个数组分别存放x和y的坐标
    int[] snakeX = new int[1008];//最大值 = 宽度格子 * 高度格子；
    int[] snakeY = new int[1008];//最大值 = 宽度格子 * 高度格子；

    //声明一个枚举类型变量，标识蛇的方向
    Direction direction = Direction.right;

    //声明一个变量，标记游戏是否开始，当值为true时表示开始，false表示未开始
    boolean isStart = false;

    //创建一个定时器对象
    Timer timer = new Timer(100,this);

    //声明两个变量，表示食物的坐标位置
    int foodX;
    int foodY;
    //声明一个随机变量random
    Random random = new Random();
    //声明食物图片
    ImageIcon food = new ImageIcon("images/food.png");

    //声明一个变量，表示游戏是否结束
    boolean isEnd = false;

    public MyPanel() {
        //设定蛇头和身体的初始值
        snakeX[0] = 100;
        snakeY[0] = 100;

        snakeX[1] = 75;
        snakeY[1] = 100;

        snakeX[2] = 50;
        snakeY[2] = 100;

        //获取键盘事件
        this.setFocusable(true);
        //添加监听
        this.addKeyListener(this);

        //启动定时器
        timer.start();

        //生成食物的坐标
        foodX = random.nextInt(20) * 25 + 25;
        foodY = random.nextInt(30) * 25 + 25;
    }

    //重写画组件的方法
    @Override
    protected void paintComponent(Graphics g) {
        //调用父类的一些方法来做一些基本工作
        super.paintComponent(g);
        //设置背景颜色
        this.setBackground(Color.red);
        //在画布上添加黑色游戏区域
        g.fillRect(0,0,700,900);

        //初始化
        //添加右侧蛇头
        //right.paintIcon(this,g,snakeX[0],snakeY[0]);
        //根据枚举变量中的值，进行判断，来决定显是那个方向的蛇头
        switch (direction){
            case top:
                top.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case bottom:
                bottom.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case left:
                left.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
            case right:
                right.paintIcon(this,g,snakeX[0],snakeY[0]);
                break;
        }


        //添加两个身体
        /*body.paintIcon(this,g,snakeX[1],snakeY[1]);
        body.paintIcon(this,g,snakeX[2],snakeY[2]);*/
        //添加身体
        for (int i =1;i < len;i++)
        {
            body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //判断当前标记游戏是否开始的值isStart如果是false就显示提示信息
        if(!isStart && !isEnd){
            //放上开始提示信息,并设置字体颜色和字体
            g.setColor(Color.white);
            g.setFont(new Font("楷体",Font.BOLD,50));
            g.drawString("请按空格键表示游戏开始",50,500);
        }

        //添加食物
        food.paintIcon(this,g,foodX,foodY);

        //判断，游戏是否结束，结束则显示结束画面
        if(isEnd)
        {
            //放上结束提示信息,并设置字体颜色和字体
            g.setColor(Color.white);
            g.setFont(new Font("楷体",Font.BOLD,50));
            g.drawString("恭喜您达到了通关目标",75,300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //判断，当按空格键时数字值应该为32
        if(keyCode == 32){
            //将标记游戏状态的的值
            isStart = !isStart;
            //重新画组件
            repaint();
        }else if(keyCode == KeyEvent.VK_UP)
        {
            direction = Direction.top;
        }else if(keyCode == KeyEvent.VK_DOWN)
        {
            direction = Direction.bottom;
        }else if(keyCode == KeyEvent.VK_LEFT)
        {
            direction = Direction.left;
        }else if(keyCode == KeyEvent.VK_RIGHT)
        {
            direction = Direction.right;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //游戏开始了才进行移动
        if(isStart)
        {
            //移动身体
            for(int i = len - 1;i > 0;i--)
            {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }

            //判断，通过方向值direction，移动蛇头
            switch (direction){
                case top:
                    //移动蛇头
                    snakeY[0] = snakeY[0] - 25;
                    //判断当前蛇头的值，如果小于0，则x的值再从900开始
                    if(snakeY[0] < 0)
                    {
                        snakeY[0] = 900;
                    }
                    break;
                case bottom:
                    //移动蛇头
                    snakeY[0] = snakeY[0] + 25;
                    //判断当前蛇头的值，如果超过900，则x的值再从0开始
                    if(snakeY[0] > 900)
                    {
                        snakeY[0] = 0;
                    }
                    break;
                case left:
                    //移动蛇头
                    snakeX[0] = snakeX[0] - 25;
                    //判断当前蛇头的值，如果小于0，则x的值再从700开始
                    if(snakeX[0] < 0)
                    {
                        snakeX[0] = 700;
                    }
                    break;
                case right:
                    //移动蛇头
                    snakeX[0] = snakeX[0] + 25;
                    //判断当前蛇头的值，如果超过700，则x的值再从0开始
                    if(snakeX[0] > 700)
                    {
                        snakeX[0] = 0;
                    }
                    break;
            }

            //判断，当蛇头x和食物坐标x一致，并且蛇头y和食物坐标y一致，则蛇的长度加一
            if(snakeX[0] == foodX && snakeY[0] == foodY)
            {
                len++;

                //每次吃到食物长度加一以后，判断长度是否达到要求，达到要求即游戏结束
                if(len == 18)
                {
                    isEnd = true;
                }

                //重新生成食物
                foodX = random.nextInt(20) * 25 + 25;
                foodY = random.nextInt(30) * 25 + 25;
            }

            //重新画组件
            if(!isEnd){
                repaint();
            }

            //重新启动定时器
            timer.start();
        }
    }
}
