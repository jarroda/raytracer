using System.Numerics;

namespace Tracer
{    
    public static class Viewing
    {
        public static Matrix4x4 ViewingTransform(
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ)
            => ViewingTransform(
                new Vector3(eyeX, eyeY, eyeZ),
                new Vector3(centerX, centerY, centerZ),
                new Vector3(upX, upY, upZ));

        public static Matrix4x4 ViewingTransform(Vector3 eye, Vector3 center, Vector3 up)
        {
            var f = Vector3.Normalize(center - eye);
            var upp = Vector3.Normalize(up);
            var s = Vector3.Cross(f, upp);
            var u = Vector3.Cross(s, f);
            var sp = Vector3.Normalize(s);
            var uprime = Vector3.Normalize(u);

            var m = Matrix4x4.Identity;
            m.M11 = sp.X;
            m.M12 = sp.Y;
            m.M13 = sp.Z;
            m.M21 = uprime.X;
            m.M22 = uprime.Y;
            m.M23 = uprime.Z;
            m.M31 = f.X;
            m.M32 = f.Y;
            m.M33 = f.Z;

            var t = Matrix.CreateTranslation(-eye.X, -eye.Y, -eye.Z);

            return Matrix4x4.Multiply(m, t);
        }

        public static Matrix4x4 InverseViewingTransform(Matrix4x4 vt, Vector3 eye)
        {
            var m = Matrix4x4.Identity;
            m.M11 = vt.M11;
            m.M12 = vt.M21;
            m.M13 = vt.M31;
            m.M21 = vt.M12;
            m.M22 = vt.M22;
            m.M23 = vt.M32;
            m.M31 = vt.M13;
            m.M32 = vt.M23;
            m.M33 = vt.M33;

            var t = Matrix.CreateTranslation(-eye.X, -eye.Y, -eye.Z);
            
            return Matrix4x4.Multiply(m, t);
        }
    }
}