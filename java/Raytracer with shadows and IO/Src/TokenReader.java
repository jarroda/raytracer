import java.io.*;

public class TokenReader
{
  public static final int tokError = 0;
  public static final int tokViewport = 100;
  public static final int tokWindow = 101;
  public static final int tokViewing = 102;
  public static final int tokEndViewing = 103;
  public static final int tokAmbientLight = 104;
  public static final int tokPointLight = 105;
  public static final int tokEndPointLight = 106;
  public static final int tokSphere = 107;
  public static final int tokEndSphere = 108;
  public static final int tokCone = 109;
  public static final int tokEndCone = 110;
  public static final int tokPlane = 111;
  public static final int tokEndPlane = 112;
  public static final int tokCheckerboard = 113;
  public static final int tokEndCheckerboard = 114;
  public static final int tokDistance = 115;
  public static final int tokEye = 116;
  public static final int tokCenter = 117;
  public static final int tokUp = 118;
  public static final int tokColor = 119;
  public static final int tokLocation = 120;
  public static final int tokBaseColor = 121;
  public static final int tokName = 122;
  public static final int tokCheckColor = 123;
  public static final int tokTranslate = 124;
  public static final int tokScale = 125;
  public static final int tokRotate = 126;
  public static final int tokReflectXY = 127;
  public static final int tokReflectXZ = 128;
  public static final int tokReflectYZ = 129;
  public static final int tokReflectOrigin = 130;
  public static final int tokShearX = 131;
  public static final int tokShearY = 132;
  public static final int tokShearZ = 133;
  public static final int tokEndScene = 134;
  public static final int tokShininess = 135;
  public static final int tokSpecularColor = 136;
  public static final int tokReflectivity = 137;
  public static final int tokCylinder = 138;
  public static final int tokEndCylinder = 139;
  public static final int tokDisk = 140;
  public static final int tokEndDisk = 141;
  public static final int tokInfiniteCylinder = 142;
  public static final int tokEndInfiniteCylinder = 143;
  public static final int tokTriangle = 144;
  public static final int tokEndTriangle = 145;
  public static final int tokVertexA = 146;
  public static final int tokVertexB = 147;
  public static final int tokVertexC = 148;
  public static final int tokRadius = 150;

  public StreamTokenizer tokenizer;

  TokenReader(String fileName)
  {
    try
    {
      tokenizer = new StreamTokenizer(new BufferedReader(new FileReader(fileName)));
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found: " + fileName);
      System.exit(1);
    }
  }

  public int nextType() {
    int result;

    try {
      result = tokenizer.nextToken();
      tokenizer.pushBack();
      return result;
    }
    catch(IOException e) {
      System.out.println("Tokenizer error: " + tokenizer.sval);
      System.exit(1);
      return 0;
    }
  }

  public boolean isNumber() {
    return nextType() == tokenizer.TT_NUMBER;
  }

  public boolean isString() {
    return nextType() == tokenizer.TT_WORD;
  }

  public boolean isEOF() {
    return nextType() == tokenizer.TT_EOF;
  }

  public int readInt()
  {
    try
    {
      if (tokenizer.nextToken() == tokenizer.TT_NUMBER)
        return (int)tokenizer.nval;
      else {
        System.out.println("Incorrect input (int): " + tokenizer.sval);
        System.exit(1);
        return 0;
      }
    }
    catch(IOException e) {
      System.out.println(e);
      System.exit(1);
      return 0;
    }
  }

  public double readDouble()
  {
    try
    {
      if (tokenizer.nextToken() == tokenizer.TT_NUMBER)
        return tokenizer.nval;
      else {
        System.out.println("Incorrect input (double): " + tokenizer.sval);
        System.exit(1);
        return 0;
      }
    }
    catch(IOException e) {
      System.out.println(e);
      System.exit(1);
      return 0;
    }
  }

  public String readString()
  {
    try
    {
      if (tokenizer.nextToken() == tokenizer.TT_WORD)
        return tokenizer.sval;
      else {
        System.out.println("Incorrect input (string): " + tokenizer.sval);
        System.exit(1);
        return "";
      }       
    }
    catch(IOException e) {
      System.out.println(e);
      System.exit(1);
      return "";
    }   
  }

