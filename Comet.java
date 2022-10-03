package com.company;

import java.awt.*;

import java.util.Timer;

import java.util.TimerTask;

public class Comet {

    private int x, y; //координаты кометы

    private final int bigR, maxScreenX, maxScreenY, minR, maxR; //радиус большой орбиты, размер монитора по X и Y, минимальный радиус кометы, максимальный

    private double mainR, greatOrbitAngle, smallCircleAngle; //радиус кометы, угол для большой орбиты, для маленькой (внутри самой кометы есть круг - он очень маленький)

    private final double minStartTime, maxStartTime; //минимальное время полёта от большой орбиты к центру монитора по оси X, максимальное

    public Comet(int minR, int maxR, int bigR, int maxScreenX, int maxScreenY, double minStartTime, double maxStartTime) { //конструктор

        this.minR = minR;

        this.maxR = maxR;

        this.bigR = bigR;

        this.maxScreenX = maxScreenX;

        this.maxScreenY = maxScreenY;

        this.minStartTime = minStartTime;

        this.maxStartTime = maxStartTime;

        restart(); //запускаем один раз, чтобы у нас всё время менялись координаты (для движения)

    }

    public void draw(Graphics2D g) { //метод для отрисовки

        final int nPoints = 3; //для хвоста кометы

        int[] xPoints = new int[nPoints];

        int[] yPoints = new int[nPoints];

        int R = (int) (mainR * 10); //длина хвоста

        int k1, k2, k3, k4;

        if (greatOrbitAngle >= 0 && greatOrbitAngle <= 90) { //для разных четвертей на окружности будут немного разные формулы (координаты) для отрисовки хвоста. Будут меняться только знаки

            k1 = 1;

            k2 = -1;

            k3 = -1;

            k4 = 1;

        }

        else if (greatOrbitAngle > 90 && greatOrbitAngle <= 180) {

            k1 = -1;

            k2 = -1;

            k3 = 1;

            k4 = -1;

        }

        else if (greatOrbitAngle > 180 && greatOrbitAngle <= 270) {

            k1 = -1;

            k2 = 1;

            k3 = -1;

            k4 = 1;

        }

        else {

            k1 = 1;

            k2 = 1;

            k3 = 1;

            k4 = -1;

        }

        for (int i = 0; i < 3; i++) { //рисуем хвост, состоящий из трёх цветов

            if (i == 0) {

                g.setColor(new Color(246, 124, 1));

            }

            if (i == 1) {

                g.setColor(new Color(255, 201, 41));

            }

            if (i == 2) {

                g.setColor(new Color(249, 240, 121));

            }

            R = R * 2 / 3; //каждый раз уменьшаем длину хвоста для нового цвета (чтобы было похоже на пламя (да, мы открыли кислород в космосе!))

            xPoints[0] = (int) (x + mainR - mainR * Math.abs(Math.cos(Math.toRadians(greatOrbitAngle + 90)))); //сложные вычисления

            xPoints[1] = (int) (x + mainR + mainR * Math.abs(Math.cos(Math.toRadians(greatOrbitAngle - 90))));

            xPoints[2] = (int) (x + mainR + k1 * R * Math.abs(Math.cos(Math.toRadians(greatOrbitAngle))));

            yPoints[0] = (int) (y + mainR + k3 * mainR * Math.abs(Math.sin(Math.toRadians(greatOrbitAngle + 90))));

            yPoints[1] = (int) (y + mainR + k4 * mainR * Math.abs(Math.sin(Math.toRadians(greatOrbitAngle - 90))));

            yPoints[2] = (int) (y + mainR + k2 * R * Math.abs(Math.sin(Math.toRadians(greatOrbitAngle))));

            g.fillPolygon(xPoints, yPoints, nPoints); //заполняем наш полигон (треугольник)

        }

        g.setColor(new Color(105, 44, 8));

        g.fillOval(x,y, (int)(mainR * 2), (int)(mainR * 2)); //рисуем основной круг

        g.setColor(new Color(87, 30, 0));

        g.fillOval((int) (x + 2 * mainR / 3 + mainR / 2*(Math.cos(Math.toRadians(smallCircleAngle)))), (int) (y + 2 * mainR / 3 + mainR / 2 * (Math.sin(Math.toRadians(smallCircleAngle)))), (int)(2* mainR /3), (int)(2* mainR /3));

        //рисуем маленький круг внутри большого (см. выше)

        g.setColor(Color.WHITE);

        g.drawOval(x,y, (int)(mainR * 2), (int)(mainR * 2)); //рисуем обводку вокруг большого круга (с ней всё-таки лучше смотрится)

    }

