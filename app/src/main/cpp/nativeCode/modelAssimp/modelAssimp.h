#ifndef MODELASSIMP_H
#define MODELASSIMP_H

#include "../common/myLogger.h"
#include "../common/myGLFunctions.h"
#include "../common/myGLCamera.h"
#include "../common/assimpLoader.h"
#include <sstream>
#include <iostream>
#include <stdio.h>
#include <string>

class ModelAssimp {
public:
    ModelAssimp();
    ~ModelAssimp();
    void    PerformGLInits();
    void    Render();
    void    SetViewport(int width, int height);
    void    DoubleTapAction();
    void    ScrollAction(float distanceX, float distanceY, float positionX, float positionY);
    void    ScaleAction(float scaleFactor);
    void    MoveAction(float distanceX, float distanceY);
    int     GetScreenWidth() const { return screenWidth; }
    int     GetScreenHeight() const { return screenHeight; }

private:
    bool    initsDone;
    int     screenWidth, screenHeight;

    std::vector<float> modelDefaultPosition;
    MyGLCamera * myGLCamera;
    AssimpLoader * modelObject;
};

#endif //MODELASSIMP_H
