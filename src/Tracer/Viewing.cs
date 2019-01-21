using System.Numerics;

namespace Tracer
{    
    public static class Viewing
    {
        public static MathNet.Numerics.LinearAlgebra.Matrix<double> ViewingTransform(
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ)
            => ViewingTransform(
                new Vector3(eyeX, eyeY, eyeZ),
                new Vector3(centerX, centerY, centerZ),
                new Vector3(upX, upY, upZ));

        public static MathNet.Numerics.LinearAlgebra.Matrix<double> ViewingTransform(Vector3 eye, Vector3 center, Vector3 up)
        {
            var f = Vector3.Normalize(center - eye);
            var upp = Vector3.Normalize(up);
            var s = Vector3.Cross(f, upp);
            var u = Vector3.Cross(s, f);
            var sp = Vector3.Normalize(s);
            var uprime = Vector3.Normalize(u);

            var m = Matrix.Create();
            m[0,0] = sp.X;
            m[0,1] = sp.Y;
            m[0,2] = sp.Z;
            m[1,0] = uprime.X;
            m[1,1] = uprime.Y;
            m[1,2] = uprime.Z;
            m[2,0] = f.X;
            m[2,1] = f.Y;
            m[2,2] = f.Z;

            var t = Matrix.CreateTranslation(-eye.X, -eye.Y, -eye.Z);
            
            return m.Multiply(t);
        }

        public static MathNet.Numerics.LinearAlgebra.Matrix<double> InverseViewingTransform(MathNet.Numerics.LinearAlgebra.Matrix<double> vt, Vector3 eye)
        {
            var m = Matrix.Create();

            for ( int i = 0; i < 3; i++ )
                for ( int j = 0; j < 3; j++ )
                    m[i,j] = vt[j,i];

            var t = Matrix.CreateTranslation(-eye.X, -eye.Y, -eye.Z);
            
            return m.Multiply(t);
        }
    }
}