    public void restart() { //метод запускает комету заново по траектории раз в n (рандомное число в заданном диапазоне) секунд

        TimerTask setCords = new TimerTask() {

            public void run() { //то, что будем выполнять раз в n секунд

                move();

            }
        };

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(setCords, (int)((Math.random() * ((maxStartTime * maxStartTime - minStartTime) + 1)) + minStartTime) * 1000, (int)(maxStartTime * 1000 * 4));

        //задаём начальное время и интервал

    }

    public void move() { //метод обновления координат для того, чтобы кометы двигалась по заданной траектории

        int finalX, finalY;

        final int FPS = 60; //частота кадров (для корректной работы движения)

        greatOrbitAngle = (int) (Math.random() * (360 + 1)); //задаём рандомом угол для радиуса большой орбиты

        smallCircleAngle = (int) (Math.random() * (360 + 1)); //для маленькой (тот самый маленький круг)

        mainR = (int)((Math.random() * ((maxR - minR) + 1)) + minR); //задаём радиус кометы

        x = (int)(maxScreenX / 2 - mainR + (Math.cos(Math.toRadians(greatOrbitAngle)) * bigR)); //её координаты

        y = (int)(maxScreenY / 2 - mainR - (Math.sin(Math.toRadians(greatOrbitAngle)) * bigR));

        finalX = (int)(maxScreenX / 2 - mainR); //те координаты, куда нужно лететь комете

        finalY = (int)((Math.random() * (((maxScreenY - mainR * 2)) + 1)));

        final boolean[] isFirstHalf = {true}; //логическая переменная (либо первая половина пути, либо вторая)

        final int startTime = (int)((Math.random() * ((maxStartTime - minStartTime) + 1)) + minStartTime); //время полёта от большой орбиты к центру монитора по оси X

        final double[] step = {startTime}; //временная переменная

        final int[] xStep = {0}; //шаг по X

        final int[] yStep = {0}; //шаг по Y

        final int[] optimalStepByX = {0}; //шаг по Х для воторой части пути

        final int[] optimalStepByY = {0}; //шаг по Y для воторой части пути

        Timer timer = new Timer();

        TimerTask changeCords = new TimerTask() {

            public void run() {

                if (isFirstHalf[0]) { //алгоритм для мервой части пути

                    xStep[0] = (int) Math.round(((double) finalX - x) / FPS / step[0]);

                    yStep[0] = (int) Math.round(((double) finalY - y) / FPS / step[0]);

                    x = x + xStep[0];

                    y = y + yStep[0];

                    if (xStep[0] != 0) {

                        optimalStepByX[0] = xStep[0];

                    }

                    if (yStep[0] != 0) {

                        optimalStepByY[0] = yStep[0];

                    }

                    step[0] -= 1.0 / FPS;

                    if (step[0] < 0) {

                        step[0] = startTime;

                        isFirstHalf[0] = false;

                    }
                }

                else { //для второй

                    x = x + optimalStepByX[0];

                    y = y + optimalStepByY[0];

                    step[0] -= 1.0 / FPS;

                    if (step[0] < - startTime * 2) {

                        timer.cancel();

                    }
                }
            }
        };

        long delay = 1000 / FPS;

        timer.scheduleAtFixedRate(changeCords, 0, delay);

    }
}
