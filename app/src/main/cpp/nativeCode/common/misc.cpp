#include "misc.h"

/**
 * Strip out the path and return just the filename
 */
std::string GetFileName(std::string fileName) {

    // assume filename is of the form "<path>/<name>.<type>"
    std::string::size_type slashIndex = fileName.find_last_of("/");

    std::string onlyName;
    if (slashIndex == std::string::npos) {
        onlyName = fileName.substr(0, std::string::npos);
    } else {
        onlyName = fileName.substr(slashIndex + 1, std::string::npos);
    }

    return onlyName;
}

/**
 * Extract only the directory part from the file name
 */
std::string GetDirectoryName(std::string fullFileName) {

    std::string::size_type slashIndex = fullFileName.find_last_of("/");
    std::string directoryName;
    if (slashIndex == std::string::npos) {
        directoryName = ".";
    } else if (slashIndex == 0) {
        directoryName = "/";
    } else {
        directoryName = fullFileName.substr(0, slashIndex);
    }
    return directoryName;
}

/**
 * Print the contents of a Glm 4x4 matrix
 */
void PrintGLMMat4(glm::mat4 testMat) {

    MyLOGD("%f %f %f %f", testMat[0][0],testMat[1][0],testMat[2][0],testMat[3][0]);
    MyLOGD("%f %f %f %f", testMat[0][1],testMat[1][1],testMat[2][1],testMat[3][1]);
    MyLOGD("%f %f %f %f", testMat[0][2],testMat[1][2],testMat[2][2],testMat[3][2]);
    MyLOGD("%f %f %f %f", testMat[0][3],testMat[1][3],testMat[2][3],testMat[3][3]);

}