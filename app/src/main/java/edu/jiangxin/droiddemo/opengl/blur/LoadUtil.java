package edu.jiangxin.droiddemo.opengl.blur;

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LoadUtil
{
    //求两个向量的叉积
    public static float[] getCrossProduct(float x1,float y1,float z1,float x2,float y2,float z2)
    {
        //求出两个矢量叉积矢量在XYZ轴的分量ABC
        float A=y1*z2-y2*z1;
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;

        return new float[]{A,B,C};
    }

    //向量规格化
    public static float[] vectorNormal(float[] vector)
    {
        //求向量的模
        float module=(float)Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
        return new float[]{vector[0]/module,vector[1]/module,vector[2]/module};
    }

    /**
     * 从obj文件中加载携带顶点信息的物体，并自动计算每个顶点的平均法向量
     * Wavefront OBJ文件格式参考: https://www.cnblogs.com/youthlion/archive/2013/01/21/2870451.html
     */
    public static LoadedObject loadFromFile(Resources resources, String fileName) {
        //原始顶点位置坐标列表
        List<Float> vertexList = new ArrayList<>();

        //原始顶点纹理坐标列表
        List<Float> vertexTextureList = new ArrayList<>();

        //原始顶点法线坐标列表
        List<Float> vertexNormalList = new ArrayList<>();

        //顶点位置坐标列表--按面组织好
        List<Float> finalVertexList = new ArrayList<>();

        //顶点纹理坐标结果列表--按面组织好
        List<Float> finalVertexTextureList = new ArrayList<>();

        //顶点法线坐标结果列表--按面组织好
        List<Float> finalVertexNormalList = new ArrayList<>();

        try {
            InputStream in = resources.getAssets().open("obj/" + fileName);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                String[] lineStrArray = lineStr.split("[ ]+");
                String option = lineStrArray[0].trim();
                if (option.equals("v")) {
                    //此行为顶点坐标，提取出此顶点的XYZ坐标添加到原始顶点坐标列表中
                    vertexList.add(Float.parseFloat(lineStrArray[1]));
                    vertexList.add(Float.parseFloat(lineStrArray[2]));
                    vertexList.add(Float.parseFloat(lineStrArray[3]));
                } else if (option.equals("vt")) {
                    //此行为纹理坐标行，提取ST坐标并添加进原始纹理坐标列表中
                    vertexTextureList.add(Float.parseFloat(lineStrArray[1]));
                    vertexTextureList.add(1 - Float.parseFloat(lineStrArray[2]));
                } else if (option.equals("vn")) {
                    vertexNormalList.add(Float.parseFloat(lineStrArray[1]));
                    vertexNormalList.add(Float.parseFloat(lineStrArray[2]));
                    vertexNormalList.add(Float.parseFloat(lineStrArray[3]));
                } else if (option.equals("f")) {
                    //将顶点坐标组织到结果顶点坐标列表中
                    //计算第0个顶点的索引，并获取此顶点的XYZ三个坐标
                    int index = Integer.parseInt(lineStrArray[1].split("/")[0]) - 1;
                    finalVertexList.add(vertexList.get(3 * index));
                    finalVertexList.add(vertexList.get(3 * index + 1));
                    finalVertexList.add(vertexList.get(3 * index + 2));
                    //计算第1个顶点的索引，并获取此顶点的XYZ三个坐标
                    index = Integer.parseInt(lineStrArray[2].split("/")[0]) - 1;
                    finalVertexList.add(vertexList.get(3 * index));
                    finalVertexList.add(vertexList.get(3 * index + 1));
                    finalVertexList.add(vertexList.get(3 * index + 2));
                    //计算第2个顶点的索引，并获取此顶点的XYZ三个坐标
                    index = Integer.parseInt(lineStrArray[3].split("/")[0]) - 1;
                    finalVertexList.add(vertexList.get(3 * index));
                    finalVertexList.add(vertexList.get(3 * index + 1));
                    finalVertexList.add(vertexList.get(3 * index + 2));

                    //将纹理坐标组织到结果纹理坐标列表中
                    //第0个顶点的纹理坐标
                    int indexTex = Integer.parseInt(lineStrArray[1].split("/")[1]) - 1;
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2));
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2 + 1));
                    //第1个顶点的纹理坐标
                    indexTex = Integer.parseInt(lineStrArray[2].split("/")[1]) - 1;
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2));
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2 + 1));
                    //第2个顶点的纹理坐标
                    indexTex = Integer.parseInt(lineStrArray[3].split("/")[1]) - 1;
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2));
                    finalVertexTextureList.add(vertexTextureList.get(indexTex * 2 + 1));


                    //将法线坐标组织到结果法线坐标列表中
                    //第0个顶点的法线坐标
                    int indexNormal = Integer.parseInt(lineStrArray[1].split("/")[2]) - 1;
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 1));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 2));
                    //第1个顶点的法线坐标
                    indexNormal = Integer.parseInt(lineStrArray[2].split("/")[2]) - 1;
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 1));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 2));
                    //第2个顶点的法线坐标
                    indexNormal = Integer.parseInt(lineStrArray[3].split("/")[2]) - 1;
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 1));
                    finalVertexNormalList.add(vertexNormalList.get(indexNormal * 3 + 2));
                }
            }

            //生成顶点数组
            int size = finalVertexList.size();
            float[] vertexXYZ = new float[size];
            for (int i = 0; i < size; i++) {
                vertexXYZ[i] = finalVertexList.get(i);
            }

            //生成纹理数组
            size = finalVertexTextureList.size();
            float[] textureST = new float[size];
            for (int i = 0; i < size; i++) {
                textureST[i] = finalVertexTextureList.get(i);
            }

            //生成法线数组
            size = finalVertexNormalList.size();
            float[] normalXYZ = new float[size];
            for (int i = 0; i < size; i++) {
                normalXYZ[i] = finalVertexNormalList.get(i);
            }

            //创建3D物体对象
            return new LoadedObject(resources, vertexXYZ, normalXYZ, textureST);
        } catch (Exception e) {
            Log.d("load error", "load error");
            e.printStackTrace();
        }
        return null;
    }
}
