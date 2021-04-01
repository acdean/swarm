package me.acdean.swarm;

import peasy.PeasyCam;
// these are only used for additive blending
import com.jogamp.opengl.GL;
import processing.core.PApplet;
import processing.opengl.PJOGL;

/*
** Main replaces the processing sketch file.
*/

public class Main extends PApplet {

    public PeasyCam cam;
    Baubles baubles = new Baubles(this);
    EList eList = new EList(this);
    SList sList = new SList(this);

    public static void main(String[] args) {
        PApplet.main("me.acdean.swarm.Main");
    }

    @Override
    public void settings() {
        //fullScreen(P3D);
        size(1600, 800, P3D);
    }

    @Override
    public void setup() {

        // set up camera
        cam = new PeasyCam(this, 300);

        // just a grid of baubles
        //baubles.init();
        eList.init();
        sList.init();
    }

    @Override
    public void draw() {
        background(0);
        
        stroke(255, 0, 0);
        line(-1000, 0, 0, 1000, 0, 0);
        stroke(0, 255, 0);
        line(0, -1000, 0, 0, 1000, 0);
        stroke(0, 0, 255);
        line(0, 0, -1000, 0, 0, 1000);

        // turn on additive blending, always fun
       // additiveBlending();

        // draw all the baubles
        eList.draw();
        sList.draw();
    }

    void additiveBlending() {
        GL gl = ((PJOGL)beginPGL()).gl.getGL();
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
        gl.glDisable(GL.GL_DEPTH_TEST);
    }

    @Override
    public void keyPressed() {
        System.out.println("Saving frame");
        saveFrame("bauble_####.png");
    }
}
