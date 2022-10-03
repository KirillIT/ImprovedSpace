package com.company;

import javax.swing.*;

import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() throws HeadlessException { //конструктор

        setExtendedState(JFrame.MAXIMIZED_BOTH); //растягиваем окно по размеру монитора

        setResizable(false); //размер окна нельзя изменять

        setTitle("Космос"); //название окна

        //setUndecorated(true); //делает экран без рамок на весь монитор

        DrawPanel dp = new DrawPanel(); //создаём панель для рисования и вызываем конструктор у неё

        this.add(dp); //добавляем панель в наше окно

    }
}