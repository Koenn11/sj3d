package sj3d;

/**
 * A <code>Camera</code> has a position and looks in a direction.
 *
 * @see #getMatrix()
 */
public final class Camera extends Object3D {

    private Vector lookAtVector, forward;

    public Camera() {

        posX = posY = 0;
        posZ = -2;
        lookAtVector = new Vector(0, 0, 0);
        rebuildFlag = true;

    }

    public Camera(float x, float y, float z, float lookX, float lookY,
            float lookZ) {

        posX = x;
        posY = y;
        posZ = z;
        lookAtVector = new Vector(lookX, lookY, lookZ);
        rebuildFlag = true;

    }

    public void lookAt(float x, float y, float z) {
        lookAtVector = new Vector(x, y, z);
        rebuildFlag = true;
    }

    public Vector getLookVector() {
        return lookAtVector.clone();
    }

    public Vector getForwardVector() {
        getMatrix();
        return forward.clone();
    }

    public Matrix getMatrix() {

        if (!rebuildFlag)
            return transform;

        forward = lookAtVector.subtract(new Vector(posX, posY, posZ));
        Vector up = new Vector(0, 1, 0);
        Vector right = up.cross(forward);
        up = right.cross(forward);

        forward.normalize();
        up.normalize();
        right.normalize();

        transform = new Matrix(right, up, forward);
        transform.data[0][3] = posX;
        transform.data[1][3] = posY;
        transform.data[2][3] = posZ;

        transform.invert();
        rebuildFlag = false;

        return transform;

    }

}