  public int readRayToken()
  {
    String tokenName = readString();

    if ( tokenName.equalsIgnoreCase("Viewport") )
      return tokViewport;
    else if ( tokenName.equalsIgnoreCase("Window") )
      return tokWindow;
    else if ( tokenName.equalsIgnoreCase("Viewing") )
      return tokViewing;
    else if ( tokenName.equalsIgnoreCase("EndViewing") )
      return tokEndViewing;
    else if ( tokenName.equalsIgnoreCase("AmbientLight") )
      return tokAmbientLight;
    else if ( tokenName.equalsIgnoreCase("PointLight") )
      return tokPointLight;
    else if ( tokenName.equalsIgnoreCase("EndPointLight") )
      return tokEndPointLight;
    else if ( tokenName.equalsIgnoreCase("Sphere") )
      return tokSphere;
    else if ( tokenName.equalsIgnoreCase("EndSphere") )
      return tokEndSphere;
    else if ( tokenName.equalsIgnoreCase("Cone") )
      return tokCone;
    else if ( tokenName.equalsIgnoreCase("EndCone") )
      return tokEndCone;
    else if ( tokenName.equalsIgnoreCase("Plane") )
      return tokPlane;
    else if ( tokenName.equalsIgnoreCase("EndPlane") )
      return tokEndPlane;
    else if ( tokenName.equalsIgnoreCase("Checkerboard") )
      return tokCheckerboard;
    else if ( tokenName.equalsIgnoreCase("EndCheckerboard") )
      return tokEndCheckerboard;
    else if ( tokenName.equalsIgnoreCase("Distance") )
      return tokDistance;
    else if ( tokenName.equalsIgnoreCase("Eye") )
      return tokEye;
    else if ( tokenName.equalsIgnoreCase("Center") )
      return tokCenter;
    else if ( tokenName.equalsIgnoreCase("Up") )
      return tokUp;
    else if ( tokenName.equalsIgnoreCase("Color") )
      return tokColor;
    else if ( tokenName.equalsIgnoreCase("Location") )
      return tokLocation;
    else if ( tokenName.equalsIgnoreCase("BaseColor") )
      return tokBaseColor;
    else if ( tokenName.equalsIgnoreCase("Name") )
      return tokName;
    else if ( tokenName.equalsIgnoreCase("CheckColor") )
      return tokCheckColor;
    else if ( tokenName.equalsIgnoreCase("Translate") )
      return tokTranslate;
    else if ( tokenName.equalsIgnoreCase("Scale") )
      return tokScale;
     else if ( tokenName.equalsIgnoreCase("Rotate") )
      return tokRotate;
    else if ( tokenName.equalsIgnoreCase("ReflectXY") )
      return tokReflectXY;
    else if ( tokenName.equalsIgnoreCase("ReflectXZ") )
      return tokReflectXZ;
    else if ( tokenName.equalsIgnoreCase("ReflectYZ") )
      return tokReflectYZ;
    else if ( tokenName.equalsIgnoreCase("ReflectOrigin") )
      return tokReflectOrigin;
    else if ( tokenName.equalsIgnoreCase("ShearX") )
      return tokShearX;
    else if ( tokenName.equalsIgnoreCase("ShearY") )
      return tokShearY;
    else if ( tokenName.equalsIgnoreCase("ShearZ") )
      return tokShearZ;
    else if ( tokenName.equalsIgnoreCase("EndScene") )
      return tokEndScene;
    else if ( tokenName.equalsIgnoreCase("Shininess") )
      return tokShininess;
    else if ( tokenName.equalsIgnoreCase("SpecularColor") )
      return tokSpecularColor;
    else if ( tokenName.equalsIgnoreCase("Reflectivity") )
      return tokReflectivity;
	 else if ( tokenName.equalsIgnoreCase("Cylinder") )
	   return tokCylinder;
	 else if ( tokenName.equalsIgnoreCase("EndCylinder") )
	   return tokEndCylinder;	
	 else if ( tokenName.equalsIgnoreCase("Disk") )
	   return tokDisk;	
	 else if ( tokenName.equalsIgnoreCase("EndDisk") )
	   return tokEndDisk;	
	 else if ( tokenName.equalsIgnoreCase("InfiniteCylinder") )
	   return tokInfiniteCylinder;	
	 else if ( tokenName.equalsIgnoreCase("EndInfiniteCylinder") )
	   return tokEndInfiniteCylinder;	
	 else if ( tokenName.equalsIgnoreCase("Triangle") )
	   return tokTriangle;	
	 else if ( tokenName.equalsIgnoreCase("EndTriangle") )
	   return tokEndTriangle;	
	 else if ( tokenName.equalsIgnoreCase("VertexA") )
	   return tokVertexA;
	 else if ( tokenName.equalsIgnoreCase("VertexB") )
	   return tokVertexB;
	 else if ( tokenName.equalsIgnoreCase("VertexC") )
	   return tokVertexC;
	 else if ( tokenName.equalsIgnoreCase("Radius") )
	   return tokRadius;
    else
        return tokError;
 }

}
