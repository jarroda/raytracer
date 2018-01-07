public class TestMatrix
{
  public static void main(String[] arg)
  {
    Matrix m = new Matrix();
	 m.body[0][0] = 1;
	 m.body[0][1] = 2;
	 m.body[0][2] = 3;
	 m.body[0][3] = 4;
	 
	 m.body[1][0] = 5;
	 m.body[1][1] = 6;
	 m.body[1][2] = 7;
	 m.body[1][3] = 8;
	 
	 m.body[2][0] = 9;
	 m.body[2][1] = 10;
	 m.body[2][2] = 11;
	 m.body[2][3] = 12;

	 m.body[3][0] = 13;
	 m.body[3][1] = 14;
	 m.body[3][2] = 15;
	 m.body[3][3] = 16;
	 
    m.dump();
	 
	 Matrix t = Matrix.translationMatrix(1, 2, 3);
	 t.dump();
	 
	 Matrix mt = Matrix.multiply(m, t);
	 mt.dump();
	 
//	 m.translate(1, 2, 3);
//	 m.dump();

    Matrix s = Matrix.scaleMatrix(2, 3, 4);
	 s.dump();
	 
	 Matrix ms = Matrix.multiply(m, s);
	 ms.dump();
	 
//	 m.scale(2, 3, 4);
//	 m.dump();
  }
}
