package test;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
<applet code="EventTest2.class" codebase="class" width="300" height="100">
</applet>
*/

public class XYmouseTEST extends Applet implements MouseListener{
  String msg;

  public void init(){
    msg = "";
    addMouseListener(this);
  }

  public void paint(Graphics g){
    g.drawString(msg, 10, 60);
  }

  public void mouseEntered(MouseEvent e){
  }

  public void mouseExited(MouseEvent e){
  }

  public void mousePressed(MouseEvent e){
  }

  public void mouseReleased(MouseEvent e){
  }

  public void mouseClicked(MouseEvent e){
    StringBuilder sb = new StringBuilder();

    Point point = e.getPoint();
    sb.append("座標:(");
    sb.append(point.x);
    sb.append(",");
    sb.append(point.y);
    sb.append("の位置で");

    int btn = e.getButton();

    if (btn == MouseEvent.BUTTON1){
      sb.append("左ボタンが");
    }else if (btn == MouseEvent.BUTTON2){
      sb.append("中ボタンが");
    }else if (btn == MouseEvent.BUTTON3){
      sb.append("右ボタンが");
    }

    sb.append(e.getClickCount());
    sb.append("回クリック");

    msg = new String(sb);

    repaint();
  }
}