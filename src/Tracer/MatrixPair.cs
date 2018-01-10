using System;
using System.Numerics;

namespace Tracer
{    
    public struct MatrixPair
    {
        private Matrix4x4 _origin;
        private Matrix4x4 _inverse;

        public void Scale(float sx, float sy, float sz)
            => Apply(Matrix4x4.CreateScale(sx, sy, sz));

        public void Translate(float tx, float ty, float tz)
            => Apply(Matrix4x4.CreateTranslation(tx, ty, tz));

        public void Rotate(float theta, float x, float y, float z)
        {
            if (x > 0)
            {
                Apply(Matrix4x4.CreateRotationX(theta));
            }
            if (y > 0)
            {
                Apply(Matrix4x4.CreateRotationY(theta));
            }
            if (z > 0)
            {
                Apply(Matrix4x4.CreateRotationZ(theta));
            }
        }

        public void RotateDeg(float deg, float x, float y, float z)
            => Rotate((float)(Math.PI * deg / 180.0), x, y, z);

        public void ReflectXY()
        {
            Apply(Matrix4x4.CreateReflection(new Plane()));
        }

        private void Apply(Matrix4x4 m)
        {
            _origin = Matrix4x4.Multiply(_origin, m);
            _inverse = Matrix4x4.Multiply(_inverse, m);

            
        }

        public Matrix4x4 Origin => _origin;
        public Matrix4x4 Inverse => _inverse;
    }
}