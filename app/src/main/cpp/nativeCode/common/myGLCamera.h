#ifndef GLCAMERA_H
#define GLCAMERA_H

#include <vector>
#include "misc.h"

// sensitivity coefficients for translating gestures to model's movements
#define SCALE_TO_Z_TRANSLATION  20
#define TRANSLATION_TO_ANGLE    5
#define XY_TRANSLATION_FACTOR   10

class MyGLCamera {
public:
    MyGLCamera(
            float FOV = 45,          // in degrees
            float zPosition = 10,    // camera's location on Z axis
            float nearPlaneDistance = 1.0f, // as large as possible
            float farPlaneDistance = 2000.0f // as small as possible
    );
    void        SetModelPosition(std::vector<float> modelPosition);
    void        SetAspectRatio(float aspect);
    glm::mat4   GetMVP(){ return mvpMat; }
    void        RotateModel(float distanceX, float distanceY, float endPositionX, float endPositionY);
    void        ScaleModel(float scaleFactor);
    void        TranslateModel(float distanceX, float distanceY);

private:
    void        ComputeMVPMatrix();

    float       FOV;
    float       nearPlaneDistance, farPlaneDistance;

    glm::mat4   projectionViewMat;
    glm::mat4   rotateMat, translateMat;
    glm::mat4   modelMat;
    glm::mat4   viewMat;
    glm::mat4   mvpMat;     // ModelViewProjection: obtained by multiplying Projection, View, & Model

    // six degrees-of-freedom of the model contained in a quaternion and x-y-z coordinates
    glm::quat   modelQuaternion;
    float       deltaX, deltaY, deltaZ;
};

#endif //GLCAMERA_H
