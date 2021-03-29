#ifndef MISC_H
#define MISC_H

#include <stdio.h>
#include <string>
#include "myLogger.h"
#include "myGLM.h"

std::string GetFileName(std::string fileName);

std::string GetDirectoryName(std::string fullFileName);

void PrintGLMMat4(glm::mat4 testMat);

#endif //MISC_H
