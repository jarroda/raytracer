

using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public static class Viewing
    {
        public static Matrix<double> ViewingTransform(
            double eyeX, double eyeY, double eyeZ,
            double centerX, double centerY, double centerZ,
            double upX, double upY, double upZ)
            => ViewingTransform(
                Vector.CreateVector3(eyeX, eyeY, eyeZ),
                Vector.CreateVector3(centerX, centerY, centerZ),
                Vector.CreateVector3(upX, upY, upZ));

        public static Matrix<double> ViewingTransform(Vector<double> eye, Vector<double> center, Vector<double> up)
        {
            var f = center.Subtract(eye).Normalize();
            var upp = up.Normalize();
            var s = f.CrossProduct(upp);
            var u = s.CrossProduct(f);
            var sp = s.Normalize();
            var uprime = u.Normalize();

            var m = Matrix.Create();
            m[0,0] = sp[0];
            m[0,1] = sp[1];
            m[0,2] = sp[2];
            m[1,0] = uprime[0];
            m[1,1] = uprime[1];
            m[1,2] = uprime[2];
            m[2,0] = f[0];
            m[2,1] = f[1];
            m[2,2] = f[2];

            var t = Matrix.CreateTranslation(-eye[0], -eye[1], -eye[2]);
            
            return m.Multiply(t);
        }

        public static Matrix<double> InverseViewingTransform(Matrix<double> vt, Vector<double> eye)
        {
            var m = Matrix.Create();

            for ( int i = 0; i < 3; i++ )
                for ( int j = 0; j < 3; j++ )
                    m[i,j] = vt[j,i];

            var t = Matrix.CreateTranslation(-eye[0], -eye[1], -eye[2]);
            
            return m.Multiply(t);
        }
    }
}