
package bulletstrategy;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Lighting {
    private FloatBuffer specularLight, diffuseLight, ambientLight, lightPosition;
    private float shininess;
    public boolean shouldSetLightPosAgain = true;

    public Lighting() {
        this.specularLight = BufferUtils.createFloatBuffer(4);
        this.specularLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        this.diffuseLight = BufferUtils.createFloatBuffer(4);
        diffuseLight.put(1.5f).put(1.5f).put(1.5f).put(1.0f).flip();

        this.ambientLight = BufferUtils.createFloatBuffer(4);
        ambientLight.put(0.2f).put(0.2f).put(0.2f).put(1.0f).flip();

        this.lightPosition = BufferUtils.createFloatBuffer(4);
        this.lightPosition.put(0f).put(0f).put(0f).put(1.0f).flip();

        this.shininess = 100f;

        setupLighting();
    }

    public void setupLighting() {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        // sets specular material color
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, this.specularLight);
        // sets shininess
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, this.shininess);

        // sets specular light to white
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, this.specularLight);

        // sets diffuse light
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, this.diffuseLight);

        // global ambient light
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, this.ambientLight);

        //set up light pos
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);

        // enables opengl to use glColor3f to define material color
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        // tell opengl glColor3f effects the ambient and diffuse properties of material
        GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
    }

    public void setPosition(float x, float y, float z, float w) {
        this.lightPosition = BufferUtils.createFloatBuffer(4);
        this.lightPosition.put(x).put(y).put(z).put(w).flip();
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
    }

    public void setPositionAgain() {
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
    }

    public void setDiffuseLight(float r, float g, float b, float a) {
        this.diffuseLight = BufferUtils.createFloatBuffer(4);
        this.diffuseLight.put(r).put(g).put(b).put(a).flip();
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, diffuseLight);
    }

    public void setAmbientLight(float r, float g, float b, float a) {
        this.ambientLight = BufferUtils.createFloatBuffer(4);
        this.ambientLight.put(r).put(g).put(b).put(a).flip();
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ambientLight);
    }

    public void setSpecularLight(float r, float g, float b, float a) {
        this.specularLight = BufferUtils.createFloatBuffer(4);
        this.specularLight.put(r).put(g).put(b).put(a).flip();
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, specularLight);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, specularLight);
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, shininess);
    }

    public FloatBuffer getSpecularLight() {
        return specularLight;
    }

    public FloatBuffer getDiffuseLight() {
        return diffuseLight;
    }

    public FloatBuffer getAmbientLight() {
        return ambientLight;
    }

    public FloatBuffer getLightPosition() {
        return lightPosition;
    }

    public float getShininess() {
        return shininess;
    }
}
